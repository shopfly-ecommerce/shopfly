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
package cloud.shopfly.b2c.api.buyer.goods;

import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsShowDetail;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.service.CategoryManager;
import cloud.shopfly.b2c.core.goods.service.GoodsGalleryManager;
import cloud.shopfly.b2c.core.goods.service.GoodsParamsManager;
import cloud.shopfly.b2c.core.goods.service.GoodsQueryManager;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
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
 * Merchandise controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
@RestController
@RequestMapping("/goods")
@Api(description = "commodity-relatedAPI")
public class GoodsBuyerController {

	@Autowired
	private GoodsQueryManager goodsQueryManager;

	@Autowired
	private GoodsParamsManager goodsParamsManager;

	@Autowired
	private CategoryManager categoryManager;

	@Autowired
	private GoodsGalleryManager goodsGalleryManager;


    @ApiOperation(value = "For details of products,Static part use")
    @ApiImplicitParam(name = "goods_id", value = "CategoriesidAnd the top of0", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{goods_id}")
    public GoodsShowDetail getGoodsDetail(@PathVariable("goods_id") Integer goodsId) {

		GoodsDO goods = goodsQueryManager.getModel(goodsId);
		GoodsShowDetail detail = new GoodsShowDetail();
		if (goods == null){
			throw new ResourceNotFoundException("This item does not exist");
		}
		BeanUtils.copyProperties(goods,detail);
		Integer goodsOff = 0;
		// Item does not exist, return directly
		if(goods == null){
			detail.setGoodsOff(goodsOff);
			return detail;
		}
		// Categories
		CategoryDO category = categoryManager.getModel(goods.getCategoryId());
		detail.setCategoryName(category == null ? "":category.getName());
		// On state
		if(goods.getMarketEnable()==1){
			goodsOff = 1;
		}
		detail.setGoodsOff(goodsOff);
		// parameter
		List<GoodsParamsGroupVO> list = this.goodsParamsManager.queryGoodsParams(goods.getCategoryId(),goodsId);
		detail.setParamList(list);
		// Photo album
		List<GoodsGalleryDO> galleryList = goodsGalleryManager.list(goodsId);
		detail.setGalleryList(galleryList);

		// Goods have a good flat rate
		detail.setGrade(goodsQueryManager.getGoodsGrade(goodsId));

		return detail;
	}

    @ApiOperation(value = "To obtainskuInformation, dynamic section of product details page")
    @ApiImplicitParam(name = "goods_id", value = "productid", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{goods_id}/skus")
    public List<GoodsSkuVO> getGoodsSkus(@PathVariable("goods_id") Integer goodsId) {

        CacheGoods goods = goodsQueryManager.getFromCache(goodsId);

        return goods.getSkuList();
    }

	@ApiOperation(value = "Check if the goods are in the distribution area1 In stock0 Is not available", notes = "Check if the goods are in the distribution area")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "goods_id", value = "productID", required = true, paramType = "path", dataType = "int"),
			@ApiImplicitParam(name = "area_id", value = "regionID", required = true, paramType = "path", dataType = "int")
	})
	@GetMapping(value = "/{goods_id}/area/{area_id}")
	public Integer checkGoodsArea(@PathVariable("goods_id") Integer goodsId,@PathVariable("area_id") Integer areaId) {

		return this.goodsQueryManager.checkArea(goodsId,areaId);
	}


}
