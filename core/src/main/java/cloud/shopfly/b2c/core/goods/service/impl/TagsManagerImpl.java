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
package cloud.shopfly.b2c.core.goods.service.impl;

import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dos.TagGoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.TagsDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.core.goods.service.TagsManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Commodity label business class
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
@Service
public class TagsManagerImpl implements TagsManager {

    @Autowired

    private DaoSupport daoSupport;


    @Override
    public List<GoodsSelectLine> queryTagGoods(Integer num, String mark) {
        String sql = "select g.goods_id,g.goods_name,g.price,g.sn,g.thumbnail,g.big,g.quantity,g.buy_count from es_tag_goods r "
                + " inner join es_goods g on g.goods_id=r.goods_id "
                + " inner join es_tags t on t.tag_id = r.tag_id"
                + " where g.disabled=1 and g.market_enable=1 and t.mark = ? limit 0,? ";

        return this.daoSupport.queryForList(sql, GoodsSelectLine.class, mark, num);
    }

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_tags ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, TagsDO.class);

        return webPage;
    }

    @Override
    public Page queryTagGoods(Integer tagId, Integer pageNo, Integer pageSize) {
        TagsDO tag = this.getModel(tagId);
        if (tag == null ) {
            throw new ServiceException(GoodsErrorCode.E309.code(), "Have the right to operate");
        }

        String sql = "select g.goods_id,g.goods_name,g.price,g.buy_count,g.enable_quantity,g.thumbnail from es_tag_goods r LEFT JOIN es_goods g ON g.goods_id=r.goods_id  "
                + "where g.disabled=1 and g.market_enable=1 and r.tag_id=? ";

        return this.daoSupport.queryForPage(sql, pageNo, pageSize, tagId);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveTagGoods(Integer tagId, Integer[] goodsIds) {
        TagsDO tag = this.getModel(tagId);
        if (tag == null ) {
            throw new ServiceException(GoodsErrorCode.E309.code(), "Have the right to operate");
        }

        if(goodsIds[0] != -1){
            List<Object> term = new ArrayList<>();
            String idStr = SqlUtil.getInSql(goodsIds, term);
            Integer count = this.daoSupport.queryForInt("select count(1) from es_goods where goods_id in (" + idStr + ") ",term.toArray());
            if (goodsIds.length != count) {
                throw new ServiceException(GoodsErrorCode.E309.code(), "Have the right to operate");
            }
        }

        // delete
        String sql = "delete from es_tag_goods where tag_id = ?";
        this.daoSupport.execute(sql,tagId);

        if(goodsIds[0] == -1){
            // Indicates that no goods are saved under this label
            return;
        }
        // add
        for (Integer goodsId : goodsIds) {
            TagGoodsDO tagGoods = new TagGoodsDO(tagId, goodsId);
            this.daoSupport.insert(tagGoods);
        }
    }

    @Override
    public TagsDO getModel(Integer id) {

        return this.daoSupport.queryForObject(TagsDO.class, id);
    }
}
