/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.member.MemberCollectionGoodsClient;
import cloud.shopfly.b2c.core.statistics.model.dto.GoodsData;
import cloud.shopfly.b2c.core.statistics.service.GoodsDataManager;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品数据收集
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/6/4 9:54
 */
@Service
public class GoodsDataManagerImpl implements GoodsDataManager {


    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private MemberCollectionGoodsClient memberCollectionGoodsClient;

    @Override
    public void addGoods(Integer[] goodsIds) {
        for (Integer goodsId : goodsIds) {
            CacheGoods cacheGoods = goodsClient.getFromCache(goodsId);
            GoodsData gd = new GoodsData(cacheGoods);
            gd.setFavoriteNum(0);
            try {
                gd.setCategoryPath(goodsClient.getCategory(gd.getCategoryId()).getCategoryPath());
            } catch (Exception e) {
                this.logger.error("错误的商品分类id：" + gd.getCategoryId());
                gd.setCategoryPath("");
            }
            this.daoSupport.insert("es_sss_goods_data", gd);
        }
    }

    /**
     * 似有方法，新增商品
     *
     * @param cacheGoods
     */
    private void saveGoods(CacheGoods cacheGoods) {
        GoodsData gd = new GoodsData(cacheGoods);
        gd.setFavoriteNum(memberCollectionGoodsClient.getGoodsCollectCount(gd.getGoodsId()));
        gd.setCategoryPath(goodsClient.getCategory(gd.getCategoryId()).getCategoryPath());
        this.daoSupport.insert("es_sss_goods_data", gd);
    }

    @Override
    public void updateGoods(Integer[] goodsIds) {

        for (Integer goodsId : goodsIds) {
            CacheGoods cacheGoods = goodsClient.getFromCache(goodsId);
            GoodsData gd = this.daoSupport.queryForObject("select * from es_sss_goods_data where goods_id = ?",
                    GoodsData.class, cacheGoods.getGoodsId());
            if (gd == null) {
                this.saveGoods(cacheGoods);
            } else {
                gd = new GoodsData(cacheGoods, gd);
                gd.setFavoriteNum(memberCollectionGoodsClient.getGoodsCollectCount(gd.getGoodsId()));
                gd.setCategoryPath(goodsClient.getCategory(gd.getCategoryId()).getCategoryPath());
                Map<String, String> where = new HashMap(16);
                where.put("id", gd.getId() + "");
                this.daoSupport.update("es_sss_goods_data", gd, where);
            }
        }

    }

    @Override
    public void deleteGoods(Integer[] goodsIds) {
        List<Object> term = new ArrayList<>();

        String str = SqlUtil.getInSql(goodsIds, term);

        String sql = "delete from es_sss_goods_data where goods_id in (" + str + ")";

        daoSupport.execute(sql, term.toArray());
    }


    /**
     * 商品收藏数量修改
     *
     * @param goodsData
     */
    @Override
    public void updateCollection(GoodsData goodsData) {
        this.daoSupport.execute("update es_sss_goods_data set favorite_num = ? where goods_id = ?", goodsData.getFavoriteNum(), goodsData.getGoodsId());
    }

    /**
     * 获取商品
     *
     * @param goodsId
     * @return
     */
    @Override
    public GoodsData get(Integer goodsId) {

        return this.daoSupport.queryForObject("select * from es_sss_goods_data where goods_id = ?", GoodsData.class, goodsId);
    }

    /**
     * 下架所有商品
     */
    @Override
    public void underAllGoods() {
        this.daoSupport.execute("update es_sss_goods_data set market_enable = 0");

    }
}
