/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.distribution.model.dos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分销返现设置
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-06-12 上午4:06
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionSetting implements Serializable {

    /**
     * 提现金额冻结周期
     */
    @Min(message = "冻结周期不能为负数", value = 0)
    @NotNull(message = "冻结周期不能为空")
    @ApiModelProperty(name = "cycle", value = "冻结周期")
    private Integer cycle = 0;
    /**
     * 是否开启商品返现
     */
    @ApiModelProperty(name = "goods_model", value = "是否开启商品返现,1开启,0不开启")
    @NotNull(message = "商品返现模式开关：1开启/0关闭")
    private Integer goodsModel = 0;

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(Integer goodsModel) {
        this.goodsModel = goodsModel;
    }

    @Override
    public String toString() {
        return "DistributionSetting{" +
                "cycle=" + cycle +
                ", goodsModel=" + goodsModel +
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

        DistributionSetting that = (DistributionSetting) o;

        if (cycle != null ? !cycle.equals(that.cycle) : that.cycle != null) {
            return false;
        }
        return goodsModel != null ? goodsModel.equals(that.goodsModel) : that.goodsModel == null;
    }

    @Override
    public int hashCode() {
        int result = cycle != null ? cycle.hashCode() : 0;
        result = 31 * result + (goodsModel != null ? goodsModel.hashCode() : 0);
        return result;
    }
}
