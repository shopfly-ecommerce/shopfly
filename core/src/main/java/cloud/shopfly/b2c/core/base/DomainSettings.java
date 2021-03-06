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
package cloud.shopfly.b2c.core.base;

import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The domain name configuration
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/6/20
 */
@Configuration
@ConfigurationProperties(prefix = "shopfly")
public class DomainSettings {

    /**
     * Buyer side domain name
     */
    @Value("${shopfly.name:#{null}}")
    private String name;

    /**
     * Buyer side domain name
     */
    @Value("${shopfly.domain.buyer:#{null}}")
    private String buyer;

    /**
     * Mobile buyer domain name
     */
    @Value("${shopfly.domain.mobileBuyer:#{null}}")
    private String mobileBuyer;


    /**
     * Buyer side domain name
     */
    @Value("${shopfly.domain.buyer-pc:#{null}}")
    private String buyerPc;

    /**
     * Mobile buyer domain name
     */
    @Value("${shopfly.domain.buyer-wap:#{null}}")
    private String buyerWap;

    /**
     * Pay the callback address
     *
     * @return
     */
    @Value("${shopfly.domain.callback:#{null}}")
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
        throw new ServiceException(MemberErrorCode.E147.code(), "domainSettings buyer The object was not initialized correctly");
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
        throw new ServiceException(MemberErrorCode.E147.code(), "domainSettings mobile buyer The object was not initialized correctly");
    }

    public void setBuyerWap(String buyerWap) {
        this.buyerWap = buyerWap;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DomainSettings{" +
                "name='" + name + '\'' +
                ", buyer='" + buyer + '\'' +
                ", mobileBuyer='" + mobileBuyer + '\'' +
                ", buyerPc='" + buyerPc + '\'' +
                ", buyerWap='" + buyerWap + '\'' +
                ", callback='" + callback + '\'' +
                '}';
    }
}
