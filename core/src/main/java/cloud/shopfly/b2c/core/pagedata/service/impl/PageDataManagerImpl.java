/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.pagedata.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.pagedata.model.PageData;
import cloud.shopfly.b2c.core.pagedata.service.PageDataManager;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.enums.ClientType;
import cloud.shopfly.b2c.core.base.message.CmsManageMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.validation.annotation.Operation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Floor business class
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 16:39:22
 */
@Service
public class PageDataManagerImpl implements PageDataManager {

    @Autowired
    
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
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PageData add(PageData page) {
        this.daoSupport.insert(page);
        // Send a message
        this.sendFocusChangeMessage(page.getClientType());
        return page;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PageData edit(PageData page, Integer id) {
        this.daoSupport.update(page, id);
        // Send a message
        this.sendFocusChangeMessage(page.getClientType());
        return page;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(Page.class, id);
        // Send a message
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
            throw new ServiceException(SystemErrorCode.E806.code(), "Floor not found");
        }
        constructPageData(page);
        return page;
    }


    @Override
    @Operation
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PageData editByType(PageData pageData) {
        PageData data = this.getByType(pageData.getClientType(), pageData.getPageType());
        // Save for the first time
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
     * Re-render floor data
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
                            // Null data is returned if the item is deleted
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
     * Send home page change messages
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
