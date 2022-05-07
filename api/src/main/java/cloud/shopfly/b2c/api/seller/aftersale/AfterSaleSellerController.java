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
package cloud.shopfly.b2c.api.seller.aftersale;

import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDTO;
import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDetailDTO;
import cloud.shopfly.b2c.core.aftersale.model.vo.AdminRefundApprovalVO;
import cloud.shopfly.b2c.core.aftersale.model.vo.ExportRefundExcelVO;
import cloud.shopfly.b2c.core.aftersale.model.vo.FinanceRefundApprovalVO;
import cloud.shopfly.b2c.core.aftersale.model.vo.RefundQueryParamVO;
import cloud.shopfly.b2c.core.aftersale.service.AfterSaleManager;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description After sales relatedAPI
 * @ClassName AfterSaleSellerController
 * @since v7.0 In the morning9:38 2018/5/10
 */
@Api("After sales relatedAPI")
@RestController
@RequestMapping("/seller/after-sales")
@Validated
public class AfterSaleSellerController {

    @Autowired
    private AfterSaleManager afterSaleManager;

    @ApiOperation(value = "The processing of the refund/Return of the goods", response = AdminRefundApprovalVO.class)
    @PostMapping(value = "/audits/{sn}")
    @ApiImplicitParam(name = "sn", value = "Refund singlesn", required = true, dataType = "String", paramType = "path")
    public AdminRefundApprovalVO audit(@Valid AdminRefundApprovalVO refundApproval, @PathVariable("sn") String sn) {
        refundApproval.setSn(sn);
        afterSaleManager.approval(refundApproval, Permission.ADMIN);
        return refundApproval;
    }

    @ApiOperation(value = "Warehouse operation")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "Refund Slip No.", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "remark", value = "Treasury note", required = false, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/stock-ins/{sn}")
    public String stockIn(@PathVariable("sn") String sn, String remark) {
        afterSaleManager.stockIn(sn, remark);
        return "";
    }

    @ApiOperation(value = "A refund", response = FinanceRefundApprovalVO.class)
    @PostMapping(value = "/refunds/{sn}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "A refund(cargo)Serial number", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "remark", value = "The refund note", required = false, dataType = "String", paramType = "query")
    })
    public String sellerRefund(@Valid FinanceRefundApprovalVO refundApply, @PathVariable("sn") @ApiIgnore String sn) {
        refundApply.setSn(sn);
        this.afterSaleManager.approval(refundApply);
        return "";
    }

    @ApiOperation(value = "To see a refund(cargo)detailed", response = RefundDetailDTO.class)
    @ApiImplicitParam(name = "sn", value = "A refund(cargo)Serial number", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/refunds/{sn}")
    public RefundDetailDTO sellerDetail(@PathVariable("sn") String sn) {

        return this.afterSaleManager.getDetail(sn);
    }

    @ApiOperation(value = "To see a refund(cargo)The list of", response = RefundDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Number of pages", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/refunds")
    public Page sellerDetail(RefundQueryParamVO queryParam, @ApiIgnore @NotNull(message = "The page number cannot be blank") Integer pageNo, @ApiIgnore @NotNull(message = "The number of pages cannot be empty") Integer pageSize) {

        queryParam.setPageNo(pageNo);
        queryParam.setPageSize(pageSize);
        return this.afterSaleManager.query(queryParam);
    }

    @ApiOperation(value = "Refund Receipt Exportexcel",response = ExportRefundExcelVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start_time" , value = "The start time" , required = true , dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time" , value = "The end of time" , required = true , dataType = "long", paramType = "query")
    })
    @GetMapping(value = "/exports/excel")
    public List<ExportRefundExcelVO> exportExcel(@ApiIgnore @NotNull(message = "The start time cannot be empty") long startTime, @ApiIgnore @NotNull(message = "The end time cannot be empty") long endTime){

        return afterSaleManager.exportExcel(startTime,endTime);
    }
}
