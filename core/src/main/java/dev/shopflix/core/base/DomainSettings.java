/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base;

import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 域名配置
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/6/20
 */
@Configuration
@ConfigurationProperties(prefix = "shopflix")
public class DomainSettings {

    /**
     * 买家端域名
     */
    @Value("${shopflix.domain.buyer:#{null}}")
    private String buyer;

    /**
     * 手机买家端域名
     */
    @Value("${shopflix.domain.mobileBuyer:#{null}}")
    private String mobileBuyer;


    /**
     * 买家端域名
     */
    @Value("${shopflix.domain.buyer-pc:#{null}}")
    private String buyerPc;

    /**
     * 手机买家端域名
     */
    @Value("${shopflix.domain.buyer-wap:#{null}}")
    private String buyerWap;

    /**
     * 支付回调地址
     *
     * @return
     */
    @Value("${shopflix.domain.callback:#{null}}")
    private String callback;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getMobileBuyer() {
        return mobileBuyer;
    }

    public void setMobileBuyer(String mobileBuyer) {
        this.mobileBuyer = mobileBuyer;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getBuyerPc() {
        if (!StringUtil.isEmpty(this.getBuyer())) {
            return this.getBuyer();
        }

        if (!StringUtil.isEmpty(buyerPc)) {
            return buyerPc;
        }
        throw new ServiceException(MemberErrorCode.E147.code(), "domainSettings buyer 对象未能正确初始化");
    }

    public void setBuyerPc(String buyerPc) {
        this.buyerPc = buyerPc;
    }

    public String getBuyerWap() {
        if (!StringUtil.isEmpty(this.getMobileBuyer())) {
            return this.getMobileBuyer();
        }

        if (!StringUtil.isEmpty(buyerWap)) {
            return buyerWap;
        }
        throw new ServiceException(MemberErrorCode.E147.code(), "domainSettings mobile buyer 对象未能正确初始化");
    }

    public void setBuyerWap(String buyerWap) {
        this.buyerWap = buyerWap;
    }

    @Override
    public String toString() {
        return "DomainSettings{" +
                "buyer='" + buyer + '\'' +
                ", mobileBuyer='" + mobileBuyer + '\'' +
                ", buyerPc='" + buyerPc + '\'' +
                ", buyerWap='" + buyerWap + '\'' +
                ", callback='" + callback + '\'' +
                '}';
    }
}
