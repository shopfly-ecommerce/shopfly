/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.vo;

import dev.shopflix.core.system.model.dos.RoleDO;
import dev.shopflix.framework.util.JsonUtil;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 权限vo
 *
 * @author zh
 * @version v7.0
 * @date 18/6/25 上午10:59
 * @since v7.0
 */

public class RoleVO {

    @ApiModelProperty(hidden = true)
    private Integer roleId;
    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称不能为空")
    @ApiModelProperty(name = "role_name", value = "角色名称", required = true)
    private String roleName;
    /**
     * 角色描述
     */
    @ApiModelProperty(name = "role_describe", value = "角色描述", required = true)
    private String roleDescribe;

    /**
     * 角色所拥有的菜单权限
     */
    @ApiModelProperty(name = "menus", value = "角色所拥有的菜单权限", required = true)
    private List<Menus> menus;

    public RoleVO(RoleDO roleDO) {
        this.setRoleId(roleDO.getRoleId());
        this.setRoleName(roleDO.getRoleName());
        this.setRoleDescribe(roleDO.getRoleDescribe());
        this.setMenus(JsonUtil.jsonToList(roleDO.getAuthIds(), Menus.class));
    }

    public RoleVO() {

    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Menus> getMenus() {
        return menus;
    }

    public void setMenus(List<Menus> menus) {
        this.menus = menus;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    @Override
    public String toString() {
        return "RoleVO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescribe='" + roleDescribe + '\'' +
                ", menus=" + menus +
                '}';
    }
}
