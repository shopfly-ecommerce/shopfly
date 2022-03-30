/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * shopflix 配置
 *
 * @author zh
 * @version v7.0
 * @date 18/4/13 下午8:19
 * @since v7.0
 */
@Configuration
@ConfigurationProperties(prefix = "shopflix")
public class ShopflixConfig {

    /**
     * token加密秘钥
     */
    private String tokenSecret;

    @Value("${shopflix.timeout.accessTokenTimeout:#{null}}")
    private Integer accessTokenTimeout;
    @Value("${shopflix.timeout.refreshTokenTimeout:#{null}}")
    private Integer refreshTokenTimeout;
    @Value("${shopflix.timeout.appAccessTokenTimeout:#{null}}")
    private Integer appAccessTokenTimeout;
    @Value("${shopflix.timeout.appRefreshTokenTimeout:#{null}}")
    private Integer appRefreshTokenTimeout;
    @Value("${shopflix.timeout.captchaTimout:#{null}}")
    private Integer captchaTimout;
    @Value("${shopflix.timeout.smscodeTimout:#{null}}")
    private Integer smscodeTimout;
    @Value("${shopflix.isDemoSite:#{false}}")
    private boolean isDemoSite;

    @Value("${shopflix.ssl:#{false}}")
    private boolean ssl;

    @Value("${shopflix.debugger:#{false}}")
    private boolean debugger;


    /**
     * 小程序二维码分享图片存储位置
     */
    @Value("${shopflix.mini-program.code-unlimit-position:#{null}}")
    @Deprecated
    private String codePosition;


    /**
     * 缓冲次数
     */
    @Value("${shopflix.pool.stock.max-update-timet:#{null}}")
    private Integer maxUpdateTime;

    /**
     * 缓冲区大小
     */
    @Value("${shopflix.pool.stock.max-pool-size:#{null}}")
    private Integer maxPoolSize;

    /**
     * 缓冲时间（秒数）
     */
    @Value("${shopflix.pool.stock.max-lazy-second:#{null}}")
    private Integer maxLazySecond;

    /**
     * 商品库存缓冲池开关
     * false：关闭（如果配置文件中没有配置此项，则默认为false）
     * true：开启（优点：缓解程序压力；缺点：有可能会导致商家中心商品库存数量显示延迟；）
     */
    @Value("${shopflix.pool.stock:#{false}}")
    private boolean stock;

    public ShopflixConfig() {
    }


    @Override
    public String toString() {
        return "JavashopConfig{" +
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