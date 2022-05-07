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

import cloud.shopfly.b2c.core.member.model.vo.ReceiptHistoryVO;
import cloud.shopfly.b2c.core.member.service.ReceiptHistoryManager;
import cloud.shopfly.b2c.framework.database.Page;
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
 * History of invoice
 *
 * @author Snow create in 2018/6/27
 * @version v2.0
 * @since v7.0.0
 */

@Api(description = "History of invoiceAPI")
@RestController
@RequestMapping("/seller/members/receipts")
@Validated
public class ReceiptSellerController {

    @Autowired
    private ReceiptHistoryManager receiptHistoryManager;

    @ApiOperation(value = "Query the list of historical invoices")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "Number of pages", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "A number of", dataType = "int", paramType = "query"),
    })
    @GetMapping()
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        Page page = this.receiptHistoryManager.list(pageNo, pageSize);

        return page;
    }

    @ApiOperation(value = "Inquire historical invoice details")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "history_id", value = "History invoice primary key", dataType = "int", paramType = "path")
    })
    @GetMapping("/{history_id}/detail")
    public ReceiptHistoryVO detail(@PathVariable("history_id") Integer historyId) {
        return this.receiptHistoryManager.getReceiptDetail(historyId);
    }
}
