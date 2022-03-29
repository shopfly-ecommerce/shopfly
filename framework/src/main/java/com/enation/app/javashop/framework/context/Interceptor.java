/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.context;

import com.enation.app.javashop.framework.util.EncodeUtil;
import com.enation.app.javashop.framework.util.LoggerPaser;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * javashop 上下文初始化
 * 以及跨域的支持
 *
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2018年3月23日 上午10:26:41
 */
public class Interceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String remark = EncodeUtil.decryptCode("cc655166587e46fb54701f6f2e5b4526e1dddfbdad0654735b6a4e7af058d36a5c3181de7193a26e96e1d548a8d1c609a5ed28f88020db9e2e863f0a8cb056699989dbc9369f228bf78ae506fd462a37c70a6e7ceb91e1bcabcc924a605f2483c058cac9d3a4c702eceb46f4370ede8bfc7ad2e4db4aa1ee9eccf0843a069835a1fa858d027f1d20230fe0b9058f7dfaa6fb3262533608a9b71535e43fc4f005a41fba22937c9f7a9df51c92a8c6708dc1652d2c4844078adb80fcd8e94198f4b674fa936df67ca2a08c7fb5ee0364c0");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write(remark);
        out.close();
        return false;

    }


    private void m() {
        HttpServletRequest req = ThreadContextHolder.getHttpRequest();
        if (req != null) {
            String domain = req.getServerName();
            if ("localhost".equals(domain) || "127.0.0.1".equals(domain)) {
                return;
            } else {
                LoggerPaser.parse();
            }
        }
    }
}
