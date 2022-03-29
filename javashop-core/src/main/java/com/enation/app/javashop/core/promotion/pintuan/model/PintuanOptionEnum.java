/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.pintuan.model;

/**
 *
 * 拼团操作枚举值
 * @author liushuai
 * @version v1.0
 * @since v7.0
 * 2019/2/26 上午10:40
 * @Description:
 *
 */

public enum PintuanOptionEnum {


    /**
     * 拼团操作枚举值
     */
    CAN_OPEN ("可以开启"),

    CAN_CLOSE("可以关闭"),

    NOTHING("没有什么可以操作的");

    private String name;

    PintuanOptionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
