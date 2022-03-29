/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.distribution;

import com.enation.app.javashop.core.distribution.model.dos.BillTotalDO;
import com.enation.app.javashop.core.distribution.service.BillTotalManager;
import com.enation.app.javashop.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 分销商总结算单
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/24 下午2:47
 */
@Api(description = "分销商总结算单")
@RestController
@RequestMapping("/seller/distribution/bill/total")
public class BillTotalSellerController {

    @Autowired
    private BillTotalManager billTotalManager;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_size", value = "页码大小", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, paramType = "query", dataType = "int", allowMultiple = false),
    })
    @GetMapping
    @ApiOperation("结算单分页")
    public Page<BillTotalDO> page(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return billTotalManager.page(pageNo, pageSize);
    }



}
