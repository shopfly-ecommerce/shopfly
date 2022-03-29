/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.vo;

import java.io.Serializable;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录用户信息
 * @ClassName Auth2Token
 * @since v7.0 上午10:09 2018/6/6
 */
public class Auth2Token implements Serializable {


    private static final long serialVersionUID = 761784446368906457L;
    /**
     * 开放用户ID
     */
    private String unionid;

    /**
     * opneid
     */
    private String opneId;

    /**
     * OpenId类型
     */
    private String type;

    /**
     * 获取openid的令牌
     */
    private String accessToken;

    /**
     * 微信刷新token 如果日后需要，从这里获取
     */
    private String refreshToken;

    /**
     * 有效时间 用于刷新token
     */
    private Long expires;

    /**
     * 存储appid 用于刷新token
     */
    private String appid;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpneId() {
        return opneId;
    }

    public void setOpneId(String opneId) {
        this.opneId = opneId;
    }

    @Override
    public String toString() {
        return "Auth2Token{" +
                "unionid='" + unionid + '\'' +
                ", opneId='" + opneId + '\'' +
                ", type='" + type + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
