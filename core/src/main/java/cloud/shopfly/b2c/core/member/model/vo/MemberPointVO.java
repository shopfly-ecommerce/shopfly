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
package cloud.shopfly.b2c.core.member.model.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

/**
 *
 * Member Points Enquiry
 * @author zh
 * @version v70
 * @since v7.0
 * 2018-04-004 15:44:12
 */
public class MemberPointVO {

    /**
     * Level score
     */
    @Min(message="Must be a number", value = 0)
    @ApiModelProperty(name="grade_point",value="Level score",required=false)
    private Integer gradePoint;

    /**consumption score*/
    @Min(message="Must be a number", value = 0)
    @ApiModelProperty(name="consum_point",value="consumption score",required=false)
    private Integer consumPoint;

    public Integer getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Integer gradePoint) {
        this.gradePoint = gradePoint;
    }

    public Integer getConsumPoint() {
        return consumPoint;
    }

    public void setConsumPoint(Integer consumPoint) {
        this.consumPoint = consumPoint;
    }

    @Override
    public String toString() {
        return "MemberPointVO{" +
                "gradePoint=" + gradePoint +
                ", consumPoint=" + consumPoint +
                '}';
    }
}
