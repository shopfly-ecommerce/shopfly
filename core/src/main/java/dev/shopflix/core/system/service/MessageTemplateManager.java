/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service;

import dev.shopflix.core.system.model.dos.MessageTemplateDO;
import dev.shopflix.core.system.model.dto.MessageTemplateDTO;
import dev.shopflix.core.system.enums.MessageCodeEnum;
import dev.shopflix.framework.database.Page;

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