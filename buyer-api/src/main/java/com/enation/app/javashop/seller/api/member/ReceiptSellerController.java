/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.member;

import com.enation.app.javashop.core.member.model.vo.ReceiptHistoryVO;
import com.enation.app.javashop.core.member.service.ReceiptHistoryManager;
import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.security.model.Seller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 历史发票
 *
 * @author Snow create in 2018/6/27
 * @version v2.0
 * @since v7.0.0
 */

@Api(description = "历史发票API")
@RestController
@RequestMapping("/seller/members/receipts")
@Validated
public class ReceiptSellerController {

    @Autowired
    private ReceiptHistoryManager receiptHistoryManager;

    @ApiOperation(value = "查询历史发票列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "条数", dataType = "int", paramType = "query"),
    })
    @GetMapping()
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        Page page = this.receiptHistoryManager.list(pageNo, pageSize);

        return page;
    }

    @ApiOperation(value = "查询历史发票详细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "history_id", value = "历史发票主键", dataType = "int", paramType = "path")
    })
    @GetMapping("/{history_id}/detail")
    public ReceiptHistoryVO detail(@PathVariable("history_id") Integer historyId) {
        return this.receiptHistoryManager.getReceiptDetail(historyId);
    }
}
