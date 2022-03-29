/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.enums;

/**
 *
 * Javashop业务类型
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/4/24
 */
public enum ServiceType {

    /**
     * 会员
     */
    MEMBER("会员"),
    /**
     * 商品
     */
    GOODS("商品"),

    /**
     * 交易
     */
    TRADE("交易"),

    /**
     * 系统
     */
    SYSTEM("系统"),

    /**
     * 统计
     */
    STATISTICS("统计"),

    /**
     * 分销
     */
    DISTRIBUTION("分销"),

    /**
     * xxl-job
     */
    XXL_JOB("xxl-job");

    /**
     * 类型名称
     */
    private String typeName;


    ServiceType(String typeName){
        this.typeName = typeName;
    }


    /**
     * 获取类型名称
     * @return
     */
    public String getTypeName(){
        return  this.typeName;
    }

}
