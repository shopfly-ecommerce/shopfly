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
package cloud.shopfly.b2c.core.client.goods.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsWordsClient;
import cloud.shopfly.b2c.core.goodssearch.util.PinYinUtil;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/8/21 16:11
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class GoodsWordsClientDefaultImpl implements GoodsWordsClient {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void delete(String words) {
        this.daoSupport.execute("update es_goods_words set goods_num  = (case goods_num-1<0 when true  then 0 else goods_num-1 end ) where words=?", words);
    }

    @Override
    public void addWords(String words) {
        String sql = "select * from es_goods_words where words=?";
        List list = this.daoSupport.queryForList(sql,words);
        if(list == null||list.size()==0){
            Map map = new HashMap(16);
            map.put("words",words);
            map.put("quanpin", PinYinUtil.getPingYin(words));
            map.put("szm",PinYinUtil.getPinYinHeadChar(words));
            map.put("goods_num",1);
            this.daoSupport.insert("es_goods_words", map);
        }else{
            this.daoSupport.execute("update es_goods_words set goods_num=goods_num+1 where words=?", words);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void delete() {
        this.daoSupport.execute("delete from  es_goods_words ");
    }
}
