/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.service.impl;

import dev.shopflix.core.goods.model.dos.DraftGoodsSkuDO;
import dev.shopflix.core.goods.model.dto.GoodsDTO;
import dev.shopflix.core.goods.model.vo.GoodsSkuVO;
import dev.shopflix.core.goods.service.DraftGoodsSkuManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 草稿商品sku业务类
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 11:38:06
 */
@Service
public class DraftGoodsSkuManagerImpl implements DraftGoodsSkuManager {

	@Autowired
	
	private DaoSupport daoSupport;
	
	@Override
	@Transactional(value = "goodsTransactionManager", propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void add(GoodsDTO goodsVO, Integer draftGoodsId) {
		
		String sql = "delete from es_draft_goods_sku where draft_goods_id = ?";
		this.daoSupport.execute(sql, draftGoodsId);
		
		List<GoodsSkuVO> skuList = goodsVO.getSkuList();
		if(StringUtil.isNotEmpty(skuList)){
			for(GoodsSkuVO skuVO : skuList){
				// 将specValue 转成json放到specs
				skuVO.setSpecs(JsonUtil.objectToJson(skuVO.getSpecList()));
				skuVO.setGoodsId(draftGoodsId);
				// po vo转换
				DraftGoodsSkuDO draftGoodsSku = new DraftGoodsSkuDO(skuVO);
				this.daoSupport.insert(draftGoodsSku);
			}
		}
	}

	@Override
	public List<GoodsSkuVO> getSkuList(Integer draftGoodsId) {
		
		String sql = "select * from es_draft_goods_sku where draft_goods_id =?";
		List<DraftGoodsSkuDO> list = daoSupport.queryForList(sql, DraftGoodsSkuDO.class, draftGoodsId);
		List<GoodsSkuVO> result = new ArrayList<>();
		for (DraftGoodsSkuDO sku : list) {
			GoodsSkuVO skuVo = new GoodsSkuVO(sku);
			result.add(skuVo);
		}
		return result;
	}
}
