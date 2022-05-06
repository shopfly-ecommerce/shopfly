/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.service.impl;

import cloud.shopfly.b2c.core.goods.model.dos.DraftGoodsSkuDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.service.DraftGoodsSkuManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Transactional( propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
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
