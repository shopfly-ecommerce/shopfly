/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 草稿商品参数表实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 11:31:20
 */
@Table(name = "es_draft_goods_params")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DraftGoodsParamsDO implements Serializable {

    private static final long serialVersionUID = 1137617128769441L;

    /**
     * ID
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 草稿ID
     */
    @Column(name = "draft_goods_id")
    @ApiModelProperty(name = "draft_goods_id", value = "草稿ID", required = false)
    private Integer draftGoodsId;
    /**
     * 参数ID
     */
    @Column(name = "param_id")
    @ApiModelProperty(name = "param_id", value = "参数ID", required = false)
    private Integer paramId;
    /**
     * 参数名
     */
    @Column(name = "param_name")
    @ApiModelProperty(name = "param_name", value = "参数名", required = false)
    private String paramName;
    /**
     * 参数值
     */
    @Column(name = "param_value")
    @ApiModelProperty(name = "param_value", value = "参数值", required = false)
    private String paramValue;

    public DraftGoodsParamsDO() {
    }

    public DraftGoodsParamsDO(GoodsParamsDO param) {
        this.paramId = param.getParamId();
        this.paramName = param.getParamName();
        this.paramValue = param.getParamValue();
    }

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDraftGoodsId() {
        return draftGoodsId;
    }

    public void setDraftGoodsId(Integer draftGoodsId) {
        this.draftGoodsId = draftGoodsId;
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
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "DraftGoodsParamsDO [id=" + id + ", draftGoodsId=" + draftGoodsId + ", paramId=" + paramId
                + ", paramName=" + paramName + ", paramValue=" + paramValue + "]";
    }


}