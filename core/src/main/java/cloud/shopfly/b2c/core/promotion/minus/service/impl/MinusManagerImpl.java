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
package cloud.shopfly.b2c.core.promotion.minus.service.impl;

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
import cloud.shopfly.b2c.core.promotion.minus.model.dos.MinusDO;
import cloud.shopfly.b2c.core.promotion.minus.model.vo.MinusVO;
import cloud.shopfly.b2c.core.promotion.minus.service.MinusManager;
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
 * Single product reduction implementation class
 * @author Snow create in 2018/3/22
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class MinusManagerImpl extends AbstractPromotionRuleManagerImpl implements MinusManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private Cache cache;


    @Override
    public MinusVO getFromDB(Integer minusId) {

        MinusDO minusDO = (MinusDO) this.cache.get(PromotionCacheKeys.getMinusKey(minusId));
        if(minusDO == null ){
            minusDO = this.daoSupport.queryForObject(MinusDO.class,minusId);
        }

        if(minusDO == null){
            return null;
        }
        MinusVO minusVO = new MinusVO();
        BeanUtils.copyProperties(minusDO,minusVO);

        List<PromotionGoodsDO> goodsDOList = this.promotionGoodsManager.getPromotionGoods(minusId, PromotionTypeEnum.MINUS.name());
        Integer[] goodsIds = new Integer[goodsDOList.size()];
        for(int i =0;i<goodsDOList.size(); i++){
            goodsIds[i] = goodsDOList.get(i).getGoodsId();
        }

        List<GoodsSelectLine> goodsSelectLineList = this.goodsClient.query(goodsIds);
        List<PromotionGoodsDTO> goodsList = new ArrayList<>();

        for(GoodsSelectLine goodsSelectLine:goodsSelectLineList){
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            BeanUtil.copyProperties(goodsSelectLine,goodsDTO);
            goodsList.add(goodsDTO);
        }

        minusVO.setGoodsList(goodsList);
        return minusVO;
    }

    @Override
    public Page list(int page, int pageSize, String keywords){
        List paramList = new ArrayList();
        List<String> sqlList = new ArrayList<>();

        StringBuffer sql = new StringBuffer("select * from es_minus");

        if(!StringUtil.isEmpty(keywords)){
            sqlList.add(" title like ? ");
            paramList.add("%"+keywords+"%");
        }
        sql.append(SqlUtil.sqlSplicing(sqlList));
        sql.append(" order by minus_id desc");
        Page webPage = this.daoSupport.queryForPage(sql.toString(),page, pageSize ,MinusVO.class,paramList.toArray());

        List<MinusVO> minusVOList = webPage.getData();
        for (MinusVO minusVO :minusVOList){
            long nowTime = DateUtil.getDateline();
            // If the current time is less than the start time of the activity, the activity has not started
            if(nowTime < minusVO.getStartTime().longValue() ){
                minusVO.setStatusText("Activity not started");
                minusVO.setStatus(PromotionStatusEnum.WAIT.toString());
                // Greater than the start time of the activity and less than the end time of the activity
            }else if(minusVO.getStartTime().longValue() < nowTime && nowTime < minusVO.getEndTime() ){
                minusVO.setStatusText("In progress");
                minusVO.setStatus(PromotionStatusEnum.UNDERWAY.toString());

            }else{
                minusVO.setStatusText("Activity expired");
                minusVO.setStatus(PromotionStatusEnum.END.toString());
            }
        }

        return webPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class})
    public	MinusVO  add(MinusVO minusVO)	{

        this.verifyTime(minusVO.getStartTime(),minusVO.getEndTime(), PromotionTypeEnum.MINUS,null);

        // Form the DTO list of commodities initially
        List<PromotionGoodsDTO> goodsDTOList = new ArrayList<>();
        // Whether all goods are involved
        if(minusVO.getRangeType() == 1){
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            goodsDTO.setGoodsId(-1);
            goodsDTO.setGoodsName("All the goods");
            goodsDTO.setThumbnail("path");
            goodsDTOList.add(goodsDTO);
            minusVO.setGoodsList(goodsDTOList);
        }
        // Test activity rule
        this.verifyRule(minusVO.getGoodsList());

        MinusDO minusDO = new MinusDO();
        BeanUtils.copyProperties(minusVO,minusDO);
        this.daoSupport.insert(minusDO);

        // Get active Id
        Integer minusId = this.daoSupport.getLastId("es_minus");
        minusDO.setMinusId(minusId);
        minusVO.setMinusId(minusId);

        PromotionDetailDTO detailDTO = new PromotionDetailDTO();
        detailDTO.setStartTime(minusVO.getStartTime());
        detailDTO.setEndTime(minusVO.getEndTime());
        detailDTO.setActivityId(minusVO.getMinusId());
        detailDTO.setPromotionType(PromotionTypeEnum.MINUS.name());
        detailDTO.setTitle(minusVO.getTitle());

        // Warehousing of moving goods
        this.promotionGoodsManager.add(minusVO.getGoodsList(),detailDTO);

        String minusKey = PromotionCacheKeys.getMinusKey(minusId);
        cache.put(minusKey, minusDO);

        return minusVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public	MinusVO  edit(MinusVO	minusVO, Integer id){

        // Check that this activity is actionable
        this.verifyStatus(id);

        this.verifyTime(minusVO.getStartTime(),minusVO.getEndTime(), PromotionTypeEnum.MINUS,id);

        // Form the DTO list of commodities initially
        List<PromotionGoodsDTO> goodsDTOList = new ArrayList<>();
        // Whether all goods are involved
        if(minusVO.getRangeType() == 1){
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            goodsDTO.setGoodsId(-1);
            goodsDTO.setGoodsName("All the goods");
            goodsDTO.setThumbnail("");
            goodsDTOList.add(goodsDTO);
            minusVO.setGoodsList(goodsDTOList);
        }
        // Test activity rule
        this.verifyRule(minusVO.getGoodsList());
        MinusDO minusDO = new MinusDO();
        BeanUtils.copyProperties(minusVO,minusDO);
        this.daoSupport.update(minusDO, id);

        // Delete the previous activity and product comparison relationship
        PromotionDetailDTO detailDTO = new PromotionDetailDTO();
        detailDTO.setStartTime(minusVO.getStartTime());
        detailDTO.setEndTime(minusVO.getEndTime());
        detailDTO.setActivityId(minusVO.getMinusId());
        detailDTO.setPromotionType(PromotionTypeEnum.MINUS.name());
        detailDTO.setTitle(minusVO.getTitle());

        // Warehousing of moving goods
        this.promotionGoodsManager.edit(minusVO.getGoodsList(),detailDTO);

        String minusKey = PromotionCacheKeys.getMinusKey(id);
        cache.put(minusKey, minusDO);

        return minusVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {RuntimeException.class, Exception.class, ServiceException.class, NoPermissionException.class})
    public	void delete( Integer id)	{

        this.verifyStatus(id);
        this.daoSupport.delete(MinusDO.class,id);
        // Delete the comparison table of the activity of the single item
        this.promotionGoodsManager.delete(id, PromotionTypeEnum.MINUS.name());
        this.cache.remove(PromotionCacheKeys.getMinusKey(id));
    }


    @Override
    public void verifyAuth(Integer minusId) {
        MinusDO minusDO = this.getFromDB(minusId);
        // Verify unauthorized operations
        if (minusDO == null){
            throw new NoPermissionException("Have the right to operate");
        }
    }


    /**
     * Verify that this activity is editable and deleted<br/>
     * Throw an exception if there is a problem
     * @param minusId   activityid
     */
    private void verifyStatus(Integer minusId) {
        MinusVO minusVO = this.getFromDB(minusId);
        long nowTime = DateUtil.getDateline();

        // If the start time is less than the present time, the activity has already started.
        if(minusVO.getStartTime().longValue() < nowTime && minusVO.getEndTime().longValue() > nowTime){
            throw new ServiceException(PromotionErrorCode.E400.code(),"The activity has started. You cannot edit or delete the activity");
        }
    }

}
