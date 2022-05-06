/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.pagecreate.model;

/**
 * PageCreatePrefixEnum
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-17 下午2:15
 */
public enum PageCreatePrefixEnum {

    /**
     * 首页
     */
    INDEX("/"),
    /**
     * 商品页面
     */
    GOODS("/goods/{goods_id}"),
    /**
     * 帮助页面
     */
    HELP("/help/{article_id}");

    String prefix;

    PageCreatePrefixEnum(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getHandlerGoods(Integer goodsId) {
        return this.prefix.replace("{goods_id}", goodsId.toString());
    }

    public String getHandlerHelp(Integer articleId) {
        return this.prefix.replace("{article_id}", articleId.toString());

    }
}
