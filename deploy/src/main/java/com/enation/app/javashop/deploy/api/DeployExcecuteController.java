/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.api;

import com.enation.app.javashop.deploy.service.DeployExecutor;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kingapex on 2018-12-28.
 * 部署执行控制器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018-12-28
 */
@RestController
@RequestMapping("/data/deploys")
public class DeployExcecuteController {

    @Autowired
    private List<DeployExecutor> deployExecutors;

    @ApiOperation(value = "执行所有部署")
    @PutMapping("/{id}/all/executor")
    public String executor(@PathVariable Integer id) {
        try {
            deployExecutors.forEach(deployExecutor -> {
                deployExecutor.deploy(id);
            });

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }


    @ApiOperation(value = "执行一种类型的部署")
    @PutMapping("/{id}/{type}/executor")
    public String executor(@PathVariable Integer id, @PathVariable String type) {
        try {
            deployExecutors.forEach(deployExecutor -> {
                if (deployExecutor.getType().equals(type)) {
                    deployExecutor.deploy(id);
                }

            });

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }


}
