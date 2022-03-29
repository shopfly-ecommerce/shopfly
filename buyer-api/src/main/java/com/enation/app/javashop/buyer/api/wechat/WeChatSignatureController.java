/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer.api.wechat;

import com.enation.app.javashop.core.payment.plugin.weixin.signaturer.WeixinSignaturer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 微信签名控制器
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-20 下午4:33
 */
@Api(description = "微信签名工具")
@RestController
@RequestMapping("/wechat")
@Validated
public class WeChatSignatureController {

    @Autowired
    private WeixinSignaturer weixinSignaturer;

    @ApiOperation(value = "小程序签名")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "WAP/REACT/NATIVE/MINI 分别为 WAP/原生app/h5app/小程序"),

    })
    @GetMapping
    public Map miniProgram(String type, String url) {
        return weixinSignaturer.signature(type, url);
    }

}
