/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
