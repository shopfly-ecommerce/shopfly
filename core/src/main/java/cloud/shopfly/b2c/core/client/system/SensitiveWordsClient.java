/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.system;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 敏感词client
 * @date 2018/8/10 14:51
 * @since v7.0.0
 */
public interface SensitiveWordsClient {

    /**
     * 获取敏感词列表
     * @return
     */
    List<String> listWords();

}
