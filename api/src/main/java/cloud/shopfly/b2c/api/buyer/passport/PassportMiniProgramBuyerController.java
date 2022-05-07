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
package cloud.shopfly.b2c.api.buyer.passport;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.plugin.wechat.WechatAbstractConnectLoginPlugin;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.framework.cache.Cache;

import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.validation.annotation.Mobile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Pattern;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: Applets login interface
 * @date 2018/11/20 14:56
 * @since v7.0.0
 */
@RestController
@RequestMapping("/passport/mini-program")
@Api(description = "Applets loginapi")
@Validated
public class PassportMiniProgramBuyerController {

    @Autowired
    public WechatAbstractConnectLoginPlugin wechatAbstractConnectLoginPlugin;

    @Autowired
    public ConnectManager connectManager;

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private Cache cache;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());



    @GetMapping("/auto-login")
    @ApiOperation(value = "Applet automatic login")
    public Map autoLogin(String code, String uuid) {

        // Obtain sessionKey and OpenID or UnionID
        String content = wechatAbstractConnectLoginPlugin.miniProgramAutoLogin(code);

        return this.connectManager.miniProgramLogin(content, uuid);
    }

    @GetMapping("/decrypt")
    @ApiOperation(value = "Decrypt and verify encrypted data")
    public Map decrypt(String code, String encryptedData, String uuid, String iv) {

        return connectManager.decrypt(code, encryptedData, uuid, iv);
    }


    @GetMapping("/code-unlimit")
    @ApiOperation(value = "Get wechat small program code")
    @ApiImplicitParam(name = "goods_id", value = "productid", required = true, dataType = "int", paramType = "query")
    public String getWXACodeUnlimit(@ApiIgnore int goodsId) {

        String accessTocken = wechatAbstractConnectLoginPlugin.getWXAccessTocken();

        return connectManager.getWXACodeUnlimit(accessTocken, goodsId);
    }

    @PostMapping("/distribution")
    @ApiOperation(value = "Store applet side distribution superiorid")
    @ApiImplicitParam(name = "from", value = "The superior memberidEncryption format", required = true, dataType = "String",dataTypeClass = String.class, paramType = "query")
    public String distribution(String from, @RequestHeader(required = false) String uuid) {
        if (logger.isDebugEnabled()) {
            logger.debug("==============The cache from the foregroundkey:" + from);
            logger.debug("==============It came from the front deskuuid:" + uuid);
        }

        if (StringUtil.notEmpty(uuid) && StringUtil.notEmpty(from)) {
            try {
                // Get the membership ID of the distribution partner from the cache
                Object memberId = cache.get(CachePrefix.MEMBER_SU.getPrefix() + from);
                if (logger.isDebugEnabled()) {
                    logger.debug("==============Membership retrieved from the cacheIDfor:" + memberId);
                }
                // If the member ID is not empty
                if (memberId != null) {
                    // Use the UUID as the key and store the member ID in the cache again
                    cache.put(CachePrefix.DISTRIBUTION_UP.getPrefix() + uuid, memberId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        return "";
    }

    @ApiOperation(value = "Applets register bindings")
    @PostMapping("/register-bind/{uuid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "A unique identifier", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "nick_name", value = "nickname", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "face", value = "Head portrait", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "gender", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "Mobile phone number", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "Password", required = true, dataType = "String", paramType = "query"),

    })
    public Map binder(@PathVariable("uuid") String uuid, @Length(max = 20, message = "The nickname exceeds the length limit") @ApiIgnore String nickName, String face, Integer sex, @Mobile String mobile, @Pattern(regexp = "[a-fA-F0-9]{32}", message = "The password format is incorrect") String password) {
        // Perform registration
        Member member = new Member();
        member.setUname("m_" + mobile);
        member.setMobile(mobile);
        member.setPassword(password);
        member.setNickname(nickName);
        member.setFace(face);
        member.setSex(sex);
        memberManager.register(member);
        // Executing the Binding account
        Map map = connectManager.mobileBind(mobile, uuid);
        return map;
    }


}
