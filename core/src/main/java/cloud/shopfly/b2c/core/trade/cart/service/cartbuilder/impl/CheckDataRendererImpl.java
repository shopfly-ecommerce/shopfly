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
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CheckDataRebderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Data correctness verification implementation
 *
 * @author zh
 * @version v7.0
 * @date 18/12/27 In the morning10:05
 * @since v7.0
 */

@Service
public class CheckDataRendererImpl implements CheckDataRebderer {


    @Autowired
    private GoodsClient goodsClient;


    @Override
    public void checkData(List<CartVO> cartList) {
        for (CartVO cartVO : cartList) {
            for (CartSkuVO cartSkuVO : cartVO.getSkuList()) {
                GoodsSkuVO goodsSkuVO = goodsClient.getSkuFromCache(cartSkuVO.getSkuId());
                if (goodsSkuVO == null || goodsSkuVO.getLastModify() > cartSkuVO.getLastModify()) {
                    // Set shopping cart is not selected
                    cartSkuVO.setChecked(0);
                    // Set up shopping cart This SKU item is invalid
                    cartSkuVO.setInvalid(1);
                    // Setting failure messages
                    cartSkuVO.setErrorMessage("Product information changes,Has the failure");
                }
            }
        }
    }


}
