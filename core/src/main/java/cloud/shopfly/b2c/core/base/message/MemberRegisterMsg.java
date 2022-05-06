/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.base.message;

import cloud.shopfly.b2c.core.member.model.dos.Member;

import java.io.Serializable;

/**
 * 会员注册发送消息
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:36:41
 */
public class MemberRegisterMsg implements Serializable {

    private static final long serialVersionUID = 1913944052387917137L;

    private Member member;

    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
