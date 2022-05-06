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
package cloud.shopfly.b2c.api.buyer.distribution;

import cloud.shopfly.b2c.core.base.model.vo.SuccessMessage;
import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionDO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionVO;
import cloud.shopfly.b2c.core.distribution.service.DistributionManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分销商控制器
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 上午8:34
 */

@RestController
@Api(description = "分销商api")
@RequestMapping("/distribution")
public class DistributionBuyerController {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Resource
    private DistributionManager distributionManager;


    @GetMapping(value = "/lower-list")
    @ApiOperation("获取下级分销商")
    public List<DistributionVO> getLowerDistributorList() {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        try {
            return this.distributionManager.getLowerDistributorTree(buyer.getUid());
        } catch (Exception e) {
            logger.error("获取下级的分销商列表出错", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @ApiOperation("获取推荐我的人")
    @GetMapping(value = "/recommend-me")
    public SuccessMessage recommendMe() {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        try {
            DistributionDO distributor = this.distributionManager.getDistributorByMemberId(buyer.getUid());
            //对分销商做非空校验
            if (distributor == null) {
                throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
            }
            return new SuccessMessage(this.distributionManager.getUpMember());
        } catch (Exception e) {
            logger.error("获取下级的分销商列表出错", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }


}
