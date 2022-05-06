/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.job.execute.impl;

import cloud.shopfly.b2c.consumer.job.execute.EveryDayExecute;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品评分定时任务
 *
 * @author fk
 * @version v1.0
 * @since v7.0
 * 2018-07-05 下午2:11
 */
@Component
public class GoodsGradeJob implements EveryDayExecute {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private GoodsClient goodsClient;

    /**
     * 每晚23:30执行
     */
    @Override
    public void everyDay() {

        try{

            logger.debug(" goods job start");
            Thread.sleep(5000);

            this.goodsClient.updateGoodsGrade();
            logger.debug(" goods job end");
        }catch (Exception e) {
            logger.error("计算商品评分出错", e);
        }

    }

}
