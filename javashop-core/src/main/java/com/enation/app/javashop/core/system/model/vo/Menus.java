/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.model.vo;

import java.util.List;

/**
 * 菜单权限vo 用于权限菜单的参数传递及展示
 *
 * @author zh
 * @version v7.0
 * @date 18/6/25 上午9:59
 * @since v7.0
 */

public class Menus {
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 菜单唯一标识
     */
    private String identifier;
    /**
     * 此菜单是否选中
     */
    private boolean checked;
    /**
     * 菜单的权限表达式
     */
    private String authRegular;

    /**
     * 子菜单
     */
    private List<Menus> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Menus> getChildren() {
        return children;
    }

    public void setChildren(List<Menus> children) {
        this.children = children;
    }

    public String getAuthRegular() {
        return authRegular;
    }

    public void setAuthRegular(String authRegular) {
        this.authRegular = authRegular;
    }

    @Override
    public String toString() {
        return "Menus{" +
                "title='" + title + '\'' +
                ", identifier='" + identifier + '\'' +
                ", checked=" + checked +
                ", authRegular='" + authRegular + '\'' +
                ", children=" + children +
                '}';
    }
}
