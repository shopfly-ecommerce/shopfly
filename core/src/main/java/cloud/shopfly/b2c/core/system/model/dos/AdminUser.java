/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 平台管理员实体
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
@Table(name = "es_admin_user")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 9076352194131639L;

    /**
     * 平台管理员id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = false)
    private Integer id;
    /**
     * 管理员名称
     */
    @Column(name = "username")
    @ApiModelProperty(name = "username", value = "管理员名称", required = false)
    private String username;
    /**
     * 管理员密码
     */
    @Column(name = "password")
    @ApiModelProperty(name = "password", value = "管理员密码", required = false)
    private String password;
    /**
     * 部门
     */
    @Column(name = "department")
    @ApiModelProperty(name = "department", value = "部门", required = false)
    private String department;
    /**
     * 权限id
     */
    @Column(name = "role_id")
    @ApiModelProperty(name = "role_id", value = "权限id", required = false)
    private Integer roleId;
    /**
     * 创建日期
     */
    @Column(name = "date_line")
    @ApiModelProperty(name = "date_line", value = "创建日期", required = false)
    private Long dateLine;
    /**
     * 备注
     */
    @Column(name = "remark")
    @ApiModelProperty(name = "remark", value = "备注", required = false)
    private String remark;
    /**
     * 是否删除
     */
    @Column(name = "user_state")
    @ApiModelProperty(name = "user_state", value = "是否删除,0为正常,-1为删除状态", required = false)
    private Integer userState;
    /**
     * 管理员真实姓名
     */
    @Column(name = "real_name")
    @ApiModelProperty(name = "real_name", value = "管理员真实姓名", required = false)
    private String realName;
    /**
     * 头像
     */
    @Column(name = "face")
    @ApiModelProperty(name = "face", value = "头像", required = false)
    private String face;
    /**
     * 是否为超级管理员
     */
    @Column(name = "founder")
    @ApiModelProperty(name = "founder", value = "是否为超级管理员,1为超级管理员,0为其他", required = false)
    private Integer founder;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Long getDateLine() {
        return dateLine;
    }

    public void setDateLine(Long dateLine) {
        this.dateLine = dateLine;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getFounder() {
        return founder;
    }

    public void setFounder(Integer founder) {
        this.founder = founder;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdminUser that = (AdminUser) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (username != null ? !username.equals(that.username) : that.username != null) {
            return false;
        }
        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }
        if (department != null ? !department.equals(that.department) : that.department != null) {
            return false;
        }
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) {
            return false;
        }
        if (dateLine != null ? !dateLine.equals(that.dateLine) : that.dateLine != null) {
            return false;
        }
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) {
            return false;
        }
        if (userState != null ? !userState.equals(that.userState) : that.userState != null) {
            return false;
        }
        if (realName != null ? !realName.equals(that.realName) : that.realName != null) {
            return false;
        }
        if (face != null ? !face.equals(that.face) : that.face != null) {
            return false;
        }
        return founder != null ? founder.equals(that.founder) : that.founder == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (dateLine != null ? dateLine.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (userState != null ? userState.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (face != null ? face.hashCode() : 0);
        result = 31 * result + (founder != null ? founder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdminUserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", department='" + department + '\'' +
                ", roleId=" + roleId +
                ", dateLine=" + dateLine +
                ", remark='" + remark + '\'' +
                ", userState=" + userState +
                ", realName='" + realName + '\'' +
                ", face='" + face + '\'' +
                ", founder=" + founder +
                '}';
    }


}