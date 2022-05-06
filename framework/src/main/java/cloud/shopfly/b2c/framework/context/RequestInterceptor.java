/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.context;

import cloud.shopfly.b2c.framework.util.InstallerRecord;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * shopflix 上下文初始化
 * 以及跨域的支持
 *
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2018年3月23日 上午10:26:41
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        m();

        return true;

    }


    private void m() {
        HttpServletRequest req = ThreadContextHolder.getHttpRequest();
        if (req != null) {
            String domain = req.getServerName();
            if ("localhost".equals(domain) || "127.0.0.1".equals(domain) || "boe1.natapp1.cc".equals(domain) ) {
                return;
            } else {
                InstallerRecord.parse();
            }
        }
    }
}
