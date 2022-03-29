/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.model.vo;

import java.util.List;

/**
 * 配置文件映射实体
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017年8月15日 上午11:35:42
 */
public class ConfigItem {
    /**
     * 配置文件name值
     */
    private String name;
    /**
     * 配置文件name映射文本值
     */
    private String text;
    /**
     * 配置文件显示在浏览器时，input的type属性
     */
    private String type;
    /**
     * 配置的值
     */
    private Object value;
    /**
     * 如果是select 是需要将可选项传递到前天
     */
    private List<RadioOption> options;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<RadioOption> getOptions() {
        return options;
    }

    public void setOptions(List<RadioOption> options) {
        this.options = options;
    }
}
