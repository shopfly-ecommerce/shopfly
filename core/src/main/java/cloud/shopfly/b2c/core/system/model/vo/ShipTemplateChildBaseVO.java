/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;

/**
 * @author fk
 * @version v2.0
 * @Description: ${todo}
 * @date 2018/10/2616:24
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShipTemplateChildBaseVO implements Serializable {

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

    @Override
    public String toString() {
        return "ShipTemplateChildBaseVO{" +
                "firstCompany=" + firstCompany +
                ", firstPrice=" + firstPrice +
                ", continuedCompany=" + continuedCompany +
                ", continuedPrice=" + continuedPrice +
                '}';
    }
}
