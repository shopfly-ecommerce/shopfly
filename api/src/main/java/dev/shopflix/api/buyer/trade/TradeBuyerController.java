/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.trade;

import dev.shopflix.core.trade.order.model.vo.TradeVO;
import dev.shopflix.core.trade.order.service.TradeManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易控制器
 *
 * @author Snow create in 2018/5/8
 * @version v2.0
 * @since v7.0.0
 */
@Api(description = "交易接口模块")
@RestController
@RequestMapping("/trade")
@Validated
public class TradeBuyerController {

    @Autowired
    @Qualifier("tradeManagerImpl")
    private TradeManager tradeManager;


    @ApiOperation(value = "创建交易")
    @PostMapping(value = "/create")
    @ApiImplicitParam(name = "client", value = "客户端类型", required = false, dataType = "String", paramType = "query", allowableValues = "PC,WAP,NATIVE,REACT")
    public TradeVO create(String client) {
        return this.tradeManager.createTrade(client);
    }


}
