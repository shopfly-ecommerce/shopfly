/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service;

import com.enation.app.javashop.deploy.model.Elasticsearch;
import com.enation.app.javashop.framework.database.Page;

/**
 * elasticsearch业务层
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2019-02-13 10:39:25
 */
public interface ElasticsearchManager	{

	/**
	 * 查询elasticsearch列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return Page 
	 */
	Page list(int page, int pageSize);
	/**
	 * 添加elasticsearch
	 * @param elasticsearch elasticsearch
	 * @return Elasticsearch elasticsearch
	 */
	Elasticsearch add(Elasticsearch elasticsearch);

	/**
	* 修改elasticsearch
	* @param elasticsearch elasticsearch
	* @param id elasticsearch主键
	* @return Elasticsearch elasticsearch
	*/
	Elasticsearch edit(Elasticsearch elasticsearch, Integer id);
	
	/**
	 * 删除elasticsearch
	 * @param id elasticsearch主键
	 */
	void delete(Integer id);
	
	/**
	 * 获取elasticsearch
	 * @param id elasticsearch主键
	 * @return Elasticsearch  elasticsearch
	 */
	Elasticsearch getModel(Integer id);


	/**
	 * 根据部署 id 获取 Elasticsearch
	 * @param deployId 部署id
	 * @return Elasticsearch部署信息
	 */
	Elasticsearch getByDeployId(Integer deployId);


	/**
	 * 初始化elasticsearch
	 * @param deployId 部署id
	 */
	void initElasticsearch(Integer deployId);

	/**
	 * 测试elasticsearch连接
	 * @param elasticsearch 连接信息
	 * @return 测试结果
	 */
	boolean testConnection(Elasticsearch elasticsearch);



}