/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.security.model;

/**
 * 管理员角色
 *
 * @author zh
 * @version v7.0
 * @date 18/6/27 上午10:09
 * @since v7.0
 */

public class Admin extends User{

    /**
     * 是否是超级管理员
     */
    private Integer founder;
    /**
     * 权限
     */
    private String role;


    public Integer getFounder() {
        return founder;
    }

    public void setFounder(Integer founder) {
        this.founder = founder;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "Admin{" +
                ", founder=" + founder +
                ", role='" + role + '\'' +
                '}';
    }
}
