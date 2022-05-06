/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.system;

import cloud.shopfly.b2c.core.pagedata.model.HotKeyword;

import java.util.List;

/**
 * @author zs
 * @version v1.0
 * @Description: 热点关键字client
 * @date 2021-01-19
 * @since v7.1.0
 */
public interface HotkeywordClient {

    /**
     * 查询热门关键字
     * @param num
     * @return
     */
    List<HotKeyword> listByNum(Integer num);


}
