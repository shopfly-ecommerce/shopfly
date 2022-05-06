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
package cloud.shopfly.b2c.framework.trigger.Interface;

/**
 * 延时执行接口
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/13 下午8:13
 */
public interface TimeTrigger {

    /**
     * 添加延时任务
     *
     * @param executerName 执行器beanid
     * @param param        执行参数
     * @param triggerTime  执行时间 时间戳 秒为单位
     * @param uniqueKey    如果是一个 需要有 修改/取消 延时任务功能的延时任务，<br/>
     *                     请填写此参数，作为后续删除，修改做为唯一凭证 <br/>
     *                     建议参数为：PINTUAZN_{ACTIVITY_ID} 例如 pintuan_123<br/>
     *                     业务内全局唯一
     */
    void add(String executerName, Object param, Long triggerTime, String uniqueKey);

    /**
     * 修改延时任务
     *
     * @param executerName   执行器beanid
     * @param param          执行参数
     * @param triggerTime    执行时间 时间戳 秒为单位
     * @param oldTriggerTime 旧的任务执行时间
     * @param uniqueKey      添加任务时的唯一凭证
     */
    void edit(String executerName, Object param, Long oldTriggerTime, Long triggerTime, String uniqueKey);

    /**
     * 删除延时任务
     *
     * @param executerName 执行器
     * @param triggerTime  执行时间
     * @param uniqueKey    添加任务时的唯一凭证
     */
    void delete(String executerName, Long triggerTime, String uniqueKey);
}
