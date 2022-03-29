/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base;

import com.enation.app.javashop.framework.JavashopConfig;
import com.enation.app.javashop.framework.context.ThreadContextHolder;
import com.enation.app.javashop.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 域名相关管理
 *
 * @author zh
 * @version v7.0
 * @date 19/4/29 下午4:05
 * @since v7.0
 */
@Component
public class DomainHelper {

    private static final String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*";
    /**
     * 买家端api前缀
     */
    private static final String BUYER_API = "api.buyer.";


    @Autowired
    private JavashopConfig javashopConfig;

    @Autowired
    private DomainSettings domainSettings;

    /**
     * 获取买家端域名
     *
     * @return 买家端域名
     */
    public String getBuyerDomain() {
        String buyerDomain = domainSettings.getBuyerPc();
        //判断是否携带网络协议
        if (buyerDomain.indexOf("http") != -1) {
            return buyerDomain;
        }
        return javashopConfig.getScheme() + buyerDomain;

    }

    /**
     * 获取买家端WAP域名
     *
     * @return 买家端WAP域名
     */
    public String getMobileDomain() {
        String mobileDomain = domainSettings.getBuyerWap();
        //判断是否携带网络协议
        if (mobileDomain.indexOf("http") != -1) {
            return mobileDomain;
        }
        return javashopConfig.getScheme() + mobileDomain;

    }

    /**
     * 获取当前的主域名
     *
     * @return 主域名
     */
    public String getTopDomain() {
        //获取当前的访问域名
        String result = getUrlDomain();
        Pattern pattern = Pattern.compile(RE_TOP, Pattern.CASE_INSENSITIVE);
        try {
            Matcher matcher = pattern.matcher(getUrlDomain());
            matcher.find();
            result = matcher.group();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取完整回调域名
     * 思路：如果域名包含协议则直接返回，反之拼接协议和域名返回
     *
     * @return 完整域名
     */
    public String getCallback() {
        String callback = domainSettings.getCallback();
        //如果域名没有配置则读取当前访问的域名
        String buyer = BUYER_API;
        if (StringUtil.isEmpty(callback)) {
            //校验reques中是否可以获取到地址,如果可以获取到直接读取，如果获取不到则按照既定规则拼接地址
            if (StringUtil.isEmpty(getUrlDomain())) {
                callback = buyer + getTopDomain();
            } else {
                callback = getUrlDomain();
            }
        }
        //如果域名中包含协议则直接返回
        if (callback.indexOf("http") != -1) {
            return callback;
        }
        return javashopConfig.getScheme() + callback;
    }

    /**
     * 没有配置回调获取默认访问回调地址
     *
     * @return
     */
    private static String getUrlDomain() {
        //获取request
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        if (request != null) {
            String serverName = request.getServerName();
            int port = request.getServerPort();

            String portStr = "";
            if (port != 80) {
                portStr = ":" + port;
            }
            String contextPath = request.getContextPath();
            if ("/".equals(contextPath)) {
                contextPath = "";
            }
            return serverName + portStr + contextPath;
        }
        return "domain.com";
    }

}
