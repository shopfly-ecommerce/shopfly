/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 满优惠活动业务类
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
            //当前时间小于活动的开始时间 则为活动未开始
            if (nowTime < fullDiscountVO.getStartTime().longValue()) {
                fullDiscountVO.setStatusText("活动未开始");
                fullDiscountVO.setStatus(PromotionStatusEnum.WAIT.toString());
                //大于活动的开始时间，小于活动的结束时间
            } else if (fullDiscountVO.getStartTime().longValue() < nowTime && nowTime < fullDiscountVO.getEndTime()) {
                fullDiscountVO.setStatusText("正在进行中");
                fullDiscountVO.setStatus(PromotionStatusEnum.UNDERWAY.toString());

            } else {
                fullDiscountVO.setStatusText("活动已结束");
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

        // 获取活动Id
        Integer id = this.daoSupport.getLastId("es_full_discount");
        fullDiscountVO.setFdId(id);
        fullDiscountDO.setFdId(id);

        PromotionDetailDTO detailDTO = new PromotionDetailDTO();
        detailDTO.setStartTime(fullDiscountVO.getStartTime());
        detailDTO.setEndTime(fullDiscountVO.getEndTime());
        detailDTO.setActivityId(fullDiscountVO.getFdId());
        detailDTO.setPromotionType(PromotionTypeEnum.FULL_DISCOUNT.name());
        detailDTO.setTitle(fullDiscountVO.getTitle());

        //将活动商品入库
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

        //删除之前的活动与商品的对照关系
        PromotionDetailDTO detailDTO = new PromotionDetailDTO();
        detailDTO.setStartTime(fullDiscountVO.getStartTime());
        detailDTO.setEndTime(fullDiscountVO.getEndTime());
        detailDTO.setActivityId(fullDiscountVO.getFdId());
        detailDTO.setPromotionType(PromotionTypeEnum.FULL_DISCOUNT.name());
        detailDTO.setTitle(fullDiscountVO.getTitle());

        //将活动商品入库
        this.promotionGoodsManager.edit(fullDiscountVO.getGoodsList(), detailDTO);
        cache.put(PromotionCacheKeys.getFullDiscountKey(fullDiscountVO.getFdId()), fullDiscountDO);

        return fullDiscountVO;
    }

    /**
     * 获取DO对象
     * @param fullDiscountVO
     * @return
     */
    private FullDiscountDO getFullDiscountDO(FullDiscountVO fullDiscountVO){

        //是否是全部商品参与
        if (fullDiscountVO.getRangeType() == 1) {
            List<PromotionGoodsDTO> goodsDTOList = new ArrayList<>();
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            goodsDTO.setGoodsId(-1);
            goodsDTO.setGoodsName("全部商品");
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
        //删除活动对照表
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
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动不存在");
        }

        FullDiscountVO fullDiscountVO = new FullDiscountVO();
        BeanUtils.copyProperties(fullDiscountDO, fullDiscountVO);

        List<PromotionGoodsDO> goodsDOList = this.promotionGoodsManager.getPromotionGoods(fdId, PromotionTypeEnum.FULL_DISCOUNT.name());
        if (goodsDOList.isEmpty()) {
            throw new ServiceException(PromotionErrorCode.E401.code(), "此活动没有商品参与");
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
        //验证越权操作
        if (fullDiscountVO == null) {
            throw new NoPermissionException("无权操作");
        }
    }


    /**
     * 验证此活动是否可进行编辑删除操作<br/>
     * 如有问题则抛出异常
     *
     * @param id 活动id
     */
    private void verifyStatus(Integer id) {
        FullDiscountVO fullDiscountVO = this.getModel(id);
        long nowTime = DateUtil.getDateline();

        //如果活动起始时间小于现在时间，活动已经开始了。
        if (fullDiscountVO.getStartTime().longValue() < nowTime && fullDiscountVO.getEndTime().longValue() > nowTime) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动已经开始，不能进行编辑删除操作");
        }
    }

}
