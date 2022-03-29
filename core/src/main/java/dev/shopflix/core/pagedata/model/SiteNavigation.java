/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.pagedata.model;

import dev.shopflix.core.pagedata.constraint.annotation.ClientMobileType;
import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 导航栏实体
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 17:07:22
 */
@Table(name = "es_site_navigation")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SiteNavigation implements Serializable {

    private static final long serialVersionUID = 964913498755358L;

    /**
     * 主键
     */
    @Id(name = "navigation_id")
    @ApiModelProperty(hidden = true)
    private Integer navigationId;
    /**
     * 导航名称
     */
    @Column(name = "navigation_name")
    @ApiModelProperty(name = "navigation_name", value = "导航名称", required = true)
    @Size(max=15,message = "导航名称字符长度校验，不可超过15个字符")
    @NotEmpty(message = "导航名称不能为空")
    private String navigationName;
    /**
     * 导航地址
     */
    @Column(name = "url")
    @ApiModelProperty(name = "url", value = "导航地址", required = true)
    @Size(max=255,message = "导航地址长度校验，不可超过255个字符")
    @NotEmpty(message = "导航地址不能为空")
    private String url;
    /**
     * 客户端类型
     */
    @Column(name = "client_type")
    @ApiModelProperty(name = "client_type", value = "客户端类型 PC/MOBILE", required = true)
    @ClientMobileType
    private String clientType;
    /**
     * 图片
     */
    @Column(name = "image")
    @ApiModelProperty(name = "image", value = "图片", required = false)
    private String image;
    /**
     * 排序
     */
    @Column(name = "sort")
    @ApiModelProperty(name = "sort", value = "排序",hidden = true)
    private Integer sort;

    @PrimaryKeyField
    public Integer getNavigationId() {
        return navigationId;
    }

    public void setNavigationId(Integer navigationId) {
        this.navigationId = navigationId;
    }

    public String getNavigationName() {
        return navigationName;
    }

    public void setNavigationName(String navigationName) {
        this.navigationName = navigationName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SiteNavigation that = (SiteNavigation) o;
        if (navigationId != null ? !navigationId.equals(that.navigationId) : that.navigationId != null) {
            return false;
        }
        if (navigationName != null ? !navigationName.equals(that.navigationName) : that.navigationName != null) {
            return false;
        }
        if (url != null ? !url.equals(that.url) : that.url != null) {
            return false;
        }
        if (clientType != null ? !clientType.equals(that.clientType) : that.clientType != null) {
            return false;
        }
        if (image != null ? !image.equals(that.image) : that.image != null) {
            return false;
        }
        return sort != null ? sort.equals(that.sort) : that.sort == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (navigationId != null ? navigationId.hashCode() : 0);
        result = 31 * result + (navigationName != null ? navigationName.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (clientType != null ? clientType.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SiteNavigation{" +
                "navigationId=" + navigationId +
                ", navigationName='" + navigationName + '\'' +
                ", url='" + url + '\'' +
                ", clientType='" + clientType + '\'' +
                ", image='" + image + '\'' +
                ", sort=" + sort +
                '}';
    }


}