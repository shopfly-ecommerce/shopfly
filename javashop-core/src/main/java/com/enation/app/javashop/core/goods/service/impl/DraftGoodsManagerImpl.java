/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.service.impl;

import com.enation.app.javashop.core.goods.GoodsErrorCode;
import com.enation.app.javashop.core.goods.model.dos.CategoryDO;
import com.enation.app.javashop.core.goods.model.dos.DraftGoodsDO;
import com.enation.app.javashop.core.goods.model.dos.GoodsDO;
import com.enation.app.javashop.core.goods.model.dos.GoodsGalleryDO;
import com.enation.app.javashop.core.goods.model.dto.GoodsDTO;
import com.enation.app.javashop.core.goods.model.vo.DraftGoodsVO;
import com.enation.app.javashop.core.goods.service.*;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.util.DateUtil;
import com.enation.app.javashop.framework.util.JsonUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 草稿商品业务类
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-26 10:40:34
 */
@Service
public class DraftGoodsManagerImpl implements DraftGoodsManager {

	@Autowired
	@Qualifier("goodsDaoSupport")
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
	@Transactional(value = "goodsTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DraftGoodsDO add(GoodsDTO goodsVO) {

		// 没有规格给这个字段塞0
		goodsVO.setHaveSpec(StringUtil.isNotEmpty(goodsVO.getSkuList()) ? 1 : 0);

		DraftGoodsDO draftGoods = new DraftGoodsDO(goodsVO);
		// 商品状态 是否可用
		draftGoods.setCreateTime(DateUtil.getDateline());
		draftGoods.setQuantity(goodsVO.getQuantity());
		// 相册
		List<GoodsGalleryDO> galleryList = goodsVO.getGoodsGalleryList();
		if (StringUtil.isNotEmpty(galleryList)) {
			List<String> list = new ArrayList<>();
			for (GoodsGalleryDO gallery : galleryList) {
				list.add(gallery.getOriginal());
			}
			draftGoods.setOriginal(JsonUtil.objectToJson(list));
		}

		// 添加草稿箱商品
		this.daoSupport.insert(draftGoods);
		// 获取添加商品的商品ID
		Integer draftGoodsId = this.daoSupport.getLastId("es_draft_goods");
		draftGoods.setDraftGoodsId(draftGoodsId);
		// 添加商品参数
		if (StringUtil.isNotEmpty(goodsVO.getGoodsParamsList())) {
			draftGoodsParamsManager.addParams(goodsVO.getGoodsParamsList(), draftGoodsId);
		}
		// 添加商品sku信息
		draftGoodsSkuManager.add(goodsVO, draftGoodsId);

		return draftGoods;
	}

	@Override
	@Transactional(value = "goodsTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DraftGoodsDO edit(GoodsDTO goodsVO, Integer id) {

		DraftGoodsDO draftGoods = this.getModel(id);
		if(draftGoods == null){
			throw new ServiceException(GoodsErrorCode.E308.code(),"无权操作");
		}

		DraftGoodsDO goods = new DraftGoodsDO(goodsVO);
		goods.setQuantity(goodsVO.getQuantity());
		// 修改后的图片列表
		List<GoodsGalleryDO> galleryList = goodsVO.getGoodsGalleryList();
		List<String> listNew = new ArrayList<>();
		if (StringUtil.isNotEmpty(galleryList)) {
			for (GoodsGalleryDO gallery : galleryList) {
				listNew.add(gallery.getOriginal());
			}
		}

		goods.setOriginal(JsonUtil.objectToJson(listNew));

		this.daoSupport.update(goods, id);
		// 处理参数信息
		// 添加商品参数
		if (StringUtil.isNotEmpty(goodsVO.getGoodsParamsList())) {
			this.draftGoodsParamsManager.addParams(goodsVO.getGoodsParamsList(), id);
		}
		// 处理规格信息
		this.draftGoodsSkuManager.add(goodsVO, id);

		return goods;
	}

	@Override
	@Transactional(value = "goodsTransactionManager", propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
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

		//商品分类赋值
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
	@Transactional(value = "goodsTransactionManager", propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public GoodsDO addMarket(GoodsDTO goodsVO, Integer draftGoodsId) {
		Integer[] goodsIds = new Integer[]{ draftGoodsId };
		this.delete(goodsIds);
		goodsVO.setMarketEnable(1);
		GoodsDO goods = goodsManager.add( goodsVO);
		return goods;
	}

}
