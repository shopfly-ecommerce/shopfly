/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.distribution;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.model.vo.SuccessMessage;
import cloud.shopfly.b2c.core.client.distribution.DistributionGoodsClient;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionGoods;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionSetting;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分销商品
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/30 上午9:42
 */
@RestController
@RequestMapping("/seller/distribution")
@Api(description = "分销商品API")
public class DistributionGoodsSellerController {


    @Autowired
    private DistributionGoodsClient distributionGoodsClient;


    @Autowired
    private SettingClient settingClient;

    @ApiOperation(value = "分销商品返利获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_id", value = "商品id", required = true, dataType = "int", paramType = "path"),
    })
    @GetMapping(value = "/goods/{goods_id}")
    public DistributionGoods querySetting(@PathVariable("goods_id") Integer goodsId) {
        return distributionGoodsClient.getModel(goodsId);
    }


    @ApiOperation(value = "分销商品返利设置")
    @PutMapping(value = "/goods")
    public DistributionGoods settingGoods(DistributionGoods distributionGoods) {
        return distributionGoodsClient.edit(distributionGoods);
    }


    @ApiOperation(value = "获取分销设置:1开启/0关闭")
    @GetMapping(value = "/setting")
    public SuccessMessage setting() {
        DistributionSetting ds = JsonUtil.jsonToObject(settingClient.get(SettingGroup.DISTRIBUTION), DistributionSetting.class);
        return new SuccessMessage(ds.getGoodsModel());
    }


}
