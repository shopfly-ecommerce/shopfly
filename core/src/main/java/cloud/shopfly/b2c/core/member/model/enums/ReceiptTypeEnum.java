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
package cloud.shopfly.b2c.core.member.model.enums;

/**
 * @author zh
 * @version v1.0
 * @Description: Invoice type enumeration
 * @date 2018/5/3 11:12
 * @since v7.0.0
 */
public enum ReceiptTypeEnum {
    /**
     * Electronic invoice
     */
    ELECTRO("Electronic invoice"),
    /**
     * VAT general invoice
     */
    VATORDINARY("VAT general invoice"),
    /**
     * VAT special invoice
     */
    VATOSPECIAL("VAT special invoice");

    private String text;

    ReceiptTypeEnum(String text) {
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String value() {
        return this.name();
    }
}
