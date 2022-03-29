/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import dev.shopflix.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


/**
 * 商品关联参数值实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-21 11:40:23
 */
@Table(name = "es_goods_params")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsParamsDO implements Serializable {

    private static final long serialVersionUID = 4134870721776090L;

    /**
     * 主键
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 商品id
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品id", hidden = true)
    private Integer goodsId;
    /**
     * 参数id
     */
    @Column(name = "param_id")
    @ApiModelProperty(name = "param_id", value = "参数id", required = true)
    private Integer paramId;
    /**
     * 参数名字
     */
    @Column(name = "param_name")
    @ApiModelProperty(name = "param_name", value = "参数名字", required = true)
    private String paramName;
    /**
     * 参数值
     */
    @Column(name = "param_value")
    @ApiModelProperty(name = "param_value", value = "参数值", required = true)
    @Length(max = 100, message = "参数值字符不能大于120")
    private String paramValue;

    @PrimaryKeyField
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

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        if (StringUtil.isEmpty(paramValue)) {
            return "";
        }
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "GoodsParamsDO [id=" + id + ", goodsId=" + goodsId + ", paramId=" + paramId + ", paramName=" + paramName
                + ", paramValue=" + paramValue + "]";
    }

}