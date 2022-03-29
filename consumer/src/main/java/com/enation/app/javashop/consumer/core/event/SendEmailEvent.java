/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.core.event;

import com.enation.app.javashop.core.base.model.vo.EmailVO;

/**
 * 发送邮件事件
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午9:55:27
 */
public interface SendEmailEvent {

    /**
     * 发送邮件
     *
     * @param emailVO 邮件
     */
    void sendEmail(EmailVO emailVO);
}
