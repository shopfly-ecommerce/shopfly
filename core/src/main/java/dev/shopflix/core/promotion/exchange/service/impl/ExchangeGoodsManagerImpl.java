/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.exchange.service.impl;

import dev.shopflix.core.promotion.exchange.model.dos.ExchangeDO;
import dev.shopflix.core.promotion.exchange.model.dto.ExchangeQueryParam;
import dev.shopflix.core.promotion.exchange.service.ExchangeGoodsManager;
import dev.shopflix.core.promotion.tool.model.dto.PromotionDetailDTO;
import dev.shopflix.core.promotion.tool.model.dto.PromotionGoodsDTO;
import dev.shopflix.core.promotion.tool.model.enums.PromotionTypeEnum;
import dev.shopflix.core.promotion.tool.service.PromotionGoodsManager;
import dev.shopflix.core.promotion.tool.support.PromotionCacheKeys;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分商品业务类
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:47:18
 */
@Service
public class ExchangeGoodsManagerImpl implements ExchangeGoodsManager {

    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private Cache cache;


    @Override
    public Page list(ExchangeQueryParam param) {

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        sql.append("select e.*,pg.start_time,pg.end_time,pg.title from es_promotion_goods pg " +
                " left join es_exchange e on e.exchange_id = pg.activity_id " +
                " where pg.promotion_type = ? and exchange_id is not null ");
        paramList.add(PromotionTypeEnum.EXCHANGE.name());

        if (param.getCatId() != null) {
            sql.append(" and e.category_id = ?");
            paramList.add(param.getCatId());
        }

        if (param.getStartTime() != null && param.getEndTime() != null) {
            sql.append("  and ? > pg.start_time and ? < pg.end_time  ");
            paramList.add(param.getStartTime());
            paramList.add(param.getEndTime());
        }

        Page page = this.daoSupport.queryForPage(sql.toString(), param.getPageNo(), param.getPageSize(), ExchangeDO.class, paramList.toArray());
        return page;
    }

    @Override
    public ExchangeDO add(ExchangeDO exchangeSetting, PromotionGoodsDTO goodsDTO) {

        exchangeSetting.setGoodsId(goodsDTO.getGoodsId());
        exchangeSetting.setGoodsName(goodsDTO.getGoodsName());
        exchangeSetting.setGoodsPrice(goodsDTO.getPrice());
        exchangeSetting.setGoodsImg(goodsDTO.getThumbnail());

        if (exchangeSetting != null && exchangeSetting.getEnableExchange() == 1) {
            if (exchangeSetting.getCategoryId() == null) {
                exchangeSetting.setCategoryId(0);
            }

            this.daoSupport.insert(exchangeSetting);
            int settingId = this.daoSupport.getLastId("es_exchange");

            exchangeSetting.setExchangeId(settingId);
            long nowTime = DateUtil.getDateline();
            long endTime = DateUtil.endOfSomeDay(365);

            PromotionDetailDTO detailDTO = new PromotionDetailDTO();
            detailDTO.setTitle("积分活动");
            detailDTO.setPromotionType(PromotionTypeEnum.EXCHANGE.name());
            detailDTO.setActivityId(settingId);
            detailDTO.setStartTime(nowTime);
            detailDTO.setEndTime(endTime);

            this.promotionGoodsManager.addModel(goodsDTO.getGoodsId(), detailDTO);
            this.cache.put(PromotionCacheKeys.getExchangeKey(settingId), exchangeSetting);
        }

        return exchangeSetting;
    }

    @Override
    public ExchangeDO edit(ExchangeDO exchangeSetting, PromotionGoodsDTO goodsDTO) {

        if (exchangeSetting != null && exchangeSetting.getEnableExchange() == 1) {
            //删除之前的相关信息
            this.deleteByGoods(goodsDTO.getGoodsId());
            //添加
            this.add(exchangeSetting, goodsDTO);

        } else {

            this.deleteByGoods(goodsDTO.getGoodsId());
        }
        return exchangeSetting;
    }

    @Override
    public void delete(Integer id) {
        //删除数据库信息
        this.daoSupport.delete(ExchangeDO.class, id);
        //删除活动商品对照表的关系
        this.promotionGoodsManager.delete(id, PromotionTypeEnum.EXCHANGE.name());
        //删除缓存
        this.cache.remove(PromotionCacheKeys.getExchangeKey(id));
    }

    @Override
    public ExchangeDO getModel(Integer id) {
        ExchangeDO exchangeDO = (ExchangeDO) this.cache.get(PromotionCacheKeys.getExchangeKey(id));
        if (exchangeDO == null) {
            exchangeDO = this.daoSupport.queryForObject(ExchangeDO.class, id);
        }
        return exchangeDO;
    }

    @Override
    public ExchangeDO getModelByGoods(Integer goodsId) {
        String sql = "select * from es_exchange where goods_id = ? ";
        return this.daoSupport.queryForObject(sql, ExchangeDO.class, goodsId);
    }

    @Override
    public void deleteByGoods(Integer goodsId) {
        ExchangeDO exchangeDO = this.getModelByGoods(goodsId);
        if (exchangeDO == null) {
            return;
        }
        this.delete(exchangeDO.getExchangeId());
    }

    @Override
    public List<ExchangeDO> getModelByCategoryId(Integer categoryId) {

        String sql = "select * from es_exchange where category_id = ? ";

        return this.daoSupport.queryForList(sql, ExchangeDO.class, categoryId);
    }
}
