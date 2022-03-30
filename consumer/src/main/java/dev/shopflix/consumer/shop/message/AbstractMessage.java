/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.message;

import com.enation.app.javashop.core.base.SettingGroup;
import com.enation.app.javashop.core.client.system.SettingClient;
import com.enation.app.javashop.core.system.model.vo.InformationSetting;
import com.enation.app.javashop.core.system.model.vo.SiteSetting;
import com.enation.app.javashop.framework.util.JsonUtil;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息设置获取公有方法
 *
 * @author zh
 * @version v7.0
 * @date 19/7/31 下午2:13
 * @since v7.0
 */

@Component
public class AbstractMessage {

    @Autowired
    private SettingClient settingClient;

    /**
     * 获取站点设置
     *
     * @return
     */
    protected SiteSetting getSiteSetting() {
        String siteSettingJson = settingClient.get(SettingGroup.SITE);
        //获取系统配置
        return JsonUtil.jsonToObject(siteSettingJson, SiteSetting.class);
    }

    /**
     * 获取平台联系方式
     *
     * @return
     */
    protected InformationSetting getInfoSetting() {
        //系统联系方式
        String infoSettingJson = settingClient.get(SettingGroup.INFO);
        return JsonUtil.jsonToObject(infoSettingJson, InformationSetting.class);
    }

    /**
     * 替换中的内容
     *
     * @param content
     * @param map     替换的文本内容
     * @return
     */
    protected String replaceContent(String content, Map map) {
        StrSubstitutor strSubstitutor = new StrSubstitutor(map);
        return strSubstitutor.replace(content);
    }

}
