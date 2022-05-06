/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.aftersale.model.vo;

import cloud.shopfly.b2c.core.aftersale.model.enums.RefundOperateEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;

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
