/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.goods;

import dev.shopflix.core.base.SettingGroup;
import dev.shopflix.core.client.system.SettingClient;
import dev.shopflix.core.goods.model.dto.GoodsSettingVO;
import dev.shopflix.framework.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author fk
 * @version v1.0
 * @Description: 商品设置控制器
 * @date 2018/5/25 10:31
 * @since v7.0.0
 */
@RestController
@RequestMapping("/seller/goods/settings")
@Api(description = "商品设置API")
public class GoodsSettingSellerController {

    @Autowired
    private SettingClient settingClient;

    @GetMapping
    @ApiOperation(value = "获取商品审核设置信息")
    public GoodsSettingVO getGoodsSetting(){

        String json = this.settingClient.get(SettingGroup.GOODS);
        return JsonUtil.jsonToObject(json, GoodsSettingVO.class);
    }

    @PostMapping
    @ApiOperation(value = "保存商品审核设置信息")
    public GoodsSettingVO save(@Valid GoodsSettingVO goodsSetting){

        this.settingClient.save(SettingGroup.GOODS,goodsSetting);

        return goodsSetting;
    }

}
