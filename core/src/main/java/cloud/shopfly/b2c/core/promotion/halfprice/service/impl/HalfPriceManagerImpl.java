/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.halfprice.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.tool.model.dos.PromotionGoodsDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDetailDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionStatusEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import cloud.shopfly.b2c.core.promotion.tool.service.impl.AbstractPromotionRuleManagerImpl;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionCacheKeys;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.core.promotion.halfprice.model.dos.HalfPriceDO;
import cloud.shopfly.b2c.core.promotion.halfprice.model.vo.HalfPriceVO;
import cloud.shopfly.b2c.core.promotion.halfprice.service.HalfPriceManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 第二件半价业务类
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 19:53:42
 */
@Service
public class HalfPriceManagerImpl extends AbstractPromotionRuleManagerImpl implements HalfPriceManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private Cache cache;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private GoodsClient goodsClient;

    @Override
    public Page list(int page, int pageSize, String keywords) {
        List paramList = new ArrayList();
        List<String> sqlList = new ArrayList<>();

        StringBuffer sql = new StringBuffer("select * from es_half_price");

        if (!StringUtil.isEmpty(keywords)) {
            sqlList.add(" title like ? ");
            paramList.add("%" + keywords + "%");
        }
        sql.append(SqlUtil.sqlSplicing(sqlList));
        sql.append(" order by hp_id desc");

        Page webPage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, HalfPriceVO.class, paramList.toArray());

        List<HalfPriceVO> halfPriceVOList = webPage.getData();
        for (HalfPriceVO halfPriceVO : halfPriceVOList) {
            long nowTime = DateUtil.getDateline();
            //当前时间小于活动的开始时间 则为活动未开始
            if (nowTime < halfPriceVO.getStartTime().longValue()) {
                halfPriceVO.setStatusText("活动未开始");
                halfPriceVO.setStatus(PromotionStatusEnum.WAIT.toString());

                //大于活动的开始时间，小于活动的结束时间
            } else if (halfPriceVO.getStartTime().longValue() < nowTime && nowTime < halfPriceVO.getEndTime()) {
                halfPriceVO.setStatusText("正在进行中");
                halfPriceVO.setStatus(PromotionStatusEnum.UNDERWAY.toString());

            } else {
                halfPriceVO.setStatusText("活动已失效");
                halfPriceVO.setStatus(PromotionStatusEnum.END.toString());
            }
        }

        return webPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public HalfPriceVO add(HalfPriceVO halfPriceVO) {

        this.verifyTime(halfPriceVO.getStartTime(), halfPriceVO.getEndTime(), PromotionTypeEnum.HALF_PRICE, null);

        //初步形成商品的DTO列表
        List<PromotionGoodsDTO> goodsDTOList = new ArrayList<>();
        //是否是全部商品参与
        if (halfPriceVO.getRangeType() == 1) {
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            goodsDTO.setGoodsId(-1);
            goodsDTO.setGoodsName("全部商品");
            goodsDTO.setThumbnail("");
            goodsDTOList.add(goodsDTO);
            halfPriceVO.setGoodsList(goodsDTOList);
        }

        this.verifyRule(halfPriceVO.getGoodsList());

        HalfPriceDO halfPriceDO = new HalfPriceDO();
        BeanUtils.copyProperties(halfPriceVO, halfPriceDO);
        this.daoSupport.insert(halfPriceDO);

        Integer id = this.daoSupport.getLastId("es_half_price");
        halfPriceDO.setHpId(id);
        halfPriceVO.setHpId(id);


        PromotionDetailDTO detailDTO = new PromotionDetailDTO();
        detailDTO.setStartTime(halfPriceVO.getStartTime());
        detailDTO.setEndTime(halfPriceVO.getEndTime());
        detailDTO.setActivityId(halfPriceVO.getHpId());
        detailDTO.setPromotionType(PromotionTypeEnum.HALF_PRICE.name());
        detailDTO.setTitle(halfPriceVO.getTitle());

        //将活动商品入库
        this.promotionGoodsManager.add(halfPriceVO.getGoodsList(), detailDTO);

        cache.put(PromotionCacheKeys.getHalfPriceKey(halfPriceVO.getHpId()), halfPriceDO);

        return halfPriceVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public HalfPriceVO edit(HalfPriceVO halfPriceVO, Integer id) {

        this.verifyStatus(id);

        this.verifyTime(halfPriceVO.getStartTime(), halfPriceVO.getEndTime(), PromotionTypeEnum.HALF_PRICE, id);

        //初步形成商品的DTO列表
        List<PromotionGoodsDTO> goodsDTOList = new ArrayList<>();
        //是否是全部商品参与
        if (halfPriceVO.getRangeType() == 1) {
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            goodsDTO.setGoodsId(-1);
            goodsDTO.setGoodsName("全部商品");
            goodsDTO.setThumbnail("");
            goodsDTOList.add(goodsDTO);
            halfPriceVO.setGoodsList(goodsDTOList);
        }

        this.verifyRule(halfPriceVO.getGoodsList());

        HalfPriceDO halfPriceDO = new HalfPriceDO();
        BeanUtils.copyProperties(halfPriceVO, halfPriceDO);

        this.daoSupport.update(halfPriceDO, id);

        PromotionDetailDTO detailDTO = new PromotionDetailDTO();
        detailDTO.setStartTime(halfPriceVO.getStartTime());
        detailDTO.setEndTime(halfPriceVO.getEndTime());
        detailDTO.setActivityId(halfPriceVO.getHpId());
        detailDTO.setPromotionType(PromotionTypeEnum.HALF_PRICE.name());
        detailDTO.setTitle(halfPriceVO.getTitle());

        //将活动商品入库
        this.promotionGoodsManager.edit(halfPriceVO.getGoodsList(), detailDTO);

        cache.put(PromotionCacheKeys.getHalfPriceKey(halfPriceVO.getHpId()), halfPriceDO);

        return halfPriceVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public void delete(Integer id) {

        this.verifyStatus(id);
        this.daoSupport.delete(HalfPriceDO.class, id);
        //删除活动关系对照表
        this.promotionGoodsManager.delete(id, PromotionTypeEnum.HALF_PRICE.name());
        this.cache.remove(PromotionCacheKeys.getHalfPriceKey(id));

    }


    @Override
    public HalfPriceVO getFromDB(Integer id) {
        //读取缓存中的活动信息
        HalfPriceDO halfPriceDO = (HalfPriceDO) this.cache.get(PromotionCacheKeys.getHalfPriceKey(id));
        //如果为空从数据库中读取
        if (halfPriceDO == null) {
            halfPriceDO = this.daoSupport.queryForObject(HalfPriceDO.class, id);
        }
        //如果从缓存和数据库读取都是空，则抛出异常
        if (halfPriceDO == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动不存在");
        }

        HalfPriceVO halfPriceVO = new HalfPriceVO();
        BeanUtils.copyProperties(halfPriceDO, halfPriceVO);

        //读取此活动参与的商品
        List<PromotionGoodsDO> goodsDOList = this.promotionGoodsManager.getPromotionGoods(id, PromotionTypeEnum.HALF_PRICE.name());
        Integer[] goodsIds = new Integer[goodsDOList.size()];
        for (int i = 0; i < goodsDOList.size(); i++) {
            goodsIds[i] = goodsDOList.get(i).getGoodsId();
        }

        //读取商品的信息
        List<GoodsSelectLine> goodsSelectLineList = this.goodsClient.query(goodsIds);
        List<PromotionGoodsDTO> goodsList = new ArrayList<>();

        for (GoodsSelectLine goodsSelectLine : goodsSelectLineList) {
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            BeanUtil.copyProperties(goodsSelectLine, goodsDTO);
            goodsList.add(goodsDTO);
        }

        halfPriceVO.setGoodsList(goodsList);

        return halfPriceVO;
    }

    @Override
    public void verifyAuth(Integer id) {
        HalfPriceVO halfPriceVO = this.getFromDB(id);
        //验证越权操作
        if (halfPriceVO == null) {
            throw new NoPermissionException("无权操作");
        }
    }


    /**
     * 验证此活动是否可进行编辑删除操作<br/>
     * 如有问题则抛出异常
     *
     * @param halfPriceId 活动id
     */
    private void verifyStatus(Integer halfPriceId) {
        HalfPriceVO halfPriceVO = this.getFromDB(halfPriceId);
        long nowTime = DateUtil.getDateline();

        //如果活动起始时间小于现在时间，活动已经开始了。
        if (halfPriceVO.getStartTime().longValue() < nowTime && halfPriceVO.getEndTime().longValue() > nowTime) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动已经开始，不能进行编辑删除操作");
        }

    }

}
