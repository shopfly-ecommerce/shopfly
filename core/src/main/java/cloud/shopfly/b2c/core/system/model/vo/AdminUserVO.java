/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;


/**
 * 平台管理员实体VO
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
public class AdminUserVO {

    /**
     * 管理员名称
     */
    @Pattern(regexp = "^(?![0-9]+$)[\\u4e00-\\u9fa5_0-9A-Za-z]{2,20}$", message = "名称不能为纯数字和特殊字符，并且长度为2-20个字符")
    @ApiModelProperty(name = "username", value = "管理员名称", required = false)
    private String username;
    /**
     * 管理员密码
     */
    @ApiModelProperty(name = "password", value = "管理员密码", required = false)
    private String password;
    /**
     * 部门
     */
    @ApiModelProperty(name = "department", value = "部门", required = false)
    private String department;
    /**
     * 权限id
     */
    @Min(message = "权限id必须为数字", value = 0)
    @NotNull(message = "权限不能为空")
    @ApiModelProperty(name = "role_id", value = "权限id", required = false)
    private Integer roleId;
    /**
     * 备注
     */
    @Size(max = 90, message = "备注最大为90个字符")
    @ApiModelProperty(name = "remark", value = "备注", required = false)
    private String remark;
    /**
     * 管理员真实姓名
     */
    @Pattern(regexp = "^(?![0-9]+$)[\\u4e00-\\u9fa5_0-9A-Za-z]{2,20}$", message = "真实姓名不能为纯数字和特殊字符，并且长度为2-20个字符")
    @ApiModelProperty(name = "real_name", value = "管理员真实姓名", required = false)
    private String realName;
    /**
     * 头像
     */
    @ApiModelProperty(name = "face", value = "头像", required = false)
    private String face;
    /**
     * 是否为超级管理员
     */
    @Min(message = "必须为数字且,1为超级管理员,0为其他", value = 0)
    @Max(message = "必须为数字且,1为超级管理员,0为其他", value = 1)
    @NotNull(message = "是否为超级管理员不能为空")
    @ApiModelProperty(name = "founder", value = "是否为超级管理员,1为超级管理员,0为其他", required = false)
    private Integer founder;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        AdminUserVO that = (AdminUserVO) o;
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
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) {
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
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (face != null ? face.hashCode() : 0);
        result = 31 * result + (founder != null ? founder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdminUserVO{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", department='" + department + '\'' +
                ", roleId=" + roleId +
                ", remark='" + remark + '\'' +
                ", realName='" + realName + '\'' +
                ", face='" + face + '\'' +
                ", founder=" + founder +
                '}';
    }


}