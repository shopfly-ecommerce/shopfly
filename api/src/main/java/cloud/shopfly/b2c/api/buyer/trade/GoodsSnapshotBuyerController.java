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
package cloud.shopfly.b2c.api.buyer.trade;

import cloud.shopfly.b2c.core.trade.snapshot.model.SnapshotVO;
import cloud.shopfly.b2c.core.trade.snapshot.service.GoodsSnapshotManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易快照控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-01 14:55:26
 */
@RestController
@RequestMapping("/trade/snapshots")
@Api(description = "交易快照相关API")
public class GoodsSnapshotBuyerController {

    @Autowired
    private GoodsSnapshotManager goodsSnapshotManager;


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个交易快照")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的交易快照主键", required = true, dataType = "int", paramType = "path")
    })
    public SnapshotVO get(@PathVariable Integer id) {

        return this.goodsSnapshotManager.get(id);
    }

}
