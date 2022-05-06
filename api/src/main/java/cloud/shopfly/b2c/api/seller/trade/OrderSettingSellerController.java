/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.trade;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSettingVO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 订单设置相关API
 * @author Snow create in 2018/7/13
 * @version v2.0
 * @since v7.0.0
 */
@Api(description = "订单设置相关API")
@RestController
@RequestMapping("/seller/trade/orders")
@Validated
public class OrderSettingSellerController {


    @Autowired
    private SettingClient settingClient;

    @GetMapping("/setting")
    @ApiOperation(value = "获取订单任务设置信息")
    public OrderSettingVO getOrderSetting(){

        String json = this.settingClient.get(SettingGroup.TRADE);

        return JsonUtil.jsonToObject(json,OrderSettingVO.class);
    }

    @PostMapping("/setting")
    @ApiOperation(value = "保存订单任务设置信息")
    public OrderSettingVO save(@Valid OrderSettingVO setting){

        System.out.println(setting.toString());
        this.settingClient.save(SettingGroup.TRADE,setting);
        return setting;
    }

}
