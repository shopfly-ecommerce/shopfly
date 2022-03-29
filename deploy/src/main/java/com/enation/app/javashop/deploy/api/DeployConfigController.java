/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.api;

import com.enation.app.javashop.deploy.model.Elasticsearch;
import com.enation.app.javashop.deploy.model.Rabbitmq;
import com.enation.app.javashop.deploy.model.Redis;
import com.enation.app.javashop.deploy.service.ElasticsearchManager;
import com.enation.app.javashop.deploy.service.RabbitmqManager;
import com.enation.app.javashop.deploy.service.RedisManager;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 部署  配置信息控制器
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/9
 */
@RestController
@RequestMapping("/data/deploys/{deployId}")
public class DeployConfigController {

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private RabbitmqManager rabbitmqManager;

    @Autowired
    private ElasticsearchManager elasticsearchManager;

    @GetMapping(value =	"/redis")
    @ApiOperation(value	= "查询某个部署的redis配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deploy_id",	value = "部署的i",	required = true, dataType = "int",	paramType = "path")
    })
    public Redis getRedis (@PathVariable Integer deployId){
        Redis redis = redisManager.getByDeployId(deployId);
        return redis;
    }


    @GetMapping(value =	"/rabbitmq")
    @ApiOperation(value	= "查询某个部署的rabbitmq配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deploy_id",	value = "部署的i",	required = true, dataType = "int",	paramType = "path")
    })
    public Rabbitmq getRabbitmq (@PathVariable Integer deployId){
        Rabbitmq rabbitmq = rabbitmqManager.getByDeployId(deployId);
        return rabbitmq;
    }


    @GetMapping(value =	"/elasticsearch")
    @ApiOperation(value	= "查询某个部署的elasticsearch配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deploy_id",	value = "部署的id",	required = true, dataType = "int",	paramType = "path")
    })
    public Elasticsearch getElasticsearch (@PathVariable Integer deployId){
        return  elasticsearchManager.getByDeployId(deployId);
    }


}
