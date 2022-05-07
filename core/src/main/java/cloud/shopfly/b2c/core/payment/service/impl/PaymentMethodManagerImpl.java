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
package cloud.shopfly.b2c.core.payment.service.impl;

import cloud.shopfly.b2c.core.payment.PaymentErrorCode;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.vo.ClientConfig;
import cloud.shopfly.b2c.core.payment.model.vo.PayConfigItem;
import cloud.shopfly.b2c.core.payment.model.vo.PaymentMethodVO;
import cloud.shopfly.b2c.core.payment.model.vo.PaymentPluginVO;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.core.payment.service.PaymentPluginManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Payment mode table business class
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
@Service
public class PaymentMethodManagerImpl implements PaymentMethodManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private List<PaymentPluginManager> paymentPluginList;

    @Autowired
    private Cache cache;

    @Override
    public Page list(int page, int pageSize) {

        List<PaymentPluginVO> resultList = new ArrayList<>();

        // Query the payment method in the database
        String sql = "select * from es_payment_method ";
        List<PaymentMethodDO> list = this.daoSupport.queryForList(sql, PaymentMethodDO.class);
        Map<String, PaymentMethodDO> map = new HashMap<>(list.size());

        for (PaymentMethodDO payment : list) {
            map.put(payment.getPluginId(), payment);
        }

        for (PaymentPluginManager plugin : paymentPluginList) {
            PaymentMethodDO payment = map.get(plugin.getPluginId());
            PaymentPluginVO result = null;

            // The payment method is already in the database
            if (payment != null) {
                result = new PaymentPluginVO(payment);
            } else {
                result = new PaymentPluginVO(plugin);
            }

            resultList.add(result);
        }
        Long size = (long) resultList.size();

        return new Page(page, size, pageSize, resultList);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PaymentMethodDO add(PaymentPluginVO paymentMethod, String paymentPluginId) {

        // Delete plug-ins in the library
        String sql = "delete from es_payment_method where plugin_id = ? ";
        this.daoSupport.execute(sql, paymentPluginId);

        PaymentPluginManager paymentPlugin = findPlugin(paymentPluginId);
        if (paymentPlugin == null) {
            throw new ServiceException(PaymentErrorCode.E501.code(), "The plug-inidIs not correct");
        }

        paymentMethod.setMethodName(paymentPlugin.getPluginName());
        paymentMethod.setPluginId(paymentPluginId);

        // Configuration information
        List<ClientConfig> clients = paymentMethod.getEnableClient();
        Map<String, ClientConfig> map = new HashMap(16);
        for (ClientConfig client : clients) {

            String keyStr = client.getKey();
            keyStr = keyStr.replace("amp;", "");
            // Distinguish between client pc_config and wap_config
            String[] keys = keyStr.split("&");

            for (String key : keys) {
                map.put(key, client);
            }

        }

        // To avoid incorrect value format, use the custom format
        List<ClientConfig> needClients = paymentPlugin.definitionClientConfig();
        Map jsonMap = new HashMap(16);
        for (ClientConfig clientConfig : needClients) {
            String keyStr = clientConfig.getKey();
            // Distinguish between client pc_config and wap_config
            String[] keys = keyStr.split("&");
            for (String key : keys) {
                // The object to which the value is passed
                ClientConfig client = map.get(key);
                if (client == null) {
                    throw new ServiceException(PaymentErrorCode.E501.code(), "The lack of" + clientConfig.getName() + "The related configuration");
                }
                // Did not open
                Integer open = client.getIsOpen();
                clientConfig.setIsOpen(client.getIsOpen());
                if (open == 0) {
                    // If this parameter is not enabled, configuration parameters are not saved
                    jsonMap.put(StringUtil.lowerUpperCaseColumn(key), JsonUtil.objectToJson(clientConfig));
                    continue;
                }
                // Configuration parameters to which values are passed
                List<PayConfigItem> list = client.getConfigList();
                if (list == null) {
                    throw new ServiceException(PaymentErrorCode.E501.code(), clientConfig.getName() + "Cannot be empty");
                }
                // Loop to key value format
                Map<String, String> valueMap = new HashMap<>(list.size());
                for (PayConfigItem item : list) {
                    valueMap.put(item.getName(), item.getValue());
                }
                // Configuration Parameter Settings
                List<PayConfigItem> configList = clientConfig.getConfigList();
                for (PayConfigItem item : configList) {
                    String value = valueMap.get(item.getName());
                    if (StringUtil.isEmpty(value)) {
                        throw new ServiceException(PaymentErrorCode.E501.code(), clientConfig.getName() + "the" + item.getText() + "required");
                    }
                    item.setValue(value);
                }
                clientConfig.setConfigList(configList);

                jsonMap.put(StringUtil.lowerUpperCaseColumn(key), JsonUtil.objectToJson(clientConfig));
            }

        }


        PaymentMethodDO payment = new PaymentMethodDO();
        // Copying configuration Information
        BeanUtil.copyPropertiesInclude(jsonMap, payment);

        BeanUtil.copyProperties(paymentMethod, payment);

        this.daoSupport.insert(payment);
        payment.setMethodId(this.daoSupport.getLastId(""));


        List<String> keys = new ArrayList<>();
        for (ClientType clientType : ClientType.values()) {
            keys.add(CachePrefix.PAYMENT_CONFIG.getPrefix() + clientType.getDbColumn() + paymentPluginId);
        }

        // Delete the cache
        cache.multiDel(keys);

        return payment;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PaymentMethodDO edit(PaymentMethodDO paymentMethod, Integer id) {

        PaymentMethodDO method = this.daoSupport.queryForObject(PaymentMethodDO.class, id);
        if (method == null) {
            throw new ServiceException(PaymentErrorCode.E501.code(), "Payment method does not exist");
        }

        this.daoSupport.update(paymentMethod, id);

        List<String> keys = new ArrayList<>();
        for (ClientType clientType : ClientType.values()) {
            keys.add(CachePrefix.PAYMENT_CONFIG.getPrefix() + clientType.getDbColumn() + id);
        }

        // Delete the cache
        cache.multiDel(keys);

        return paymentMethod;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(PaymentMethodDO.class, id);

        List<String> keys = new ArrayList<>();
        for (ClientType clientType : ClientType.values()) {
            keys.add(CachePrefix.PAYMENT_CONFIG.getPrefix() + clientType.getDbColumn() + id);
        }
        // Delete the cache
        cache.multiDel(keys);
    }

    @Override
    public PaymentMethodDO getByPluginId(String pluginId) {

        if (pluginId == null) {
            return null;
        }

        String sql = "select * from es_payment_method where plugin_id = ?";

        return this.daoSupport.queryForObject(sql, PaymentMethodDO.class, pluginId);

    }

    @Override
    public List<PaymentMethodVO> queryMethodByClient(String clientType) {

        String column = ClientType.valueOf(clientType).getDbColumn();
        String sql = "select * from es_payment_method where " + column + " like ?";

        return this.daoSupport.queryForList(sql, PaymentMethodVO.class, "%\"is_open\":1%");
    }


    @Override
    public PaymentPluginVO getByPlugin(String pluginId) {

        PaymentMethodDO paymentMethod = this.getByPluginId(pluginId);

        if (paymentMethod == null) {

            PaymentPluginManager plugin = findPlugin(pluginId);
            if(plugin == null ){
                throw new ServiceException(PaymentErrorCode.E501.code(),"Payment method does not exist");
            }
            PaymentPluginVO payment = new PaymentPluginVO(plugin);
            payment.setEnableClient(plugin.definitionClientConfig());
            return payment;
        } else {

            Map<String, ClientConfig> map = new HashMap(16);

            String pcConfig = paymentMethod.getPcConfig();
            if (pcConfig != null) {
                ClientConfig config = JsonUtil.jsonToObject(pcConfig, ClientConfig.class);
                map.put(config.getKey(), config);
            }

            String wapConfig = paymentMethod.getWapConfig();
            if (wapConfig != null) {
                ClientConfig config = JsonUtil.jsonToObject(wapConfig, ClientConfig.class);
                map.put(config.getKey(), config);
            }

            String appReactConfig = paymentMethod.getAppReactConfig();
            if (appReactConfig != null) {
                ClientConfig config = JsonUtil.jsonToObject(appReactConfig, ClientConfig.class);
                map.put(config.getKey(), config);
            }

            String appNativeConfig = paymentMethod.getAppNativeConfig();
            if (appNativeConfig != null) {
                ClientConfig config = JsonUtil.jsonToObject(appNativeConfig, ClientConfig.class);
                map.put(config.getKey(), config);
            }

            String miniConfig = paymentMethod.getMiniConfig();
            if (miniConfig != null) {
                ClientConfig config = JsonUtil.jsonToObject(miniConfig, ClientConfig.class);
                map.put(config.getKey(), config);
            }

            PaymentPluginVO pluginVO = new PaymentPluginVO();
            BeanUtil.copyProperties(paymentMethod, pluginVO);
            // Configuration information
            List<ClientConfig> clientConfigs = new ArrayList<>();
            for (String key : map.keySet()) {
                clientConfigs.add(map.get(key));
            }
            pluginVO.setEnableClient(clientConfigs);

            return pluginVO;
        }

    }


    /**
     * Find payment plug-ins
     *
     * @param pluginId
     * @return
     */
    private PaymentPluginManager findPlugin(String pluginId) {
        for (PaymentPluginManager plugin : paymentPluginList) {
            if (plugin.getPluginId().equals(pluginId)) {
                return plugin;
            }
        }
        return null;
    }
}
