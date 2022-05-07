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
package cloud.shopfly.b2c.api.seller.system;

import cloud.shopfly.b2c.core.system.model.enums.ProgressEnum;
import cloud.shopfly.b2c.core.system.model.vo.ProgressVo;
import cloud.shopfly.b2c.core.system.model.vo.TaskProgress;
import cloud.shopfly.b2c.core.system.service.ProgressManager;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Schedule controller
 *
 * @author kingapex
 * @version v1.0
 * @since v1.0
 * 2015years5month13On the afternoon5:57:48
 */
@RestController
@RequestMapping("/seller/task")
@Api(description = "Schedule controller")
public class ProgressSellerController {

    @Autowired
    private ProgressManager progressManager;

    @ApiOperation("Check whether there are tasks in progress,There is a task return taskid,No Task return404")
    @ApiImplicitParam(name = "task_id", value = "taskid", dataType = "String", paramType = "path", required = true, example = "page_create")
    @GetMapping(value = "/{task_id}")
    public String hasTask(@PathVariable("task_id") String taskId) {

        /** ifredisIn theid Deemed to be on a mission*/
        if (progressManager.getProgress(taskId) != null) {
            return taskId;
        }

        throw new ResourceNotFoundException("Progress does not exist");
    }


    @ApiOperation("Viewing the Task Progress")
    @ApiImplicitParam(name = "task_id", value = "taskid", dataType = "String", paramType = "path", required = true, example = "page_create")
    @GetMapping(value = "/{task_id}/progress")
    public ProgressVo viewProgress(@PathVariable("task_id") String taskId) {

        TaskProgress taskProgress = progressManager.getProgress(taskId);
        if (taskProgress == null) {
            return new ProgressVo(100, ProgressEnum.SUCCESS.name());
        }
        /** Remove the task if it is complete or faulty*/
        if (!taskProgress.getTaskStatus().equals(ProgressEnum.DOING.name())) {
            progressManager.remove(taskId);
        }
        return new ProgressVo(taskProgress);


    }

    @ApiOperation("Clearing a Task")
    @ApiImplicitParam(name = "task_id", value = "taskid", dataType = "String", paramType = "path", required = true, example = "page_create")
    @DeleteMapping(value = "/{task_id}")
    public String clear(@PathVariable("task_id") String taskId) {
        progressManager.remove(taskId);
        return null;
    }
}
