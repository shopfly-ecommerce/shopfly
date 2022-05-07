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
package cloud.shopfly.b2c.core.system.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Integral
 *
 * @author zh
 * @version v7.0
 * @date 18/5/30 In the afternoon7:25
 * @since v7.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PointSetting {

    /**
     * Whether to enable registration to obtain credits,1open,0不open
     */
    @ApiModelProperty(name = "register", value = "Whether to enable registration to obtain credits,1open,0不open")
    @Min(message = "Whether to enable registration to obtain credits must be a number and,1To open,0To shut down", value = 0)
    @Max(message = "Whether to enable registration to obtain credits must be a number and,1To open,0To shut down", value = 1)
    @NotNull(message = "Whether to enable registration to obtain points cannot be left blank")
    private Integer register;
    /**
     * Sign up for credit points
     */
    @Min(message = "The number of points registered for consumption cannot be negative", value = 0)
    @NotNull(message = "The number of credits registered for consumption cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "register_consumer_point", value = "Sign up for credit points")
    private Integer registerConsumerPoint;
    /**
     * Register to get level points
     */
    @Min(message = "The number of registered grade points cannot be negative", value = 0)
    @NotNull(message = "Register to gain level credits cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "register_grade_point", value = "Register to get level points")
    private Integer registerGradePoint;
    /**
     * Whether to enable login to obtain credits,1open,0不open
     */
    @Min(message = "Whether to enable login to obtain credits must be numeric and,1To open,0To shut down", value = 0)
    @Max(message = "Whether to enable login to obtain credits must be numeric and,1To open,0To shut down", value = 1)
    @NotNull(message = "Whether to enable login to obtain credits cannot be empty or the format is wrong")
    @ApiModelProperty(name = "login", value = "Whether to enable login to obtain credits,1open,0不open")
    private Integer login;
    /**
     * Log in to get credit points
     */
    @Min(message = "Log in to obtain consumption credits cannot be negative", value = 0)
    @NotNull(message = "Login to obtain consumption credits cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "login_consumer_point", value = "Log in to get credit points")
    private Integer loginConsumerPoint;
    /**
     * Log in to get rating points
     */
    @Min(message = "Log in to get rating points cannot be negative", value = 0)
    @NotNull(message = "Log in to get level credits cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "login_grade_point", value = "Log in to get rating points")
    private Integer loginGradePoint;
    /**
     * Whether to enable online payment to obtain points,1open,0不open
     */
    @Min(message = "Whether to enable online payment to obtain credits must be numerical and,1To open,0To shut down", value = 0)
    @Max(message = "Whether to enable online payment to obtain credits must be numerical and,1To open,0To shut down", value = 1)
    @NotNull(message = "Whether to enable online payment to obtain points cannot be left blank")
    @ApiModelProperty(name = "online_pay", value = "Whether to enable online payment to obtain points,1open,0不open")
    private Integer onlinePay;
    /**
     * Online payment to obtain consumption points
     */
    @Min(message = "Online payment to obtain consumption points can not be negative", value = 0)
    @NotNull(message = "Online payment to obtain consumption points cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "online_pay_consumer_point", value = "Online payment to obtain consumption points")
    private Integer onlinePayConsumerPoint;
    /**
     * Pay online to obtain grade points
     */
    @Min(message = "The number of credits for online payments cannot be negative", value = 0)
    @NotNull(message = "Online payment gain level credits cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "online_pay_grade_point", value = "Pay online to obtain grade points")
    private Integer onlinePayGradePoint;
    /**
     * Whether to enable picture comment to obtain credits,1open,0不open
     */
    @Min(message = "Whether to enable picture comment to obtain credits must be numeric and,1To open,0To shut down", value = 0)
    @Max(message = "Whether to enable picture comment to obtain credits must be numeric and,1To open,0To shut down", value = 1)
    @NotNull(message = "Whether to enable picture comment to obtain credits cannot be left blank")
    @ApiModelProperty(name = "comment_img", value = "Whether to enable picture comment to obtain credits,1open,0不open")
    private Integer commentImg;
    /**
     * Picture comments get credit points
     */
    @Min(message = "Image comments can not be negative", value = 0)
    @NotNull(message = "Image comments to obtain consumption credits cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "comment_img_consumer_point", value = "Picture comments get credit points")
    private Integer commentImgConsumerPoint;
    /**
     * Picture comments get rating points
     */
    @Min(message = "The rating integral for picture comments cannot be negative", value = 0)
    @NotNull(message = "Image comments can not be empty or incorrectly formatted")
    @ApiModelProperty(name = "comment_img_grade_point", value = "Picture comments get rating points")
    private Integer commentImgGradePoint;
    /**
     * Whether to enable text comments for credits,1open,0不open
     */
    @Min(message = "Whether to enable text comments to obtain credits must be numeric and,1To open,0To shut down", value = 0)
    @Max(message = "Whether to enable text comments to obtain credits must be numeric and,1To open,0To shut down", value = 1)
    @NotNull(message = "Whether to enable text comments to obtain credits cannot be left blank")
    @ApiModelProperty(name = "comment", value = "Whether to enable text comments for credits,1open,0不open")
    private Integer comment;
    /**
     * Text comments get credit points
     */
    @Min(message = "The credits for text comments cannot be negative", value = 0)
    @NotNull(message = "Text comments can not be empty or incorrectly formatted")
    @ApiModelProperty(name = "comment_consumer_point", value = "Text comments get credit points")
    private Integer commentConsumerPoint;
    /**
     * Text comments get rating points
     */
    @Min(message = "The rating score for text comments cannot be negative", value = 0)
    @NotNull(message = "The number of ratings for text comments cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "comment_grade_point", value = "Text comments get rating points")
    private Integer commentGradePoint;
    /**
     * Whether to enable credits for first comment,1open,0不open
     */
    @Min(message = "Whether to open the first review to obtain credits must be numerical and,1To open,0To shut down", value = 0)
    @Max(message = "Whether to open the first review to obtain credits must be numerical and,1To open,0To shut down", value = 1)
    @NotNull(message = "Whether to enable first comment to obtain credits cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "first_comment", value = "Whether to enable credits for first comment,1open,0不open")
    private Integer firstComment;
    /**
     * Score points for your first comment
     */
    @Min(message = "The number of credits for first reviews cannot be negative", value = 0)
    @NotNull(message = "The number of credits for first-time comments cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "first_comment_consumer_point", value = "Score points for your first comment")
    private Integer firstCommentConsumerPoint;
    /**
     * First comment gets rating points
     */
    @Min(message = "The rating score for first review cannot be negative", value = 0)
    @NotNull(message = "The first comment to get rating points cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "first_comment_grade_point", value = "First comment gets rating points")
    private Integer firstCommentGradePoint;
    /**
     * Whether to enable the purchase of goods to obtain points,1open,0不open
     */
    @Min(message = "Whether to open the purchase of goods to obtain points must be a number and,1To open,0To shut down", value = 0)
    @Max(message = "Whether to open the purchase of goods to obtain points must be a number and,1To open,0To shut down", value = 1)
    @NotNull(message = "Whether to enable the purchase of goods to obtain points cannot be empty")
    @ApiModelProperty(name = "buy_goods", value = "Whether to enable the purchase of goods to obtain points,1open,0不open")
    private Integer buyGoods;
    /**
     * Purchase goods to obtain consumption points
     */
    @Min(message = "The number of points for purchasing goods cannot be negative", value = 0)
    @NotNull(message = "The number of credits for purchasing goods cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "buy_goods_consumer_point", value = "Purchase goods to obtain consumption points")
    private Integer buyGoodsConsumerPoint;
    /**
     * Purchase items to obtain grade points
     */
    @Min(message = "The number of grade points for purchases cannot be negative", value = 0)
    @NotNull(message = "Grade points for purchases cannot be empty or incorrectly formatted")
    @ApiModelProperty(name = "buy_goods_grade_point", value = "Purchase items to obtain grade points")
    private Integer buyGoodsGradePoint;
    /**
     * Whether to enable the conversion ratio between RMB and points
     */
    @Min(message = "Whether to open the conversion ratio between RMB and points must be numerical and,1To open,0To shut down", value = 0)
    @Max(message = "Whether to open the conversion ratio between RMB and points must be numerical and,1To open,0To shut down", value = 1)
    @NotNull(message = "The conversion ratio between RMB and points cannot be empty")
    @ApiModelProperty(name = "parities", value = "Whether to enable the conversion ratio between RMB and points")
    private Integer parities;
    /**
     * Number of points exchanged between RMB and points
     */
    @Min(message = "The conversion of RMB into points cannot be negative", value = 0)
    @NotNull(message = "The number of points exchanged between RMB and points cannot be empty or the format is wrong")
    @ApiModelProperty(name = "parities_point", value = "Number of points exchanged between RMB and points")
    private Integer paritiesPoint;


    public Integer getRegister() {
        return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    public Integer getRegisterConsumerPoint() {
        return registerConsumerPoint;
    }

    public void setRegisterConsumerPoint(Integer registerConsumerPoint) {
        this.registerConsumerPoint = registerConsumerPoint;
    }

    public Integer getRegisterGradePoint() {
        return registerGradePoint;
    }

    public void setRegisterGradePoint(Integer registerGradePoint) {
        this.registerGradePoint = registerGradePoint;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public Integer getLoginConsumerPoint() {
        return loginConsumerPoint;
    }

    public void setLoginConsumerPoint(Integer loginConsumerPoint) {
        this.loginConsumerPoint = loginConsumerPoint;
    }

    public Integer getLoginGradePoint() {
        return loginGradePoint;
    }

    public void setLoginGradePoint(Integer loginGradePoint) {
        this.loginGradePoint = loginGradePoint;
    }

    public Integer getOnlinePay() {
        return onlinePay;
    }

    public void setOnlinePay(Integer onlinePay) {
        this.onlinePay = onlinePay;
    }

    public Integer getOnlinePayConsumerPoint() {
        return onlinePayConsumerPoint;
    }

    public void setOnlinePayConsumerPoint(Integer onlinePayConsumerPoint) {
        this.onlinePayConsumerPoint = onlinePayConsumerPoint;
    }

    public Integer getOnlinePayGradePoint() {
        return onlinePayGradePoint;
    }

    public void setOnlinePayGradePoint(Integer onlinePayGradePoint) {
        this.onlinePayGradePoint = onlinePayGradePoint;
    }

    public Integer getCommentImg() {
        return commentImg;
    }

    public void setCommentImg(Integer commentImg) {
        this.commentImg = commentImg;
    }

    public Integer getCommentImgConsumerPoint() {
        return commentImgConsumerPoint;
    }

    public void setCommentImgConsumerPoint(Integer commentImgConsumerPoint) {
        this.commentImgConsumerPoint = commentImgConsumerPoint;
    }

    public Integer getCommentImgGradePoint() {
        return commentImgGradePoint;
    }

    public void setCommentImgGradePoint(Integer commentImgGradePoint) {
        this.commentImgGradePoint = commentImgGradePoint;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getCommentConsumerPoint() {
        return commentConsumerPoint;
    }

    public void setCommentConsumerPoint(Integer commentConsumerPoint) {
        this.commentConsumerPoint = commentConsumerPoint;
    }

    public Integer getCommentGradePoint() {
        return commentGradePoint;
    }

    public void setCommentGradePoint(Integer commentGradePoint) {
        this.commentGradePoint = commentGradePoint;
    }

    public Integer getFirstComment() {
        return firstComment;
    }

    public void setFirstComment(Integer firstComment) {
        this.firstComment = firstComment;
    }

    public Integer getFirstCommentConsumerPoint() {
        return firstCommentConsumerPoint;
    }

    public void setFirstCommentConsumerPoint(Integer firstCommentConsumerPoint) {
        this.firstCommentConsumerPoint = firstCommentConsumerPoint;
    }

    public Integer getFirstCommentGradePoint() {
        return firstCommentGradePoint;
    }

    public void setFirstCommentGradePoint(Integer firstCommentGradePoint) {
        this.firstCommentGradePoint = firstCommentGradePoint;
    }

    public Integer getBuyGoods() {
        return buyGoods;
    }

    public void setBuyGoods(Integer buyGoods) {
        this.buyGoods = buyGoods;
    }

    public Integer getBuyGoodsConsumerPoint() {
        return buyGoodsConsumerPoint;
    }

    public void setBuyGoodsConsumerPoint(Integer buyGoodsConsumerPoint) {
        this.buyGoodsConsumerPoint = buyGoodsConsumerPoint;
    }

    public Integer getBuyGoodsGradePoint() {
        return buyGoodsGradePoint;
    }

    public void setBuyGoodsGradePoint(Integer buyGoodsGradePoint) {
        this.buyGoodsGradePoint = buyGoodsGradePoint;
    }

    public Integer getParities() {
        return parities;
    }

    public void setParities(Integer parities) {
        this.parities = parities;
    }

    public Integer getParitiesPoint() {
        return paritiesPoint;
    }

    public void setParitiesPoint(Integer paritiesPoint) {
        this.paritiesPoint = paritiesPoint;
    }

    @Override
    public String toString() {
        return "PointSetting{" +
                "register=" + register +
                ", registerConsumerPoint=" + registerConsumerPoint +
                ", registerGradePoint=" + registerGradePoint +
                ", login=" + login +
                ", loginConsumerPoint=" + loginConsumerPoint +
                ", loginGradePoint=" + loginGradePoint +
                ", onlinePay=" + onlinePay +
                ", onlinePayConsumerPoint=" + onlinePayConsumerPoint +
                ", onlinePayGradePoint=" + onlinePayGradePoint +
                ", commentImg=" + commentImg +
                ", commentImgConsumerPoint=" + commentImgConsumerPoint +
                ", commentImgGradePoint=" + commentImgGradePoint +
                ", comment=" + comment +
                ", commentConsumerPoint=" + commentConsumerPoint +
                ", commentGradePoint=" + commentGradePoint +
                ", firstComment=" + firstComment +
                ", firstCommentConsumerPoint=" + firstCommentConsumerPoint +
                ", firstCommentGradePoint=" + firstCommentGradePoint +
                ", buyGoods=" + buyGoods +
                ", buyGoodsConsumerPoint=" + buyGoodsConsumerPoint +
                ", buyGoodsGradePoint=" + buyGoodsGradePoint +
                ", parities=" + parities +
                ", paritiesPoint=" + paritiesPoint +
                '}';
    }
}
