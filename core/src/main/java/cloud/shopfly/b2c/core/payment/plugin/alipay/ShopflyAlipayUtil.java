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
package cloud.shopfly.b2c.core.payment.plugin.alipay;

import com.alipay.api.internal.util.AlipaySignature;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Alipay tool
 * Provide validation methods
 *
 * @author kingapex
 * @version 1.0
 * 2015years9month24On the afternoon1:47:42
 */
public class ShopflyAlipayUtil {

    /**
     * The new validation2017years8month1day15:27:48
     * @param  alipayPublicKey The public key
     * @return
     */
    public static boolean verify(String alipayPublicKey) {
        try {

            HttpServletRequest request = ThreadContextHolder.getHttpRequest();
            Map<String, String> params = new HashMap<String, String>(16);
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            // Invoke the SDK to verify the signature
            boolean signVerified = AlipaySignature.rsaCheckV1(params,  alipayPublicKey, AlipayConfig.charset, AlipayConfig.signType);

            return signVerified;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
