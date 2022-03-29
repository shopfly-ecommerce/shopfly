/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.distribution.model.vo;

import com.enation.app.javashop.core.distribution.model.dos.DistributionDO;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分销商显示vo
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-23 上午9:57
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionVO {

    @ApiModelProperty(value = "当前会员id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "lv1id", name = "lv1_id")
    private Integer lv1Id;
    @ApiModelProperty(value = "lv2id", name = "lv2_id")
    private Integer lv2Id;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "模版名称", name = "current_tpl_name")
    private String currentTplName;
    @ApiModelProperty(value = "模版id", name = "current_tpl_id")
    private Integer currentTplId;
    @ApiModelProperty(value = "下线人数", name = "downline")
    private Integer downline;
    @ApiModelProperty(value = "返利金额")
    private Double rebateTotal;

    @Column(name = "order_num")
    @ApiModelProperty(name = "order_num", value = "提成相关订单数", required = true)
    private Integer orderNum = 0;

    @Column(name = "turnover_price")
    @ApiModelProperty(name = "turnover_price", value = "营业额总额", required = true)
    private Double turnoverPrice = 0D;

    @ApiModelProperty(value = "下线", name = "item")
    private List<DistributionVO> item;

    @ApiModelProperty(value = "结算单", name = "bill_member_vo")
    private BillMemberVO billMemberVO;

    @ApiModelProperty(name = "create_time", value = "创建时间", required = false)
    private Long createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentTplName() {
        return currentTplName;
    }

    public void setCurrentTplName(String currentTplName) {
        this.currentTplName = currentTplName;
    }

    public Integer getDownline() {
        return downline;
    }

    public void setDownline(Integer downline) {
        this.downline = downline;
    }

    public Double getRebateTotal() {
        return rebateTotal;
    }

    public void setRebateTotal(Double rebateTotal) {
        this.rebateTotal = rebateTotal;
    }

    public List<DistributionVO> getItem() {
        return item;
    }

    public void setItem(List<DistributionVO> item) {
        this.item = item;
    }

    public Integer getLv1Id() {
        return lv1Id;
    }

    public void setLv1Id(Integer lv1Id) {
        this.lv1Id = lv1Id;
    }

    public Integer getLv2Id() {
        return lv2Id;
    }

    public void setLv2Id(Integer lv2Id) {
        this.lv2Id = lv2Id;
    }

    public Integer getId() {
        return id;
    }

    public DistributionVO(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Double getTurnoverPrice() {
        return turnoverPrice;
    }

    public void setTurnoverPrice(Double turnoverPrice) {
        this.turnoverPrice = turnoverPrice;
    }

    public Integer getCurrentTplId() {
        return currentTplId;
    }

    public void setCurrentTplId(Integer currentTplId) {
        this.currentTplId = currentTplId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public DistributionVO(DistributionDO ddo) {
        this.id = ddo.getMemberId();
        this.lv1Id = ddo.getMemberIdLv1();
        this.lv2Id = ddo.getMemberIdLv2();
        this.rebateTotal = ddo.getRebateTotal();
        this.downline = ddo.getDownline();
        this.turnoverPrice = ddo.getTurnoverPrice();
        this.name = ddo.getMemberName();
        this.currentTplName = ddo.getCurrentTplName();
        this.orderNum = ddo.getOrderNum();
        this.currentTplId = ddo.getCurrentTplId();
        this.createTime = ddo.getCreateTime();
    }

    public BillMemberVO getBillMemberVO() {
        return billMemberVO;
    }

    public void setBillMemberVO(BillMemberVO billMemberVO) {
        this.billMemberVO = billMemberVO;
    }

    @Override
    public String toString() {
        return "DistributionVO{" +
                "id=" + id +
                ", lv1Id=" + lv1Id +
                ", lv2Id=" + lv2Id +
                ", name='" + name + '\'' +
                ", currentTplName='" + currentTplName + '\'' +
                ", currentTplId=" + currentTplId +
                ", downline=" + downline +
                ", rebateTotal=" + rebateTotal +
                ", orderNum=" + orderNum +
                ", turnoverPrice=" + turnoverPrice +
                ", item=" + item +
                ", billMemberVO=" + billMemberVO +
                ", createTime=" + createTime +
                '}';
    }
}
