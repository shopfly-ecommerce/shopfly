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
package cloud.shopfly.b2c.api.seller.member;

import cloud.shopfly.b2c.core.member.model.dos.ConnectSettingDO;
import cloud.shopfly.b2c.core.member.model.dto.ConnectSettingDTO;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingVO;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录设置
 * @ClassName ConnectController
 * @since v7.0 下午4:23 2018/6/15
 */
@Api(description="信任登录设置api")
@RestController
@RequestMapping("/seller/members/connect")
public class MemberConnectSellerController {

    @Autowired
    private ConnectManager connectManager;



    @GetMapping()
    @ApiOperation(value = "获取信任登录配置参数",response = ConnectSettingDO.class)
    public List<ConnectSettingVO> list(){
        return  connectManager.list();
    }

    @PutMapping(value = "/{type}")
    @ApiOperation(value = "修改信任登录参数", response = ConnectSettingDTO.class)
    @ApiImplicitParam(name = "type", value = "用户名", required = true, dataType = "String", paramType = "path",allowableValues = "QQ,ALIPAY,WEIBO,WECHAT")
    public ConnectSettingDTO editConnectSetting(@RequestBody ConnectSettingDTO connectSettingDTO, @PathVariable("type")  String type) {
        connectManager.save(connectSettingDTO);
        return connectSettingDTO;
    }


}
