/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.service;

import dev.shopflix.core.distribution.model.dos.ShortUrlDO;

/**
 * 短链接Manager接口
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 上午8:37
 */
public interface ShortUrlManager {

    /**
     * 生成一个短链接
     *
     * @param memberId
     * @param goodsId
     * @return
     */
    ShortUrlDO createShortUrl(Integer memberId, Integer goodsId);

    /**
     * 根据短链接获得长链接
     *
     * @param shortUrl 短链接 （可带前缀 即：http:xxx/）
     * @return 所对应的长链接
     */
    ShortUrlDO getLongUrl(String shortUrl);

}
