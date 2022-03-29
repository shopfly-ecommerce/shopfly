/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.pagedata;

import dev.shopflix.core.pagedata.constraint.annotation.ClientAppType;
import dev.shopflix.core.pagedata.constraint.annotation.PageType;
import dev.shopflix.core.pagedata.model.PageData;
import dev.shopflix.core.pagedata.service.PageDataManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 楼层控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 16:39:22
 */
@RestController
@RequestMapping("/pages")
@Api(description = "楼层相关API")
@Validated
public class PageDataBuyerController {

    @Autowired
    private PageDataManager pageManager;

    @GetMapping(value = "/{client_type}/{page_type}")
    @ApiOperation(value = "查询楼层数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_type", value = "要查询的客户端类型 APP/WAP/PC", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "page_type", value = "要查询的页面类型 INDEX 首页/SPECIAL 专题", required = true, dataType = "string", paramType = "path")
    })
    public PageData get(@ClientAppType @PathVariable("client_type") String clientType,@PageType @PathVariable("page_type") String pageType) {

        PageData page = this.pageManager.queryPageData(clientType,pageType);

        return page;
    }

}