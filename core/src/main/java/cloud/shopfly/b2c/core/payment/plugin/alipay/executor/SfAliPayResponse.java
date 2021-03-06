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
package cloud.shopfly.b2c.core.payment.plugin.alipay.executor;
/**
 * shopfly Pay corresponding object
 *
 * @author zh
 * @version v7.0
 * @date 18/7/19 In the afternoon4:47
 * @since v7.0
 */

import cloud.shopfly.b2c.core.payment.model.vo.Form;
import com.alipay.api.AlipayResponse;

public class SfAliPayResponse extends AlipayResponse {
    /**
     * Organize the form information in the data structure
     */
    private Form form;


    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return "shopflyPayResponse{" +
                "form=" + form +
                '}';
    }
}
