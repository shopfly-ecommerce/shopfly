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
package cloud.shopfly.b2c.core.system.model.enums;

/**
 * Payment client type
 * 
 * @author fk
 * @version v6.4
 * @since v6.4 2017years10month17The morning of10:49:25
 */
public enum ClientType {

	// PC end, mobile WAP end, mobile APP end
	PC("pc_enable", "PC"), WAP("wap_enable", "WAP"), APP("app_enable", "APP");

	private String description;
	private String client;

	ClientType(String description, String client) {
		this.description = description;
		this.client = client;

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	public String description() {
		return this.description;
	}

	public String value() {
		return this.name();
	}
	

}
