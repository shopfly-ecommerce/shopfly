/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.job.execute.impl;

import com.enation.app.javashop.consumer.job.execute.EveryDayExecute;
import com.enation.app.javashop.core.promotion.tool.model.dos.PromotionGoodsDO;
import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 积分商品定时任务
 * @date 2018/11/7 9:32
 * @since v7.0.0
 */
@Component
public class PointGoodsJob implements EveryDayExecute {

    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;


    @Override
    public void everyDay() {
        //现在添加的积分商品默认是一年的有效期，所以每天检查积分商品是否到了有效期，过了有效期后，将积分商品的时间增加一年

        String sql = "select * from es_promotion_goods where promotion_type = ? and (end_time - ?)/86400<1  ";

        List<PromotionGoodsDO> list = this.daoSupport.queryForList(sql, PromotionGoodsDO.class, PromotionTypeEnum.EXCHANGE.name(), DateUtil.getDateline());
        if(list !=null){
            for(PromotionGoodsDO promotionGoodsDO : list){

                promotionGoodsDO.setEndTime(promotionGoodsDO.getEndTime()+60*60*24*365);
                this.daoSupport.update(promotionGoodsDO,promotionGoodsDO.getPgId());

            }
        }

    }
}
