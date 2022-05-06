/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.converter;

import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartPromotionVo;
import org.springframework.beans.BeanUtils;

/**
 * 转换promotion包下，此包使用到的model及字段
 *
 * @author Snow create in 2018/3/20
 * @version v2.0
 * @since v7.0.0
 */
public class TradePromotionConverter {

    public static CartPromotionVo promotionGoodsVOConverter(PromotionVO promotionVO) {
        CartPromotionVo convertPromotionGoodsVO = new CartPromotionVo();
        BeanUtils.copyProperties(promotionVO, convertPromotionGoodsVO);
        return convertPromotionGoodsVO;
    }
}
