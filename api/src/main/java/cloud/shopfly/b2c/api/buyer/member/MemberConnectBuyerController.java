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
package cloud.shopfly.b2c.api.buyer.member;

import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.vo.ConnectVO;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.member.service.impl.AbstractConnectLoginPlugin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description 会员信任登录API
 * @ClassName MemberConnectController
 * @since v7.0 上午10:26 2018/6/14
 */
@Api(description = "会员信任登录API")
@RestController
@RequestMapping("/account-binder")
public class MemberConnectBuyerController {

    @Autowired
    private ConnectManager connectManager;


    @GetMapping("/pc/{type}")
    @ApiOperation(value = "发起账号绑定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "登录方式:QQ,微博,微信,支付宝", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY", paramType = "path")
    })
    public String initiate(@PathVariable("type") @ApiIgnore String type) throws IOException {
        ConnectTypeEnum connectTypeEnum = ConnectTypeEnum.valueOf(type);
        AbstractConnectLoginPlugin connectionLogin = connectManager.getConnectionLogin(connectTypeEnum);
        return connectionLogin.getLoginUrl();
    }

    @ApiOperation(value = "会员解绑操作")
    @PostMapping("/unbind/{type}")
    @ApiImplicitParam(name = "type", value = "登录方式:QQ,微博,微信,支付宝", allowableValues = "QQ,WEIBO,WECHAT,ALIPAY", paramType = "path")
    public void unbind(@PathVariable("type") String type) {
        connectManager.unbind(type);
    }

    @ApiOperation(value = "微信退出解绑操作")
    @PostMapping("/unbind/out")
    public void wechatOut() {
        connectManager.wechatOut();
    }


    @ApiOperation(value = "登录绑定openid")
    @PostMapping("/login/{uuid}")
    @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "path")
    public Map openidBind(@PathVariable("uuid") @ApiIgnore String uuid) {
        return connectManager.openidBind(uuid);
    }

    @ApiOperation(value = "注册绑定openid")
    @PostMapping("/register/{uuid}")
    @ApiImplicitParam(name = "uuid", value = "客户端唯一标识", required = true, dataType = "String", paramType = "path")
    public void registerBind(@PathVariable("uuid") @ApiIgnore String uuid) {
        connectManager.registerBind(uuid);
    }

    @ApiOperation(value = "获取绑定列表API")
    @GetMapping("/list")
    public List<ConnectVO> get() {
        return connectManager.get();
    }

}
