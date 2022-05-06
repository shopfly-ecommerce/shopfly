/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.Message;
import cloud.shopfly.b2c.core.system.model.vo.MessageVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 站内消息业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-04 21:50:52
 */
public interface MessageManager {

    /**
     * 查询站内消息列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加站内消息
     *
     * @param messageVO 站内消息
     * @return Message 站内消息
     */
    Message add(MessageVO messageVO);

    /**
     * 通过id查询站内消息
     *
     * @param id 消息id
     * @return 站内消息对象
     */
    Message get(Integer id);
}