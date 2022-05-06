/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.system.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;

import java.io.Serializable;
import java.util.List;


/**
 * 菜单VO 用于读取所有的菜单
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-19 11:13:31
 */
public class MenusVO implements Serializable {

    /**
     * 菜单id
     */
    private Integer id;
    /**
     * 父id
     */
    private Integer parentId;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 菜单url
     */
    private String url;
    /**
     * 菜单唯一标识
     */
    private String identifier;
    /**
     * 权限表达式
     */
    private String authRegular;
    /**
     * 删除标记
     */
    private Integer deleteFlag;
    /**
     * 子菜单
     */
    private List<MenusVO> children;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAuthRegular() {
        return authRegular;
    }

    public void setAuthRegular(String authRegular) {
        this.authRegular = authRegular;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<MenusVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenusVO> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "MenusVO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", identifier='" + identifier + '\'' +
                ", authRegular='" + authRegular + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", children=" + children +
                '}';
    }
}