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
package cloud.shopfly.b2c.framework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * shopfly 配置
 *
 * @author zh
 * @version v7.0
 * @date 18/4/13 下午8:19
 * @since v7.0
 */
@Configuration
@ConfigurationProperties(prefix = "shopfly")
public class ShopflyConfig {

    /**
     * token加密秘钥
     */
    private String tokenSecret;

    @Value("${shopfly.timeout.accessTokenTimeout:#{null}}")
    private Integer accessTokenTimeout;
    @Value("${shopfly.timeout.refreshTokenTimeout:#{null}}")
    private Integer refreshTokenTimeout;
    @Value("${shopfly.timeout.appAccessTokenTimeout:#{null}}")
    private Integer appAccessTokenTimeout;
    @Value("${shopfly.timeout.appRefreshTokenTimeout:#{null}}")
    private Integer appRefreshTokenTimeout;
    @Value("${shopfly.timeout.captchaTimout:#{null}}")
    private Integer captchaTimout;
    @Value("${shopfly.timeout.smscodeTimout:#{null}}")
    private Integer smscodeTimout;
    @Value("${shopfly.isDemoSite:#{false}}")
    private boolean isDemoSite;

    @Value("${shopfly.ssl:#{false}}")
    private boolean ssl;

    @Value("${shopfly.debugger:#{false}}")
    private boolean debugger;


    /**
     * 小程序二维码分享图片存储位置
     */
    @Value("${shopfly.mini-program.code-unlimit-position:#{null}}")
    @Deprecated
    private String codePosition;


    /**
     * 缓冲次数
     */
    @Value("${shopfly.pool.stock.max-update-timet:#{null}}")
    private Integer maxUpdateTime;

    /**
     * 缓冲区大小
     */
    @Value("${shopfly.pool.stock.max-pool-size:#{null}}")
    private Integer maxPoolSize;

    /**
     * 缓冲时间（秒数）
     */
    @Value("${shopfly.pool.stock.max-lazy-second:#{null}}")
    private Integer maxLazySecond;

    /**
     * 商品库存缓冲池开关
     * false：关闭（如果配置文件中没有配置此项，则默认为false）
     * true：开启（优点：缓解程序压力；缺点：有可能会导致商家中心商品库存数量显示延迟；）
     */
    @Value("${shopfly.pool.stock:#{false}}")
    private boolean stock;

    public ShopflyConfig() {
    }


    @Override
    public String toString() {
        return "shopflyConfig{" +
                "accessTokenTimeout=" + accessTokenTimeout +
                ", refreshTokenTimeout=" + refreshTokenTimeout +
                "appAccessTokenTimeout=" + appAccessTokenTimeout +
                ", appRefreshTokenTimeout=" + appRefreshTokenTimeout +
                ", captchaTimout=" + captchaTimout +
                ", smscodeTimout=" + smscodeTimout +
                ", isDemoSite=" + isDemoSite +
                ", ssl=" + ssl +
                ", codePosition='" + codePosition + '\'' +
                ", maxUpdateTime=" + maxUpdateTime +
                ", maxPoolSize=" + maxPoolSize +
                ", maxLazySecond=" + maxLazySecond +
                ", stock=" + stock +
                '}';
    }

    /**
     * 获取协议
     *
     * @return 协议
     */
    public final String getScheme() {
        if (this.getSsl()) {
            return "https://";
        }
        return "http://";
    }

    public boolean isDemoSite() {
        return isDemoSite;
    }

    public boolean getSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public Integer getAccessTokenTimeout() {
        return accessTokenTimeout;
    }

    public void setAccessTokenTimeout(Integer accessTokenTimeout) {
        this.accessTokenTimeout = accessTokenTimeout;
    }

    public Integer getRefreshTokenTimeout() {
        return refreshTokenTimeout;
    }

    public void setRefreshTokenTimeout(Integer refreshTokenTimeout) {
        this.refreshTokenTimeout = refreshTokenTimeout;
    }

    public Integer getAppAccessTokenTimeout() {
        return appAccessTokenTimeout;
    }

    public void setAppAccessTokenTimeout(Integer appAccessTokenTimeout) {
        this.appAccessTokenTimeout = appAccessTokenTimeout;
    }

    public Integer getAppRefreshTokenTimeout() {
        return appRefreshTokenTimeout;
    }

    public void setAppRefreshTokenTimeout(Integer appRefreshTokenTimeout) {
        this.appRefreshTokenTimeout = appRefreshTokenTimeout;
    }

    public Integer getCaptchaTimout() {
        return captchaTimout;
    }

    public void setCaptchaTimout(Integer captchaTimout) {
        this.captchaTimout = captchaTimout;
    }

    public Integer getSmscodeTimout() {
        return smscodeTimout;
    }

    public void setSmscodeTimout(Integer smscodeTimout) {
        this.smscodeTimout = smscodeTimout;
    }

    public boolean getIsDemoSite() {
        return isDemoSite;
    }

    public void setDemoSite(boolean demoSite) {
        isDemoSite = demoSite;
    }

    public String getCodePosition() {
        return codePosition;
    }

    public void setCodePosition(String codePosition) {
        this.codePosition = codePosition;
    }

    public Integer getMaxUpdateTime() {
        return maxUpdateTime;
    }

    public void setMaxUpdateTime(Integer maxUpdateTime) {
        this.maxUpdateTime = maxUpdateTime;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getMaxLazySecond() {
        return maxLazySecond;
    }

    public void setMaxLazySecond(Integer maxLazySecond) {
        this.maxLazySecond = maxLazySecond;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public boolean isSsl() {
        return ssl;
    }

    public boolean isDebugger() {
        return debugger;
    }

    public void setDebugger(boolean debugger) {
        this.debugger = debugger;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}