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
package cloud.shopfly.b2c.core.promotion.fulldiscount.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountManager;
import cloud.shopfly.b2c.core.promotion.tool.model.dos.PromotionGoodsDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDetailDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionStatusEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import cloud.shopfly.b2c.core.promotion.tool.service.impl.AbstractPromotionRuleManagerImpl;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionCacheKeys;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Full discount activities business class
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 17:34:32
 */
@Service
public class FullDiscountManagerImpl extends AbstractPromotionRuleManagerImpl implements FullDiscountManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private Cache cache;

    @Override
    public Page list(int page, int pageSize, String keywords) {

        List params = new ArrayList();

        StringBuffer sql = new StringBuffer("select * from es_full_discount");

        if (!StringUtil.isEmpty(keywords)) {
            sql.append(" where title like ? ");
            params.add("%" + keywords + "%");
        }
        sql.append(" order by fd_id desc");
        Page webPage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, FullDiscountVO.class, params.toArray());

        List<FullDiscountVO> fullDiscountVOList = webPage.getData();
        for (FullDiscountVO fullDiscountVO : fullDiscountVOList) {
            long nowTime = DateUtil.getDateline();
            // If the current time is less than the start time of the activity, the activity has not started
            if (nowTime < fullDiscountVO.getStartTime().longValue()) {
                fullDiscountVO.setStatusText("Activity not started");
                fullDiscountVO.setStatus(PromotionStatusEnum.WAIT.toString());
                // Greater than the start time of the activity and less than the end time of the activity
            } else if (fullDiscountVO.getStartTime().longValue() < nowTime && nowTime < fullDiscountVO.getEndTime()) {
                fullDiscountVO.setStatusText("In progress");
                fullDiscountVO.setStatus(PromotionStatusEnum.UNDERWAY.toString());

            } else {
                fullDiscountVO.setStatusText("Activity is over");
                fullDiscountVO.setStatus(PromotionStatusEnum.END.toString());
            }
        }

        return webPage;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public FullDiscountVO add(FullDiscountVO fullDiscountVO) {

        this.verifyTime(fullDiscountVO.getStartTime(), fullDiscountVO.getEndTime(), PromotionTypeEnum.FULL_DISCOUNT, null);

        FullDiscountDO fullDiscountDO = this.getFullDiscountDO(fullDiscountVO);

        this.daoSupport.insert(fullDiscountDO);

        // Get active Id
        Integer id = this.daoSupport.getLastId("es_full_discount");
        fullDiscountVO.setFdId(id);
        fullDiscountDO.setFdId(id);

        PromotionDetailDTO detailDTO = new PromotionDetailDTO();
        detailDTO.setStartTime(fullDiscountVO.getStartTime());
        detailDTO.setEndTime(fullDiscountVO.getEndTime());
        detailDTO.setActivityId(fullDiscountVO.getFdId());
        detailDTO.setPromotionType(PromotionTypeEnum.FULL_DISCOUNT.name());
        detailDTO.setTitle(fullDiscountVO.getTitle());

        // Warehousing of moving goods
        this.promotionGoodsManager.add(fullDiscountVO.getGoodsList(), detailDTO);
        cache.put(PromotionCacheKeys.getFullDiscountKey(id), fullDiscountDO);

        return fullDiscountVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public FullDiscountVO edit(FullDiscountVO fullDiscountVO, Integer id) {

        this.verifyStatus(id);

        this.verifyTime(fullDiscountVO.getStartTime(), fullDiscountVO.getEndTime(), PromotionTypeEnum.FULL_DISCOUNT, id);

        FullDiscountDO fullDiscountDO = getFullDiscountDO(fullDiscountVO);

        this.daoSupport.update(fullDiscountDO, id);

        // Delete the previous activity and product comparison relationship
        PromotionDetailDTO detailDTO = new PromotionDetailDTO();
        detailDTO.setStartTime(fullDiscountVO.getStartTime());
        detailDTO.setEndTime(fullDiscountVO.getEndTime());
        detailDTO.setActivityId(fullDiscountVO.getFdId());
        detailDTO.setPromotionType(PromotionTypeEnum.FULL_DISCOUNT.name());
        detailDTO.setTitle(fullDiscountVO.getTitle());

        // Warehousing of moving goods
        this.promotionGoodsManager.edit(fullDiscountVO.getGoodsList(), detailDTO);
        cache.put(PromotionCacheKeys.getFullDiscountKey(fullDiscountVO.getFdId()), fullDiscountDO);

        return fullDiscountVO;
    }

    /**
     * To obtainDOobject
     * @param fullDiscountVO
     * @return
     */
    private FullDiscountDO getFullDiscountDO(FullDiscountVO fullDiscountVO){

        // Whether all goods are involved
        if (fullDiscountVO.getRangeType() == 1) {
            List<PromotionGoodsDTO> goodsDTOList = new ArrayList<>();
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            goodsDTO.setGoodsId(-1);
            goodsDTO.setGoodsName("All the goods");
            goodsDTO.setThumbnail("");
            goodsDTOList.add(goodsDTO);
            fullDiscountVO.setGoodsList(goodsDTOList);
        }

        this.verifyRule(fullDiscountVO.getGoodsList());

        FullDiscountDO fullDiscountDO = new FullDiscountDO();
        BeanUtils.copyProperties(fullDiscountVO, fullDiscountDO);

        return fullDiscountDO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public void delete(Integer id) {

        this.verifyStatus(id);
        this.daoSupport.delete(FullDiscountDO.class, id);
        // Delete the activity table
        this.promotionGoodsManager.delete(id, PromotionTypeEnum.FULL_DISCOUNT.name());
        this.cache.remove(PromotionCacheKeys.getFullDiscountKey(id));
    }


    @Override
    public FullDiscountVO getModel(Integer fdId) {
        FullDiscountDO fullDiscountDO = (FullDiscountDO) this.cache.get(PromotionCacheKeys.getFullDiscountKey(fdId));
        if (fullDiscountDO == null) {
            fullDiscountDO = this.daoSupport.queryForObject(FullDiscountDO.class, fdId);
        }

        if (fullDiscountDO == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "Activity does not exist");
        }

        FullDiscountVO fullDiscountVO = new FullDiscountVO();
        BeanUtils.copyProperties(fullDiscountDO, fullDiscountVO);

        List<PromotionGoodsDO> goodsDOList = this.promotionGoodsManager.getPromotionGoods(fdId, PromotionTypeEnum.FULL_DISCOUNT.name());
        if (goodsDOList.isEmpty()) {
            throw new ServiceException(PromotionErrorCode.E401.code(), "There is no merchandise involved");
        }

        Integer[] goodsIds = new Integer[goodsDOList.size()];
        for (int i = 0; i < goodsDOList.size(); i++) {
            goodsIds[i] = goodsDOList.get(i).getGoodsId();
        }

        List<GoodsSelectLine> goodsSelectLineList = this.goodsClient.query(goodsIds);
        List<PromotionGoodsDTO> goodsList = new ArrayList<>();

        for (GoodsSelectLine goodsSelectLine : goodsSelectLineList) {
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            BeanUtils.copyProperties(goodsSelectLine, goodsDTO);
            goodsList.add(goodsDTO);
        }

        fullDiscountVO.setGoodsList(goodsList);
        return fullDiscountVO;
    }


    @Override
    public void verifyAuth(Integer id) {
        FullDiscountVO fullDiscountVO = this.getModel(id);
        // Verify unauthorized operations
        if (fullDiscountVO == null) {
            throw new NoPermissionException("Have the right to operate");
        }
    }


    /**
     * Verify that this activity is editable and deleted<br/>
     * Throw an exception if there is a problem
     *
     * @param id activityid
     */
    private void verifyStatus(Integer id) {
        FullDiscountVO fullDiscountVO = this.getModel(id);
        long nowTime = DateUtil.getDateline();

        // If the start time is less than the present time, the activity has already started.
        if (fullDiscountVO.getStartTime().longValue() < nowTime && fullDiscountVO.getEndTime().longValue() > nowTime) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "The activity has started. You cannot edit or delete the activity");
        }
    }

}
