/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.goods;

import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsSkuQuantityDTO;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.service.GoodsQuantityManager;
import cloud.shopfly.b2c.core.goods.service.GoodsQueryManager;
import cloud.shopfly.b2c.framework.ShopflixConfig;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品库存维护
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午11:23:05
 */
@Api(description = "商家中心商品库存单独维护api")
@RestController
@RequestMapping("/seller/goods/{goods_id}/quantity")
@Validated
public class GoodsQuantitySellerController {

    @Autowired
    private GoodsQueryManager goodsQueryManager;
    @Autowired
    private GoodsQuantityManager goodsQuantityManager;
    @Autowired
    private ShopflixConfig shopflixConfig;

    @ApiOperation(value = "商家单独维护库存接口", notes = "商家单独维护库存接口时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_id", value = "商品id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "sku_quantity_list", value = "库存集合，是个数组", required = true, dataType = "GoodsSkuQuantityDTO", paramType = "body", allowMultiple = true),
    })
    @PutMapping
    public void updateQuantity(@ApiIgnore @Valid @RequestBody List<GoodsSkuQuantityDTO> skuQuantityList, @PathVariable("goods_id") Integer goodsId) {

        CacheGoods goods = goodsQueryManager.getFromCache(goodsId);

        if (goods == null) {
            throw new ServiceException(GoodsErrorCode.E307.code(), "没有操作权限");
        }

        // 原有的sku集合
        List<GoodsSkuVO> skuList = goods.getSkuList();
        Map<Integer, GoodsSkuVO> skuMap = new HashMap<>(skuList.size());
        for (GoodsSkuVO sku : skuList) {
            skuMap.put(sku.getSkuId(), sku);
        }


        //要更新的库存列表
        List<GoodsQuantityVO> stockList = new ArrayList<>();

        for (GoodsSkuQuantityDTO quantity : skuQuantityList) {

            if (quantity.getQuantityCount() == null || quantity.getQuantityCount() < 0) {
                throw new ServiceException(GoodsErrorCode.E307.code(), "sku总库存不能为空或负数");
            }

            GoodsSkuVO sku = skuMap.get(quantity.getSkuId());
            if (sku == null) {
                throw new ServiceException(GoodsErrorCode.E307.code(), "商品sku不存在");
            }
            //待发货数
            Integer waitRogCount = sku.getQuantity() - sku.getEnableQuantity();
            //判断库存是否小于待发货数
            if (quantity.getQuantityCount() < waitRogCount) {
                throw new ServiceException(GoodsErrorCode.E307.code(), "sku库存数不能小于待发货数");
            }

            //实际库存
            GoodsQuantityVO actualQuantityVo = new GoodsQuantityVO();
            //用传递的数量-现有的，就是变化的，如传递的是2000，原来是200，则就+1800，如果传递的是100，原来是200则就是-100
            int stockNum = quantity.getQuantityCount() - sku.getQuantity();
            actualQuantityVo.setQuantity(stockNum);
            actualQuantityVo.setGoodsId(goodsId);
            actualQuantityVo.setQuantityType(QuantityType.actual);
            actualQuantityVo.setSkuId(quantity.getSkuId());

            stockList.add(actualQuantityVo);

            //clone 一个quantity vo 设置为更新可用库存
            try {
                GoodsQuantityVO enableVo = (GoodsQuantityVO) actualQuantityVo.clone();
                enableVo.setQuantityType(QuantityType.enable);
                stockList.add(enableVo);
            } catch (CloneNotSupportedException e) {
                throw new ServiceException(GoodsErrorCode.E307.code(), "goodsQuantityVo clone error");
            }

        }

        //更新库存
        this.goodsQuantityManager.updateSkuQuantity(stockList);

        //如果商品库存缓冲池开启了，那么需要立即同步数据库的商品库存，以保证商品库存显示正常
        if (shopflixConfig.isStock()) {
            //立即同步数据库的库存
            goodsQuantityManager.syncDataBase();
        }
    }

}
