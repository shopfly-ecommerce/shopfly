/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.vo;

import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录参数VO
 * @ClassName ConnectSettingParametersVO
 * @since v7.0 下午7:56 2018/6/28
 */
public class ConnectSettingParametersVO {
    private String name;
    private List<ConnectSettingConfigItem> configList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConnectSettingConfigItem> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ConnectSettingConfigItem> configList) {
        this.configList = configList;
    }

    @Override
    public String toString() {
        return "ConnectSettingParametersVO{" +
                "name='" + name + '\'' +
                ", configList=" + configList +
                '}';
    }
}
