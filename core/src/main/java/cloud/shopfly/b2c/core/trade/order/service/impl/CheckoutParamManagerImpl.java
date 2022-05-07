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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.ReceiptVO;
import cloud.shopfly.b2c.core.trade.order.service.CheckoutParamManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.client.member.MemberAddressClient;
import cloud.shopfly.b2c.core.client.system.RegionsClient;
import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;
import cloud.shopfly.b2c.core.trade.order.model.vo.CheckoutParamVO;
import cloud.shopfly.b2c.core.trade.order.support.CheckoutParamName;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Settlement parameters business layer implementation class
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class CheckoutParamManagerImpl implements CheckoutParamManager {

    @Autowired
    private Cache cache;

    @Autowired
    private MemberAddressClient memberAddressClient;

    @Autowired
    private RegionsClient regionsClient;


    @Override
    public CheckoutParamVO getParam() {
        CheckoutParamVO param = this.read();

        // If there is no new in session, assign the default value
        if (param == null) {

            param = new CheckoutParamVO();
            Buyer buyer = UserContext.getBuyer();

            MemberAddress address = this.memberAddressClient.getDefaultAddress(buyer.getUid());
            int addrId = 0;
            if (address != null) {
                addrId = address.getAddrId();
            }
            // Default shipping address
            param.setAddressId(addrId);

            // Default payment method
            param.setPaymentType(PaymentTypeEnum.defaultType());

            // No invoice is required by default
            ReceiptVO receipt = new ReceiptVO();
            param.setReceipt(receipt);

            // The default time
            param.setReceiveTime("At any time");

            this.write(param);
        }
        return param;
    }

    @Override
    public void setAddressId(Integer addressId) {
        this.cache.putHash(getRedisKey(), CheckoutParamName.ADDRESS_ID, addressId);
    }

    @Override
    public void setPaymentType(PaymentTypeEnum paymentTypeEnum) {
        this.cache.putHash(getRedisKey(), CheckoutParamName.PAYMENT_TYPE, paymentTypeEnum);
    }

    @Override
    public void setReceipt(ReceiptVO receipt) {
        this.cache.putHash(getRedisKey(), CheckoutParamName.RECEIPT, receipt);
    }

    @Override
    public void setReceiveTime(String receiveTime) {
        this.cache.putHash(getRedisKey(), CheckoutParamName.RECEIVE_TIME, receiveTime);
    }

    @Override
    public void setRemark(String remark) {
        this.cache.putHash(getRedisKey(), CheckoutParamName.REMARK, remark);
    }

    @Override
    public void setClientType(String clientType) {
        this.cache.putHash(getRedisKey(), CheckoutParamName.CLIENT_TYPE, clientType);
    }

    @Override
    public void deleteReceipt() {
        this.cache.putHash(getRedisKey(), CheckoutParamName.RECEIPT, null);
    }

    @Override
    public void setAll(CheckoutParamVO paramVO) {
        this.write(paramVO);
    }

    @Override
    public void checkCod(PaymentTypeEnum paymentTypeEnum) {
        if(!PaymentTypeEnum.COD.equals(paymentTypeEnum)){
            return ;
        }

        CheckoutParamVO paramVO = this.getParam();
        Integer addressId = paramVO.getAddressId();

        MemberAddress memberAddress = this.memberAddressClient.getModel(addressId);

        if(memberAddress == null){
            return;
        }

//        List<Integer> addIds = new ArrayList<>();
//        addIds.add(memberAddress.getProvinceId());
//        addIds.add(memberAddress.getCityId());
//        addIds.add(memberAddress.getCountyId());
//        addIds.add(memberAddress.getTownId());
//
//        for (Integer region: addIds) {
//            Regions regions = this.regionsClient.getModel(region);
//            if(regions == null){
//                continue;
//            }
//            if(regions.getCod() == 0){
//                throw new NoPermissionException("["+regions.getLocalName() + "]Cash on delivery is not supported");
//            }
//        }
    }


    /**
     * readKey
     *
     * @return
     */
    private String getRedisKey() {
        Buyer buyer = UserContext.getBuyer();
        return CachePrefix.CHECKOUT_PARAM_ID_PREFIX.getPrefix() + buyer.getUid();
    }


    /**
     * writemapvalue
     *
     * @param paramVO
     */
    private void write(CheckoutParamVO paramVO) {
        String redisKey = getRedisKey();
        Map map = new HashMap<>(4);

        if (paramVO.getAddressId() != null) {
            map.put(CheckoutParamName.ADDRESS_ID, paramVO.getAddressId());
        }

        if (paramVO.getReceiveTime() != null) {
            map.put(CheckoutParamName.RECEIVE_TIME, paramVO.getReceiveTime());
        }

        if (paramVO.getPaymentType() != null) {
            map.put(CheckoutParamName.PAYMENT_TYPE, paramVO.getPaymentType());
        }
        if (paramVO.getReceipt() != null) {
            map.put(CheckoutParamName.RECEIPT, paramVO.getReceipt());
        }
        if (paramVO.getRemark() != null) {
            map.put(CheckoutParamName.REMARK, paramVO.getRemark());
        }
        if (paramVO.getClientType() != null) {
            map.put(CheckoutParamName.CLIENT_TYPE, paramVO.getClientType());
        }

        this.cache.putAllHash(redisKey, map);
    }


    /**
     * byReidsRead fetch parameters in
     */
    private CheckoutParamVO read() {
        String key = getRedisKey();
        Map<String, Object> map = this.cache.getHash(key);

        // Returns NULL if it has not been saved
        if (map == null || map.isEmpty()) {
            return null;
        }

        // If it does, it is fetched to generate param
        Integer addressId = (Integer) map.get(CheckoutParamName.ADDRESS_ID);
        PaymentTypeEnum paymentType = (PaymentTypeEnum) map.get(CheckoutParamName.PAYMENT_TYPE);
        ReceiptVO receipt = (ReceiptVO) map.get(CheckoutParamName.RECEIPT);
        String receiveTime = (String) map.get(CheckoutParamName.RECEIVE_TIME);
        String remark = (String) map.get(CheckoutParamName.REMARK);
        String clientType = (String) map.get(CheckoutParamName.CLIENT_TYPE);


        CheckoutParamVO param = new CheckoutParamVO();

        param.setAddressId(addressId);
        param.setReceipt(receipt);
        if (receiveTime == null) {
            receiveTime = "At any time";
        }
        param.setReceiveTime(receiveTime);
        param.setRemark(remark);
        if (paymentType == null) {
            paymentType = PaymentTypeEnum.defaultType();
        }
        param.setPaymentType(paymentType);
        param.setClientType(clientType);
        return param;
    }

}
