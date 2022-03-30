/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.pagecreate;


import dev.shopflix.consumer.core.event.CategoryChangeEvent;
import dev.shopflix.consumer.core.event.IndexChangeEvent;
import dev.shopflix.consumer.core.event.MobileIndexChangeEvent;
import dev.shopflix.consumer.shop.pagecreate.service.PageCreator;
import dev.shopflix.core.base.message.CategoryChangeMsg;
import dev.shopflix.core.base.message.CmsManageMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 首页生成
 *
 * @author zh
 * @version v1.0
 * @since v6.4.0
 * 2017年8月29日 下午3:41:18
 */
@Component
public class IndexChangeConsumer implements IndexChangeEvent, CategoryChangeEvent, MobileIndexChangeEvent {

    @Autowired
    private PageCreator pageCreator;

    /**
     * 生成首页
     */
    @Override
    public void createIndexPage(CmsManageMsg operation) {
        this.createIndex();
    }

    @Override
    public void categoryChange(CategoryChangeMsg categoryChangeMsg) {
        this.createIndex();
    }

    private void createIndex() {
        try {
            // 生成静态页面
            pageCreator.createIndex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mobileIndexChange(CmsManageMsg operation) {
        this.createIndex();
    }

}
