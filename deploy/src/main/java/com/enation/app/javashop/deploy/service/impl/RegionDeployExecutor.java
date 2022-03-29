/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service.impl;

import com.enation.app.javashop.deploy.service.DeployExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kingapex on 2018-12-28.
 * 地区数据部署
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018-12-28
 */
@Service
public class RegionDeployExecutor implements DeployExecutor {

    @Autowired
    private  DataBaseDeployExecutor dataBaseDeployExecutor;

    @Override
    public void deploy(Integer deployId) {

        dataBaseDeployExecutor.importRegionSQl(deployId);

    }

    @Override
    public String getType() {
        return "region";
    }
}
