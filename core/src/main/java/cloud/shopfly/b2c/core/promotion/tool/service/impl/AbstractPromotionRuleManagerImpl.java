/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.tool.service.impl;

import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动规则检测
 *
 * @author Snow create in 2018/4/25
 * @version v2.0
 * @since v7.0.0
 */
@Service
public abstract class AbstractPromotionRuleManagerImpl {

    @Autowired
    
    private DaoSupport daoSupport;





    /**
     * 检测活动与活动之间的规则冲突
     *
     * @param goodsDTOList 活动商品
     */
    protected void verifyRule(List<PromotionGoodsDTO> goodsDTOList) {

        if (goodsDTOList == null || goodsDTOList.isEmpty()) {
            throw new ServiceException(PromotionErrorCode.E401.code(), "没有可用的商品");
        }
    }

    /**
     * 验证活动名称重名
     * @param name  名称
     * @param isUpdate  是否修改
     * @param activeId  修改时需要填充活动id
     */
    protected void verifyName(String name,boolean isUpdate,Integer activeId) {

        if(isUpdate){
            //判断活动重名
            if (this.daoSupport.queryForInt("select count(0) from es_groupbuy_active where act_name=? and act_id != ?", name,activeId) > 0) {
                throw new ServiceException(PromotionErrorCode.E402.code(), "当前活动重名，请修正");
            }
        }else {
            //判断活动重名
            if (this.daoSupport.queryForInt("select count(0) from es_groupbuy_active where act_name=?", name) > 0) {
                throw new ServiceException(PromotionErrorCode.E402.code(), "当前活动重名，请修正");
            }
        }
    }

    /**
     * 验证活动时间
     * 同一时间只能有一个活动生效
     *
     * @param startTime
     * @param endTime
     */
    protected void verifyTime(long startTime, long endTime, PromotionTypeEnum typeEnum, Integer activityId) {

        //（新添活动起始时间大于之前活动的起始时间小于之前活动的截止时间）or （新添活动结束时间大于之前活动的起始时间小于之前活动的截止时间）
        String sql = "";
        List params = new ArrayList();

        switch (typeEnum) {
            case HALF_PRICE:
                sql = "select count(0) from es_half_price where ((start_time <= ? and ? <= end_time ) or (start_time <= ? and ? <= end_time ))";
                params.add(startTime);
                params.add(startTime);
                params.add(endTime);
                params.add(endTime);
                if (activityId != null) {
                    sql += " and hp_id != ?";
                    params.add(activityId);
                }
                break;

            case MINUS:
                sql = "select count(0) from es_minus where ((start_time <= ? and ? <= end_time ) or (start_time <= ? and ? <= end_time ))";
                params.add(startTime);
                params.add(startTime);
                params.add(endTime);
                params.add(endTime);
                if (activityId != null) {
                    sql += " and minus_id != ?";
                    params.add(activityId);
                }
                break;

            case FULL_DISCOUNT:
                sql = "select count(0) from es_full_discount where ((start_time <= ? and ? <= end_time ) or (start_time <= ? and ? <= end_time ))";
                params.add(startTime);
                params.add(startTime);
                params.add(endTime);
                params.add(endTime);
                if (activityId != null) {
                    sql += " and fd_id != ?";
                    params.add(activityId);
                }
                break;

            case GROUPBUY:
                sql = "select count(0) from es_groupbuy_active where ((start_time <= ? and ? <= end_time ) or (start_time <= ? and ? <= end_time ))";
                params.add(startTime);
                params.add(startTime);
                params.add(endTime);
                params.add(endTime);
                if (activityId != null) {
                    sql += " and act_id != ?";
                    params.add(activityId);
                }
                break;

            case SECKILL:
                sql = "select count(0) from es_seckill  where ((start_day <= ? and ? <= start_day ))";
                params.add(startTime);
                params.add(startTime);
                if (activityId != null) {
                    sql += " and seckill_id != ?";
                    params.add(activityId);
                }
                break;

            case NO:
                break;

            default:
                break;
        }

        if (!StringUtil.isEmpty(sql)) {
            int num = this.daoSupport.queryForInt(sql, params.toArray());
            if (num > 0) {
                throw new ServiceException(PromotionErrorCode.E402.code(), "当前时间内已存在此类活动");
            }
        }

    }

}
