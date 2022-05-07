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
package cloud.shopfly.b2c.core.base.plugin.waybill;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.client.system.LogiCompanyClient;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.dos.LogiCompanyDO;
import cloud.shopfly.b2c.core.system.model.vo.InformationSetting;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.core.base.plugin.waybill.vo.Commodity;
import cloud.shopfly.b2c.core.base.plugin.waybill.vo.Information;
import cloud.shopfly.b2c.core.base.plugin.waybill.vo.WayBillJson;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.Base64;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Express bird electronic panel plug-in
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017years8month14The morning of10:39:03
 */
@SuppressWarnings("unchecked")
@Component("kdnPlugin")
public class KDNPlugin implements WayBillEvent {

    @Autowired
    private LogiCompanyClient logiCompanyClient;
    @Autowired
    private OrderClient orderclient;
    @Autowired
    private SettingClient settingClient;

    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList<>();
        ConfigItem sellerMchidItem = new ConfigItem();
        sellerMchidItem.setName("EBusinessID");
        sellerMchidItem.setText("electricityID");
        sellerMchidItem.setType("text");

        ConfigItem selleAppidItem = new ConfigItem();
        selleAppidItem.setName("AppKey");
        selleAppidItem.setText("The key");
        selleAppidItem.setType("text");

        ConfigItem sellerKeyItem = new ConfigItem();
        sellerKeyItem.setName("ReqURL");
        sellerKeyItem.setText("requesturl");
        sellerKeyItem.setType("text");

        list.add(sellerMchidItem);
        list.add(selleAppidItem);
        list.add(sellerKeyItem);
        return list;
    }

    @Override
    public String getPluginId() {
        return "kdnPlugin";
    }

    @Override
    public String createPrintData(String orderSn, Integer logisticsId, Map config) throws Exception {
        if (config == null) {
            throw new ServiceException(SystemErrorCode.E912.code(), "Single parameter error of electron surface");
        }
        // Get order information
        OrderDetailDTO orderDetailDTO = orderclient.getModel(orderSn);
        // Access logistics company information
        LogiCompanyDO logiCompanyDO = logiCompanyClient.getModel(logisticsId);

        JSONObject jsonObject = JSONObject.fromObject(config);
        String eBusinessID = jsonObject.getString("EBusinessID");
        String appKey = jsonObject.getString("AppKey");
        String reqURL = jsonObject.getString("ReqURL");

        // Payment method docking, obtain the order payment method
        // Postage payment :1- cash, 2- collect, 3- monthly settlement, 4- third-party payment
        Integer payType = 1;
        WayBillJson wayBillJson = new WayBillJson();
        // Sender assignment
        String siteSettingJson = settingClient.get(SettingGroup.SITE);
        SiteSetting siteSetting = JsonUtil.jsonToObject(siteSettingJson, SiteSetting.class);

        String infoSettingJson = settingClient.get(SettingGroup.INFO);
        InformationSetting informationSetting = JsonUtil.jsonToObject(infoSettingJson, InformationSetting.class);

        Information senders = new Information();
        senders.setName(siteSetting.getSiteName());
        senders.setMobile(informationSetting.getPhone());
        senders.setProvinceName(informationSetting.getProvince());
        senders.setCityName(informationSetting.getCity());
        senders.setExpAreaName(informationSetting.getCounty());
        senders.setAddress(informationSetting.getAddress());
        // Receiver assignment
        Information receivers = new Information();
        receivers.setName(orderDetailDTO.getShipName());
        receivers.setMobile(orderDetailDTO.getShipMobile());
        receivers.setProvinceName(orderDetailDTO.getShipProvince());
        receivers.setCityName(orderDetailDTO.getShipCity());
        receivers.setExpAreaName(orderDetailDTO.getShipCounty());
        receivers.setAddress(orderDetailDTO.getShipAddr());

        List<OrderSkuDTO> list = orderDetailDTO.getOrderSkuList();
        List<Commodity> commoditys = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Commodity commodity = new Commodity();
            commodity.setGoodsName(list.get(i).getName());
            commodity.setGoodsquantity(list.get(i).getNum());
            commodity.setGoodsWeight(list.get(i).getGoodsWeight());
            commodity.setGoodsPrice(list.get(i).getPurchasePrice());
            commoditys.add(commodity);
        }

        wayBillJson.setOrderCode(orderDetailDTO.getSn());
        wayBillJson.setShipperCode(logiCompanyDO.getKdcode());
        wayBillJson.setPayType(payType);
        wayBillJson.setExpType("1");
        wayBillJson.setCustomerName(logiCompanyDO.getCustomerName());
        wayBillJson.setCustomerPwd(logiCompanyDO.getCustomerPwd());
        wayBillJson.setCost(orderDetailDTO.getShippingPrice());
        wayBillJson.setOtherCost(1.0);
        wayBillJson.setWeight(orderDetailDTO.getWeight());
        wayBillJson.setQuantity(orderDetailDTO.getGoodsNum());
        wayBillJson.setVolume(0.0);
        wayBillJson.setRemark(orderDetailDTO.getRemark());
        wayBillJson.setRemark("Handle with care");
        wayBillJson.setIsReturnPrintTemplate("1");
        wayBillJson.setSender(senders);
        wayBillJson.setReceiver(receivers);
        wayBillJson.setCommodity(commoditys);


        String requestData = JsonUtil.objectToJson(wayBillJson);
        Map<String, String> params = new HashMap<>(16);
        params.put("RequestData", URLEncoder.encode(requestData, "UTF-8"));
        params.put("EBusinessID", eBusinessID);
        params.put("RequestType", "1007");

        String dataSign = this.encrypt(requestData, appKey, "UTF-8");
        params.put("DataSign", URLEncoder.encode(dataSign, "UTF-8"));
        params.put("DataType", "2");
        return this.sendPost(reqURL, params);

    }

    @Override
    public String getPluginName() {
        return "Express bird";
    }

    @Override
    public Integer getOpen() {
        return 0;
    }

    @SuppressWarnings("unused")
    private String encrypt(String content, String keyValue, String charset) throws Exception {
        if (keyValue != null) {
            return Base64.encode(StringUtil.md5(content + keyValue, charset).getBytes(charset));
        }
        return Base64.encode(StringUtil.md5(content, charset).getBytes(charset));
    }

    /**
     * To specify theURL sendPOSTMethod request
     *
     * @param url    requestingURL
     * @param params A collection of requested parameters
     * @return Response result of the remote resource
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // The following two lines must be set to send a POST request
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST method
            conn.setRequestMethod("POST");
            // Set common request properties
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // Gets the output stream corresponding to the URLConnection object
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // Send request parameters
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                }
                out.write(param.toString());
            }
            // Flush Buffers the output stream
            out.flush();
            // Defines the BufferedReader input stream to read the response to the URL
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Use the finally block to close the output stream, input stream
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}

