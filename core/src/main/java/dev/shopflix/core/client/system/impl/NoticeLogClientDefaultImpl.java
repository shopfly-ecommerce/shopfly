/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system.impl;

import dev.shopflix.core.client.system.NoticeLogClient;
import dev.shopflix.core.system.model.dos.NoticeLogDO;
import dev.shopflix.core.system.service.NoticeLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v2.0
 * @Description: 店铺消息
 * @date 2018/8/14 10:22
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="javashop.product", havingValue="stand")
public class NoticeLogClientDefaultImpl implements NoticeLogClient {


    @Autowired
    private NoticeLogManager noticeLogManager;

    @Override
    public NoticeLogDO add(NoticeLogDO shopNoticeLog) {
        return noticeLogManager.add(shopNoticeLog);
    }
}
