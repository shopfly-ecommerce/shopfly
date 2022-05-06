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
package cloud.shopfly.b2c.core.member.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 地区VO
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-25 17:34:28
 */
public class RegionVO implements Serializable {

    private static final long serialVersionUID = 3444861223072695184L;

    /**
     * 地区名称
     */
    public String localName;
    /**
     * 地区id
     */
    public Integer id;
    /**
     * 父地区id
     */
    private Integer parentId;
    /**
     * 子对象集合
     */
    private List<RegionVO> children;
    /**
     * 级别
     */
    private Integer level;


    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

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

    public List<RegionVO> getChildren() {
        return children;
    }

    public void setChildren(List<RegionVO> children) {
        this.children = children;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "RegionVO{" +
                "localName='" + localName + '\'' +
                ", id=" + id +
                ", parentId=" + parentId +
                ", children=" + children +
                ", level=" + level +
                '}';
    }
}
