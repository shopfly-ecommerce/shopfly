/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.job.execute.impl;

import dev.shopflix.consumer.job.execute.EveryDayExecute;
import com.enation.app.javashop.core.client.goods.GoodsClient;
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
            this.goodsClient.updateGoodsGrade();
        }catch (Exception e) {
            logger.error("计算商品评分出错", e);
        }

    }

}
