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
package cloud.shopfly.b2c.core.member.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Comment Query Conditions
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:38:00
 */
@ApiModel
public class CommentQueryParam {

    @ApiModelProperty(value = "Comment on the content", name = "content")
    private String content;

    @ApiModelProperty(value = "productid", name = "goods_id")
    private Integer goodsId;

    @ApiModelProperty(value = "Name", name = "goods_name")
    private String goodsName;

    @ApiModelProperty(value = "Good to bad", name = "grade")
    private String grade;

    @ApiModelProperty(value = "If there is a figure", name = "have_image")
    private Boolean haveImage;

    @ApiModelProperty(value = "Fuzzy query keyword", name = "keyword")
    private String keyword;

    @ApiModelProperty(value = "membersid", name = "member_id")
    private Integer memberId;

    @ApiModelProperty(value = "Member name", name = "member_name")
    private String memberName;

    @ApiModelProperty(value = "The page number", name = "page_no")
    private Integer pageNo;

    @ApiModelProperty(value = "Number of pages", name = "page_size")
    private Integer pageSize;

    @ApiModelProperty(value = "Reply to state", name = "reply_status")
    private Integer replyStatus;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Boolean getHaveImage() {
        return haveImage;
    }

    public void setHaveImage(Boolean haveImage) {
        this.haveImage = haveImage;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
    }

}
