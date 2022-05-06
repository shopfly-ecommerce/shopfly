/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.shop.goodssearch;

import cloud.shopfly.b2c.consumer.core.event.GoodsIndexInitEvent;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.goods.GoodsIndexClient;
import cloud.shopfly.b2c.core.system.model.TaskProgressConstant;
import cloud.shopfly.b2c.core.system.service.ProgressManager;
import cloud.shopfly.b2c.framework.logs.Debugger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v1.0
 * @Description: 商品索引初始化
 * @date 2018/6/25 11:38
 * @since v7.0.0
 */
@Service
public class GoodsIndexInitConsumer implements GoodsIndexInitEvent {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ProgressManager progressManager;
    @Autowired
    private GoodsIndexClient goodsIndexClient;
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private Debugger debugger;

    @Override
    public void createGoodsIndex() {

        if (logger.isDebugEnabled()) {
            logger.debug("开始生成索引");
        }
        debugger.log("开始生成索引");

        String key = TaskProgressConstant.GOODS_INDEX;
        try {
            /** 获取商品数 */
            int goodsCount = this.goodsClient.queryGoodsCount();

            /** 生成任务进度 */
            progressManager.taskBegin(key, goodsCount);

            //生成普通商品商品索引
            boolean goodsResult = createOrdinaryGoods(goodsCount);

            //任务结束
            progressManager.taskEnd(key, "索引生成完成");

            if (logger.isDebugEnabled()) {
                logger.debug("索引生成完成");
            }
            debugger.log("索引生成完成");

            if (goodsResult) {
                debugger.log("索引生成出现错误");
            }

        } catch (Exception e) {
            debugger.log("索引生成异常");
            progressManager.taskError(key, "生成索引异常，请联系运维人员");
            this.logger.error("生成索引异常：", e);

        }

    }

    /**
     * 生成普通商品的索引
     *
     * @param goodsCount 商品数
     */
    private boolean createOrdinaryGoods(Integer goodsCount) {

        //用来标记是否有错误
        boolean hasError = false;

        int pageSize = 100;
        int pageCount;
        pageCount = goodsCount / pageSize;
        pageCount = goodsCount % pageSize > 0 ? pageCount + 1 : pageCount;
        for (int i = 1; i <= pageCount; i++) {
            List<Map> goodsList = this.goodsClient.queryGoodsByRange(i, pageSize);
            Integer[] goodsIds = new Integer[goodsList.size()];
            int j = 0;
            for (Map map : goodsList) {
                goodsIds[j] = Integer.valueOf(map.get("goods_id").toString());
                j++;
            }
            List<Map<String, Object>> list = goodsClient.getGoodsAndParams(goodsIds);
            boolean result = goodsIndexClient.addAll(list, i);

            //有过错误就是有错误
            hasError = result && hasError;
        }

        return hasError;

    }

}
