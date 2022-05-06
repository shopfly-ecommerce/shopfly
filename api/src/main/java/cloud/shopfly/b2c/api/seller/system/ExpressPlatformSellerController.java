/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.api.seller.system;

import cloud.shopfly.b2c.core.system.model.dos.ExpressPlatformDO;
import cloud.shopfly.b2c.core.system.model.dos.SmsPlatformDO;
import cloud.shopfly.b2c.core.system.model.vo.ExpressPlatformVO;
import cloud.shopfly.b2c.core.system.service.ExpressPlatformManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 快递平台控制器
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-11 14:42:50
 */
@RestController
@RequestMapping("/seller/systems/express-platforms")
@Api(description = "快递平台相关API")
public class ExpressPlatformSellerController {

    @Autowired
    private ExpressPlatformManager expressPlatformManager;


    @ApiOperation(value = "查询快递平台列表", response = ExpressPlatformDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        return this.expressPlatformManager.list(pageNo, pageSize);
    }

    @ApiOperation(value = "修改快递平台", response = SmsPlatformDO.class)
    @PutMapping(value = "/{bean}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bean", value = "快递平台bean id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "express_platform", value = "快递平台对象", required = true, dataType = "ExpressPlatformVO", paramType = "body")
    })
    public ExpressPlatformVO edit(@PathVariable String bean, @RequestBody @ApiIgnore ExpressPlatformVO expressPlatformVO) {
        expressPlatformVO.setBean(bean);
        return this.expressPlatformManager.edit(expressPlatformVO);
    }

    @ApiOperation(value = "获取快递平台的配置", response = String.class)
    @GetMapping("/{bean}")
    @ApiImplicitParam(name = "bean", value = "快递平台bean id", required = true, dataType = "String", paramType = "path")
    public ExpressPlatformVO getUploadSetting(@PathVariable String bean) {
        return this.expressPlatformManager.getExoressConfig(bean);
    }

    @ApiOperation(value = "开启某个快递平台方案", response = String.class)
    @PutMapping("/{bean}/open")
    @ApiImplicitParam(name = "bean", value = "bean", required = true, dataType = "String", paramType = "path")
    public String open(@PathVariable String bean) {
        this.expressPlatformManager.open(bean);
        return null;
    }


}
