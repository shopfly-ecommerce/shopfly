/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.sss;

import dev.shopflix.consumer.core.event.MemberRegisterEvent;
import dev.shopflix.core.base.message.MemberRegisterMsg;
import dev.shopflix.core.client.statistics.MemberDataClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员数据收集消费
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/20 下午2:20
 */
@Service
public class DataMemberConsumer implements MemberRegisterEvent {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private MemberDataClient memberDataClient;

    /**
     * 会员注册
     *
     * @param memberRegisterMsg
     */
    @Override
    public void memberRegister(MemberRegisterMsg memberRegisterMsg) {

        try {
            this.memberDataClient.register(memberRegisterMsg.getMember());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("会员注册消息异常:",e);
        }
    }
}
