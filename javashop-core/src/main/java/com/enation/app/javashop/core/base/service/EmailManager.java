/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.service;

import com.enation.app.javashop.core.base.model.vo.EmailVO;
import com.enation.app.javashop.core.system.model.dos.SmtpDO;

/**
 * 发送邮件接口
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月26日 下午3:11:00
 */
public interface EmailManager {

    /**
     * 邮件发送到amqp，供具体业务使用
     *
     * @param emailVO 邮件发送vo
     */
    void sendMQ(EmailVO emailVO);

    /**
     * 邮件发送实现，供消费者调用
     *
     * @param emailVO
     */
    void sendEmail(EmailVO emailVO);

    /**
     * 通过java Transport发送邮件  支持ssl
     *
     * @param smtp    smtp设置
     * @param emailVO 邮件内容
     */
    void sendMailByTransport(SmtpDO smtp, EmailVO emailVO);

    /**
     * 通过java Transport发送邮件  不支持ssl
     *
     * @param smtp    smtp设置
     * @param emailVO 邮件内容
     */
    void sendMailByMailSender(SmtpDO smtp, EmailVO emailVO);
}
