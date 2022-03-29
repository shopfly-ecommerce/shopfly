/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.vo;

/**
 * 管理员登录vo
 *
 * @author zh
 * @version v7.0
 * @date 18/6/27 上午9:28
 * @since v7.0
 */

public class AdminLoginVO {
    /**
     * 平台管理员id
     */
    private Integer uid;
    /**
     * 管理员名称
     */
    private String username;
    /**
     * 部门
     */
    private String department;
    /**
     * 头像
     */
    private String face;

    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 是否是超级管理员
     */
    private Integer founder;
    /**
     * token
     */
    private String accessToken;
    /**
     * 刷新token
     */
    private String refreshToken;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getFounder() {
        return founder;
    }

    public void setFounder(Integer founder) {
        this.founder = founder;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AdminLoginVO{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", department='" + department + '\'' +
                ", face='" + face + '\'' +
                ", roleId=" + roleId +
                ", founder=" + founder +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
