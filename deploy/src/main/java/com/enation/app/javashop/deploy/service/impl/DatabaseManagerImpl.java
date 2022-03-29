/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.enation.app.javashop.deploy.enums.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.deploy.model.Database;
import com.enation.app.javashop.deploy.service.DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据库业务类
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-04-24 13:34:30
 */
@Service
public class DatabaseManagerImpl implements DatabaseManager{

	@Autowired
	private	DaoSupport	daoSupport;
	
	@Override
	public Page list(int page,int pageSize){
		
		String sql = "select * from es_database  ";
		Page  webPage = this.daoSupport.queryForPage(sql,page, pageSize ,Database.class );
		
		return webPage;
	}

	@Override
	public List<Database> list(Integer deployId) {
		String sql = "select * from es_database where deploy_id=?";
		List<Database>  dbList  =daoSupport.queryForList(sql, Database.class, deployId);
		return dbList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	Database  add(Database	database)	{
		this.daoSupport.insert(database);
		
		return database;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	Database  edit(Database	database,Integer id){
		this.daoSupport.update(database, id);
		return database;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	void delete( Integer id)	{
		this.daoSupport.delete(Database.class,	id);
	}
	
	@Override
	public	Database getModel(Integer id)	{
		return this.daoSupport.queryForObject(Database.class, id);
	}

	@Override
	public void initDatabase(Integer deployId) {
		ServiceType[] serviceTypes = ServiceType.values();

		//默认为每个业务创建一个数据库
		for ( ServiceType serviceType: serviceTypes  ) {
			Database	database = new Database();
			database.setDeployId(deployId);
			database.setDbName(serviceType.name());
			database.setServiceType(serviceType.name());
			database.setDbIp("127.0.0.1");
			database.setDbType("mysql");
			database.setDbPort("3306");
			database.setDbUsername("root");
			database.setDbPassword("root");

			this.add(database);
		}

	}

	@Override
	public Connection createConnection(Database database) throws ClassNotFoundException, SQLException {
		String  driver = "com.mysql.jdbc.Driver";
		String  url ="jdbc:mysql://"+ database.getDbIp()+":"+ database.getDbPort()+"/"+ database.getDbName();

		Class.forName(driver);

		Connection conn = DriverManager.getConnection(url,database.getDbUsername(),database.getDbPassword());
		return conn;
	}


	@Override
	public boolean testConnection(Database database){
		try {
			Connection conn = createConnection(database);
			conn.close();
		 	return true;
		} catch (Exception e) {
			 return  false;
		}
	}

}
