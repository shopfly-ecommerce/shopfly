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
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsSkuDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectorSkuVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.core.goods.service.GoodsGalleryManager;
import cloud.shopfly.b2c.core.goods.service.GoodsQuantityManager;
import cloud.shopfly.b2c.core.goods.service.GoodsSkuManager;
import cloud.shopfly.b2c.core.goods.service.impl.util.SearchUtil;
import cloud.shopfly.b2c.core.goods.service.impl.util.StockCacheKeyUtil;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.IntegerMapper;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * productskuBusiness class
 *
 * @author fk
 * @author kingapex
 * @version v3.0
 * @since v7.0.0 2018-03-21 11:48:40
 * <p>
 * version 3.0 written by kingapex 2019-02-22 :<br/>
 * <li>skuTable byhashcodeField to determine if there has been a specification change</li>
 * <li>throughluaScripts update inventory</li>
 */
@Service
public class GoodsSkuManagerImpl implements GoodsSkuManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private GoodsGalleryManager goodsGalleryManager;

    @Autowired
    private GoodsQuantityManager goodsQuantityManager;

    @Autowired
    private Cache cache;

//    @Autowired
//    private ShopCatClient shopCatClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ShopflyConfig shopflyConfig;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    @Override
    public Page list(GoodsQueryParam goodsQueryParam) {

        StringBuffer sqlBuffer = new StringBuffer(
                "select s.* from es_goods_sku s inner join es_goods g on g.goods_id = s.goods_id");
        List<Object> term = new ArrayList<>();

        // Based on the query
        SearchUtil.baseQuery(goodsQueryParam, term, sqlBuffer);
        // Classification of query
        SearchUtil.categoryQuery(goodsQueryParam, term, sqlBuffer, daoSupport);

        sqlBuffer.append(" order by g.goods_id desc");
        Page<GoodsSelectorSkuVO> page = this.daoSupport.queryForPage(sqlBuffer.toString(), goodsQueryParam.getPageNo(),
                goodsQueryParam.getPageSize(), GoodsSelectorSkuVO.class, term.toArray());

        return page;
    }

    @Override
    public List<GoodsSkuVO> listByGoodsId(Integer goodsId) {
        String sql = "select * from es_goods_sku where goods_id =?";
        List<GoodsSkuDO> list = daoSupport.queryForList(sql, GoodsSkuDO.class, goodsId);
        List<GoodsSkuVO> result = new ArrayList<>();
        for (GoodsSkuDO sku : list) {
            GoodsSkuVO skuVo = new GoodsSkuVO();
            BeanUtils.copyProperties(sku, skuVo);
            result.add(skuVo);
        }
        return result;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void add(List<GoodsSkuVO> skuList, GoodsDO goods) {

        List<GoodsSkuDO> newSkuList = new ArrayList<>();
        // If there is a specification
        if (skuList != null && skuList.size() > 0) {
            // Add a commodity SKU
            this.addGoodsSku(skuList, goods);

            // To do
            skuList.forEach(skuVO -> {
                skuVO.setGoodsId(goods.getGoodsId());
                newSkuList.add(skuVO);
            });
        } else {
            // Example Add skU information without specifications
            GoodsSkuDO newSku = this.addNoSku(goods);
            newSkuList.add(newSku);
        }

        // Increase inventory for new SKUs
        updateStock(newSkuList);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void edit(List<GoodsSkuVO> skuList, GoodsDO goods) {
        // A new list of SKUs to synchronize the cache of these SKUs
        List<GoodsSkuDO> newSkuList  =new ArrayList<>();

        // If skU data is changed during the editing (including the combination of specifications is changed to no specifications or no specifications is changed to yes specifications) hasChanged=1 The specifications are changed
        Integer goodsId = goods.getGoodsId();

        // The set of all skUs that generated this item, those not following this set will be deleted
        String hashCodeStr = "";

        if (skuList != null) {
            for (GoodsSkuVO goodsSkuVO : skuList) {
                goodsSkuVO.setGoodsId(goodsId);

                int hashCode = buildHashCode(goodsSkuVO.getSpecList());
                goodsSkuVO.setHashCode(hashCode);

                if (!StringUtil.isEmpty(hashCodeStr)) {
                    hashCodeStr = hashCodeStr + ",";
                }
                hashCodeStr = hashCodeStr + "" + hashCode;

                // The new sku
                if (goodsSkuVO.getSkuId() == null || goodsSkuVO.getSkuId() == 0) {
                    GoodsSkuDO newSku =  add(goodsSkuVO, goods);
                    newSku.setGoodsId(goodsId);
                    newSkuList.add(newSku);
                } else {
                    // Update existing
                    update(goodsSkuVO, goods);
                }
            }
        }


        // Clear the cache and database of non-existent SKUs
        cleanNotExits(hashCodeStr,goodsId);


        // For items without specifications, use goods_id and hash_code=-1 as conditions
        if (skuList == null || skuList.isEmpty()) {

            // Find if there are skUs without specifications, update them if there are, and add one if not
            int count = daoSupport.queryForInt("select count(0) from es_goods_sku where goods_id=? and hash_code =-1", goods.getGoodsId());
            if (count > 0) {
                // Example Modify skU information without specifications
                GoodsSkuDO goodsSku = new GoodsSkuDO();
                BeanUtils.copyProperties(goods, goodsSku);
                goodsSku.setQuantity(goods.getQuantity() == null ? 0 : goods.getQuantity());
                // Skus with no specification have a hashcode of -1
                goodsSku.setHashCode(-1);

                Map map = new HashMap(16);
                map.put("goods_id", goods.getGoodsId());
                map.put("hash_code", "-1");
                this.daoSupport.update("es_goods_sku", goodsSku, map);
            } else {
                GoodsSkuDO newSku =  addNoSku(goods);
                newSku.setGoodsId(goodsId);
                newSkuList.add(newSku);

            }

        }

        if (newSkuList != null && !newSkuList.isEmpty()) {
            // Increase inventory for new SKUs
            updateStock(newSkuList);
        }

        // Recalculate inventory
        reCountGoodsStock(goodsId);

    }

    @Override
    public List<GoodsSkuVO> query(Integer[] skuIds) {
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(skuIds, term);
        if (skuIds == null || skuIds.length == 0) {
            return new ArrayList<>();
        }
        String sql = "select * from es_goods_sku where sku_id in(" + str + ")";
        return this.daoSupport.queryForList(sql, GoodsSkuVO.class, skuIds);
    }

    @Override
    public GoodsSkuVO getSkuFromCache(Integer skuId) {
        // Read SKU information from the cache
        GoodsSkuVO skuVo = (GoodsSkuVO) cache.get(CachePrefix.SKU.getPrefix() + skuId);
        // No item is found in the cache, or the last modification time is empty (indicating a data exception), queried from the database
        if (skuVo == null || skuVo.getLastModify() == null) {
            GoodsSkuDO sku = this.getModel(skuId);
            if (sku == null) {
                return null;
            }
            skuVo = new GoodsSkuVO();
            BeanUtils.copyProperties(sku, skuVo);

            // The following information is obtained from the product
            GoodsDO goods = this.daoSupport.queryForObject(GoodsDO.class, sku.getGoodsId());

            skuVo.setLastModify(goods.getLastModify());
            skuVo.setGoodsTransfeeCharge(goods.getGoodsTransfeeCharge());
            skuVo.setDisabled(goods.getDisabled());
            skuVo.setMarketEnable(goods.getMarketEnable());
            skuVo.setTemplateId(goods.getTemplateId());
            skuVo.setGoodsType(goods.getGoodsType());
            cache.put(CachePrefix.SKU.getPrefix() + skuId, skuVo);
            return skuVo;
        } else {
            // Populate the inventory information in the SKU
            fillStock(skuVo);
        }
        return skuVo;
    }

    @Override
    public GoodsSkuDO getModel(Integer id) {
        return this.daoSupport.queryForObject(GoodsSkuDO.class, id);
    }

    /**
     * forskuFill inventory information<br/>
     * Inventory information is stored in a separate cachekeyIn the<br/>
     * Read from the cacheskuAvailable stock and actual stock, and set to respectivelyskuInventory information in order to ensure the real-time accuracy of inventory<br/>
     *
     * @param goodsSkuVO
     */
    private void fillStock(GoodsSkuVO goodsSkuVO) {
        // Gets the actual inventory of SKUs in the cache
        String cacheActualStock = stringRedisTemplate.opsForValue().get(StockCacheKeyUtil.skuActualKey(goodsSkuVO.getSkuId()));
        // Gets the available inventory of SKUs in the cache
        String cacheEnableStock = stringRedisTemplate.opsForValue().get(StockCacheKeyUtil.skuEnableKey(goodsSkuVO.getSkuId()));

        // If neither item is empty, that is, both items are present in the cache, then the cache inventory information is set into the SKU object
        if (StringUtil.notEmpty(cacheActualStock) && StringUtil.notEmpty(cacheEnableStock)) {
            goodsSkuVO.setQuantity(StringUtil.toInt(cacheActualStock, goodsSkuVO.getQuantity()));
            goodsSkuVO.setEnableQuantity(StringUtil.toInt(cacheEnableStock, goodsSkuVO.getEnableQuantity()));
        }

    }

    /**
     * Add without specificationsskuinformation
     *
     * @param goods Product information
     * @return
     */
    private GoodsSkuDO addNoSku(GoodsDO goods) {

        GoodsSkuDO goodsSku = new GoodsSkuDO();
        BeanUtils.copyProperties(goods, goodsSku);
        goodsSku.setEnableQuantity(goodsSku.getQuantity());
        goodsSku.setHashCode(-1);
        this.daoSupport.insert("es_goods_sku", goodsSku);
        goodsSku.setSkuId(this.daoSupport.getLastId("es_goods_sku"));
        return goodsSku;
    }

    /**
     * increaseskuA collection of
     *
     * @param skuList
     * @param goods
     */
    private void addGoodsSku(List<GoodsSkuVO> skuList, GoodsDO goods) {

        for (GoodsSkuVO skuVO : skuList) {
            add(skuVO, goods);
        }
    }

    private GoodsSkuDO add(GoodsSkuVO skuVO, GoodsDO goods) {
        skuVO.setGoodsId(goods.getGoodsId());
        GoodsSkuDO sku = new GoodsSkuDO();
        BeanUtils.copyProperties(skuVO, sku);

        sku.setEnableQuantity(sku.getQuantity());
        sku.setGoodsName(goods.getGoodsName());
        sku.setCategoryId(goods.getCategoryId());
        // Get json for the spec value
        Map map  = getSpecListJson(skuVO.getSpecList());
        sku.setSpecs((String) map.get("spec_json"));
        sku.setGoodsId(goods.getGoodsId());
        String thumbnail = (String) map.get("thumbnail");
        sku.setThumbnail(thumbnail == null ? goods.getThumbnail() : thumbnail);
        if (sku.getHashCode() == null || sku.getHashCode() == 0) {
            int hashCode = buildHashCode(skuVO.getSpecList());
            sku.setHashCode(hashCode);
            skuVO.setHashCode(hashCode);

        }
        this.daoSupport.insert(sku);
        Integer skuId = this.daoSupport.getLastId("es_goods_sku");
        skuVO.setSkuId(skuId);
        sku.setSkuId(skuId);

        return sku;
    }

    /**
     * Synchronization of inventory
     *
     * @param skuList
     */
    private void updateStock(List<GoodsSkuDO> skuList) {
        List<GoodsQuantityVO> quantityList = new ArrayList<>();
        skuList.forEach(sku -> {
            addStockToList(quantityList, sku);
        });
        goodsQuantityManager.updateSkuQuantity(quantityList);

        // If the item inventory buffer pool is enabled, the item inventory in the database needs to be synchronized immediately to ensure that the item inventory displays properly
        if (shopflyConfig.isStock()) {
            goodsQuantityManager.syncDataBase();
        }
    }

    /**
     * skuConvert to inventory information and press inlist
     *
     * @param quantityList Inventory to be pressed inlist
     * @param sku          sku
     */
    private void addStockToList(List<GoodsQuantityVO> quantityList, GoodsSkuDO sku) {
        // Actual inventory VO
        GoodsQuantityVO actualQuantityVO = new GoodsQuantityVO();
        actualQuantityVO.setQuantity(sku.getQuantity());
        actualQuantityVO.setGoodsId(sku.getGoodsId());
        actualQuantityVO.setSkuId(sku.getSkuId());
        actualQuantityVO.setQuantityType(QuantityType.actual);

        // Available stock VO
        GoodsQuantityVO enableQuantityVO = new GoodsQuantityVO();
        enableQuantityVO.setQuantity(sku.getQuantity());
        enableQuantityVO.setGoodsId(sku.getGoodsId());
        enableQuantityVO.setSkuId(sku.getSkuId());
        enableQuantityVO.setQuantityType(QuantityType.enable);

        quantityList.add(actualQuantityVO);
        quantityList.add(enableQuantityVO);
    }

    /**
     * skuIn thespecField operation, returnsjson
     *
     * @param specList Set of specification values
     * @return valuesjson
     */
    private Map getSpecListJson(List<SpecValueVO> specList) {
        Map<String, Object> map = new HashMap<>();
        String thumbnail = null;
        for (SpecValueVO specvalue : specList) {
            if (specvalue.getSpecType() == null) {
                specvalue.setSpecType(0);
            }
            if (specvalue.getSpecType() == 1) {
                GoodsGalleryDO goodsGallery = goodsGalleryManager.getGoodsGallery(specvalue.getSpecImage());
                specvalue.setBig(goodsGallery.getBig());
                specvalue.setSmall(goodsGallery.getSmall());
                specvalue.setThumbnail(goodsGallery.getThumbnail());
                thumbnail = goodsGallery.getThumbnail();
                specvalue.setTiny(goodsGallery.getTiny());
                // Only the first specification has a picture, so you can jump out of the loop when you find a specification that has a picture
                break;
            }
        }
        map.put("spec_json", JsonUtil.objectToJson(specList));
        map.put("thumbnail", thumbnail);
        return map;
    }

    /**
     * Generate hash values for specifications
     *
     * @param specValueVOList
     * @return
     */
    private int buildHashCode(List<SpecValueVO> specValueVOList) {
        HashCodeBuilder codeBuilder = new HashCodeBuilder(17, 37);
        specValueVOList.forEach(specValueVO -> {
            String specValue = specValueVO.getSpecValue();
            codeBuilder.append(specValue);

        });
        int hashCode = codeBuilder.toHashCode();

        return hashCode;
    }

    /**
     * Recalculate inventory of goods
     * @param goodsId
     */
    private void reCountGoodsStock(Integer goodsId) {
        String sql = "select sum(s.quantity) as quantity,sum(s.enable_quantity) as enable_quantity  from es_goods_sku  s where goods_id=?";
        Map<String, BigDecimal> map  = daoSupport.queryForMap(sql, goodsId);
        Integer quantity  = map.get("quantity").intValue();
        Integer enableQuantity =  map.get("enable_quantity").intValue();

        // Update the database inventory
        sql = "update es_goods set quantity=?,enable_quantity=? where goods_id=?";
        daoSupport.execute(sql, quantity, enableQuantity, goodsId);

        // Update the cached inventory
        stringRedisTemplate.opsForValue().set(StockCacheKeyUtil.goodsActualKey(goodsId), ""+quantity);
        stringRedisTemplate.opsForValue().set(StockCacheKeyUtil.goodsEnableKey(goodsId), ""+enableQuantity);

    }

    private void update(GoodsSkuVO sku, GoodsDO goods) {
        Map map = getSpecListJson(sku.getSpecList());
        this.daoSupport.execute(
                "update es_goods_sku set category_id=?,goods_name=?,sn=?,price=?,cost=?,weight=?,thumbnail=? ,specs=? where sku_id=?",
                goods.getCategoryId(), goods.getGoodsName(), sku.getSn(), sku.getPrice(), sku.getCost(),
                sku.getWeight(), map.get("thumbnail") == null ? goods.getThumbnail() : map.get("thumbnail"), map.get("spec_json"), sku.getSkuId());
    }

    /**
     * Remove what doesnt existskuCaches and databases
     * @param hashCodeStr
     * @param goodsId
     */
    private void cleanNotExits(String hashCodeStr, Integer goodsId) {
        List<Integer> skuIdList;
        if (StringUtil.isEmpty(hashCodeStr)) {
            String sql = "select sku_id from es_goods_sku where goods_id=? and hash_code!=-1 ";
            skuIdList = daoSupport.queryForList(sql, new IntegerMapper(), goodsId);
        }else {
            String sql = "select sku_id from es_goods_sku where goods_id=? and hash_code not in(" + hashCodeStr + ") ";
            skuIdList = daoSupport.queryForList(sql, new IntegerMapper(), goodsId);

        }

        List<String> keys = StockCacheKeyUtil.skuKeys(skuIdList);

        if (logger.isDebugEnabled()) {
            logger.debug("deletekeys:");
            logger.debug(keys.toString());
        }

        if (keys!=null && !keys.isEmpty() ){

            stringRedisTemplate.delete(keys);
        }

        // Delete the skuList that does not exist in hashcode, but cannot be hashcode=-1, because it is possible that the skuList is empty because there is no specification
        if (StringUtil.isEmpty(hashCodeStr)) {
            daoSupport.execute("delete from es_goods_sku where goods_id=? and hash_code!=-1 ", goodsId);
        } else {
            daoSupport.execute("delete from es_goods_sku where goods_id=? and hash_code not in(" + hashCodeStr + ") ", goodsId);
        }

    }
}
