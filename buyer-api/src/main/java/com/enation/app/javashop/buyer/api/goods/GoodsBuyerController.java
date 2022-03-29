/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer.api.goods;

import com.enation.app.javashop.core.goods.model.dos.CategoryDO;
import com.enation.app.javashop.core.goods.model.dos.GoodsDO;
import com.enation.app.javashop.core.goods.model.dos.GoodsGalleryDO;
import com.enation.app.javashop.core.goods.model.vo.CacheGoods;
import com.enation.app.javashop.core.goods.model.vo.GoodsParamsGroupVO;
import com.enation.app.javashop.core.goods.model.vo.GoodsShowDetail;
import com.enation.app.javashop.core.goods.model.vo.GoodsSkuVO;
import com.enation.app.javashop.core.goods.service.CategoryManager;
import com.enation.app.javashop.core.goods.service.GoodsGalleryManager;
import com.enation.app.javashop.core.goods.service.GoodsParamsManager;
import com.enation.app.javashop.core.goods.service.GoodsQueryManager;
import com.enation.app.javashop.framework.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品控制器
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
@RestController
@RequestMapping("/goods")
@Api(description = "商品相关API")
public class GoodsBuyerController {

	@Autowired
	private GoodsQueryManager goodsQueryManager;

	@Autowired
	private GoodsParamsManager goodsParamsManager;

	@Autowired
	private CategoryManager categoryManager;

	@Autowired
	private GoodsGalleryManager goodsGalleryManager;


    @ApiOperation(value = "浏览商品的详情,静态部分使用")
    @ApiImplicitParam(name = "goods_id", value = "分类id，顶级为0", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{goods_id}")
    public GoodsShowDetail getGoodsDetail(@PathVariable("goods_id") Integer goodsId) {

		GoodsDO goods = goodsQueryManager.getModel(goodsId);
		GoodsShowDetail detail = new GoodsShowDetail();
		if (goods == null){
			throw new ResourceNotFoundException("不存在此商品");
		}
		BeanUtils.copyProperties(goods,detail);
		Integer goodsOff = 0;
		//商品不存在，直接返回
		if(goods == null){
			detail.setGoodsOff(goodsOff);
			return detail;
		}
		//分类
		CategoryDO category = categoryManager.getModel(goods.getCategoryId());
		detail.setCategoryName(category == null ? "":category.getName());
		//上架状态
		if(goods.getMarketEnable()==1){
			goodsOff = 1;
		}
		detail.setGoodsOff(goodsOff);
		//参数
		List<GoodsParamsGroupVO> list = this.goodsParamsManager.queryGoodsParams(goods.getCategoryId(),goodsId);
		detail.setParamList(list);
		//相册
		List<GoodsGalleryDO> galleryList = goodsGalleryManager.list(goodsId);
		detail.setGalleryList(galleryList);

		//商品好平率
		detail.setGrade(goodsQueryManager.getGoodsGrade(goodsId));

		return detail;
	}

    @ApiOperation(value = "获取sku信息，商品详情页动态部分")
    @ApiImplicitParam(name = "goods_id", value = "商品id", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{goods_id}/skus")
    public List<GoodsSkuVO> getGoodsSkus(@PathVariable("goods_id") Integer goodsId) {

        CacheGoods goods = goodsQueryManager.getFromCache(goodsId);

        return goods.getSkuList();
    }

	@ApiOperation(value = "查看商品是否在配送区域 1 有货  0 无货", notes = "查看商品是否在配送区域")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "goods_id", value = "商品ID", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "area_id", value = "地区ID", required = true, paramType = "path", dataType = "int")
	})
	@GetMapping(value = "/{goods_id}/area/{area_id}")
	public Integer checkGoodsArea(@PathVariable("goods_id") Integer goodsId,@PathVariable("area_id") Integer areaId) {

		return this.goodsQueryManager.checkArea(goodsId,areaId);
	}


}
