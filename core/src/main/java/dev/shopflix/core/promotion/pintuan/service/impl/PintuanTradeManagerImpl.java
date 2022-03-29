/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.pintuan.service.impl;

import dev.shopflix.core.member.model.dos.MemberAddress;
import dev.shopflix.core.promotion.pintuan.exception.PintuanErrorCode;
import dev.shopflix.core.promotion.pintuan.service.PintuanCartManager;
import dev.shopflix.core.promotion.pintuan.service.PintuanOrderManager;
import dev.shopflix.core.trade.cart.model.vo.CartSkuVO;
import dev.shopflix.core.trade.cart.model.vo.CartView;
import dev.shopflix.core.trade.order.model.dto.OrderDTO;
import dev.shopflix.core.trade.order.model.enums.OrderTypeEnum;
import dev.shopflix.core.trade.order.model.vo.CheckoutParamVO;
import dev.shopflix.core.trade.order.model.vo.TradeVO;
import dev.shopflix.core.trade.order.service.TradeCreator;
import dev.shopflix.core.trade.order.service.impl.DefaultTradeCreator;
import dev.shopflix.core.trade.order.service.impl.TradeManagerImpl;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Buyer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kingapex on 2019-01-24.
 * 拼团交易业务类<br/>
 * 继承默认的交易业务类<br/>
 * 其中不同的是:<br/>
 * 1、使用 {@link PintuanCartManager} 获取购物车内容<br/>
 * 2、不检测优惠活动的合法性，因为拼团不存在其它活动的重叠
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-24
 */
@Service
public class PintuanTradeManagerImpl extends TradeManagerImpl {

    @Autowired
    private PintuanCartManager pintuanCartManager;

    @Autowired
    private PintuanOrderManager pintuanOrderManager;

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 创建订单并创建拼团订单
     *
     * @param client
     * @param pinTuanOrderId
     * @return
     */
    public TradeVO createTrade(String client, Integer pinTuanOrderId) {

        //自己参与自己拼团的判定
        if (pinTuanOrderId != null) {
            Buyer buyer = UserContext.getBuyer();
            this.pintuanOrderManager.getModel(pinTuanOrderId).getParticipants().forEach(participant -> {
                if (participant.getId().equals(buyer.getUid())) {
                    throw new ServiceException(PintuanErrorCode.E5013.code(), "不能参加自己创建的拼团");
                }
            });
        }


        //设置客户的类型
        super.setClientType(client);

        CheckoutParamVO param = checkoutParamManager.getParam();

        CartView cartView = pintuanCartManager.getCart();

        MemberAddress memberAddress = this.memberAddressClient.getModel(param.getAddressId());

        if (logger.isDebugEnabled()) {
            logger.debug("准备创建拼团订单");
            logger.debug("param:" + param);
            logger.debug("cartView:" + cartView);
            logger.debug("memberAddress:" + memberAddress);
        }

        TradeCreator tradeCreator = new DefaultTradeCreator(param, cartView, memberAddress).setTradeSnCreator(tradeSnCreator).setGoodsClient(goodsClient).setMemberClient(memberClient).setShippingManager(shippingManager);

        //和普通的交易不同，不用检测活动的合法性，因为拼团不会存在活动的重叠
        //检测配置范围-> 检测商品合法性 -> 创建交易
        TradeVO tradeVO = tradeCreator.checkShipRange().checkGoods().createTrade();
        OrderDTO orderDTO = tradeVO.getOrderList().get(0);
        orderDTO.setOrderType(OrderTypeEnum.pintuan.name());

        if (logger.isDebugEnabled()) {
            logger.debug("生成交易：" + tradeVO);
        }

        //订单入库
        this.tradeIntodbManager.intoDB(tradeVO);


        //创建拼团订单
        OrderDTO order = tradeVO.getOrderList().get(0);
        CartSkuVO skuVO = cartView.getCartList().get(0).getSkuList().get(0);

        pintuanOrderManager.createOrder(order, skuVO.getSkuId(), pinTuanOrderId);

        return tradeVO;
    }
}
