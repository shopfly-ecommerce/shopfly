/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.system;

import com.enation.app.javashop.core.system.model.dos.SmtpDO;
import com.enation.app.javashop.core.system.service.SmtpManager;
import com.enation.app.javashop.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 邮件控制器
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-25 16:16:53
 */
@RestController
@RequestMapping("/seller/systems/smtps")
@Api(description = "邮件相关API")
public class SmtpSellerController {

    @Autowired
    private SmtpManager smtpManager;


    @ApiOperation(value = "查询邮件列表", response = SmtpDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore @NotEmpty(message = "页码不能为空") Integer pageNo, @ApiIgnore @NotEmpty(message = "每页数量不能为空") Integer pageSize) {
        return this.smtpManager.list(pageNo, pageSize);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改smtp", response = SmtpDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public SmtpDO edit(@Valid SmtpDO smtp, @PathVariable Integer id) {

        this.smtpManager.edit(smtp, id);

        return smtp;
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个smtp")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的smtp主键", required = true, dataType = "int", paramType = "path")
    })
    public SmtpDO get(@PathVariable Integer id) {
        SmtpDO smtp = this.smtpManager.getModel(id);
        return smtp;
    }

    @ApiOperation(value = "添加smtp", response = SmtpDO.class)
    @PostMapping
    public SmtpDO add(@Valid SmtpDO smtp) {
        this.smtpManager.add(smtp);
        return smtp;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除smtp")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的smtp主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        this.smtpManager.delete(id);
        return null;
    }

    @PostMapping(value = "/send")
    @ApiOperation(value = "测试发送邮件")
    @ApiImplicitParam(name = "email", value = "要发送的邮箱地址", required = true, dataType = "String", paramType = "query")
    public String send(@Valid String email, SmtpDO smtp) {
        this.smtpManager.send(email, smtp);
        return null;
    }

}
