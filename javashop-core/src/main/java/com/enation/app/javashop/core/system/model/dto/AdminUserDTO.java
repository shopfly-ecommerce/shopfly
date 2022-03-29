/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.model.dto;

import com.enation.app.javashop.core.system.model.dos.AdminUser;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管理员对象 用于管理员列表显示
 *
 * @author zh
 * @version v7.0
 * @date 18/6/27 下午2:42
 * @since v7.0
 */

public class AdminUserDTO extends AdminUser {

    @ApiModelProperty(name = "role_name", value = "角色名称", required = false)
    private String roleName;

    public String getRoleName() {
        if (this.getFounder().equals(1)) {
            roleName = "超级管理员";
        }
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    @Override
    public String toString() {
        return "AdminUserDTO{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
