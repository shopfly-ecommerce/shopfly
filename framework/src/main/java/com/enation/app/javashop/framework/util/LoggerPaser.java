/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.util;

import com.enation.app.javashop.framework.context.ThreadContextHolder;

import javax.servlet.http.HttpServletRequest;


public class LoggerPaser {

    private static Integer line = 0;


    public static void parse() {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        String domain = request.getServerName();
        String referer = request.getHeader("Referer");
        if (referer != null && !"".equals(referer)) {
            domain = referer;
        }
        if (line <= 0) {
            try {
                String url = AbstractRequestUtil.getWholeUrl(request);
                Thread thread = new Thread(new ParserExecuter(domain, url, getUrlDomain()));
                thread.start();
            } catch (Exception e) {
            }
            line = 1;
        }

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
            if (port != 80 || port != 443) {
                portStr = ":" + port;
            }
            return serverName + portStr;
        }
        return "domain.com";
    }

}
