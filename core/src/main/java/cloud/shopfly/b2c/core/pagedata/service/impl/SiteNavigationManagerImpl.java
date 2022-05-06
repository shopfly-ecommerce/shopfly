/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.pagedata.service.impl;

import cloud.shopfly.b2c.core.pagedata.model.SiteNavigation;
import cloud.shopfly.b2c.core.pagedata.model.enums.ClientType;
import cloud.shopfly.b2c.core.pagedata.service.SiteNavigationManager;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 导航栏业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 17:07:22
 */
@Service
public class SiteNavigationManagerImpl implements SiteNavigationManager {

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired
    private Cache cache;
    @Autowired
    private MessageSender messageSender;

    @Override
    public Page list(int page, int pageSize, String clientType) {

        String sql = "select * from es_site_navigation  where  client_type = ? order by sort desc";

        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, SiteNavigation.class, clientType);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SiteNavigation add(SiteNavigation siteNavigation) {

        //移动端图片地址必填
        if (ClientType.MOBILE.name().equals(siteNavigation.getClientType())) {
            if (StringUtil.isEmpty(siteNavigation.getImage())) {
                throw new ServiceException(SystemErrorCode.E953.code(), "移动端导航，图片必传");
            }
        }
        //导航名称长度不能超过6
        if (siteNavigation.getNavigationName().length() > 12) {
            throw new ServiceException(SystemErrorCode.E953.code(), "导航栏菜单名称已经超出最大限制");
        }

        /**查询数据库sort最大值方便给新添加的数据赋值*/
        Integer sort = this.daoSupport.queryForInt("select MAX(sort) from es_site_navigation");
        siteNavigation.setSort(sort + 1);

        this.daoSupport.insert(siteNavigation);

        int id = this.daoSupport.getLastId("");

        siteNavigation.setNavigationId(id);

        this.avigationChange(siteNavigation);

        return siteNavigation;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SiteNavigation edit(SiteNavigation siteNavigation, Integer id) {

        SiteNavigation siteNav = this.getModel(id);
        if (siteNav == null) {
            throw new ServiceException(SystemErrorCode.E953.code(), "导航栏不存在，请正确操作");
        }

        //移动端图片地址必填
        if (ClientType.MOBILE.name().equals(siteNavigation.getClientType())) {
            if (StringUtil.isEmpty(siteNavigation.getImage())) {
                throw new ServiceException(SystemErrorCode.E953.code(), "移动端导航，图片必传");
            }
        }
        //导航名称长度不能超过6
        if (siteNavigation.getNavigationName().length() > 12) {
            throw new ServiceException(SystemErrorCode.E953.code(), "导航栏菜单名称已经超出最大限制");
        }

        siteNavigation.setSort(siteNav.getSort());

        this.avigationChange(siteNav);

        this.daoSupport.update(siteNavigation, id);

        return siteNavigation;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {

        SiteNavigation siteNav = this.getModel(id);
        if (siteNav == null) {
            throw new ServiceException(SystemErrorCode.E953.code(), "导航栏不存在，请正确操作");
        }

        this.avigationChange(siteNav);

        this.daoSupport.delete(SiteNavigation.class, id);
    }

    @Override
    public SiteNavigation getModel(Integer id) {
        return this.daoSupport.queryForObject(SiteNavigation.class, id);
    }

    @Override
    public SiteNavigation updateSort(Integer id, String sort) {

        SiteNavigation siteNav = this.getModel(id);
        if (siteNav == null) {
            throw new ServiceException(SystemErrorCode.E953.code(), "导航栏不存在，请正确操作");
        }

        Integer menuSort = siteNav.getSort();
        String sql = "";
        /**判断是否操作是下移或者上移 up上移 否则 下移*/
        if ("up".equals(sort)) {
            sql = "select * from es_site_navigation where sort >? and client_type=? order by sort asc limit 1";
        } else {
            sql = "select * from es_site_navigation where sort < ? and client_type= ? order by sort desc limit 1 ";
        }
        /** 当前记录的上或者下一条记录 */
        SiteNavigation operationSiteMenu = this.daoSupport.queryForObject(sql, SiteNavigation.class, siteNav.getSort(), siteNav.getClientType());
        /**如果为null 则为最顶级或者最下级*/
        if (operationSiteMenu != null) {
            Integer operMenuSort = operationSiteMenu.getSort();
            /** 改变当前记录的排序 */
            siteNav.setSort(operMenuSort);
            this.daoSupport.update(siteNav, siteNav.getNavigationId());
            operationSiteMenu.setSort(menuSort);
            this.daoSupport.update(operationSiteMenu, operationSiteMenu.getNavigationId());
        }

        this.avigationChange(siteNav);

        return siteNav;
    }

    @Override
    public List<SiteNavigation> listByClientType(String clientType) {

        List<SiteNavigation> list = (List<SiteNavigation>) cache.get(CachePrefix.SITE_NAVIGATION.getPrefix() + clientType);

        if (list == null || list.isEmpty()) {
            String sql = "select * from es_site_navigation  where  client_type = ? order by sort desc";

            list = this.daoSupport.queryForList(sql, SiteNavigation.class, clientType);

            cache.put(CachePrefix.SITE_NAVIGATION.getPrefix() + clientType, list);
        }

        return list;
    }

    /**
     * 导航栏变化清除缓存，发送mq消息
     *
     * @param siteNav
     */
    private void avigationChange(SiteNavigation siteNav) {

        this.cache.remove(CachePrefix.SITE_NAVIGATION.getPrefix() + siteNav.getClientType());

        this.messageSender.send(new MqMessage(AmqpExchange.TEST_EXCHANGE,
                AmqpExchange.TEST_EXCHANGE + "_ROUTING",
                siteNav.getClientType()));
    }


}
