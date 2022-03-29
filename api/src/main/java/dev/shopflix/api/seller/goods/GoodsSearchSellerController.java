/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.goods;

import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.system.model.TaskProgressConstant;
import dev.shopflix.core.system.service.ProgressManager;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品全文检索
 * @date 2018/6/1915:55
 * @since v7.0.0
 */
@RestController
@RequestMapping("/seller/goods/search")
@Api(description = "商品检索相关API")
public class GoodsSearchSellerController {

    @Autowired
    private ProgressManager progressManager;
    @Autowired
    private MessageSender messageSender;

    @GetMapping
    @ApiOperation(value = "商品索引初始化")
    public String create(){

        if (progressManager.getProgress(TaskProgressConstant.GOODS_INDEX) != null) {
            throw new ResourceNotFoundException("有索引任务正在进行中，需等待本次任务完成后才能再次生成。");
        }
        /** 发送索引生成消息 */
        this.messageSender.send(new MqMessage(AmqpExchange.INDEX_CREATE, AmqpExchange.INDEX_CREATE+"_ROUTING","1"));

        return TaskProgressConstant.GOODS_INDEX;
    }
}
