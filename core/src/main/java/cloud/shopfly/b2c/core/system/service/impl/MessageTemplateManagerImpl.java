/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 消息模版业务类
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
