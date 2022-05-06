/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.base.service;

import cloud.shopfly.b2c.core.base.model.vo.EmailVO;
import cloud.shopfly.b2c.core.system.model.dos.SmtpDO;

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
