/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.system;

import dev.shopflix.core.system.model.dos.Message;
import dev.shopflix.core.system.model.vo.MessageVO;
import dev.shopflix.core.system.service.MessageManager;
import dev.shopflix.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 站内消息控制器
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-04 21:50:52
 */
@RestController
@RequestMapping("/seller/systems/messages")
@Api(description = "站内消息相关API")
public class MessageManagerController {

    @Autowired
    private MessageManager messageManager;


    @ApiOperation(value = "查询站内消息列表", response = Message.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        return this.messageManager.list(pageNo, pageSize);
    }


    @ApiOperation(value = "添加站内消息", response = Message.class)
    @PostMapping
    public Message add(@Valid MessageVO messageVO) {
        return this.messageManager.add(messageVO);
    }

}
