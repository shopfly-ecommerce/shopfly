/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.model.vo;


import com.enation.app.javashop.core.member.model.dos.Member;
import io.swagger.annotations.ApiModel;


/**
 * 会员实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 14:27:48
 */
@ApiModel
public class MemberVO {

    /**
     * 会员ID
     */
    private Integer uid;
    /**
     * 会员登陆用户名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String nickname;
    /**
     * token令牌
     */
    private String accessToken;
    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * 会员头像
     */
    private String face;


    public MemberVO() {

    }

    public MemberVO(Member member, String sccessToken, String refreshToken) {
        this.uid = member.getMemberId();
        this.face = member.getFace();
        this.username = member.getUname();
        this.nickname = member.getNickname();
        this.accessToken = sccessToken;
        this.refreshToken = refreshToken;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", face='" + face + '\'' +
                '}';
    }
}