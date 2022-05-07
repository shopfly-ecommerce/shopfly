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
package cloud.shopfly.b2c.api.seller.distribution;

import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.model.vo.WithdrawApplyVO;
import cloud.shopfly.b2c.core.distribution.service.WithdrawManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Distribution withdrawal controller
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/24 In the afternoon5:11
 */
@Api(description = "Distribution withdrawal controller")
@RestController
@RequestMapping("/seller/distribution/withdraw")
public class WithdrawSellerController {
    @Autowired
    private WithdrawManager withdrawManager;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @ApiOperation("Check list of withdrawal application")
    @GetMapping(value = "/apply")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_size", value = "Page size", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_no", value = "The page number", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "uname", value = "Member name", required = false, paramType = "query", dataType = "String", allowMultiple = false),
            @ApiImplicitParam(name = "end_time", value = "The end of time", required = false, paramType = "query", dataType = "String", allowMultiple = false),
            @ApiImplicitParam(name = "start_time", value = "The start time", required = false, paramType = "query", dataType = "String", allowMultiple = false),
            @ApiImplicitParam(name = "status", value = "State all, do not pass parametersAPPLY:In the application/VIA_AUDITING:Review the success/FAIL_AUDITING:Audit failure/RANSFER_ACCOUNTS:Have transfer", required = false, paramType = "query", dataType = "String", allowMultiple = false)
    })
    public Page<WithdrawApplyVO> pageApply(@ApiIgnore String endTime, @ApiIgnore String startTime, @ApiIgnore String uname, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String status) {

        Map<String,String> map = new HashMap(4);
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        map.put("uname", uname);
        map.put("status",status);
        return withdrawManager.pageApply(pageNo, pageSize, map);
    }


    @PostMapping(value = "/auditing")
    @ApiOperation("Review withdrawal request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apply_id", value = "To apply forid", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "remark", value = "note", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "audit_result", value = "Application resultsVIA_AUDITING：approved/FAIL_AUDITING：Audit refused to", required = false, paramType = "query", dataType = "String", allowMultiple = false),
    })
    public void auditing(@ApiIgnore int applyId, String remark, @ApiIgnore String auditResult) {
        try {

            this.withdrawManager.auditing(applyId, remark, auditResult);
        } catch (DistributionException e) {
            logger.error("Abnormal withdrawal audit：", e);
            throw e;
        } catch (Exception e) {
            logger.error("Abnormal withdrawal audit：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @ApiOperation("Set as transferred")
    @PostMapping(value = "/account/end")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apply_id", value = "To apply forid", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "remark", value = "note", required = false, paramType = "query", dataType = "int", allowMultiple = false),
    })
    public void accountPaid(@ApiIgnore int applyId, String remark) {
        try {
            this.withdrawManager.transfer(applyId, remark);
        } catch (DistributionException e) {
            logger.error("Set it as abnormal transfer：", e);
            throw e;
        } catch (Exception e) {
            logger.error("Set it as abnormal transfer：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }

    }
}
