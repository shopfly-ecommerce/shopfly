/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service.impl;

import com.enation.app.javashop.deploy.model.Deploy;
import com.enation.app.javashop.deploy.service.*;
import com.enation.app.javashop.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;


/**
 * 部署业务类
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-04-23 14:27:13
 */
@Service
public class DeployManagerImpl implements DeployManager {

	@Autowired
	private	DaoSupport	daoSupport;

	@Autowired
	private DatabaseManager databaseManager;

	@Autowired
	private RedisManager redisManager;

	@Autowired
	private RabbitmqManager rabbitmqManager;

	@Autowired
	private ElasticsearchManager elasticsearchManager;

	@Override
	public Page list(int page,int pageSize){

		String sql = "select * from es_deploy order by deploy_id desc ";
		Page  webPage = this.daoSupport.queryForPage(sql,page, pageSize ,Deploy.class );
		
		return webPage;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	Deploy  add(Deploy	deploy)	{

		deploy.setCreateTime(DateUtil.getDateline());
		this.daoSupport.insert(deploy);
		Integer deployId = this.daoSupport.queryForInt("select max(deploy_id) from es_deploy");

		//初始化数据库设置
		databaseManager.initDatabase(deployId);

		//初始化Redis
		redisManager.initRedis(deployId);

		//初始化RabbitMq
		rabbitmqManager.initRabbitMq(deployId);

		//初始化elasticsearch
		elasticsearchManager.initElasticsearch(deployId);

		deploy.setDeployId(deployId);
		return deploy;
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	Deploy  edit(Deploy	deploy,Integer id){
		this.daoSupport.update(deploy, id);
		return deploy;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	void delete( Integer id)	{
		this.daoSupport.delete(Deploy.class,	id);
	}

	@Override
	public	Deploy getModel(Integer id)	{
		return this.daoSupport.queryForObject(Deploy.class, id);
	}


	

}
