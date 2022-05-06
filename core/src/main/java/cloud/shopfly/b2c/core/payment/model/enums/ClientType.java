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
package cloud.shopfly.b2c.core.payment.model.enums;

/**
 * 支付客户端类型
 *
 * @author fk
 * @version v6.4
 * @since v6.4 2017年10月17日 上午10:49:25
 */
public enum ClientType {

    /**
     * pc客户端
     */
    PC("pc_config", "PC"),
    /**
     * wap
     */
    WAP("wap_config", "WAP"),
    /**
     * 原生
     */
    NATIVE("app_native_config", "APP"),
    /**
     * RN
     */
    REACT("app_react_config", "APP"),
    /**
     * 微信小程序
     */
    MINI("mini_config", "MINI");

    private String dbColumn;
    private String client;

    ClientType(String dbColumn, String client) {
        this.dbColumn = dbColumn;
        this.client = client;

    }

    public String getDbColumn() {
        return dbColumn;
    }

    public void setDbColumn(String dbColumn) {
        this.dbColumn = dbColumn;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String value() {
        return this.name();
    }


}
