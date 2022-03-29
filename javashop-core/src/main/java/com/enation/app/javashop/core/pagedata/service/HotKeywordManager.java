/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.pagedata.service;

import com.enation.app.javashop.core.pagedata.model.HotKeyword;
import com.enation.app.javashop.framework.database.Page;

import java.util.List;

/**
 * 热门关键字业务层
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-04 10:43:23
 */
public interface HotKeywordManager {

	/**
	 * 查询热门关键字列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return Page 
	 */
	Page list(int page, int pageSize);
	/**
	 * 添加热门关键字
	 * @param hotKeyword 热门关键字
	 * @return HotKeyword 热门关键字
	 */
	HotKeyword add(HotKeyword hotKeyword);

	/**
	* 修改热门关键字
	* @param hotKeyword 热门关键字
	* @param id 热门关键字主键
	* @return HotKeyword 热门关键字
	*/
	HotKeyword edit(HotKeyword hotKeyword, Integer id);
	
	/**
	 * 删除热门关键字
	 * @param id 热门关键字主键
	 */
	void delete(Integer id);
	
	/**
	 * 获取热门关键字
	 * @param id 热门关键字主键
	 * @return HotKeyword  热门关键字
	 */
	HotKeyword getModel(Integer id);

	/**
	 * 查询热门关键字
	 * @param num
	 * @return
	 */
	List<HotKeyword> listByNum(Integer num);
}