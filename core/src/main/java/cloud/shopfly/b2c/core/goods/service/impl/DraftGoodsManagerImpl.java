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
package cloud.shopfly.b2c.core.goods.service.impl;

import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.DraftGoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.core.goods.model.vo.DraftGoodsVO;
import cloud.shopfly.b2c.core.goods.service.*;
import cloud.shopfly.b2c.core.goods.service.*;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Draft commodity business class
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-26 10:40:34
 */
@Service
public class DraftGoodsManagerImpl implements DraftGoodsManager {

	@Autowired
	
	private DaoSupport daoSupport;

	@Autowired
	private DraftGoodsParamsManager draftGoodsParamsManager;

	@Autowired
	private DraftGoodsSkuManager draftGoodsSkuManager;

	@Autowired
	private GoodsManager goodsManager;

	@Autowired
	private GoodsQueryManager goodsQueryManager;

	@Autowired
	private CategoryManager categoryManager;

	@Override
	public Page list(int page, int pageSize, String keyword, String shopCatPath) {

		StringBuffer sqlBuffer = new StringBuffer("select * from es_draft_goods where draft_goods_id !=0 ");
		List<Object> term = new ArrayList<>();

		if(!StringUtil.isEmpty(keyword)){
			sqlBuffer.append(" and (goods_name like ? or sn like ? )");
			term.add("%"+keyword+"%");
			term.add("%"+keyword+"%");
		}

		sqlBuffer.append(" order by create_time desc ");
		Page webPage = this.daoSupport.queryForPage(sqlBuffer.toString(), page, pageSize, DraftGoodsDO.class,term.toArray());

		return webPage;
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DraftGoodsDO add(GoodsDTO goodsVO) {

		// There is no specification to fill this field with 0
		goodsVO.setHaveSpec(StringUtil.isNotEmpty(goodsVO.getSkuList()) ? 1 : 0);

		DraftGoodsDO draftGoods = new DraftGoodsDO(goodsVO);
		// Whether the status of the item is available
		draftGoods.setCreateTime(DateUtil.getDateline());
		draftGoods.setQuantity(goodsVO.getQuantity());
		// Photo album
		List<GoodsGalleryDO> galleryList = goodsVO.getGoodsGalleryList();
		if (StringUtil.isNotEmpty(galleryList)) {
			List<String> list = new ArrayList<>();
			for (GoodsGalleryDO gallery : galleryList) {
				list.add(gallery.getOriginal());
			}
			draftGoods.setOriginal(JsonUtil.objectToJson(list));
		}

		// Add draft box goods
		this.daoSupport.insert(draftGoods);
		// Gets the ID of the item to which the item is added
		Integer draftGoodsId = this.daoSupport.getLastId("es_draft_goods");
		draftGoods.setDraftGoodsId(draftGoodsId);
		// Add commodity parameters
		if (StringUtil.isNotEmpty(goodsVO.getGoodsParamsList())) {
			draftGoodsParamsManager.addParams(goodsVO.getGoodsParamsList(), draftGoodsId);
		}
		// Add product SKU information
		draftGoodsSkuManager.add(goodsVO, draftGoodsId);

		return draftGoods;
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DraftGoodsDO edit(GoodsDTO goodsVO, Integer id) {

		DraftGoodsDO draftGoods = this.getModel(id);
		if(draftGoods == null){
			throw new ServiceException(GoodsErrorCode.E308.code(),"Have the right to operate");
		}

		DraftGoodsDO goods = new DraftGoodsDO(goodsVO);
		goods.setQuantity(goodsVO.getQuantity());
		// List of modified images
		List<GoodsGalleryDO> galleryList = goodsVO.getGoodsGalleryList();
		List<String> listNew = new ArrayList<>();
		if (StringUtil.isNotEmpty(galleryList)) {
			for (GoodsGalleryDO gallery : galleryList) {
				listNew.add(gallery.getOriginal());
			}
		}

		goods.setOriginal(JsonUtil.objectToJson(listNew));

		this.daoSupport.update(goods, id);
		// Processing parameter Information
		// Add commodity parameters
		if (StringUtil.isNotEmpty(goodsVO.getGoodsParamsList())) {
			this.draftGoodsParamsManager.addParams(goodsVO.getGoodsParamsList(), id);
		}
		// Processing specification information
		this.draftGoodsSkuManager.add(goodsVO, id);

		return goods;
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(Integer[] draftGoodsIds) {
		for(Integer id : draftGoodsIds){
			this.daoSupport.delete(DraftGoodsDO.class, id);
		}
	}

	@Override
	public DraftGoodsDO getModel(Integer id) {
		return this.daoSupport.queryForObject(DraftGoodsDO.class, id);
	}


	@Override
	public DraftGoodsVO getVO(Integer id) {
		DraftGoodsVO draftGoodsVO = this.daoSupport.queryForObject("select * from es_draft_goods where draft_goods_id = ?", DraftGoodsVO.class,id);
		draftGoodsVO.setCategoryName(goodsQueryManager.queryCategoryPath(draftGoodsVO.getCategoryId()));

		// Item category assignment
		Integer categoryId = draftGoodsVO.getCategoryId();
		CategoryDO category = categoryManager.getModel(categoryId);
		String sql = "select name,category_id from es_category " +
				"where category_id in (" + category.getCategoryPath().replace("|", ",") + "-1) " +
				"order by category_id asc";
		List<Map> list = this.daoSupport.queryForList(sql);
		String categoryName = "";
		Integer[] categoryIds = new Integer[3];
		int i = 0;
		if (StringUtil.isNotEmpty(list)) {
			for (Map map : list) {
				if ("".equals(categoryName)) {
					categoryName = " " + map.get("name").toString();
				} else {
					categoryName += ">" + map.get("name").toString() + " ";
				}
				categoryIds[i] = StringUtil.toInt(map.get("category_id"), false);
				i++;
			}
		}
		draftGoodsVO.setCategoryIds(categoryIds);

		return draftGoodsVO;
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public GoodsDO addMarket(GoodsDTO goodsVO, Integer draftGoodsId) {
		Integer[] goodsIds = new Integer[]{ draftGoodsId };
		this.delete(goodsIds);
		goodsVO.setMarketEnable(1);
		GoodsDO goods = goodsManager.add( goodsVO);
		return goods;
	}

}
