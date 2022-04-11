/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goodssearch.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingapex on 2019-02-14.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-14
 */
public class MyMap {
    private Map map;

    public MyMap() {
        map = new HashMap();
    }

    public MyMap put(Object key,Object value) {
        map.put(key, value);
        return this;
    }

    public Map getMap() {

        return map;
    }
}
