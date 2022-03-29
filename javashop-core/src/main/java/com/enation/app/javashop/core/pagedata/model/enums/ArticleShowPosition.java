/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.pagedata.model.enums;

/**
 * @author fk
 * @version v1.0
 * @Description: 文章显示位置
 * @date 2018/5/21 16:05
 * @since v7.0.0
 */
public enum ArticleShowPosition {

    /**
     * 注册协议
     */
    REGISTRATION_AGREEMENT("注册协议"),
    /**
     * 入驻协议
     */
    COOPERATION_AGREEMENT("入驻协议"),
    /**
     * 平台联系方式
     */
    CONTACT_INFORMATION("平台联系方式"),

    /**
     * 团购活动协议
     */
    GROUP_BUY_AGREEMENT("团购活动协议"),


    /**
     * 其他
     */
    OTHER("其他");

    private String description;

    ArticleShowPosition(String description) {
        this.description = description;

    }
    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
