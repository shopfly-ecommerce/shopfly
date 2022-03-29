/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system;

import dev.shopflix.core.system.model.dos.NoticeLogDO;

/**
 * @author fk
 * @version v2.0
 * @Description: 店铺消息模板
 * @date 2018/8/14 10:21
 * @since v7.0.0
 */
public interface NoticeLogClient {

    /**
     * 添加店铺站内消息
     * @param noticeLog 店铺站内消息
     * @return NoticeLogDO 店铺站内消息
     */
    NoticeLogDO add(NoticeLogDO noticeLog);
}
