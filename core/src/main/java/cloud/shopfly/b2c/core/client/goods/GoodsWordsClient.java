/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.goods;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品分词client
 * @date 2018/8/21 11:04
 * @since v7.0.0
 */
public interface GoodsWordsClient {

    /**
     * 删除某个分词
     * @param words
     */
    void delete(String words);

    /**
     * 添加一组分词，存在累加数量，不存在新增
     * @param words
     */
    void addWords(String words);

    /**
     * 删除
     */
    void delete();

}
