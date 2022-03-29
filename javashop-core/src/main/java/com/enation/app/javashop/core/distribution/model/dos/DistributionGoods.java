/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.distribution.model.dos;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * DistributionGoods
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-06-11 上午7:39
 */

@Table(name = "es_distribution_goods")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionGoods {

    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品id", required = true)
    private Integer goodsId;

    @Column(name = "grade1_rebate")
    @ApiModelProperty(name = "grade1_rebate", value = "1级提成金额", required = true)
    private double grade1Rebate = 0;

    @Column(name = "grade2_rebate")
    @ApiModelProperty(name = "grade2_rebate", value = "2级提成金额", required = true)
    private double grade2Rebate = 0;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public double getGrade1Rebate() {
        return grade1Rebate;
    }

    public void setGrade1Rebate(double grade1Rebate) {
        this.grade1Rebate = grade1Rebate;
    }

    public double getGrade2Rebate() {
        return grade2Rebate;
    }

    public void setGrade2Rebate(double grade2Rebate) {
        this.grade2Rebate = grade2Rebate;
    }

    @Override
    public String toString() {
        return "DistributionGoods{" +
                "goodsId=" + goodsId +
                ", grade1Rebate=" + grade1Rebate +
                ", grade2Rebate=" + grade2Rebate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DistributionGoods that = (DistributionGoods) o;

        if (Double.compare(that.grade1Rebate, grade1Rebate) != 0) {
            return false;
        }
        if (Double.compare(that.grade2Rebate, grade2Rebate) != 0) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return goodsId != null ? goodsId.equals(that.goodsId) : that.goodsId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        temp = Double.doubleToLongBits(grade1Rebate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(grade2Rebate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
