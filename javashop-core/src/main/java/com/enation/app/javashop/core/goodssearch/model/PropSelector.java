/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goodssearch.model;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 参数属性选择器
 * @date 2018/8/16 9:23
 * @since v7.0.0
 */
public class PropSelector {

    private String key;

    private List<SearchSelector> value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<SearchSelector> getValue() {
        return value;
    }

    public void setValue(List<SearchSelector> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PropSelector{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
