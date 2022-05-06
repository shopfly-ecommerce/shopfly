/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.member;

/**
 * @author fk
 * @version v1.0
 * @Description: 评论对外接口
 * @date 2018/7/26 11:30
 * @since v7.0.0
 */
public interface MemberAskClient {

    /**
     * 获取未回复的咨询数量
     *
     * @return
     */
    Integer getNoReplyCount();
}
