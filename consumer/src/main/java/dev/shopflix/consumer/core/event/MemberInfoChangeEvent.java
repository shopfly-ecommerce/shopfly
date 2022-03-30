/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.event;

/**
 * 会员资料修改事件
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:24:31
 */
public interface MemberInfoChangeEvent {

    /**
     * 会员资料修改后事件
     *
     * @param memberId 会员id
     */
    void memberInfoChange(Integer memberId);
}
