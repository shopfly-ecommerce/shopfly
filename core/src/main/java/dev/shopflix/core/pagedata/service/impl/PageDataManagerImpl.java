/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.pagedata.service.impl;

import dev.shopflix.core.base.message.CmsManageMsg;
import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.goods.model.vo.CacheGoods;
import dev.shopflix.core.pagedata.model.PageData;
import dev.shopflix.core.pagedata.service.PageDataManager;
import dev.shopflix.core.system.SystemErrorCode;
import dev.shopflix.core.system.model.enums.ClientType;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import dev.shopflix.framework.validation.annotation.Operation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 楼层业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 16:39:22
 */
@Service
public class PageDataManagerImpl implements PageDataManager {

    @Autowired
    @Qualifier("systemDaoSupport")
    private DaoSupport daoSupport;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private GoodsClient goodsClient;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_page  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, Page.class);

        return webPage;
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PageData add(PageData page) {
        this.daoSupport.insert(page);
        //发送消息
        this.sendFocusChangeMessage(page.getClientType());
        return page;
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PageData edit(PageData page, Integer id) {
        this.daoSupport.update(page, id);
        //发送消息
        this.sendFocusChangeMessage(page.getClientType());
        return page;
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(Page.class, id);
        //发送消息
        PageData page = this.getModel(id);
        this.sendFocusChangeMessage(page.getClientType());
    }

    @Override
    public PageData getModel(Integer id) {
        PageData page = this.daoSupport.queryForObject(PageData.class, id);
        return page;
    }


    @Override
    public PageData queryPageData(String clientType, String pageType) {
        PageData page = this.getByType(clientType, pageType);
        if (page == null) {
            throw new ServiceException(SystemErrorCode.E806.code(), "楼层找不到");
        }
        constructPageData(page);
        return page;
    }


    @Override
    @Operation
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PageData editByType(PageData pageData) {
        PageData data = this.getByType(pageData.getClientType(), pageData.getPageType());
        //首次保存
        if (data == null) {
            this.daoSupport.insert(pageData);
            pageData.setPageId(this.daoSupport.getLastId(""));
        } else {
            this.daoSupport.update(pageData, data.getPageId());
            pageData.setPageId(data.getPageId());
        }

        return pageData;
    }

    @Override
    public PageData getByType(String clientType, String pageType) {

        String sql = "select * from es_page where client_type = ? and page_type = ?";

        PageData page = this.daoSupport.queryForObject(sql, PageData.class, clientType, pageType);

        constructPageData(page);

        return page;
    }

    /**
     * 重新渲染楼层数据
     *
     * @param page
     */
    private void constructPageData(PageData page) {

        if (page != null && "WAP".equals(page.getClientType())) {
            String pageData = page.getPageData();
            List<HashMap> dataList = JsonUtil.jsonToList(pageData, HashMap.class);
            for (Map map : dataList) {
                List<Map> blockList = (List<Map>) map.get("blockList");
                for (Map blockMap : blockList) {
                    String blockType = (String) blockMap.get("block_type");
                    switch (blockType) {
                        case "GOODS":

                            Object object = blockMap.get("block_value");
                            if (object instanceof  String && StringUtil.isEmpty((String) blockMap.get("block_value"))) {
                                break;
                            }

                            Map blockValue = (Map) object;

                            if (blockValue == null) {
                                break;
                            }
                            Integer goodsId = StringUtil.toInt(blockValue.get("goods_id"), false);
                            CacheGoods goods = null;
                            try {
                                goods = this.goodsClient.getFromCache(goodsId);
                            } catch (Exception e) {
                                logger.error(e);
                            }
                            //如果商品被删除则返回空数据
                            if (goods == null || goods.getDisabled() == 0) {
                                blockMap.put("block_value", null);
                                break;
                            }
                            blockValue.put("goods_name", goods.getGoodsName());
                            blockValue.put("sn", goods.getSn());
                            blockValue.put("thumbnail", goods.getThumbnail());
                            blockValue.put("goods_image", goods.getThumbnail());
                            blockValue.put("enable_quantity", goods.getEnableQuantity());
                            blockValue.put("quantity", goods.getQuantity());
                            blockValue.put("goods_price", goods.getPrice());
                            blockValue.put("market_enable", goods.getMarketEnable());

                            break;
                        default:
                            break;
                    }

                }

            }
            page.setPageData(JsonUtil.objectToJson(dataList));
        }
    }

    /**
     * 发送首页变化消息
     *
     * @param clientType
     */
    private void sendFocusChangeMessage(String clientType) {

        CmsManageMsg cmsManageMsg = new CmsManageMsg();

        if (ClientType.PC.name().equals(clientType)) {
            this.messageSender.send(new MqMessage(AmqpExchange.PC_INDEX_CHANGE, AmqpExchange.PC_INDEX_CHANGE + "_ROUTING", cmsManageMsg));
        } else {
            this.messageSender.send(new MqMessage(AmqpExchange.MOBILE_INDEX_CHANGE, AmqpExchange.MOBILE_INDEX_CHANGE + "_ROUTING", cmsManageMsg));
        }
    }

}
