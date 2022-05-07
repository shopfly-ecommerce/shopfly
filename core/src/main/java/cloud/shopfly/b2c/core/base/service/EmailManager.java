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
 * Mail sending interface
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018years3month26On the afternoon3:11:00
 */
public interface EmailManager {

    /**
     * Email sent toamqpFor specific business use
     *
     * @param emailVO Mail deliveryvo
     */
    void sendMQ(EmailVO emailVO);

    /**
     * Mail delivery implementation for consumers to call
     *
     * @param emailVO
     */
    void sendEmail(EmailVO emailVO);

    /**
     * throughjava TransportSend Mail Supportssl
     *
     * @param smtp    smtpSet up the
     * @param emailVO Email content
     */
    void sendMailByTransport(SmtpDO smtp, EmailVO emailVO);

    /**
     * throughjava TransportSending emails is not supportedssl
     *
     * @param smtp    smtpSet up the
     * @param emailVO Email content
     */
    void sendMailByMailSender(SmtpDO smtp, EmailVO emailVO);
}
