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

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.client.member.ShipTemplateClient;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.client.trade.ExchangeGoodsClient;
import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsSettingVO;
import cloud.shopfly.b2c.core.goods.model.enums.GoodsType;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsVO;
import cloud.shopfly.b2c.core.goods.service.*;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateSettingVO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateVO;
import cloud.shopfly.b2c.core.goods.service.impl.util.SearchUtil;
import cloud.shopfly.b2c.core.goods.service.impl.util.StockCacheKeyUtil;
import cloud.shopfly.b2c.core.goods.service.*;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.IntegerMapper;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Commodity business category
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
@Service
public class GoodsQueryManagerImpl implements GoodsQueryManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired
    private GoodsGalleryManager goodsGalleryManager;
    @Autowired
    private GoodsSkuManager goodsSkuManager;

    @Autowired
    private Cache cache;

    @Autowired
    private CategoryManager categoryManager;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private GoodsQuantityManager goodsQuantityManager;

    @Autowired
    private ExchangeGoodsClient exchangeGoodsClient;

    @Autowired
    private ShipTemplateClient shipTemplateClient;

    @Autowired
    private SettingClient settingClient;


    @Override
    public GoodsDO getModel(Integer goodsId) {
        return this.daoSupport.queryForObject(GoodsDO.class, goodsId);
    }

    @Override
    public Double getGoodsGrade(Integer goodsId) {
        Double grade = (Double) cache.get(CachePrefix.GOODS_GRADE.getPrefix() + goodsId);
        if (grade == null) {
            String sql = "select grade from es_goods where goods_id = ?";
            grade = this.daoSupport.queryForDouble(sql, goodsId);
            cache.put(CachePrefix.GOODS_GRADE.getPrefix() + goodsId, grade);
        }
        return grade;
    }

    @Override
    public CacheGoods getFromCache(Integer goodsId) {
        CacheGoods goods = (CacheGoods) cache.get(CachePrefix.GOODS.getPrefix() + goodsId);
        if (logger.isDebugEnabled()) {
            logger.debug("Read the item from the cache：");
            logger.debug(goods);
        }
        if (goods == null) {

            GoodsDO goodsDB = this.getModel(goodsId);
            if (goodsDB == null) {
                throw new ServiceException(GoodsErrorCode.E301.code(), "The item has been completely removed");
            }

            // The skuList in GoodsVo must be filled
            List<GoodsSkuVO> skuList = goodsSkuManager.listByGoodsId(goodsId);


            goods = new CacheGoods();
            BeanUtils.copyProperties(goodsDB, goods);
            goods.setSkuList(skuList);

            // Filling inventory data
            fillStock(goods);
            cache.put(CachePrefix.GOODS.getPrefix() + goodsId, goods);

            if (logger.isDebugEnabled()) {
                logger.debug("Null item read from cache,Returns goods from the database：");
                logger.debug(goods);
            }
            return goods;
        } else {
            // Filling inventory data
            fillStock(goods);
        }

        return goods;
    }

    @Override
    public Integer checkArea(Integer goodsId, Integer areaId) {
        CacheGoods goods = this.getFromCache(goodsId);

        // Determine the inventory to be 0, indicating no stock
        if (goods.getEnableQuantity() == 0) {
            return 0;
        }

        // The seller bears the freight
        if (goods.getGoodsTransfeeCharge() == 1) {
            // In stock
            return 1;
        }

        ShipTemplateVO temp = this.shipTemplateClient.get(goods.getTemplateId());

        for (ShipTemplateSettingVO child : temp.getItems()) {
            if (child.getAreaId() != null) {
                if (child.getAreaId().indexOf("," + areaId + ",") >= 0) {
                    // In stock
                    return 1;
                }
            }
        }

        // Is not available
        return 0;
    }

    @Override
    public String queryCategoryPath(Integer id) {
        CategoryDO category = categoryManager.getModel(id);
        String sql = "select name from es_category " +
                "where category_id in (" + category.getCategoryPath().replace("|", ",") + "-1) " +
                "order by category_id asc";
        List<Map> list = this.daoSupport.queryForList(sql);
        String categoryName = "";
        if (StringUtil.isNotEmpty(list)) {
            for (Map map : list) {
                if ("".equals(categoryName)) {
                    categoryName = " " + map.get("name").toString();
                } else {
                    categoryName += ">" + map.get("name").toString() + " ";
                }
            }
        }
        return categoryName;
    }

    @Override
    public Page list(GoodsQueryParam goodsQueryParam) {
        StringBuffer sqlBuffer = new StringBuffer(
                "select g.goods_id,g.goods_name,g.sn,g.thumbnail,g.enable_quantity,g.quantity,g.price,g.create_time,g.market_enable,g.under_message "
                        + "from es_goods g   ");

        List<Object> term = new ArrayList<>();

        // Based on the query
        SearchUtil.baseQuery(goodsQueryParam, term, sqlBuffer);
        // Classification of query
        SearchUtil.categoryQuery(goodsQueryParam, term, sqlBuffer, daoSupport);

        sqlBuffer.append(" order by g.goods_id desc");
        Page page = this.daoSupport.queryForPage(sqlBuffer.toString(), goodsQueryParam.getPageNo(),
                goodsQueryParam.getPageSize(), term.toArray());

        return page;
    }

    @Override
    public Page warningGoodsList(int pageNo, int pageSize, String keyword) {
        StringBuffer sqlBuffer = new StringBuffer("select g.* from es_goods g where g.goods_id in(" +
                " select s.goods_id from es_goods_sku s WHERE s.enable_quantity <= ? ) and g.market_enable = 1 AND g.disabled = 1");

        List<Object> term = new ArrayList<>();

        // Get the number of pre-warning goods
        String goodsSetting = settingClient.get(SettingGroup.GOODS);

        GoodsSettingVO setting = JsonUtil.jsonToObject(goodsSetting, GoodsSettingVO.class);
        term.add(setting.getGoodsWarningCount() == null ? 5 : setting.getGoodsWarningCount());

        if (!StringUtil.isEmpty(keyword)) {
            sqlBuffer.append(" and (g.goods_name like ? or g.sn like ?)");
            term.add("%" + keyword + "%");
            term.add("%" + keyword + "%");
        }

        return this.daoSupport.queryForPage(sqlBuffer.toString(), pageNo, pageSize, term.toArray());

    }

    @Override
    public GoodsVO queryGoods(Integer goodsId) {

        GoodsDO goods = this.getModel(goodsId);

        if (goods == null) {
            throw new ServiceException(GoodsErrorCode.E301.code(), "No operation permission");
        }
        List<GoodsGalleryDO> galleryList = goodsGalleryManager.list(goodsId);
        GoodsVO goodsVO = new GoodsVO();

        BeanUtils.copyProperties(goods, goodsVO);

        goodsVO.setGoodsGalleryList(galleryList);

        // Item category assignment
        Integer categoryId = goods.getCategoryId();
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
        goodsVO.setCategoryIds(categoryIds);
        goodsVO.setCategoryName(categoryName);

        // Query integral product information
        if (goodsVO.getGoodsType().equals(GoodsType.POINT.name())) {
            ExchangeDO exchangeDO = exchangeGoodsClient.getModelByGoods(goodsId);

            goodsVO.setExchange(exchangeDO);
        }

        return goodsVO;
    }

    @Override
    public List<GoodsSelectLine> query(Integer[] goodsIds) {
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(goodsIds, term);
        if (goodsIds == null || goodsIds.length == 0) {
            return new ArrayList<>();
        }
        String sql = "select * from es_goods where goods_id in (" + str + ")";

        return this.daoSupport.queryForList(sql, GoodsSelectLine.class, term.toArray());

    }


    /**
     * Queries a retrievable set of parameters associated with the item
     *
     * @param list Raw commodity data
     * @return
     */
    private List<Map<String, Object>> getIndexGoodsList(List<Map<String, Object>> list) {
        if (list != null) {
            for (Map<String, Object> map : list) {
                // Queries a retrievable set of parameters associated with the item
                String sql = "select gp.* from es_goods_params gp inner join es_parameters p on gp.param_id=p.param_id "
                        + "where goods_id = ? and is_index = 1";
                List listParams = this.daoSupport.queryForList(sql,
                        map.get("goods_id"));
                map.put("params", listParams);
            }
        }
        return list;
    }


    /**
     * Fill in inventory information for items<br/>
     * Inventory information is stored in a separate cachekeyIn the<br/>
     * Read all from the cacheskuInventory, and respectivelygoods.skuListIn theskuSet up inventory to ensure the inventory is correct at all times<br/>
     * Its also going to put all of theskuInventory accumulation is set to the inventory of goods
     *
     * @param goods
     */
    private void fillStock(CacheGoods goods) {

        List<GoodsSkuVO> skuList = goods.getSkuList();

        // The available and actual inventory of the SKU is retrieved from the cache
        // This operation is a batch operation. Because it is a high-frequency operation, the number of interactions with Redis should be minimized
        List keys = createKeys(skuList);

        // Read the available stock of goods together with the actual stock
        keys.add(StockCacheKeyUtil.goodsEnableKey(goods.getGoodsId()));
        keys.add(StockCacheKeyUtil.goodsActualKey(goods.getGoodsId()));

        List<String> quantityList = stringRedisTemplate.opsForValue().multiGet(keys);

        int enableTotal = 0;
        int actualTotal = 0;

        int i = 0;
        for (GoodsSkuVO skuVO : skuList) {

            // The first is available inventory
            Integer enable = StringUtil.toInt(quantityList.get(i), null);

            i++;
            // The second is real inventory
            Integer actual = StringUtil.toInt(quantityList.get(i), null);

            // The cache is breached and read from the database
            if (enable == null || actual == null) {
                Map<String, Integer> map = goodsQuantityManager.fillCacheFromDB(skuVO.getSkuId());
                enable = map.get("enable_quantity");
                actual = map.get("quantity");

                // Reset the inventory in the cache
                stringRedisTemplate.opsForValue().set(StockCacheKeyUtil.skuEnableKey(skuVO.getSkuId()), "" + enable);
                stringRedisTemplate.opsForValue().set(StockCacheKeyUtil.skuActualKey(skuVO.getSkuId()), "" + actual);
            }


            skuVO.setEnableQuantity(enable);
            skuVO.setQuantity(actual);

            if (enable == null) {
                enable = 0;
            }

            if (actual == null) {
                actual = 0;
            }
            // Accumulated inventory of goods
            enableTotal += enable;
            actualTotal += actual;

            i++;
        }


        // Set up inventory of goods
        goods.setEnableQuantity(enableTotal);
        goods.setQuantity(actualTotal);


        // Read the inventory of items in the cache to see if there is a breach
        // The first is available inventory
        Integer goodsEnable = StringUtil.toInt(quantityList.get(i), null);

        i++;
        // The second is real inventory
        Integer goodsActual = StringUtil.toInt(quantityList.get(i), null);

        // The inventory of goods was punctured
        if (goodsEnable == null || goodsActual == null) {
            // Reset the inventory in the cache
            stringRedisTemplate.opsForValue().set(StockCacheKeyUtil.goodsEnableKey(goods.getGoodsId()), "" + enableTotal);
            stringRedisTemplate.opsForValue().set(StockCacheKeyUtil.goodsActualKey(goods.getGoodsId()), "" + actualTotal);
        }


    }

    /**
     * Generate batch fetchskuinventorykeys
     *
     * @param goodsList
     * @return
     */
    private List createKeys(List<GoodsSkuVO> goodsList) {
        List keys = new ArrayList();
        for (GoodsSkuVO goodsSkuVO : goodsList) {

            keys.add(StockCacheKeyUtil.skuEnableKey(goodsSkuVO.getSkuId()));
            keys.add(StockCacheKeyUtil.skuActualKey(goodsSkuVO.getSkuId()));
        }
        return keys;
    }

    @Override
    public Integer getGoodsCountByParam(Integer status, Integer disabled) {
        StringBuffer sql = new StringBuffer("select count(0) from es_goods");
        List paramList = new ArrayList();
        List<String> sqlList = new ArrayList<>();
        // Commodity status query
        if (status != null) {
            sqlList.add(" market_enable = ?");
            paramList.add(status.toString());
        }

        // Item deletion status query
        if (disabled != null) {
            sqlList.add(" disabled = ?");
            paramList.add(disabled.toString());
        }
        sql.append(SqlUtil.sqlSplicing(sqlList));
        Integer num = this.daoSupport.queryForInt(sql.toString(), paramList.toArray());
        return num;
    }

    @Override
    public List<Map<String, Object>> getGoodsAndParams(Integer[] goodsIds) {

        if (goodsIds == null) {
            return null;
        }

        StringBuffer sqlBuffer = new StringBuffer("select g.* from es_goods g ");

        List<Object> term = new ArrayList<>();

        String str = SqlUtil.getInSql(goodsIds, term);
        sqlBuffer.append(" where goods_id in (" + str + ")  order by goods_id desc");

        List<Map<String, Object>> list = this.daoSupport.queryForList(sqlBuffer.toString(), term.toArray());
        // Queries a retrievable set of parameters associated with the item
        this.getIndexGoodsList(list);
        return list;
    }

    @Override
    public List<Map<String, Object>> getGoods(Integer[] goodsIds) {

        List<Integer> term = new ArrayList<>();
        String idStr = StringUtil.getIdStr(goodsIds, term);
        String sql = "select goods_id,goods_name,price as original_price from es_goods where goods_id in (" + idStr + ")";
        List<Map<String, Object>> result = this.daoSupport.queryForList(sql, term.toArray());
        return result;
    }

    @Override
    public List<Integer> getAllGoodsId() {

        String sql = "select goods_id from es_goods  order by goods_id desc";
        List<Integer> result = this.daoSupport.queryForList(sql, new IntegerMapper());

        return result;
    }


}
