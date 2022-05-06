/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.member;

/**
 * 会员上产品收藏
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午4:42
 * @since v7.0
 */

public interface MemberCollectionGoodsClient {


    /**
     * 某商品收藏数量
     *
     * @param goodsId 商品id
     * @return 收藏数量
     */
    Integer getGoodsCollectCount(Integer goodsId);


}
