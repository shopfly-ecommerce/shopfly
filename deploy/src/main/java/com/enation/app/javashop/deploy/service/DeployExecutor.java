/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service;

/**
 * 部署执行接口
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/14
 */
public interface DeployExecutor {


   /**
    * 执行部署
    * @param deployId 部署id
    */
   void deploy(Integer deployId);

   /**
    * 定义类型
    * @return
    */
   String getType();

}
