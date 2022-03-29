/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.pintuan.service.impl;

import com.enation.app.javashop.core.client.goods.GoodsClient;
import com.enation.app.javashop.core.goods.model.vo.CacheGoods;
import com.enation.app.javashop.core.goods.model.vo.GoodsSkuVO;
import com.enation.app.javashop.core.promotion.model.PromotionAbnormalGoods;
import com.enation.app.javashop.core.promotion.pintuan.exception.PintuanErrorCode;
import com.enation.app.javashop.core.promotion.pintuan.model.PinTuanGoodsVO;
import com.enation.app.javashop.core.promotion.pintuan.model.Pintuan;
import com.enation.app.javashop.core.promotion.pintuan.model.PintuanGoodsDO;
import com.enation.app.javashop.core.promotion.pintuan.model.PintuanGoodsDTO;
import com.enation.app.javashop.core.promotion.pintuan.service.PinTuanSearchManager;
import com.enation.app.javashop.core.promotion.pintuan.service.PintuanGoodsManager;
import com.enation.app.javashop.core.promotion.pintuan.service.PintuanManager;
import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionStatusEnum;
import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.exception.ResourceNotFoundException;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.util.DateUtil;
import com.enation.app.javashop.framework.util.SqlUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 拼团商品业务类
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-22 11:20:56
 */
@Service
public class PintuanGoodsManagerImpl implements PintuanGoodsManager {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport tradeDaoSupport;


    @Autowired
    private PinTuanSearchManager pinTuanSearchManager;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private PintuanManager pintuanManager;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_pintuan_goods ";
        Page webPage = this.tradeDaoSupport.queryForPage(sql, page, pageSize, PintuanGoodsDO.class);

