/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.job.execute.impl;

import dev.shopflix.consumer.job.execute.EveryMonthExecute;
import com.enation.app.javashop.core.client.member.MemberClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 会员登录数量归零
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-19 下午2:40
 */
@Component
public class MemberLoginNumToZeroJob implements EveryMonthExecute {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private MemberClient memberClient;

    /**
     * 每月执行
     */
    @Override
    public void everyMonth() {
        try {
            memberClient.loginNumToZero();
        } catch (Exception e) {
            this.logger.error("会员登陆次数归零异常：", e);
        }
    }
}
