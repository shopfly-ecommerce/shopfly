/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.pagedata.service.impl;

import cloud.shopfly.b2c.core.pagedata.model.HotKeyword;
import cloud.shopfly.b2c.core.pagedata.service.HotKeywordManager;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.message.CmsManageMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 热门关键字业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-04 10:43:23
 */
@Service
public class HotKeywordManagerImpl implements HotKeywordManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private Cache cache;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_hot_keyword  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, HotKeyword.class);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public HotKeyword add(HotKeyword hotKeyword) {
        this.daoSupport.insert(hotKeyword);
        hotKeyword.setId(this.daoSupport.getLastId(""));
        this.sendMessage();
        return hotKeyword;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public HotKeyword edit(HotKeyword hotKeyword, Integer id) {

        HotKeyword keyword = this.getModel(id);
        if (keyword == null) {
            throw new ServiceException(SystemErrorCode.E954.code(), "该记录不存在，请正确操作");
        }
        this.daoSupport.update(hotKeyword, id);

        this.sendMessage();

        return hotKeyword;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(HotKeyword.class, id);
        this.sendMessage();
    }

    @Override
    public HotKeyword getModel(Integer id) {
        return this.daoSupport.queryForObject(HotKeyword.class, id);
    }

    @Override
    public List<HotKeyword> listByNum(Integer num) {

        List<HotKeyword> result = (List<HotKeyword>) cache.get(CachePrefix.HOT_KEYWORD.getPrefix() + num);

        if (result == null || result.isEmpty()) {
            if (num == null) {
                num = 5;
            }
            String sql = " select * from es_hot_keyword order by sort asc  limit 0,? ";
            result = this.daoSupport.queryForList(sql, HotKeyword.class, num);
            cache.put(CachePrefix.HOT_KEYWORD.getPrefix() + num, result);
        }

        return result;
    }

    /**
     * 发送首页变化消息
     */
    private void sendMessage() {

        //使用模糊删除
        this.cache.vagueDel(CachePrefix.HOT_KEYWORD.getPrefix());

        CmsManageMsg cmsManageMsg = new CmsManageMsg();

        this.messageSender.send(new MqMessage(AmqpExchange.PC_INDEX_CHANGE, AmqpExchange.PC_INDEX_CHANGE + "_ROUTING", cmsManageMsg));

    }
}
