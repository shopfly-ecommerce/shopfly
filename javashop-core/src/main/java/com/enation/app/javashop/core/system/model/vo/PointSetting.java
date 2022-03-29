/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 积分设置
 *
 * @author zh
 * @version v7.0
 * @date 18/5/30 下午7:25
 * @since v7.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PointSetting {

    /**
     * 是否开启注册获取积分,1开启,0不开启
     */
    @ApiModelProperty(name = "register", value = "是否开启注册获取积分,1开启,0不开启")
    @Min(message = "是否开启注册获取积分必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "是否开启注册获取积分必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "是否开启注册获取积分不能为空")
    private Integer register;
    /**
     * 注册获取消费积分数
     */
    @Min(message = "注册获取消费积分数不能为负数", value = 0)
    @NotNull(message = "注册获取消费积分数不能为空或格式错误")
    @ApiModelProperty(name = "register_consumer_point", value = "注册获取消费积分数")
    private Integer registerConsumerPoint;
    /**
     * 注册获取等级积分数
     */
    @Min(message = "注册获取等级积分数不能为负数", value = 0)
    @NotNull(message = "注册获取等级积分数不能为空或格式错误")
    @ApiModelProperty(name = "register_grade_point", value = "注册获取等级积分数")
    private Integer registerGradePoint;
    /**
     * 是否开启登录获取积分,1开启,0不开启
     */
    @Min(message = "是否开启登录获取积分必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "是否开启登录获取积分必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "是否开启登录获取积分不能为空或格式错误")
    @ApiModelProperty(name = "login", value = "是否开启登录获取积分,1开启,0不开启")
    private Integer login;
    /**
     * 登录获取消费积分数
     */
    @Min(message = "登录获取消费积分数不能为负数", value = 0)
    @NotNull(message = "登录获取消费积分数不能为空或格式错误")
    @ApiModelProperty(name = "login_consumer_point", value = "登录获取消费积分数")
    private Integer loginConsumerPoint;
    /**
     * 登录获取等级积分数
     */
    @Min(message = "登录获取等级积分数不能为负数", value = 0)
    @NotNull(message = "登录获取等级积分数不能为空或格式错误")
    @ApiModelProperty(name = "login_grade_point", value = "登录获取等级积分数")
    private Integer loginGradePoint;
    /**
     * 是否开启在线支付获取积分,1开启,0不开启
     */
    @Min(message = "是否开启在线支付获取积分必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "是否开启在线支付获取积分必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "是否开启在线支付获取积分不能为空")
    @ApiModelProperty(name = "online_pay", value = "是否开启在线支付获取积分,1开启,0不开启")
    private Integer onlinePay;
    /**
     * 在线支付获取消费积分数
     */
    @Min(message = "在线支付获取消费积分数不能为负数", value = 0)
    @NotNull(message = "在线支付获取消费积分数不能为空或格式错误")
    @ApiModelProperty(name = "online_pay_consumer_point", value = "在线支付获取消费积分数")
    private Integer onlinePayConsumerPoint;
    /**
     * 在线支付获取等级积分数
     */
    @Min(message = "在线支付获取等级积分数不能为负数", value = 0)
    @NotNull(message = "在线支付获取等级积分数不能为空或格式错误")
    @ApiModelProperty(name = "online_pay_grade_point", value = "在线支付获取等级积分数")
    private Integer onlinePayGradePoint;
    /**
     * 是否开启图片评论获取积分,1开启,0不开启
     */
    @Min(message = "是否开启图片评论获取积分必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "是否开启图片评论获取积分必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "是否开启图片评论获取积分不能为空")
    @ApiModelProperty(name = "comment_img", value = "是否开启图片评论获取积分,1开启,0不开启")
    private Integer commentImg;
    /**
     * 图片评论获取消费积分数
     */
    @Min(message = "图片评论获取消费积分数不能为负数", value = 0)
    @NotNull(message = "图片评论获取消费积分不能为空或格式错误")
    @ApiModelProperty(name = "comment_img_consumer_point", value = "图片评论获取消费积分数")
    private Integer commentImgConsumerPoint;
    /**
     * 图片评论获取等级积分数
     */
    @Min(message = "图片评论获取等级积分数不能为负数", value = 0)
    @NotNull(message = "图片评论获取等级积分数不能为空或格式错误")
    @ApiModelProperty(name = "comment_img_grade_point", value = "图片评论获取等级积分数")
    private Integer commentImgGradePoint;
    /**
     * 是否开启文字评论获取积分,1开启,0不开启
     */
    @Min(message = "是否开启文字评论获取积分必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "是否开启文字评论获取积分必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "是否开启文字评论获取积分不能为空")
    @ApiModelProperty(name = "comment", value = "是否开启文字评论获取积分,1开启,0不开启")
    private Integer comment;
    /**
     * 文字评论获取消费积分数
     */
    @Min(message = "文字评论获取消费积分数不能为负数", value = 0)
    @NotNull(message = "文字评论获取消费积分数不能为空或格式错误")
    @ApiModelProperty(name = "comment_consumer_point", value = "文字评论获取消费积分数")
    private Integer commentConsumerPoint;
    /**
     * 文字评论获取等级积分数
     */
    @Min(message = "文字评论获取等级积分数不能为负数", value = 0)
    @NotNull(message = "文字评论获取等级积分数不能为空或格式错误")
    @ApiModelProperty(name = "comment_grade_point", value = "文字评论获取等级积分数")
    private Integer commentGradePoint;
    /**
     * 是否开启首次评论获取积分,1开启,0不开启
     */
    @Min(message = "是否开启首次评论获取积分必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "是否开启首次评论获取积分必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "是否开启首次评论获取积分不能为空或格式错误")
    @ApiModelProperty(name = "first_comment", value = "是否开启首次评论获取积分,1开启,0不开启")
    private Integer firstComment;
    /**
     * 首次评论获取消费积分数
     */
    @Min(message = "首次评论获取消费积分数不能为负数", value = 0)
    @NotNull(message = "首次评论获取消费积分数不能为空或格式错误")
    @ApiModelProperty(name = "first_comment_consumer_point", value = "首次评论获取消费积分数")
    private Integer firstCommentConsumerPoint;
    /**
     * 首次评论获取等级积分数
     */
    @Min(message = "首次评论获取等级积分数不能为负数", value = 0)
    @NotNull(message = "首次评论获取等级积分数不能为空或格式错误")
    @ApiModelProperty(name = "first_comment_grade_point", value = "首次评论获取等级积分数")
    private Integer firstCommentGradePoint;
    /**
     * 是否开启购买商品获取积分,1开启,0不开启
     */
    @Min(message = "是否开启购买商品获取积分必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "是否开启购买商品获取积分必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "是否开启购买商品获取积分不能为空")
    @ApiModelProperty(name = "buy_goods", value = "是否开启购买商品获取积分,1开启,0不开启")
    private Integer buyGoods;
    /**
     * 购买商品获取消费积分数
     */
    @Min(message = "购买商品获取消费积分数不能为负数", value = 0)
    @NotNull(message = "购买商品获取消费积分数不能为空或格式错误")
    @ApiModelProperty(name = "buy_goods_consumer_point", value = "购买商品获取消费积分数")
    private Integer buyGoodsConsumerPoint;
    /**
     * 购买商品获取等级积分数
     */
    @Min(message = "购买商品获取等级积分数不能为负数", value = 0)
    @NotNull(message = "购买商品获取等级积分数不能为空或格式错误")
    @ApiModelProperty(name = "buy_goods_grade_point", value = "购买商品获取等级积分数")
    private Integer buyGoodsGradePoint;
    /**
     * 是否开启人民币与积分兑换比例
     */
    @Min(message = "是否开启人民币与积分兑换比例必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "是否开启人民币与积分兑换比例必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "是否开启人民币与积分兑换比例不能为空")
    @ApiModelProperty(name = "parities", value = "是否开启人民币与积分兑换比例")
    private Integer parities;
    /**
     * 人民币与积分兑换积分数
     */
    @Min(message = "人民币与积分兑换积分数不能为负数", value = 0)
    @NotNull(message = "人民币与积分兑换积分数不能为空或格式错误")
    @ApiModelProperty(name = "parities_point", value = "人民币与积分兑换积分数")
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
