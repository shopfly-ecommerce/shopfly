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

import cloud.shopfly.b2c.core.system.enums.MessageCodeEnum;
import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;
import cloud.shopfly.b2c.core.system.model.dto.MessageTemplateDTO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.Map;

/**
 * Message template business layer
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 16:38:43
 */
public interface MessageTemplateManager {

	/**
	 * Example Query the message template list
	 * @param page The page number
	 * @param pageSize Number each page
	 * @param type Template type
	 * @return Page 
	 */
	Page list(int page, int pageSize, String type);

	/**
	* Modify the message template
	* @param messageTemplate The message template
	* @param id Message template primary key
	* @return MessageTemplateDO The message template
	*/
	MessageTemplateDO edit(MessageTemplateDTO messageTemplate, Integer id);

	/**
	 * Get the message template
	 * @param messageCodeEnum Message template encoding
	 * @return MessageTemplateDO  The message template
	 */
	MessageTemplateDO getModel(MessageCodeEnum messageCodeEnum);

	/**
	 * Replace text content
	 * @param content The text
	 * @param valuesMap The text to be replaced
	 * @return
	 */
	String replaceContent(String content, Map<String, Object> valuesMap);

	/**
	 * throughidAccess to the template
	 * @param id
	 * @return
	 */
	MessageTemplateDO getModel(Integer id);
}
