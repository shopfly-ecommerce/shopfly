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
package cloud.shopfly.b2c.core.promotion.fulldiscount.service.impl;

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountGiftManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Full preferential gift business class
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 17:34:46
 */
@Service
public class FullDiscountGiftManagerImpl implements FullDiscountGiftManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize, String keyword) {

        String sql = "select * from es_full_discount_gift";

        List<Object> params = new ArrayList<>();
        // If the field is not empty
        if (!StringUtil.isEmpty(keyword)) {
            sql += " where gift_name like ? ";
            params.add("%" + keyword + "%");

        }

        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, FullDiscountGiftDO.class, params.toArray());

        return webPage;
    }

    @Override
    public List<FullDiscountGiftDO> listAll() {
        String sql = "select * from es_full_discount_gift";
        return this.daoSupport.queryForList(sql, FullDiscountGiftDO.class);
    }

    @Override
    public FullDiscountGiftDO add(FullDiscountGiftDO fullDiscountGift) {
        this.daoSupport.insert(fullDiscountGift);
        int id = this.daoSupport.getLastId("es_full_discount_gift");
        fullDiscountGift.setGiftId(id);
        return fullDiscountGift;
    }

    @Override
    public FullDiscountGiftDO edit(FullDiscountGiftDO giftDO, Integer id) {
        this.daoSupport.update(giftDO, id);
        return giftDO;
    }

    @Override
    public void delete(Integer id) {
        this.daoSupport.execute("delete from es_full_discount_gift where gift_id=?", id);
    }

    @Override
    public FullDiscountGiftDO getModel(Integer id) {
        FullDiscountGiftDO giftDO = this.daoSupport.queryForObject(FullDiscountGiftDO.class, id);
        return giftDO;
    }

    @Override
    public void verifyAuth(Integer id) {

        FullDiscountGiftDO fullDiscountGift = this.getModel(id);
        // Verify unauthorized operations
        if (fullDiscountGift == null) {
            throw new NoPermissionException("Have the right to operate");
        }

    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean addGiftQuantity(List<FullDiscountGiftDO> giftDOList) {
        try {
            for (FullDiscountGiftDO giftDO : giftDOList) {
                // There are freebies for current canceled orders
                String giftSql = "update es_full_discount_gift set enable_store=enable_store+1 ,actual_store=actual_store+1 where gift_id=?";
                daoSupport.execute(giftSql, giftDO.getGiftId());
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean addGiftEnableQuantity(List<FullDiscountGiftDO> giftDOList) {
        try {
            for (FullDiscountGiftDO giftDO : giftDOList) {
                // Current cancelled orders have freebies, increase available inventory for freebies
                String giftSql = "update es_full_discount_gift set enable_store=enable_store+1 where gift_id=?";
                daoSupport.execute(giftSql, giftDO.getGiftId());
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean reduceGiftQuantity(List<FullDiscountGiftDO> giftDOList, QuantityType type) {
        try {

            String giftSql = "";
            if (QuantityType.enable.equals(type)) {
                giftSql = "update es_full_discount_gift set enable_store=enable_store-1 where gift_id=? and enable_store>0";
            } else if (QuantityType.actual.equals(type)) {
                giftSql = "update es_full_discount_gift set actual_store=actual_store-1 where gift_id=? and actual_store>0";
            }

            for (FullDiscountGiftDO giftDO : giftDOList) {
                // There are freebies for current canceled orders
                daoSupport.execute(giftSql, giftDO.getGiftId());
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
