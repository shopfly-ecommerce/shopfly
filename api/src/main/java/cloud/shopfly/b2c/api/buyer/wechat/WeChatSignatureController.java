/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.api.buyer.wechat;

import cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer.WeixinSignaturer;
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
 * Wechat signature controller
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-20 In the afternoon4:33
 */
@Api(description = "Wechat signature tool")
@RestController
@RequestMapping("/wechat")
@Validated
public class WeChatSignatureController {

    @Autowired
    private WeixinSignaturer weixinSignaturer;

    @ApiOperation(value = "Applets signature")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "WAP/REACT/NATIVE/MINI Respectively,WAP/nativeapp/h5app/Small program"),

    })
    @GetMapping
    public Map miniProgram(String type, String url) {
        return weixinSignaturer.signature(type, url);
    }

}
