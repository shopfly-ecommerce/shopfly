/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.trade.impl;

import cloud.shopfly.b2c.core.client.trade.CouponClient;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 优惠券client实现
 *
 * @author zh
 * @version v7.0
 * @date 18/12/6 下午4:28
 * @since v7.0
 */
@Service
public class CouponClientImpl implements CouponClient {


    @Autowired
    private CouponManager couponManager;

    @Override
    public CouponDO getModel(Integer id) {
        return couponManager.getModel(id);
    }

    @Override
    public void addReceivedNum(Integer couponId) {
        couponManager.addReceivedNum(couponId);
    }
}
