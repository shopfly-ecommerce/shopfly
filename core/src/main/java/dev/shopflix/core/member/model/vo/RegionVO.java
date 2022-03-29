/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.vo;

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
