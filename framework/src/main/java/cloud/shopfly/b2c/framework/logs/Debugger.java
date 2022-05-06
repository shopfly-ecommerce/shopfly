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
