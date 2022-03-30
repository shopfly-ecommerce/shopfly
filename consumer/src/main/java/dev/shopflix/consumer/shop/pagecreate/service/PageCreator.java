/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.pagecreate.service;


/**
 * 静态页生成接口
 *
 * @author zh
 * @version v1.0
 * @since v6.4.0
 * 2017年8月29日 上午11:43:42
 */
public interface PageCreator {

    /**
     * 生成单个页面
     *
     * @param path 页面地址
     * @param type 客户端类型
     * @param name 生成静态页面在redis中的key
     * @throws Exception
     */
    void createOne(String path, String type, String name) throws Exception;

    /**
     * 生成所有页面
     *
     * @throws Exception
     */
    void createAll() throws Exception;

    /**
     * 生成商品页面
     *
     * @throws Exception
     */
    void createGoods() throws Exception;

    /**
     * 生成首页
     *
     * @throws Exception
     */
    void createIndex() throws Exception;

    /**
     * 生成帮助中心页面
     *
     * @throws Exception
     */
    void createHelp() throws Exception;

    /**
     * 删除某个商品页
     *
     * @param name
     * @throws Exception
     */
    void deleteGoods(String name) throws Exception;
}
