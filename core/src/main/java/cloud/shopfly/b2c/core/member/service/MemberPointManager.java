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
 * 会员积分管理
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-04-3 11:33:56
 */
public interface MemberPointManager {

    /**
     * 会员积分操作，这个方法可同时进行添加积分和消费积分
     * 1、添加积分
     * gadePointType 为1则为添加等级积分  gadePoint为积分值
     * consumPointType 为1则为添加消费积分  consumPoint为消费积分值
     * <p>
     * 2、消费积分
     * gadePointType 为0则为消费等级积分  gadePoint为积分值
     * consumPointType 为0则为消费消费积分  consumPoint为消费积分值
     * <p>
     * 如果没有操作则gadePointType 为0 gadePointType为1
     * 如果没有操作则consumPoint 为0 consumPointType为1
     *
     * @param memberPointHistory 会员积分
     */
    void pointOperation(MemberPointHistory memberPointHistory);


    /**
     * 检测积分设置是否开启
     *
     * @return 是否开启
     */
    boolean checkPointSettingOpen();

}
