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
package cloud.shopfly.b2c.core.payment.service;

import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.model.vo.PaymentMethodVO;
import cloud.shopfly.b2c.core.payment.model.vo.PaymentPluginVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Payment mode table business layer
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
public interface PaymentMethodManager {

    /**
     * Query the payment mode table list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add payment method table
     *
     * @param paymentMethod   Form of payment
     * @param paymentPluginId The plug-inid
     * @return PaymentMethod Form of payment
     */
    PaymentMethodDO add(PaymentPluginVO paymentMethod, String paymentPluginId);

    /**
     * Modify the payment form
     *
     * @param paymentMethod Form of payment
     * @param id            Primary key of payment mode table
     * @return PaymentMethod Form of payment
     */
    PaymentMethodDO edit(PaymentMethodDO paymentMethod, Integer id);

    /**
     * Delete the payment method table
     *
     * @param id Primary key of payment mode table
     */
    void delete(Integer id);

    /**
     * According to the payment plug-inidGet payment method details
     *
     * @param pluginId
     * @return
     */
    PaymentMethodDO getByPluginId(String pluginId);

    /**
     * Query the payment mode supported by a client
     *
     * @param clientType
     * @return
     */
    List<PaymentMethodVO> queryMethodByClient(String clientType);

    /**
     * According to the plug-inidTo obtainVOobject
     *
     * @param pluginId
     * @return
     */
    PaymentPluginVO getByPlugin(String pluginId);

}
