/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.service;

import com.enation.app.javashop.core.base.model.dos.SensitiveWords;
import com.enation.app.javashop.framework.database.Page;

import java.util.List;

/**
 * 敏感词业务层
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-02 11:30:59
 */
public interface SensitiveWordsManager {

	/**
	 * 查询敏感词列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param keyword
     * @return Page
	 */
	Page list(int page, int pageSize, String keyword);
	/**
	 * 添加敏感词
	 * @param sensitiveWords 敏感词
	 * @return SensitiveWords 敏感词
	 */
	SensitiveWords add(SensitiveWords sensitiveWords);

	/**
	* 修改敏感词
	* @param sensitiveWords 敏感词
	* @param id 敏感词主键
	* @return SensitiveWords 敏感词
	*/
	SensitiveWords edit(SensitiveWords sensitiveWords, Integer id);
	
	/**
	 * 删除敏感词
	 * @param id 敏感词主键
	 */
	void delete(Integer id);
	
	/**
	 * 获取敏感词
	 * @param id 敏感词主键
	 * @return SensitiveWords  敏感词
	 */
	SensitiveWords getModel(Integer id);

	/**
	 * 查询需要过滤的敏感词汇
	 * @return
	 */
	List<String> listWords();

}