/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.tool.support;

import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.util.DateUtil;

import java.util.List;

/**
 * 参数验证
 * @author Snow create in 2018/3/30
 * @version v2.0
 * @since v7.0.0
 */
public class PromotionValid {


    /**
     * 参数验证
     * 1、活动起始时间必须大于当前时间
     * 2、验证活动开始时间是否大于活动结束时间
     *
     * 无返回值，如有错误直接抛异常
     * @param startTime 活动开始时间
     * @param endTime   活动结束时间
     * @param rangeType 是否全部商品参与
     * @param goodsList 选择的商品
     *
     */
    public static void paramValid(Long startTime, Long endTime, int rangeType, List<PromotionGoodsDTO> goodsList){

        long nowTime  = DateUtil.getDateline();

        //如果活动起始时间小于现在时间
        if(startTime.longValue() < nowTime){
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"活动起始时间必须大于当前时间");
        }

        // 开始时间不能大于结束时间
        if (startTime.longValue() > endTime.longValue() ) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"活动起始时间不能大于活动结束时间");
        }

        //部分商品
        int part = 2;

        // 如果促销活动选择的是部分商品参加活动
        if (rangeType == part) {
            // 商品id组不能为空
            if (goodsList == null) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"请选择要参与活动的商品");
            }
        }
    }


}
