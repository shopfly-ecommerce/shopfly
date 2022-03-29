/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.aftersale.model.vo;

import com.enation.app.javashop.core.aftersale.model.enums.RefundOperateEnum;
import com.enation.app.javashop.core.aftersale.model.enums.RefundStatusEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @since v7.0 上午11:20 2018/5/2
 */
public class RefundStepVO implements Serializable {

    private RefundStatusEnum status;

    private List<RefundOperateEnum> allowableOperate;


    public RefundStepVO(RefundStatusEnum status, RefundOperateEnum... operates) {
        this.status = status;
        this.allowableOperate = new ArrayList<>();
        for (RefundOperateEnum refundOperate : operates) {
            allowableOperate.add(refundOperate);
        }

    }

    /**
     * 检测操作是否在步骤中
     *
     * @param operate
     * @return
     */
    public boolean checkAllowable(RefundOperateEnum operate) {
        for (RefundOperateEnum orderOperate : allowableOperate) {
            if (operate.compareTo(orderOperate) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "RefundStepVO{" +
                "status=" + status +
                ", allowableOperate=" + allowableOperate +
                '}';
    }
}
