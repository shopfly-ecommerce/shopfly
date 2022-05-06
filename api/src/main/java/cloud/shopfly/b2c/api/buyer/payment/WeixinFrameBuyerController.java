/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.buyer.payment;

import cloud.shopfly.b2c.core.base.DomainHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信PC端支付，在frame中显示二维码的页面<br>
 * 架构详见:http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/ding-dan-zhi-fu-jia-gou.html
 * Created by kingapex on 2018/7/20.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/7/20
 */
@Api(description = "订单支付API")
@Controller
@RequestMapping("/order/pay")
@Validated
public class WeixinFrameBuyerController {

    @Autowired
    private DomainHelper domainHelper;

    @ApiIgnore
    @ApiOperation(value = "微信二维码显示页")
    @PostMapping("/weixin/qrpage/{weixin_trade_sn}/{pr}")
    public String qrPage(Model model, @PathVariable(name = "weixin_trade_sn") String weixinTradeSn, @PathVariable(name = "pr") String pr, HttpServletResponse response, HttpServletRequest request) {

        String buyerDomain = domainHelper.getBuyerDomain();

        //获取虚拟目录
        String cxt = request.getContextPath();
        if ("/".equals(cxt)) {
            cxt = "";
        }
        String paySuccessUrl = buyerDomain + "/payment-complete";

        model.addAttribute("pr", pr);
        model.addAttribute("weixinTradeSn", weixinTradeSn);
        model.addAttribute("paySuccessUrl", paySuccessUrl);

        model.addAttribute("jquery_path", domainHelper.getCallback() + "/jquery.min.js");
        model.addAttribute("default_gateway_url", domainHelper.getCallback());
        //二维码模式嵌在的iframe中的，要设置此相应允许被buyer域名的frame嵌套
        response.setHeader("Content-Security-Policy", "frame-ancestors " + buyerDomain);
        return "weixin_qr";
    }

}
