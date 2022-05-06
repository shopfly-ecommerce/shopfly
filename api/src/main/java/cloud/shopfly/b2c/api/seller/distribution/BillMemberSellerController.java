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
import cloud.shopfly.b2c.core.distribution.model.vo.BillMemberVO;
import cloud.shopfly.b2c.core.distribution.service.BillMemberManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 分销会员结算单控制器
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/24 下午2:39
 */
@Api(description = "分销会员结算单控制器")
@RestController
@RequestMapping("/seller/distribution/bill/member")
public class BillMemberSellerController {

    @Autowired
    private BillMemberManager billMemberManager;

    @ApiOperation("分销商分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "total_id", value = "总结算单id", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_size", value = "页码大小", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "uname", value = "会员名", required = false, paramType = "query", dataType = "String", allowMultiple = false)
    })
    @GetMapping
    public Page<BillMemberVO> page(@ApiIgnore Integer totalId, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String uname) {

        if (totalId == null) {
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
        try {
            return billMemberManager.page(pageNo, pageSize, totalId, uname);
        } catch (Exception e) {
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("获取某个业绩详情")
    @ApiImplicitParam(name = "id", value = "业绩单id", required = false, paramType = "query", dataType = "int", allowMultiple = false)
    public BillMemberVO billMemberVO(@PathVariable Integer id) {
        try {
            return new BillMemberVO(billMemberManager.getBillMember(id));
        } catch (Exception e) {

            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }


    @GetMapping("/down")
    @ApiOperation("获取某个分销商下级业绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "当前页面 业绩单id", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "member_id", value = "会员id", required = false, paramType = "query", dataType = "int", allowMultiple = false)
    })
    public List<BillMemberVO> downBillMemberVO(Integer id, @ApiIgnore Integer memberId) {
        try {
            List<BillMemberVO> list = billMemberManager.allDown(memberId, id);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @ApiOperation("导出会员结算单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "total_id", value = "总结算单id", required = false, paramType = "query", dataType = "int", allowMultiple = false),
    })
    @GetMapping("/export")
    public List<BillMemberVO> export(@ApiIgnore Integer totalId) {
        return billMemberManager.page(1, 99999, totalId, "").getData();
    }

}
