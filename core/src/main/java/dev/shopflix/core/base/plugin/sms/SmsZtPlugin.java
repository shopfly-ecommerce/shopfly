/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.plugin.sms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import dev.shopflix.core.base.model.vo.ConfigItem;
import dev.shopflix.framework.logs.Debugger;

import dev.shopflix.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 助通短信 插件
 *
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2018年3月25日 下午2:42:20
 */
@Component
public class SmsZtPlugin implements SmsPlatform {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private Debugger debugger;

    @Override
    public boolean onSend(String phone, String content, Map param) {

        try {
            if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(content) || CollUtil.isEmpty(param)) {
                logger.error("发送短信参数异常,请检查phone="+phone+" ,content="+content+" ,param= "+param);
                return false;
            }
            debugger.log("调起SmsZtPlugin", "参数为：", param.toString());
            String urls = "https://api.mix2.zthysms.com/v2/sendSms";
            long tKey = System.currentTimeMillis() / 1000;
            //用户
            param.put("username", param.get("name"));
            param.remove("name");
            //密码
            param.put("password", SecureUtil.md5(SecureUtil.md5(param.get("password").toString()) + tKey));
            //tKey
            param.put("tKey", tKey + "");
            //手机号
            param.put("mobile", phone);
            //内容
            param.put("content", content);

            debugger.log("向ztsms.cn发出请求，请求url为：", urls);
            // 返回发送结果
            String result = HttpRequest.post(urls)
                    .header(Header.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(JSONUtil.toJsonStr(param))
                    .timeout(60000)
                    .execute()
                    .body();
            debugger.log("收到返回结果：", result);
            JSONObject jsonObject = JSONUtil.parseObj(result);
            if (!StringUtil.equals(jsonObject.get("code").toString(), "200")) {
                logger.error("发送短信异常" + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("发送短信异常", e);
        }
        return false;
    }

    @Override
    public String getPluginId() {
        return "smsZtPlugin";
    }


    @Override
    public String getPluginName() {
        return "助通网关短信";
    }


    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList();


        ConfigItem name = new ConfigItem();
        name.setType("text");
        name.setName("name");
        name.setText("用户名");

        ConfigItem password = new ConfigItem();
        password.setType("text");
        password.setName("password");
        password.setText("密码");

        ConfigItem id = new ConfigItem();
        id.setType("text");
        id.setName("id");
        id.setText("产品id");

        ConfigItem trumpet = new ConfigItem();
        trumpet.setType("text");
        trumpet.setName("trumpet");
        trumpet.setText("扩展的小号（没有请留空）");

        list.add(name);
        list.add(password);
        list.add(id);
        list.add(trumpet);

        return list;
    }


    @Override
    public Integer getIsOpen() {
        return 0;
    }

}
