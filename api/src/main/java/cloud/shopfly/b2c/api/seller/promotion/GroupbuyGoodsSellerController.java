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
package cloud.shopfly.b2c.api.seller.promotion;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyQueryParam;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyActiveManager;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 团购商品控制器
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:57:26
 */
@RestController
@RequestMapping("/seller/promotion/group-buy-goods")
@Api(description = "团购商品相关API")
@Validated
public class GroupbuyGoodsSellerController	{

	@Autowired
	private GroupbuyGoodsManager groupbuyGoodsManager;

	@Autowired
	private GroupbuyActiveManager groupbuyActiveManager;

	@ApiOperation(value	= "查询团购商品列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name	= "act_id", value = "团购活动id",required = true,dataType = "int",paramType =	"query"),
			@ApiImplicitParam(name	= "keywords", value = "关键字", dataType = "String",	paramType =	"query"),
			@ApiImplicitParam(name	= "goods_name", value = "商品名称", dataType = "String",	paramType =	"query"),
			@ApiImplicitParam(name	= "start_time", value = "开始时间", dataType = "long",paramType =	"query"),
			@ApiImplicitParam(name	= "end_time", value = "结束时间", dataType = "long",paramType =	"query"),
			@ApiImplicitParam(name	= "page_no", value = "页码", dataType = "int",	paramType =	"query"),
			@ApiImplicitParam(name	= "page_size", value = "条数", dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page<GroupbuyGoodsVO> list(@ApiIgnore @NotNull(message = "活动ID必传") Integer actId, @ApiIgnore String keywords, @ApiIgnore String goodsName,
									   @ApiIgnore Long startTime, @ApiIgnore Long endTime, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

		GroupbuyQueryParam param = new GroupbuyQueryParam();
		param.setActId(actId);
		param.setKeywords(keywords);
		param.setGoodsName(goodsName);
		param.setStartTime(startTime);
		param.setEndTime(endTime);
		param.setPage(pageNo);
		param.setPageSize(pageSize);
		Page webPage = this.groupbuyGoodsManager.listPage(param);
		return webPage;
	}


	@ApiOperation(value	= "添加团购商品", response = GroupbuyGoodsDO.class)
	@ApiImplicitParam(name = "groupbuyGoods", value = "团购商品信息", required = true, dataType = "GroupbuyGoodsDO", paramType = "body")
	@PostMapping
	public GroupbuyGoodsDO add(@Valid  GroupbuyGoodsDO groupbuyGoods) {
		groupbuyGoods.setAddTime(DateUtil.getDateline());
		this.verifyParam(groupbuyGoods);
		groupbuyGoods.setBuyNum(0);
		groupbuyGoods.setViewNum(0);
		this.groupbuyGoodsManager.add(groupbuyGoods);

		return	groupbuyGoods;
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "修改团购商品", response = GroupbuyGoodsDO.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public GroupbuyGoodsDO edit(@Valid  GroupbuyGoodsDO groupbuyGoods, @PathVariable Integer id) {

		this.verifyParam(groupbuyGoods);
		this.groupbuyGoodsManager.verifyAuth(id);
		this.groupbuyGoodsManager.edit(groupbuyGoods,id);

		return	groupbuyGoods;
	}


	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除团购商品")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"要删除的团购商品主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {

		this.groupbuyGoodsManager.verifyAuth(id);
		this.groupbuyGoodsManager.delete(id);

		return "";
	}


	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个团购商品")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "要查询的团购商品主键",	required = true, dataType = "int",	paramType = "path")
	})
	public GroupbuyGoodsVO get(@PathVariable Integer id)	{
		GroupbuyGoodsVO groupbuyGoods = this.groupbuyGoodsManager.getModelAndQuantity(id);
		if(groupbuyGoods == null){
			throw new NoPermissionException("无权操作");
		}
		return	groupbuyGoods;
	}


	@ApiOperation(value	= "查询可以参与的团购活动列表", response = GroupbuyGoodsDO.class)
	@GetMapping(value = "/active")
	public List<GroupbuyActiveDO> listActive()	{

		return	this.groupbuyActiveManager.getActiveList();
	}


	/**
	 * 验证参数
	 * @param goodsDO
	 */
	private void verifyParam(GroupbuyGoodsDO goodsDO){

		String gbName = goodsDO.getGbName();
		if(!StringUtil.validMaxLen(gbName,30)){
			throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"团购名称字数超限");
		}

	}

}
