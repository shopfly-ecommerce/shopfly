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
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionVO;
import cloud.shopfly.b2c.core.distribution.service.DistributionManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Background Distributor management
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/25 In the morning10:55
 */
@RestController
@RequestMapping("/seller/distribution/member")
@Api(description = "distributors")
public class DistributionSellerController {

    @Autowired
    private DistributionManager distributionManager;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @ApiOperation("List of Distributors")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member_name", value = "The member name", required = false, paramType = "query", dataType = "String", allowMultiple = false),
            @ApiImplicitParam(name = "page_size", value = "Page size", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_no", value = "The page number", required = false, paramType = "query", dataType = "int", allowMultiple = false),
    })
    public Page<DistributionVO> page(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore String memberName) {

        try {
            return distributionManager.page(pageNo, pageSize, memberName);
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Template obtaining exception：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }



    @ApiOperation("Modify the template")
    @PutMapping("/tpl")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member_id", value = "membersID", required = true, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "tpl_id", value = "templateid", required = true, paramType = "query", dataType = "int", allowMultiple = false),
    })
    public void changeTpl(@ApiIgnore Integer memberId, @ApiIgnore Integer tplId) throws Exception {
        if (memberId == null || tplId == null) {
            throw new DistributionException(DistributionErrorCode.E1011.code(), DistributionErrorCode.E1011.des());
        }
        try {
            distributionManager.changeTpl(memberId, tplId);
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Template modification is abnormal：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }


}
