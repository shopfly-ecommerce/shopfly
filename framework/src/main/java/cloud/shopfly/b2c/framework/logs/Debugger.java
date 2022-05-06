/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.logs;

import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 调试器
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-17
 */
@Component
public class Debugger {

    @Autowired
    private ShopflyConfig shopflyConfig;

    @Autowired
    private Cache cache;

    private static final String LOG_KEY="debug_log";


    /**
     * 记录多个并换行
     */
    public void log(String... ar) {

        String str = "";
        for (String s : ar) {
            str+="<br>"+s;
        }

        log(str);
    }

    /**
     * 记录日志
     * @param str
     */
    public void log(String str) {
        //只有debugger开启才操作
        if (!shopflyConfig.isDebugger()) {
            return;
        }

       String logStr =(String) cache.get(LOG_KEY);
        if (logStr == null) {
            logStr = "";
        }

        logStr+="<br/>"+str;

        /**
         * 日志记录默认为10分后失效
         */
        cache.put(LOG_KEY, logStr, 10 * 60);
    }


    /***
     * 获取日志
     * @return
     */
    public String getLog() {
        //只有debugger开启才操作
        if (!shopflyConfig.isDebugger()) {
            return "";
        }
        String logStr =(String) cache.get(LOG_KEY);
        if (logStr == null) {
            logStr = "";
        }

        return logStr;
    }

    /**
     * 清空日志
     */
    public void clear() {
        //只有debugger开启才操作
        if (!shopflyConfig.isDebugger()) {
            return;
        }
        cache.remove(LOG_KEY);

    }
}
