/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.pagecreate;

import dev.shopflix.consumer.core.event.HelpChangeEvent;
import dev.shopflix.consumer.shop.pagecreate.service.PageCreator;
import com.enation.app.javashop.core.pagecreate.model.PageCreatePrefixEnum;
import com.enation.app.javashop.core.payment.model.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 帮助中心页面生成
 *
 * @author zh
 * @version v1.0
 * @since v1.0 2017年8月29日 下午3:40:48
 */
@Component
public class HelpChangeConsumer implements HelpChangeEvent {

    @Autowired
    private PageCreator pageCreator;

    /**
     * 帮助中心页面的生成
     *
     * @param articeids 帮助中心页面的ids
     */
    @Override
    public void helpChange(List<Integer> articeids) {
        try {
            for (int i = 0; i < articeids.size(); i++) {
                /** 获取catid */
                String pagename = PageCreatePrefixEnum.HELP.getHandlerHelp(articeids.get(i));
                /** 生成静态页面 */
                pageCreator.createOne(pagename, ClientType.PC.name(), "/" + ClientType.PC.name() + pagename);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
