/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.vo;

import java.io.Serializable;

/**
 * 会员登陆消息
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年10月18日 下午9:39:06
 */
public class MemberLoginMsg implements Serializable {

    private static final long serialVersionUID = 8173084471934834777L;

    /**
     * 会员id
     */
    private Integer memberId;
    /**
     * 上次登录时间
     */
    private Long lastLoginTime;


    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "MemberLoginMsg{" +
                "memberId=" + memberId +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
