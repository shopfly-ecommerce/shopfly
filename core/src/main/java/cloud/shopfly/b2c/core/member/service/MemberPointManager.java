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
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;

/**
 * Member Points Management
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-04-3 11:33:56
 */
public interface MemberPointManager {

    /**
     * Member points operation, this method can add points and consumption points at the same time
     * 1、Add integral
     * gadePointType for1则for添加等级积分gadePointfor积分值
     * consumPointType for1则for添加消费积分consumPointfor消费积分值
     * <p>
     * 2、consumption score
     * gadePointType for0则for消费等级积分gadePointfor积分值
     * consumPointType for0则for消费消费积分consumPointfor消费积分值
     * <p>
     * If no operation is performedgadePointType for0 gadePointTypefor1
     * If no operation is performedconsumPoint for0 consumPointTypefor1
     *
     * @param memberPointHistory Member of the integral
     */
    void pointOperation(MemberPointHistory memberPointHistory);


    /**
     * Check whether integral setting is enabled
     *
     * @return Whether open
     */
    boolean checkPointSettingOpen();

}
