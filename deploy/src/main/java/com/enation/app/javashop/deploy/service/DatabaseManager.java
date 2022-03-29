/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service;

import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.deploy.model.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据库业务层
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-04-24 13:34:30
 */
public interface DatabaseManager	{

	/**
	 * 查询数据库列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return Page 
	 */
	Page list(int page, int pageSize);

	/**
	 * 获取某个部署的数据库列表
	 * @param deployId 部署id
	 * @return 数据库列表
	 */
	List<Database> list(Integer deployId);

	/**
	 * 添加数据库
	 * @param database 数据库
	 * @return Database 数据库
	 */
	Database add(Database database);

	/**
	* 修改数据库
	* @param database 数据库
	* @param id 数据库主键
	* @return Database 数据库
	*/
	Database edit(Database database, Integer id);
	
	/**
	 * 删除数据库
	 * @param id 数据库主键
	 */
	void delete(Integer id);
	
	/**
	 * 获取数据库
	 * @param id 数据库主键
	 * @return Database  数据库
	 */
	Database getModel(Integer id);


	/**
	 * 初始始化数据库
	 * @param deployId 部署id
	 */
	void initDatabase(Integer deployId);

	/**
	 * 根据数据库信息创建连接
	 * @param database 数据库配置信息
	 * @return 数据库连接
	 * @throws ClassNotFoundException
	 * @throws  SQLException
	 */
	Connection createConnection(Database database) throws ClassNotFoundException, SQLException;


	/**
	 * 检测数据库连接
	 * @param database
	 * @return 如果参数正确返回真，否则返回假
	 */
	boolean testConnection(Database database);


}