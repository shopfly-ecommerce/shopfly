/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.base.service.impl;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.base.model.vo.SmsSendVO;
import cloud.shopfly.b2c.core.base.plugin.sms.SmsPlatform;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.base.service.SmsManager;
import cloud.shopfly.b2c.core.client.system.MessageTemplateClient;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.member.model.RandomCreate;
import cloud.shopfly.b2c.core.system.enums.MessageCodeEnum;
import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.core.system.model.vo.SmsPlatformVO;
import cloud.shopfly.b2c.core.system.service.SmsPlatformManager;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ApplicationContextHolder;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手机短信实现
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018年3月19日 下午4:01:49
 */
@Service
public class SmsManagerImpl implements SmsManager {


    @Autowired
    private Cache cache;

    @Autowired
    private ShopflyConfig shopflyConfig;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private MessageTemplateClient messageTemplateClient;

    @Autowired
    private SettingClient settingClient;

    @Autowired
    private SmsPlatformManager smsPlatformManager;

    @Autowired
    private Debugger debugger;

    protected static final Log LOGER = LogFactory.getLog(SmsManagerImpl.class);


    @Override
    public void send(SmsSendVO smsSendVO) {

        SmsPlatformVO platformVO = smsPlatformManager.getOpen();
        debugger.log("找到短信插件:",platformVO.toString());
        if (platformVO != null) {
            SmsPlatform sendEvent = (SmsPlatform) ApplicationContextHolder.getBean(platformVO.getBean());
            sendEvent.onSend(smsSendVO.getMobile(), smsSendVO.getContent(), this.getConfig(platformVO.getConfig()));
        }
        LOGER.debug("已发送短信:短信内容为:" + smsSendVO.getContent() + "手机号:" + smsSendVO.getMobile());
    }

    @Override
    public boolean valid(String scene, String mobile, String code) {
        //从传入参数组织key
        String valCode = CachePrefix.SMS_CODE.getPrefix() + scene + "_" + mobile;
        //redis中获取验证码
        Object obj = cache.get(valCode);
        if (obj != null && obj.equals(code)) {
            //验证码校验通过后清除缓存
            cache.remove(valCode);
            //将标识放入缓存中，在验证验证码正确后，下一步操作需要校验是否经过验证验证码(缓存中是否存在)
            cache.put(CachePrefix.MOBILE_VALIDATE.getPrefix() + scene + "_" + mobile, mobile, shopflyConfig.getCaptchaTimout());
            return true;
        }
        return false;
    }

    @Override
    public void record(String scene, String mobile, String code) {
        cache.put(CachePrefix.SMS_CODE.getPrefix() + scene + "_" + mobile, code, shopflyConfig.getSmscodeTimout());
    }

    @Override
    public void sendSmsMessage(String byName, String mobile, SceneType sceneType) {
        // 随机生成的动态码
        String dynamicCode = "";

        MessageTemplateDO template = messageTemplateClient.getModel(MessageCodeEnum.MOBILECODESEND);

        String siteSettingJson = settingClient.get(SettingGroup.SITE);

        SiteSetting siteSetting = JsonUtil.jsonToObject(siteSettingJson, SiteSetting.class);

        if (siteSetting.getTestMode().equals(1)) {
            dynamicCode = "1111";
        } else {
            dynamicCode = RandomCreate.getRandomCode();
        }
        String smsContent = template.getSmsContent();
        Map<String, Object> valuesMap = new HashMap<String, Object>(4);
        valuesMap.put("byName", byName);
        valuesMap.put("code", dynamicCode);
        valuesMap.put("siteName", siteSetting.getSiteName());
        StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
        String replace = strSubstitutor.replace(smsContent);

        SmsSendVO smsSendVO = new SmsSendVO();
        smsSendVO.setContent(replace);
        smsSendVO.setMobile(mobile);
        //发送短信验证码
        this.messageSender.send(new MqMessage(AmqpExchange.SEND_MESSAGE, AmqpExchange.SEND_MESSAGE + "_QUEUE",
                smsSendVO));

        //缓存中记录验证码
        this.record(sceneType.name(), mobile, dynamicCode);
    }

    /**
     * 将json参数转换为map格式
     *
     * @param config 短信参数config
     * @return 短信参数
     */
    private Map getConfig(String config) {
        if (StringUtil.isEmpty(config)) {
            return new HashMap<>(16);
        }
        Gson gson = new Gson();
        List<ConfigItem> list = gson.fromJson(config, new TypeToken<List<ConfigItem>>() {
        }.getType());
        Map<String, String> result = new HashMap<>(16);
        if (list != null) {
            for (ConfigItem item : list) {
                result.put(item.getName(), StringUtil.toString(item.getValue()));
            }
        }
        return result;

    }
}
