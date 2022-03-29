/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.model;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.enation.app.javashop.framework.util.DateUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 部署实体
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-04-23 14:40:27
 */
@Table(name="es_deploy")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Deploy implements Serializable {

    private static final long serialVersionUID = 7962497529274547L;

    /**主键*/
    @Id(name = "deploy_id")
    @ApiModelProperty(hidden=true)
    private Integer deployId;
    /**部署名称*/
    @Column(name = "deploy_name")
    @NotEmpty(message="部署名称不能为空")
    @ApiModelProperty(name="deploy_name",value="部署名称",required=true)
    private String deployName;
    /**备注*/
    @Column(name = "remark")
    @ApiModelProperty(name="remark",value="备注",required=false)
    private String remark;
    /**安装类型*/
    @Column(name = "deploy_type")
    @NotEmpty(message="安装类型不能为空")
    @ApiModelProperty(name="deploy_type",value="安装类型",required=true)
    private String deployType;
    /**管理员账号*/
    @Column(name = "admin_name")
    @NotEmpty(message="管理员账号不能为空")
    @ApiModelProperty(name="admin_name",value="管理员账号",required=true)
    private String adminName;
    /**管理员密码*/
    @Column(name = "admin_pwd")
    @NotEmpty(message="管理员密码不能为空")
    @ApiModelProperty(name="admin_pwd",value="管理员密码",required=true)
    private String adminPwd;
    /**创建时间*/
    @Column(name = "create_time")
    @ApiModelProperty(name="create_time",value="创建时间",required=false)
    private Long createTime;

    private String createTimeText;

    public String getCreateTimeText() {
        if (createTime!=null) {
            createTimeText = DateUtil.toString(createTime, "yyyy-MM-dd HH:MM:ss");
            return createTimeText;
        }
        return "";

    }

    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }

    @PrimaryKeyField
    public Integer getDeployId() {
        return deployId;
    }
    public void setDeployId(Integer deployId) {
        this.deployId = deployId;
    }

    public String getDeployName() {
        return deployName;
    }
    public void setDeployName(String deployName) {
        this.deployName = deployName;
    }

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeployType() {
        return deployType;
    }
    public void setDeployType(String deployType) {
        this.deployType = deployType;
    }

    public String getAdminName() {
        return adminName;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPwd() {
        return adminPwd;
    }
    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Deploy that = (Deploy) o;
        if (deployId != null ? !deployId.equals(that.deployId) : that.deployId != null) {return false;}
        if (deployName != null ? !deployName.equals(that.deployName) : that.deployName != null) {return false;}
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) {return false;}
        if (deployType != null ? !deployType.equals(that.deployType) : that.deployType != null) {return false;}
        if (adminName != null ? !adminName.equals(that.adminName) : that.adminName != null) {return false;}
        if (adminPwd != null ? !adminPwd.equals(that.adminPwd) : that.adminPwd != null) {return false;}
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {return false;}
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (deployId != null ? deployId.hashCode() : 0);
        result = 31 * result + (deployName != null ? deployName.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (deployType != null ? deployType.hashCode() : 0);
        result = 31 * result + (adminName != null ? adminName.hashCode() : 0);
        result = 31 * result + (adminPwd != null ? adminPwd.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Deploy{" +
                "deployId=" + deployId +
                ", deployName='" + deployName + '\'' +
                ", remark='" + remark + '\'' +
                ", deployType='" + deployType + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPwd='" + adminPwd + '\'' +
                ", createTime=" + createTime +
                '}';
    }

}