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
 * 消息模版业务层
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 16:38:43
 */
public interface MessageTemplateManager {

	/**
	 * 查询消息模版列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param type 模版类型
	 * @return Page 
	 */
	Page list(int page, int pageSize, String type);

	/**
	* 修改消息模版
	* @param messageTemplate 消息模版
	* @param id 消息模版主键
	* @return MessageTemplateDO 消息模版
	*/
	MessageTemplateDO edit(MessageTemplateDTO messageTemplate, Integer id);

	/**
	 * 获取消息模版
	 * @param messageCodeEnum 消息模版编码
	 * @return MessageTemplateDO  消息模版
	 */
	MessageTemplateDO getModel(MessageCodeEnum messageCodeEnum);

	/**
	 * 替换文本内容
	 * @param content 文本
	 * @param valuesMap 要替换的文字
	 * @return
	 */
	String replaceContent(String content, Map<String, Object> valuesMap);

	/**
	 * 通过id获取模版
	 * @param id
	 * @return
	 */
	MessageTemplateDO getModel(Integer id);
}