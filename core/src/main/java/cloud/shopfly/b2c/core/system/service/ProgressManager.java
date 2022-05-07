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
 * Schedule Management Interface
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 In the afternoon3:10
 */
public interface ProgressManager {
    /**
     * Getting progress information
     *
     * @param id A unique identifier
     * @return The progress of
     */
    TaskProgress getProgress(String id);

    /**
     * Write to schedule
     *
     * @param id
     * @param progress
     */
    void putProgress(String id, TaskProgress progress);

    /**
     * Remove the task
     *
     * @param id A unique identifier
     */
    void remove(String id);

    /**
     * Task start
     *
     * @param key   taskid
     * @param count Number of jobs
     */
    void taskBegin(String key, Integer count);

    /**
     * End of the task
     *
     * @param key     taskid
     * @param message The message
     */
    void taskEnd(String key, String message);

    /**
     * Abnormal task
     *
     * @param key     taskid
     * @param message The message
     */
    void taskError(String key, String message);

    /**
     * Update task Messages
     *
     * @param key
     * @param message
     */
    void taskUpdate(String key, String message);


}
