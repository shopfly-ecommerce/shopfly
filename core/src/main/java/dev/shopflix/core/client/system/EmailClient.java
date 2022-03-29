/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system;

import dev.shopflix.core.base.model.vo.EmailVO;

/**
 * @author fk
 * @version v2.0
 * @Description: 邮件
 * @date 2018/8/13 16:25
 * @since v7.0.0
 */
public interface EmailClient {

    /**
     * 邮件发送实现，供消费者调用
     *
     * @param emailVO
     */
    void sendEmail(EmailVO emailVO);
}
