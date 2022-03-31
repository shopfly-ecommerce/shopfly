/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.service.impl;


import dev.shopflix.core.distribution.model.dos.ShortUrlDO;
import dev.shopflix.core.distribution.service.ShortUrlManager;
import dev.shopflix.core.distribution.util.ShortUrlGenerator;
import dev.shopflix.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 短链接实现类
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/14 上午7:13
 */

@Component
public class ShortUrlManagerImpl implements ShortUrlManager {

    @Autowired

    private DaoSupport daoSupport;


    @Override
    public ShortUrlDO createShortUrl(Integer memberId, Integer goodsId) {
        ShortUrlDO shortUrlDO = null;

        if (goodsId == null) {
            goodsId = 0;
        }
        String url;
        if (goodsId == 0) {
            url = "/index.html?member_id=" + memberId;
        } else {
            url = "/goods/" + goodsId + "?member_id=" + memberId;
        }

        ShortUrlDO result = daoSupport.queryForObject("select * from es_short_url where url = ?", ShortUrlDO.class, url);
        if (result != null) {
            return result;
        }
        String[] shortUrls = ShortUrlGenerator.getShortUrl(url);

        //检测是否存在
        for (String tempUrl : shortUrls) {
            String sql = "SELECT count(0) FROM es_short_url WHERE su = ?";
            int num = this.daoSupport.queryForInt(sql, tempUrl);

            if (num == 0) {
                shortUrlDO = new ShortUrlDO();
                shortUrlDO.setSu(tempUrl);
                shortUrlDO.setUrl(url);

                this.daoSupport.insert("es_short_url", shortUrlDO);
                break;
            }
        }

        return shortUrlDO;
    }

    @Override
    public ShortUrlDO getLongUrl(String shortUrl) {
        return this.daoSupport.queryForObject("select * from es_short_url where su = ?", ShortUrlDO.class, shortUrl);
    }

}
