/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
 */
package cloud.shopfly.b2c.core.payment.plugin.weixin.executor;

import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinPuginConfig;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author fk
 * @version v2.0
 * @Description: 微信wap端
 * @date 2018/4/1810:12
 * @since v7.0.0
 */
@Service
public class WeixinPaymentAppExecutor extends WeixinPuginConfig {


    /**
     * 支付
     *
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {

        try {
            Map<String, String> params = new TreeMap<>();
            params.put("spbill_create_ip", getIpAddress());
            params.put("trade_type", "APP");

            Map<String, String> map = super.createUnifiedOrder(bill, params);
            String resultCode = map.get("result_code");
            if (SUCCESS.equals(resultCode)) {
                String prepayId = map.get("prepay_id");
                Map<String, String> result = new TreeMap();
                result.put("appid", map.get("appid"));
                result.put("partnerid", map.get("mchid"));
                result.put("prepayid", prepayId);
                result.put("package", "Sign=WXPay");
                result.put("noncestr", StringUtil.getRandStr(10));
                result.put("timestamp", DateUtil.getDateline() + "");
                result.put("sign", WeixinUtil.createSign(result, map.get("key")));
                return result;
            }
        } catch (Exception e) {
            this.logger.error("返回参数转换失败", e);
        }
        return null;
    }


}
