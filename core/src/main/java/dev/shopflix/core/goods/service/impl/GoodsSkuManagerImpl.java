/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.goods.model.dos.GoodsDO;
import dev.shopflix.core.goods.model.dos.GoodsGalleryDO;
import dev.shopflix.core.goods.model.dos.GoodsSkuDO;
import dev.shopflix.core.goods.model.dto.GoodsQueryParam;
import dev.shopflix.core.goods.model.enums.QuantityType;
import dev.shopflix.core.goods.model.vo.GoodsQuantityVO;
import dev.shopflix.core.goods.model.vo.GoodsSelectorSkuVO;
import dev.shopflix.core.goods.model.vo.GoodsSkuVO;
import dev.shopflix.core.goods.model.vo.SpecValueVO;
import dev.shopflix.core.goods.service.GoodsGalleryManager;
import dev.shopflix.core.goods.service.GoodsQuantityManager;
import dev.shopflix.core.goods.service.GoodsSkuManager;
import dev.shopflix.core.goods.service.impl.util.SearchUtil;
import dev.shopflix.core.goods.service.impl.util.StockCacheKeyUtil;
import dev.shopflix.framework.ShopflixConfig;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.IntegerMapper;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * 商品sku业务类
 *
 * @author fk
 * @author kingapex
 * @version v3.0
 * @since v7.0.0 2018-03-21 11:48:40
 * <p>
 * version 3.0 written by kingapex 2019-02-22 :<br/>
 * <li>sku表通过hashcode字段来确定是否有规格变化</li>
 * <li>通过lua脚本来更新库存</li>
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
    private ShopflixConfig shopflixConfig;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public Page list(GoodsQueryParam goodsQueryParam) {

        StringBuffer sqlBuffer = new StringBuffer(
                "select s.* from es_goods_sku s inner join es_goods g on g.goods_id = s.goods_id");
        List<Object> term = new ArrayList<>();

        //基础查询
        SearchUtil.baseQuery(goodsQueryParam, term, sqlBuffer);
        //分类查询
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
    @Transactional(value = "goodsTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void add(List<GoodsSkuVO> skuList, GoodsDO goods) {

        List<GoodsSkuDO> newSkuList = new ArrayList<>();
        // 如果有规格
        if (skuList != null && skuList.size() > 0) {
            // 添加商品sku
            this.addGoodsSku(skuList, goods);

            //转为do
            skuList.forEach(skuVO -> {
                skuVO.setGoodsId(goods.getGoodsId());
                newSkuList.add(skuVO);
            });
        } else {
            // 添加没有规格的sku信息
            GoodsSkuDO newSku = this.addNoSku(goods);
            newSkuList.add(newSku);
        }

        //为新增的sku增加库存
        updateStock(newSkuList);
    }

    @Override
    @Transactional(value = "goodsTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void edit(List<GoodsSkuVO> skuList, GoodsDO goods) {
        //新增的sku列表，用于同步这些sku的缓存
        List<GoodsSkuDO> newSkuList  =new ArrayList<>();

        // 如果编辑的时候sku数据有变化(包括规格项组合变了，有规格改成无规格，无规格改成有规格) hasChanged=1 规格有改变
        Integer goodsId = goods.getGoodsId();

        //生成这个商品的所有sku的集合，不在这个集后的要删除
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

                //新增的sku
                if (goodsSkuVO.getSkuId() == null || goodsSkuVO.getSkuId() == 0) {
                    GoodsSkuDO newSku =  add(goodsSkuVO, goods);
                    newSku.setGoodsId(goodsId);
                    newSkuList.add(newSku);
                } else {
                    //更新已经存在的
                    update(goodsSkuVO, goods);
                }
            }
        }


        //清除不存在的sku的缓存及数据库
        cleanNotExits(hashCodeStr,goodsId);


        //没有规格的商品，用goods_id和hash_code=-1做为条件
        if (skuList == null || skuList.isEmpty()) {

            //查找是否有不带规格的sku，如果有则更新，没有则添加一个
            int count = daoSupport.queryForInt("select count(0) from es_goods_sku where goods_id=? and hash_code =-1", goods.getGoodsId());
            if (count > 0) {
                // 修改没有规格的sku信息
                GoodsSkuDO goodsSku = new GoodsSkuDO();
                BeanUtils.copyProperties(goods, goodsSku);
                goodsSku.setQuantity(goods.getQuantity() == null ? 0 : goods.getQuantity());
                //没有规格的sku的hashcode 为-1
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
            //为新增的sku增加库存
            updateStock(newSkuList);
        }

        //重新计算库存
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
        // 从缓存读取sku信息
        GoodsSkuVO skuVo = (GoodsSkuVO) cache.get(CachePrefix.SKU.getPrefix() + skuId);
        // 缓存中没有找到商品，或者最后修改时间为空（表示数据异常），从数据库中查询
        if (skuVo == null || skuVo.getLastModify() == null) {
            GoodsSkuDO sku = this.getModel(skuId);
            if (sku == null) {
                return null;
            }
            skuVo = new GoodsSkuVO();
            BeanUtils.copyProperties(sku, skuVo);

            //以下信息由商品中获取
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
            //填充sku中的库存信息
            fillStock(skuVo);
        }
        return skuVo;
    }

    @Override
    public GoodsSkuDO getModel(Integer id) {
        return this.daoSupport.queryForObject(GoodsSkuDO.class, id);
    }

    /**
     * 为sku填充库存信息<br/>
     * 库存的信息存储在单独的缓存key中<br/>
     * 由缓存中读取出sku的可用库存和实际库存，并分别设置到sku库存信息中，以保证库存的实时正确性<br/>
     *
     * @param goodsSkuVO
     */
    private void fillStock(GoodsSkuVO goodsSkuVO) {
        //获取缓存中sku的实际库存
        String cacheActualStock = stringRedisTemplate.opsForValue().get(StockCacheKeyUtil.skuActualKey(goodsSkuVO.getSkuId()));
        //获取缓存中sku的可用库存
        String cacheEnableStock = stringRedisTemplate.opsForValue().get(StockCacheKeyUtil.skuEnableKey(goodsSkuVO.getSkuId()));

        //如果以上两项都不为空，也就是缓存中都存在，那么就将缓存中的库存信息set进sku对象中
        if (StringUtil.notEmpty(cacheActualStock) && StringUtil.notEmpty(cacheEnableStock)) {
            goodsSkuVO.setQuantity(StringUtil.toInt(cacheActualStock, goodsSkuVO.getQuantity()));
            goodsSkuVO.setEnableQuantity(StringUtil.toInt(cacheEnableStock, goodsSkuVO.getEnableQuantity()));
        }

    }

    /**
     * 添加没有规格的sku信息
     *
     * @param goods 商品信息
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
     * 增加sku集合
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
        // 得到规格值的json
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
     * 同步库存
     *
     * @param skuList
     */
    private void updateStock(List<GoodsSkuDO> skuList) {
        List<GoodsQuantityVO> quantityList = new ArrayList<>();
        skuList.forEach(sku -> {
            addStockToList(quantityList, sku);
        });
        goodsQuantityManager.updateSkuQuantity(quantityList);

        //如果商品库存缓冲池开启了，那么需要立即同步数据库的商品库存，以保证商品库存显示正常
        if (shopflixConfig.isStock()) {
            goodsQuantityManager.syncDataBase();
        }
    }

    /**
     * sku转为库存信息并压入list
     *
     * @param quantityList 要压入的库存list
     * @param sku          sku
     */
    private void addStockToList(List<GoodsQuantityVO> quantityList, GoodsSkuDO sku) {
        //实际库存vo
        GoodsQuantityVO actualQuantityVO = new GoodsQuantityVO();
        actualQuantityVO.setQuantity(sku.getQuantity());
        actualQuantityVO.setGoodsId(sku.getGoodsId());
        actualQuantityVO.setSkuId(sku.getSkuId());
        actualQuantityVO.setQuantityType(QuantityType.actual);

        //可用库存vo
        GoodsQuantityVO enableQuantityVO = new GoodsQuantityVO();
        enableQuantityVO.setQuantity(sku.getQuantity());
        enableQuantityVO.setGoodsId(sku.getGoodsId());
        enableQuantityVO.setSkuId(sku.getSkuId());
        enableQuantityVO.setQuantityType(QuantityType.enable);

        quantityList.add(actualQuantityVO);
        quantityList.add(enableQuantityVO);
    }

    /**
     * sku中的spec字段的操作，返回json
     *
     * @param specList 规格值集合
     * @return 规格值json
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
                // 规格只有第一个规格有图片，所以找到有图片的规格后就可跳出循环
                break;
            }
        }
        map.put("spec_json", JsonUtil.objectToJson(specList));
        map.put("thumbnail", thumbnail);
        return map;
    }

    /**
     * 生成规格的哈希值
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
     * 重新计算商品的库存
     * @param goodsId
     */
    private void reCountGoodsStock(Integer goodsId) {
        String sql = "select sum(s.quantity) as quantity,sum(s.enable_quantity) as enable_quantity  from es_goods_sku  s where goods_id=?";
        Map<String, BigDecimal> map  = daoSupport.queryForMap(sql, goodsId);
        Integer quantity  = map.get("quantity").intValue();
        Integer enableQuantity =  map.get("enable_quantity").intValue();

        //更新数据库的库存
        sql = "update es_goods set quantity=?,enable_quantity=? where goods_id=?";
        daoSupport.execute(sql, quantity, enableQuantity, goodsId);

        //更新缓存的库存
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
     * 清除不存在的sku的缓存及数据库
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
            logger.debug("删除keys:");
            logger.debug(keys.toString());
        }

        if (keys!=null && !keys.isEmpty() ){

            stringRedisTemplate.delete(keys);
        }

        //批量删除要删除的：hashcode 不存在的 ，但不能是hashcode=-1的，因为有可能是没有规格导致的skuList为空
        if (StringUtil.isEmpty(hashCodeStr)) {
            daoSupport.execute("delete from es_goods_sku where goods_id=? and hash_code!=-1 ", goodsId);
        } else {
            daoSupport.execute("delete from es_goods_sku where goods_id=? and hash_code not in(" + hashCodeStr + ") ", goodsId);
        }

    }
}
