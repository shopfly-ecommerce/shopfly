/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.member;

import dev.shopflix.core.member.model.dos.ConnectSettingDO;
import dev.shopflix.core.member.model.dto.ConnectSettingDTO;
import dev.shopflix.core.member.model.vo.ConnectSettingVO;
import dev.shopflix.core.member.service.ConnectManager;
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
