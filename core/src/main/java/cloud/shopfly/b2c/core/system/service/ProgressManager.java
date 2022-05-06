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
package cloud.shopfly.b2c.core.system.service;


import cloud.shopfly.b2c.core.system.model.vo.TaskProgress;

/**
 * 进度管理接口
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 下午3:10
 */
public interface ProgressManager {
    /**
     * 获取进度信息
     *
     * @param id 唯一标识
     * @return 进度
     */
    TaskProgress getProgress(String id);

    /**
     * 写入进度
     *
     * @param id
     * @param progress
     */
    void putProgress(String id, TaskProgress progress);

    /**
     * 移除任务
     *
     * @param id 唯一标识
     */
    void remove(String id);

    /**
     * 任务开始
     *
     * @param key   任务id
     * @param count 任务数
     */
    void taskBegin(String key, Integer count);

    /**
     * 任务结束
     *
     * @param key     任务id
     * @param message 消息
     */
    void taskEnd(String key, String message);

    /**
     * 任务异常
     *
     * @param key     任务id
     * @param message 消息
     */
    void taskError(String key, String message);

    /**
     * 更新任务消息
     *
     * @param key
     * @param message
     */
    void taskUpdate(String key, String message);


}
