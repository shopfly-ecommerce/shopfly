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
package cloud.shopfly.b2c.core.system.service.impl;

import cloud.shopfly.b2c.core.system.enums.MessageCodeEnum;
import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;
import cloud.shopfly.b2c.core.system.model.dto.MessageTemplateDTO;
import cloud.shopfly.b2c.core.system.service.MessageTemplateManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Message template business class
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 16:38:43
 */
@Service
public class MessageTemplateManagerImpl implements MessageTemplateManager {

	@Autowired
	
	private DaoSupport daoSupport;
	
	@Override
	public Page list(int page, int pageSize, String type){
		
		String sql = "select * from es_message_template where type = ? ";
		Page webPage = this.daoSupport.queryForPage(sql,page, pageSize , MessageTemplateDO.class ,type);
		return webPage;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public MessageTemplateDO edit(MessageTemplateDTO messageTemplate, Integer id){
		this.daoSupport.update(messageTemplate, id);
		return this.getModel(id);
	}

	
	@Override
	public MessageTemplateDO getModel(MessageCodeEnum messageCodeEnum )	{
		String sql = "SELECT * FROM es_message_template WHERE tpl_code = ? ";
		return this.daoSupport.queryForObject(sql, MessageTemplateDO.class, messageCodeEnum.value());
	}

	@Override
	public String replaceContent(String content, Map<String, Object> valuesMap) {
		StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
		return strSubstitutor.replace(content);
	}

	@Override
	public MessageTemplateDO getModel(Integer id) {
		return this.daoSupport.queryForObject(MessageTemplateDO.class, id);
	}
}
