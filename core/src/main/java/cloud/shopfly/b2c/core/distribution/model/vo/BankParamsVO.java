/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.distribution.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Withdrawal parameters
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-22 In the afternoon1:22
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BankParamsVO implements Serializable {

    @ApiModelProperty(value = "Account name", required = true, name = "member_name")
    @NotEmpty(message = "The account name cannot be empty")
    private String memberName;
    @ApiModelProperty(value = "Bank name", required = true, name = "bank_name")
    @NotEmpty(message = "The bank name cannot be empty")
    private String bankName;
    @ApiModelProperty(value = "Bank no.", required = true, name = "opening_num")
    @NotEmpty(message = "The opening bank number cannot be blank")
    private String openingNum;
    @ApiModelProperty(value = "Bank card number", required = true, name = "bank_card")
    @NotEmpty(message = "The bank card number cannot be empty")
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
