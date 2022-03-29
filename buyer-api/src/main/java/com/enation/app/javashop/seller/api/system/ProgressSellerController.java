/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.system;

import com.enation.app.javashop.core.system.model.enums.ProgressEnum;
import com.enation.app.javashop.core.system.model.vo.ProgressVo;
import com.enation.app.javashop.core.system.model.vo.TaskProgress;
import com.enation.app.javashop.core.system.service.ProgressManager;
import com.enation.app.javashop.framework.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 进度控制器
 *
 * @author kingapex
 * @version v1.0
 * @since v1.0
 * 2015年5月13日 下午5:57:48
 */
@RestController
@RequestMapping("/seller/task")
@Api(description = "进度控制器")
public class ProgressSellerController {

    @Autowired
    private ProgressManager progressManager;

    @ApiOperation("检测是否有任务正在进行,有任务返回任务id,无任务返回404")
    @ApiImplicitParam(name = "task_id", value = "任务id", dataType = "String", paramType = "path", required = true, example = "page_create")
    @GetMapping(value = "/{task_id}")
    public String hasTask(@PathVariable("task_id") String taskId) {

        /** 如果redis中有此id 视为有任务进行 */
        if (progressManager.getProgress(taskId) != null) {
            return taskId;
        }

        throw new ResourceNotFoundException("进度不存在");
    }


    @ApiOperation("查看任务进度")
    @ApiImplicitParam(name = "task_id", value = "任务id", dataType = "String", paramType = "path", required = true, example = "page_create")
    @GetMapping(value = "/{task_id}/progress")
    public ProgressVo viewProgress(@PathVariable("task_id") String taskId) {

        TaskProgress taskProgress = progressManager.getProgress(taskId);
        if (taskProgress == null) {
            return new ProgressVo(100, ProgressEnum.SUCCESS.name());
        }
        /** 如果是完成或者出错 需要移除任务 */
        if (!taskProgress.getTaskStatus().equals(ProgressEnum.DOING.name())) {
            progressManager.remove(taskId);
        }
        return new ProgressVo(taskProgress);


    }

    @ApiOperation("清除某任务")
    @ApiImplicitParam(name = "task_id", value = "任务id", dataType = "String", paramType = "path", required = true, example = "page_create")
    @DeleteMapping(value = "/{task_id}")
    public String clear(@PathVariable("task_id") String taskId) {
        progressManager.remove(taskId);
        return null;
    }
}
