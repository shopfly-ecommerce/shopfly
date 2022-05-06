/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.buyer.sss;

import cloud.shopfly.b2c.core.statistics.service.DisplayTimesManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问次数统计
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/7 上午8:17
 */

@RestController
@RequestMapping("/view")
@Api(description = "访问次数统计")
public class DisplayTimesBuyerController {

    @Autowired
    private DisplayTimesManager displayTimesManager;

    @GetMapping()
    @ApiOperation(value = "访问页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "url地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "query")
    })
    public void view(String url, String uuid) {
        displayTimesManager.view(url, uuid);
    }


}