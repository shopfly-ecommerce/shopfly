/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.model.dto;

import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Table;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员注册数据
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/3/25 下午10:47
 */

@Table(name="es_sss_member_register_data")
public class MemberRegisterData {


    private Integer id;

    @ApiModelProperty(value = "会员id")
    @Column(name = "member_id")
    private Integer memberId;

    @ApiModelProperty(value = "会员名称")
    @Column(name = "member_name")
    private String memberName;

    @ApiModelProperty(value = "创建日期")
    @Column(name = "create_time")
    private Long createTime;

    public MemberRegisterData() {
    }


	public MemberRegisterData(Member member) {
		this.setCreateTime(member.getCreateTime());
		this.setMemberId(member.getMemberId());
		this.setMemberName(member.getUname());
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberRegisterData that = (MemberRegisterData) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) {
            return false;
        }
        if (memberName != null ? !memberName.equals(that.memberName) : that.memberName != null) {
            return false;
        }
        return createTime != null ? createTime.equals(that.createTime) : that.createTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberRegisterData{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
