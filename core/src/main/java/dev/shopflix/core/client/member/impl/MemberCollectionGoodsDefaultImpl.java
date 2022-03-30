/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.member.impl;

import dev.shopflix.core.client.member.MemberCollectionGoodsClient;
import dev.shopflix.core.member.service.MemberCollectionGoodsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 会员收藏商品默认实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午4:47
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class MemberCollectionGoodsDefaultImpl implements MemberCollectionGoodsClient {

    @Autowired
    private MemberCollectionGoodsManager memberCollectionGoodsManager;

    @Override
    public Integer getGoodsCollectCount(Integer goodsId) {
        return memberCollectionGoodsManager.getGoodsCollectCount(goodsId);
    }
}
