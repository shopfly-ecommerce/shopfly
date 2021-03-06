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
package cloud.shopfly.b2c.core.system.service.impl;

import cloud.shopfly.b2c.core.system.model.enums.ProgressEnum;
import cloud.shopfly.b2c.core.system.model.vo.TaskProgress;
import cloud.shopfly.b2c.core.system.service.ProgressManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of Schedule Management
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 In the afternoon3:10
 */
@Component
public class ProgressManagerImpl implements ProgressManager {

    @Autowired
    private Cache cache;

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Override
    public TaskProgress getProgress(String id) {
        id = TaskProgress.PROCESS + id;
        return (TaskProgress) cache.get(id);

    }

    @Override
    public void putProgress(String id, TaskProgress progress) {
        id = TaskProgress.PROCESS + id;
        progress.setId(id);
        cache.put(id, progress,100);


    }

    @Override
    public void remove(String id) {
        id = TaskProgress.PROCESS + id;
        cache.remove(id);

    }

    @Override
    public void taskBegin(String key, Integer count) {
        try {
            // A new task
            TaskProgress tk = new TaskProgress(count);
            // Storage tasks
            this.putProgress(key, tk);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Failed to create a task" + e);
            }

        }
    }

    @Override
    public void taskEnd(String key, String message) {
        try {
            // Access to task
            TaskProgress tk = this.getProgress(key);
            if (tk != null) {
                tk.step(message);
                tk.success();
                // Update progress is put back into the cache
                this.putProgress(key, tk);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("End of Task Exception" + e);
            }

        }
    }

    @Override
    public void taskError(String key, String message) {
        try {
            TaskProgress tk = this.getProgress(key);
            if (tk != null) {
                tk.setTaskStatus(ProgressEnum.EXCEPTION.name());
                tk.setMessage(message);
                this.putProgress(key, tk);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Abnormal task" + e);
            }
        }
    }

    @Override
    public void taskUpdate(String key, String message) {
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("key is "+ key);

            }
            TaskProgress tk = this.getProgress(key);
            if (tk != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("tk is ");
                    logger.debug(tk);
                }
                tk.step(message);
                this.putProgress(key, tk);
            }else {
                if (logger.isDebugEnabled()) {
                    logger.debug("tk  is null");
                    logger.debug(tk);
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Update task failed" + e);
            }

        }
    }
}
