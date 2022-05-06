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
package cloud.shopfly.b2c.core.base.plugin.waybill.dto;

/**
 * 电子面单dto
 *
 * @author zh
 * @version v7.0
 * @date 18/6/11 下午5:03
 * @since v7.0
 */

public class WayBillDTO {
    /**
     * 电子面单模板
     */
    private String template;
    /**
     * 物流公司编码
     */
    private String code;


    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "WayBillDTO{" +
                "template='" + template + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
