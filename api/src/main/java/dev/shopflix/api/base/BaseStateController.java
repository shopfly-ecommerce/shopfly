package dev.shopflix.api.base;

import dev.shopflix.framework.util.FileUtil;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统状态心跳功能
 * @Author shen
 * @Date 2021/6/30 15:49
 */

@Api(description = "系统状态心跳功能")
@RestController
@RequestMapping("/webjars/system/base/state")
@Validated
public class BaseStateController {

    @GetMapping
    public String  live() {
        return  FileUtil.readFile("Heartbeat.ftl");
    }
}