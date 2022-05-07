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
package cloud.shopfly.b2c.core.payment.plugin.weixin.executor;

import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinPuginConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: WeChatwapend
 * @date 2018/4/1810:12
 * @since v7.0.0
 */
@Service
public class WeixinPaymentMiniExecutor extends WeixinPuginConfig {

    @Autowired
    private WeixinPaymentJsapiExecutor weixinPaymentJsapiExecutor;

    /**
     * pay
     *
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {
        return weixinPaymentJsapiExecutor.onPay(bill);
    }


}
