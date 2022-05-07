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
 * The second half price business category
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
            // If the current time is less than the start time of the activity, the activity has not started
            if (nowTime < halfPriceVO.getStartTime().longValue()) {
                halfPriceVO.setStatusText("Activity not started");
                halfPriceVO.setStatus(PromotionStatusEnum.WAIT.toString());

                // Greater than the start time of the activity and less than the end time of the activity
            } else if (halfPriceVO.getStartTime().longValue() < nowTime && nowTime < halfPriceVO.getEndTime()) {
                halfPriceVO.setStatusText("In progress");
                halfPriceVO.setStatus(PromotionStatusEnum.UNDERWAY.toString());

            } else {
                halfPriceVO.setStatusText("Activity expired");
                halfPriceVO.setStatus(PromotionStatusEnum.END.toString());
            }
        }

        return webPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public HalfPriceVO add(HalfPriceVO halfPriceVO) {

        this.verifyTime(halfPriceVO.getStartTime(), halfPriceVO.getEndTime(), PromotionTypeEnum.HALF_PRICE, null);

        // Form the DTO list of commodities initially
        List<PromotionGoodsDTO> goodsDTOList = new ArrayList<>();
        // Whether all goods are involved
        if (halfPriceVO.getRangeType() == 1) {
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            goodsDTO.setGoodsId(-1);
            goodsDTO.setGoodsName("All the goods");
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

        // Warehousing of moving goods
        this.promotionGoodsManager.add(halfPriceVO.getGoodsList(), detailDTO);

        cache.put(PromotionCacheKeys.getHalfPriceKey(halfPriceVO.getHpId()), halfPriceDO);

        return halfPriceVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public HalfPriceVO edit(HalfPriceVO halfPriceVO, Integer id) {

        this.verifyStatus(id);

        this.verifyTime(halfPriceVO.getStartTime(), halfPriceVO.getEndTime(), PromotionTypeEnum.HALF_PRICE, id);

        // Form the DTO list of commodities initially
        List<PromotionGoodsDTO> goodsDTOList = new ArrayList<>();
        // Whether all goods are involved
        if (halfPriceVO.getRangeType() == 1) {
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            goodsDTO.setGoodsId(-1);
            goodsDTO.setGoodsName("All the goods");
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

        // Warehousing of moving goods
        this.promotionGoodsManager.edit(halfPriceVO.getGoodsList(), detailDTO);

        cache.put(PromotionCacheKeys.getHalfPriceKey(halfPriceVO.getHpId()), halfPriceDO);

        return halfPriceVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public void delete(Integer id) {

        this.verifyStatus(id);
        this.daoSupport.delete(HalfPriceDO.class, id);
        // Delete the activity relation mapping table
        this.promotionGoodsManager.delete(id, PromotionTypeEnum.HALF_PRICE.name());
        this.cache.remove(PromotionCacheKeys.getHalfPriceKey(id));

    }


    @Override
    public HalfPriceVO getFromDB(Integer id) {
        // Read the activity information in the cache
        HalfPriceDO halfPriceDO = (HalfPriceDO) this.cache.get(PromotionCacheKeys.getHalfPriceKey(id));
        // If empty, read from the database
        if (halfPriceDO == null) {
            halfPriceDO = this.daoSupport.queryForObject(HalfPriceDO.class, id);
        }
        // If reads from the cache and database are empty, an exception is thrown
        if (halfPriceDO == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "Activity does not exist");
        }

        HalfPriceVO halfPriceVO = new HalfPriceVO();
        BeanUtils.copyProperties(halfPriceDO, halfPriceVO);

        // Read the commodities that participate in this activity
        List<PromotionGoodsDO> goodsDOList = this.promotionGoodsManager.getPromotionGoods(id, PromotionTypeEnum.HALF_PRICE.name());
        Integer[] goodsIds = new Integer[goodsDOList.size()];
        for (int i = 0; i < goodsDOList.size(); i++) {
            goodsIds[i] = goodsDOList.get(i).getGoodsId();
        }

        // Read information about goods
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
        // Verify unauthorized operations
        if (halfPriceVO == null) {
            throw new NoPermissionException("Have the right to operate");
        }
    }


    /**
     * Verify that this activity is editable and deleted<br/>
     * Throw an exception if there is a problem
     *
     * @param halfPriceId activityid
     */
    private void verifyStatus(Integer halfPriceId) {
        HalfPriceVO halfPriceVO = this.getFromDB(halfPriceId);
        long nowTime = DateUtil.getDateline();

        // If the start time is less than the present time, the activity has already started.
        if (halfPriceVO.getStartTime().longValue() < nowTime && halfPriceVO.getEndTime().longValue() > nowTime) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "The activity has started. You cannot edit or delete the activity");
        }

    }

}
