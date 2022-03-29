/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;


/**
 * 模版详细配置
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-22 15:10:51
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "es_ship_template_child")
public class ShipTemplateChild implements Serializable {

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     */
    private static final long serialVersionUID = -2310849247997108107L;

    @Id
    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(hidden = true)
    @Column(name = "template_id")
    private Integer templateId;

    @ApiParam("首重／首件")
    @Column(name = "first_company")
    private Double firstCompany;

    @ApiParam("运费")
    @Column(name = "first_price")
    private Double firstPrice;

    @ApiParam("续重／需件")
    @Column(name = "continued_company")
    private Double continuedCompany;

    @ApiParam("续费")
    @Column(name = "continued_price")
    private Double continuedPrice;

    @ApiParam("地区‘，‘分隔   示例参数：北京，山西，天津，上海")
    @Column(name = "area")
    private String area;

    @ApiModelProperty(value = "地区id‘，‘分隔  示例参数：1，2，3，4 ",hidden = true)
    @Column(name = "area_id")
    private String areaId;


    public Double getFirstCompany() {
        return firstCompany;
    }

    public void setFirstCompany(Double firstCompany) {
        this.firstCompany = firstCompany;
    }

    public Double getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(Double firstPrice) {
        this.firstPrice = firstPrice;
    }

    public Double getContinuedCompany() {
        return continuedCompany;
    }

    public void setContinuedCompany(Double continuedCompany) {
        this.continuedCompany = continuedCompany;
    }

    public Double getContinuedPrice() {
        return continuedPrice;
    }

    public void setContinuedPrice(Double continuedPrice) {
        this.continuedPrice = continuedPrice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}