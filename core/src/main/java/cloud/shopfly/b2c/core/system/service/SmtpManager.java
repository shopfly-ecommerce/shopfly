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
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.SmtpDO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Mail business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-25 16:16:53
 */
public interface SmtpManager {

    /**
     * Querying mailing lists
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Modify the mail
     *
     * @param smtp mail
     * @param id   Mail the primary key
     * @return Smtp mail
     */
    SmtpDO edit(SmtpDO smtp, Integer id);

    /**
     * Access to email
     *
     * @param id Mail the primary key
     * @return Smtp  mail
     */
    SmtpDO getModel(Integer id);

    /**
     * Add the mail
     *
     * @param smtp mail
     * @return Smtp mail
     */
    SmtpDO add(SmtpDO smtp);

    /**
     * Delete the email
     *
     * @param id Mail the primary key
     */
    void delete(Integer id);

    /**
     * Send a test email
     *
     * @param send Sending email address
     * @param smtp smtpSet up the
     */
    void send(String send, SmtpDO smtp);

    /**
     * Gets the currently usedsmtpplan
     *
     * @return smtp
     */
    SmtpDO getCurrentSmtp();
}
