/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.groupbuy.service;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyQueryParam;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDTO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 团购商品业务层
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:57:26
 */
public interface GroupbuyGoodsManager {


    /**
     * 商家查询团购商品列表
     *
     * @param param 查询参数
     * @return Page
     */
    Page listPage(GroupbuyQueryParam param);


    /**
     * 卖家查询团购商品列表
     *
     * @param param 查询参数
     * @return Page
     */
    Page listPageByBuyer(GroupbuyQueryParam param);


    /**
     * 添加团购商品
     *
     * @param groupbuyGoods 团购商品
     * @return GroupbuyGoods 团购商品
     */
    GroupbuyGoodsDO add(GroupbuyGoodsDO groupbuyGoods);

    /**
     * 修改团购商品
     *
     * @param groupbuyGoods 团购商品
     * @param id            团购商品主键
     * @return GroupbuyGoods 团购商品
     */
    GroupbuyGoodsDO edit(GroupbuyGoodsDO groupbuyGoods, Integer id);

    /**
     * 删除团购商品
     *
     * @param id 团购商品主键
     */
    void delete(Integer id);

    /**
     * 获取团购商品
     *
     * @param gbId 团购商品主键
     * @return GroupbuyGoods  团购商品
     */
    GroupbuyGoodsVO getModel(Integer gbId);


    /**
     * 获取团购商品
     *
     * @param actId   团购活动ID
     * @param goodsId 商品ID
     * @return GroupbuyGoods  团购商品
     */
    GroupbuyGoodsDO getModel(Integer actId, Integer goodsId);

    /**
     * 验证操作权限<br/>
     * 如有问题直接抛出权限异常
     *
     * @param id
     */
    void verifyAuth(Integer id);

    /**
     * 修改审核状态
     *
     * @param gbId
     * @param status
     */
    void updateStatus(Integer gbId, Integer status);


    /**
     * 扣减团购商品库存
     *
     * @param orderSn
     * @param promotionDTOList
     * @return
     */
    boolean cutQuantity(String orderSn, List<PromotionDTO> promotionDTOList);

    /**
     * 恢复团购商品库存
     *
     * @param orderSn
     */
    void addQuantity(String orderSn);


    /**
     * 查询团购商品信息和商品库存信息
     *
     * @param id
     * @return
     */
    GroupbuyGoodsVO getModelAndQuantity(Integer id);


    /**
     * 根据商品id，修改团购商品信息
     *
     * @param goodsIds
     */
    void updateGoodsInfo(Integer[] goodsIds);

    /**
     * 回滚库存
     *
     * @param promotionDTOList
     * @param orderSn
     */
    void rollbackStock(List<PromotionDTO> promotionDTOList, String orderSn);

}
