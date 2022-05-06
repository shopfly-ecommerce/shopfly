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
package cloud.shopfly.b2c.framework.security.model;

/**
 *
 * 卖家
 * Created by kingapex on 2018/3/11.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/11
 */
public class Seller extends  Buyer {

    /**
     * 卖家id
     */
    private  Integer sellerId;

    /**
     * 卖家店铺名称
     */
    private String sellerName;
    
    /**
     * 是否是自营  0 不是  1是
     */
    private Integer selfOperated;


    public Seller() {
         //seller有 买家的角色和卖宾角色
         add( Role.SELLER.name());
    }


    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }


	public Integer getSelfOperated() {
		return selfOperated;
	}


	public void setSelfOperated(Integer selfOperated) {
		this.selfOperated = selfOperated;
	}
    
    
}
