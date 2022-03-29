/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.model.dos;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录DO
 * @ClassName ConnectDO
 * @since v7.0 下午2:43 2018/6/20
 */
@Table(name = "es_connect")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConnectDO {

    /**Id*/
    @Id(name = "id")
    @ApiModelProperty(hidden=true)
    private Integer id;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id",value = "会员id")
    private Integer memberId;
    /**
     * 唯一标示union_id
     */
    @Column(name = "union_id")
    @ApiModelProperty(name = "union_id", value = "唯一标示")
    private String unionId;
    /**
     * 信任登录类型
     */
    @Column(name = "union_type")
    @ApiModelProperty(name = "union_type", value = "信任登录类型")
    private String unionType;
    /**
     * 解绑时间
     */
    @Column(name = "unbound_time")
    @ApiModelProperty(name = "unbound_time",value = "解绑时间")
    private Long unboundTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getUnionType() {
        return unionType;
    }

    public void setUnionType(String unionType) {
        this.unionType = unionType;
    }

    public Long getUnboundTime() {
        return unboundTime;
    }

    public void setUnboundTime(Long unboundTime) {
        this.unboundTime = unboundTime;
    }

    @Override
    public String toString() {
        return "ConnectDO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", unionId='" + unionId + '\'' +
                ", unionType='" + unionType + '\'' +
                ", unboundTime=" + unboundTime +
                '}';
    }
}