        return webPage;
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PintuanGoodsDO add(PintuanGoodsDO pintuanGoods) {

        this.tradeDaoSupport.insert(pintuanGoods);
        if (logger.isDebugEnabled()) {
            logger.debug("将拼团商品" + pintuanGoods + "写入数据库");
        }

        return pintuanGoods;
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(Integer pintuanId, List<PintuanGoodsDO> pintuanGoodsList) {

        Pintuan pintuan = pintuanManager.getModel(pintuanId);


        Integer[] skuIds = new Integer[pintuanGoodsList.size()];

        for (int i = 0; i < pintuanGoodsList.size(); i++) {
            skuIds[i] = pintuanGoodsList.get(i).getSkuId();
        }
        //拼团促销活动
        List<PromotionAbnormalGoods> promotionAbnormalGoods = this.checkPromotion(skuIds, pintuan.getStartTime(), pintuan.getEndTime(), pintuan.getPromotionId());

        StringBuffer stringBuffer = new StringBuffer();
        if (promotionAbnormalGoods.size() > 0) {
            promotionAbnormalGoods.forEach(pGoods -> {
                stringBuffer.append("商品【" + pGoods.getGoodsName() + "】参与【" + PromotionTypeEnum.valueOf(pGoods.getPromotionType()).getPromotionName() + "】活动,");
                stringBuffer.append("时间冲突【" + DateUtil.toString(pGoods.getStartTime(), "yyyy-MM-dd HH:mm:ss") + "~" + DateUtil.toString(pGoods.getEndTime(), "yyyy-MM-dd HH:mm:ss") + "】;");
            });
            throw new ServiceException(PintuanErrorCode.E5015.code(), stringBuffer.toString());
        }

        tradeDaoSupport.execute("delete from es_pintuan_goods where pintuan_id=?", pintuan.getPromotionId());

        pintuanGoodsList.forEach(pintuanGoodsDO -> {

            pintuanGoodsDO.setPintuanId(pintuan.getPromotionId());
            GoodsSkuVO skuVo = goodsClient.getSkuFromCache(pintuanGoodsDO.getSkuId());
            //验证拼团价格和原始价格
            if (pintuanGoodsDO.getSalesPrice() > skuVo.getPrice()) {
                throw new ServiceException(PintuanErrorCode.E5015.code(), skuVo.getGoodsName() + ",此商品的拼团价格不能大于商品销售价格");
            }
            pintuanGoodsDO.setSellerId(skuVo.getSellerId());
            pintuanGoodsDO.setSellerName(skuVo.getSellerName());
            pintuanGoodsDO.setThumbnail(skuVo.getThumbnail());
            pintuanGoodsDO.setSpecs(skuVo.getSpecs());
            pintuanGoodsDO.setOriginPrice(skuVo.getPrice());
            pintuanGoodsDO.setGoodsId(skuVo.getGoodsId());
            pintuanGoodsDO.setGoodsName(skuVo.getGoodsName());
            pintuanGoodsDO.setLockedQuantity(0);
            pintuanGoodsDO.setSoldQuantity(0);
            pintuanGoodsDO.setSn(skuVo.getSn());
            add(pintuanGoodsDO);

        });

        //如果拼团活动是进行中的状态，则同步商品
        if (pintuan.getStatus().equals(PromotionStatusEnum.UNDERWAY.name())) {
            //同步es
            pinTuanSearchManager.syncIndexByPinTuanId(pintuanId);
        }


    }


    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PintuanGoodsDO edit(PintuanGoodsDO pintuanGoods, Integer id) {
        this.tradeDaoSupport.update(pintuanGoods, id);
        return pintuanGoods;
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.tradeDaoSupport.delete(PintuanGoodsDO.class, id);
    }

    @Override
    public PintuanGoodsDO getModel(Integer id) {
        return this.tradeDaoSupport.queryForObject(PintuanGoodsDO.class, id);
    }

    /**
     * 获取拼团商品
     *
     * @param pintuanId
     * @param skuId
     * @return
     */
    @Override
    public PintuanGoodsDO getModel(Integer pintuanId, Integer skuId) {
        return this.tradeDaoSupport.queryForObject("select * from es_pintuan_goods where pintuan_id = ? and sku_id = ?", PintuanGoodsDO.class, pintuanId, skuId);
    }


    @Override
    public PinTuanGoodsVO getDetail(Integer skuId) {
        String sql = "select g.*,pt.required_num,pt.limit_num,pt.end_time from es_pintuan_goods g ,es_pintuan pt where g.pintuan_id= pt.promotion_id and g.sku_id=? and pt.start_time<? and pt.end_time>? ";

        Long now = DateUtil.getDateline();

        List<PinTuanGoodsVO> list = tradeDaoSupport.queryForList(sql, PinTuanGoodsVO.class, skuId, now, now);
        PinTuanGoodsVO pinTuanGoodsVO = null;
        if (list.size() > 0) {
            pinTuanGoodsVO = list.get(0);
        }
        if (pinTuanGoodsVO == null) {
            throw new ResourceNotFoundException("skuid为" + skuId + "的拼团商品不存在");
        }

        //计算剩余秒数为结束时间减掉当前时间（秒数）
        long currTime = DateUtil.getDateline();
        pinTuanGoodsVO.setTimeLeft(pinTuanGoodsVO.getEndTime() - currTime);

        return pinTuanGoodsVO;
    }

    @Override
    public List<GoodsSkuVO> skus(Integer goodsId, Integer pintuanId) {

        CacheGoods cacheGoods = goodsClient.getFromCache(goodsId);
        List<GoodsSkuVO> skuList = cacheGoods.getSkuList();
        String sql = "select sku_id from es_pintuan_goods where goods_id=? and pintuan_id = ?";
        List<Map> list = tradeDaoSupport.queryForList(sql, goodsId, pintuanId);

        //将参加了拼团的sku压入到新list中
        List<GoodsSkuVO> newList = new ArrayList<>();
        skuList.forEach(goodsSkuVO -> {
            list.forEach(map -> {
                Integer dbSkuId = (Integer) map.get("sku_id");
                Integer skuId = goodsSkuVO.getSkuId();
                if (skuId.equals(dbSkuId)) {
                    newList.add(goodsSkuVO);
                }
            });
        });

        return newList;
    }

    /**
     * 更新已团数量
     *
     * @param id  拼团商品id
     * @param num 数量
     */
    @Override
    public void addQuantity(Integer id, Integer num) {
        this.tradeDaoSupport.execute("update es_pintuan_goods set sold_quantity = sold_quantity + ? where id = ? ", num, id);
    }

    /**
     * 获取某活动所有商品
     *
     * @param promotionId
     * @return
     */
    @Override
    public List<PinTuanGoodsVO> all(Integer promotionId) {
        String sql = "select * from es_pintuan_goods where pintuan_id=?";
        List<PinTuanGoodsVO> pinTuanGoodsVOS = tradeDaoSupport.queryForList(sql, PinTuanGoodsVO.class, promotionId);

        pinTuanGoodsVOS.forEach(pinTuanGoodsVO -> {

            Integer skuId = pinTuanGoodsVO.getSkuId();
            GoodsSkuVO goodsSkuVO = goodsClient.getSkuFromCache(skuId);
            Integer quantity = 0;
            if (null == goodsSkuVO) {
                logger.error("错误：sku数据为空，相关信息为" + pinTuanGoodsVO.toString());
                // 无法使用break，lambda只能用reburn
                // break;
                return;
            } else {
                quantity = goodsSkuVO.getEnableQuantity();
            }
            pinTuanGoodsVO.setEnableQuantity(quantity);

        });

        return pinTuanGoodsVOS;

    }

    /**
     * 关闭一个活动的促销商品索引
     *
     * @param promotionId
     */
    @Override
    public void delIndex(Integer promotionId) {
        pinTuanSearchManager.deleteByPintuanId(promotionId);
    }

    /**
     * 开启一个活动的促销商品索引
     *
     * @param promotionId
     */
    @Override
    public boolean addIndex(Integer promotionId) {
        List<PinTuanGoodsVO> pinTuanGoodsVOS = this.all(promotionId);

        boolean hasError = false;

        for (PinTuanGoodsVO pintuanGoods : pinTuanGoodsVOS) {
            boolean result = pinTuanSearchManager.addIndex(pintuanGoods);
            hasError = result && hasError;
        }

        return hasError;

    }


    /**
     * 商品查询
     *
     * @param page        页码
     * @param pageSize    分页大小
     * @param promotionId 促销id
     * @param name        商品名称
     * @return
     */
    @Override
    public Page page(Integer page, Integer pageSize, Integer promotionId, String name) {

        String sql = "select * from es_pintuan_goods where pintuan_id = ? ";
        List params = new ArrayList();
        params.add(promotionId);
        if (!StringUtil.isEmpty(name)) {
            sql += " and goods_name like ?";
            params.add("%" + name + "%");
        }
        Page webPage = this.tradeDaoSupport.queryForPage(sql, page, pageSize, PinTuanGoodsVO.class, params.toArray());

        return webPage;
    }

    /**
     * 查询指定时间范围，是否有参与其他活动
     *
     * @param skuIds    SKUid集合
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    @Override
    public List<PromotionAbnormalGoods> checkPromotion(Integer[] skuIds, Long startTime, Long endTime, Integer promotionId) {


        //何为冲突
        //（1）
        //                       校验时间
        //                  --》【              】《--
        // ******************************************************************       时间轴
        //                已经存在 活动
        //       --》【              】《--
        // ******************************************************************       时间轴

        //（2）
        //                       校验时间
        //                  --》【              】《--
        // ******************************************************************       时间轴
        //                                       已经存在 活动
        //                              --》【              】《--
        // ******************************************************************       时间轴

        //（3）
        //                       校验时间
        //                  --》【              】《--
        // ******************************************************************       时间轴
        //                       已经存在 活动
        //        --》【                               】《--
        // ******************************************************************       时间轴

        //（4）
        //                       校验时间
        //                  --》【              】《--
        // ******************************************************************       时间轴
        //                       已经存在 活动
        //                      --》【     】《--
        // ******************************************************************       时间轴
        List term = new ArrayList();
        for (Integer skuid :
                skuIds) {
            term.add(skuid);
        }

        term.add(startTime);
        term.add(startTime);
        term.add(endTime);
        term.add(endTime);
        term.add(startTime);
        term.add(endTime);
        term.add(endTime);
        term.add(startTime);
        term.add(promotionId);
        //查询时间轴以外的促销活动商品
        List<PintuanGoodsDTO> promotionGoodsDOS = this.tradeDaoSupport.queryForList("select * from es_pintuan_goods pg left join es_pintuan p on pg.pintuan_id=p.promotion_id " +
                        " where sku_id in (" + SqlUtil.getInSql(skuIds) + ") and ( (start_time < ? and end_time > ?) or (start_time < ? and end_time > ?) or(start_time < ? and end_time > ?) or(start_time < ? and end_time > ?))" +
                        " and pg.pintuan_id != ? ",

                PintuanGoodsDTO.class, term.toArray()
        );
        List<PromotionAbnormalGoods> promotionAbnormalGoods = new ArrayList<>();
        promotionGoodsDOS.forEach(goods -> {
            PromotionAbnormalGoods paGoods = new PromotionAbnormalGoods();
            paGoods.setPromotionType(PromotionTypeEnum.PINTUAN.name());
            paGoods.setGoodsName(goods.getGoodsName());
            paGoods.setGoodsId(goods.getGoodsId());
            paGoods.setEndTime(goods.getEndTime());
            paGoods.setStartTime(goods.getStartTime());
            promotionAbnormalGoods.add(paGoods);
        });
        return promotionAbnormalGoods;
    }
}
