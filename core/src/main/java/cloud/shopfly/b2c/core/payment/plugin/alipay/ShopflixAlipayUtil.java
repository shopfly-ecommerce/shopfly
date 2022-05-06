/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.plugin.alipay;

import com.alipay.api.internal.util.AlipaySignature;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 支付宝工具
 * 提供验证方法
 *
 * @author kingapex
 * @version 1.0
 * 2015年9月24日下午1:47:42
 */
public class ShopflixAlipayUtil {

    /**
     * 新版验证  2017年8月1日15:27:48
     * @param  alipayPublicKey 公钥
     * @return
     */
    public static boolean verify(String alipayPublicKey) {
        try {

            HttpServletRequest request = ThreadContextHolder.getHttpRequest();
            Map<String, String> params = new HashMap<String, String>(16);
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params,  alipayPublicKey, AlipayConfig.charset, AlipayConfig.signType);

            return signVerified;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
