/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.system;

import dev.shopflix.core.system.model.dos.MessageTemplateDO;
import dev.shopflix.core.system.model.dto.MessageTemplateDTO;
import dev.shopflix.core.system.service.MessageTemplateManager;
import dev.shopflix.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 消息模版控制器
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 16:38:43
 */
@RestController
@RequestMapping("/seller/systems/message-templates")
@Api(description = "消息模版相关API")
@Validated
public class MessageTemplateSellerController {

    @Autowired
    private MessageTemplateManager messageTemplateManager;


    @ApiOperation(value = "查询消息模版列表", response = MessageTemplateDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "消息类型", required = true, dataType = "String", paramType = "query", allowableValues = "SHOP,MEMBER")
    })
    @GetMapping
    public Page list(@ApiIgnore @NotNull(message = "页码不能为空") Integer pageNo, @ApiIgnore @NotNull(message = "每页数量不能为空") Integer pageSize, @NotEmpty(message = "模版类型必填") String type) {

        return this.messageTemplateManager.list(pageNo, pageSize, type);
    }


    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改消息模版", response = MessageTemplateDO.class)
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    public MessageTemplateDO edit(@Valid MessageTemplateDTO messageTemplate, @PathVariable("id") Integer id) {
        return this.messageTemplateManager.edit(messageTemplate, id);
    }

}
