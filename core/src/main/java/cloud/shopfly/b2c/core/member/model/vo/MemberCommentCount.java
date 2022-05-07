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

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author fk
 * @version v2.0
 * @Description: Comment number
 * @date 2018/9/12 11:10
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberCommentCount {

    /**
     * Total number of comments
     */
    @ApiModelProperty(name = "all_count", value = "Total number of comments")
    private Integer allCount;

    /**
     * Number of favorable comments
     */
    @ApiModelProperty(name = "good_count", value = "Number of favorable comments")
    private Integer goodCount;

    /**
     * In the evaluation of the number
     */
    @ApiModelProperty(name = "neutral_count", value = "In the evaluation of the number")
    private Integer neutralCount;

    /**
     * Number of bad review
     */
    @ApiModelProperty(name = "bad_count", value = "Number of bad review")
    private Integer badCount;

    /**
     * Number of comments with images
     */
    @ApiModelProperty(name = "image_count", value = "Number of comments with images")
    private Integer imageCount;

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getNeutralCount() {
        return neutralCount;
    }

    public void setNeutralCount(Integer neutralCount) {
        this.neutralCount = neutralCount;
    }

    public Integer getBadCount() {
        return badCount;
    }

    public void setBadCount(Integer badCount) {
        this.badCount = badCount;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public MemberCommentCount() {
    }

    public MemberCommentCount(Integer allCount, Integer goodCount, Integer neutralCount, Integer badCount, Integer imageCount) {
        this.allCount = allCount;
        this.goodCount = goodCount;
        this.neutralCount = neutralCount;
        this.badCount = badCount;
        this.imageCount = imageCount;
    }

    @Override
    public String toString() {
        return "MemberCommentCount{" +
                "allCount=" + allCount +
                ", goodCount=" + goodCount +
                ", neutralCount=" + neutralCount +
                ", badCount=" + badCount +
                ", imageCount=" + imageCount +
                '}';
    }
}
