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
package cloud.shopfly.b2c.core.member.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dos.MemberCollectionGoods;
import cloud.shopfly.b2c.core.member.service.MemberCollectionGoodsManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.statistics.model.dto.GoodsData;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员商品收藏业务类
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 10:13:41
 */
@Service
public class MemberCollectionGoodsManagerImpl implements MemberCollectionGoodsManager {

    @Autowired
    
    private DaoSupport memberDaoSupport;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private MemberManager memberManager;

    @Autowired
    private MessageSender messageSender;

    @Override
    public Page list(int page, int pageSize) {
        Buyer buyer = UserContext.getBuyer();
        String sql = "select * from es_member_collection_goods where member_id = ? ";
        Page webPage = this.memberDaoSupport.queryForPage(sql, page, pageSize, MemberCollectionGoods.class, buyer.getUid());
        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberCollectionGoods add(MemberCollectionGoods memberCollectionGoods) {
        Buyer buyer = UserContext.getBuyer();
        //查询当前会员是否存在
        Member member = memberManager.getModel(buyer.getUid());
        if (member == null) {
            throw new ResourceNotFoundException("当前会员不存在");
        }
        //获取商品id
        Integer goodsId = memberCollectionGoods.getGoodsId();
        //查询此商品信息
        CacheGoods goods = goodsClient.getFromCache(memberCollectionGoods.getGoodsId());
        //判断商品是否存在
        if (goods == null) {
            throw new ResourceNotFoundException("此商品不存在");
        }
        //判断当前商品是否已经添加为收藏
        String sql = "select * from es_member_collection_goods where member_id = ? and goods_id = ?";
        List<MemberCollectionGoods> list = this.memberDaoSupport.queryForList(sql, MemberCollectionGoods.class, buyer.getUid(), goodsId);
        if (list.size() > 0) {
            throw new ServiceException(MemberErrorCode.E105.code(), "当前商品已经添加为收藏");
        }
        memberCollectionGoods.setMemberId(buyer.getUid());
        memberCollectionGoods.setGoodsName(goods.getGoodsName());
        memberCollectionGoods.setGoodsImg(goods.getThumbnail());
        memberCollectionGoods.setCreateTime(DateUtil.getDateline());
        memberCollectionGoods.setGoodsSn(goods.getSn());
        memberCollectionGoods.setGoodsPrice(goods.getPrice());
        this.memberDaoSupport.insert("es_member_collection_goods", memberCollectionGoods);
        memberCollectionGoods.setId(memberDaoSupport.getLastId("es_member_collection_goods"));
        //发送消息
        GoodsData goodsData = new GoodsData();
        goodsData.setGoodsId(goodsId);
        goodsData.setFavoriteNum(this.getGoodsCollectCount(goodsId));
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_COLLECTION_CHANGE, AmqpExchange.GOODS_COLLECTION_CHANGE + "_ROUTING",
                goodsData));
        return memberCollectionGoods;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer goodsId) {
        Buyer buyer = UserContext.getBuyer();
        MemberCollectionGoods memberCollectionGoods = this.memberDaoSupport.queryForObject("select * from es_member_collection_goods where goods_id = ? and member_id = ?", MemberCollectionGoods.class, goodsId, buyer.getUid());
        if (memberCollectionGoods != null) {
            this.memberDaoSupport.delete(MemberCollectionGoods.class, memberCollectionGoods.getId());
            //发送消息
            GoodsData goodsData = new GoodsData();
            goodsData.setGoodsId(goodsId);
            goodsData.setFavoriteNum(this.getGoodsCollectCount(goodsId));
            this.messageSender.send(new MqMessage(AmqpExchange.GOODS_COLLECTION_CHANGE, AmqpExchange.GOODS_COLLECTION_CHANGE + "_ROUTING",
                    goodsData));
        }
    }


    @Override
    public boolean isCollection(Integer id) {
        Buyer buyer = UserContext.getBuyer();
        int count = this.memberDaoSupport.queryForInt("select count(0) from es_member_collection_goods where goods_id = ? and member_id = ?", id, buyer.getUid());
        return count > 0;
    }

    @Override
    public MemberCollectionGoods getModel(Integer id) {
        return this.memberDaoSupport.queryForObject(MemberCollectionGoods.class, id);
    }

    @Override
    public Integer getMemberCollectCount() {
        return this.memberDaoSupport.queryForInt("select count(*) from es_member_collection_goods where member_id = ?", UserContext.getBuyer().getUid());
    }

    /**
     * 获取会员收藏商品数
     *
     * @return 收藏商品数
     */
    @Override
    public Integer getGoodsCollectCount(Integer goodsId) {
        try {
            return this.memberDaoSupport.queryForInt("select count(0) from es_member_collection_goods where goods_id = ?", goodsId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}
