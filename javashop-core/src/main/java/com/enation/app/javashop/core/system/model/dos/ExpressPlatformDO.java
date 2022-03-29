/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.model.dos;

import com.enation.app.javashop.core.system.model.vo.ExpressPlatformVO;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 快递平台实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-11 14:42:50
 */
@Table(name = "es_express_platform")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExpressPlatformDO implements Serializable {

    private static final long serialVersionUID = 9536539335454134L;

    /**
     * 快递平台id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 快递平台名称
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "快递平台名称", required = false)
    private String name;
    /**
     * 是否开启快递平台,1开启，0未开启
     */
    @Column(name = "open")
    @ApiModelProperty(name = "open", value = "是否开启快递平台,1开启，0未开启", required = false)
    private Integer open;
    /**
     * 快递平台配置
     */
    @Column(name = "config")
    @ApiModelProperty(name = "config", value = "快递平台配置", required = false)
    private String config;
    /**
     * 快递平台beanid
     */
    @Column(name = "bean")
    @ApiModelProperty(name = "bean", value = "快递平台beanid", required = false)
    private String bean;

    public ExpressPlatformDO(ExpressPlatformVO expressPlatformVO) {
        this.id = expressPlatformVO.getId();
        this.name = expressPlatformVO.getName();
        this.open = expressPlatformVO.getOpen();
        this.bean = expressPlatformVO.getBean();
        Gson gson = new Gson();
        this.config = gson.toJson(expressPlatformVO.getConfigItems());
    }

    public ExpressPlatformDO() {

    }

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExpressPlatformDO that = (ExpressPlatformDO) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (open != null ? !open.equals(that.open) : that.open != null) {
            return false;
        }
        if (config != null ? !config.equals(that.config) : that.config != null) {
            return false;
        }
        return bean != null ? bean.equals(that.bean) : that.bean == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (open != null ? open.hashCode() : 0);
        result = 31 * result + (config != null ? config.hashCode() : 0);
        result = 31 * result + (bean != null ? bean.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExpressPlatformDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", config='" + config + '\'' +
                ", bean='" + bean + '\'' +
                '}';
    }


}