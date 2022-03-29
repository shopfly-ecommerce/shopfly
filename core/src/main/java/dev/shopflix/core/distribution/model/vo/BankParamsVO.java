/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 提现参数
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-22 下午1:22
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BankParamsVO implements Serializable {

    @ApiModelProperty(value = "户名", required = true, name = "member_name")
    @NotEmpty(message = "户名不能为空")
    private String memberName;
    @ApiModelProperty(value = "银行名称", required = true, name = "bank_name")
    @NotEmpty(message = "银行名称不能为空")
    private String bankName;
    @ApiModelProperty(value = "开户行号", required = true, name = "opening_num")
    @NotEmpty(message = "开户行号不能为空")
    private String openingNum;
    @ApiModelProperty(value = "银行卡号", required = true, name = "bank_card")
    @NotEmpty(message = "银行卡号不能为空")
    private String bankCard;


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOpeningNum() {
        return openingNum;
    }

    public void setOpeningNum(String openingNum) {
        this.openingNum = openingNum;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    @Override
    public String toString() {
        return "BankParamsVO{" +
                "memberName='" + memberName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", openingNum='" + openingNum + '\'' +
                ", bankCard='" + bankCard + '\'' +
                '}';
    }
}
