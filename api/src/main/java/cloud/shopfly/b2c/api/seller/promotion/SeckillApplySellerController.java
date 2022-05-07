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
package cloud.shopfly.b2c.api.seller.promotion;

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillApplyDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillRangeDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillQueryParam;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillVO;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillGoodsManager;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillManager;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillRangeManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Flash sale application controller
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 17:30:09
 */
@RestController
@RequestMapping("/seller/promotion/seckill-applys")
@Api(description = "Flash sale application relatedAPI")
@Validated
public class SeckillApplySellerController {

    @Autowired
    private SeckillGoodsManager seckillApplyManager;

    @Autowired
    private SeckillRangeManager seckillRangeManager;

    @Autowired
    private SeckillManager seckillManager;


    @ApiOperation(value = "Query the flash sale application list", response = SeckillApplyDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "seckill_id", value = "Flash salesid", dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page<SeckillVO> list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize,
                                @ApiIgnore @NotNull(message = "Flash sale parameter is empty") Integer seckillId) {

        SeckillQueryParam queryParam = new SeckillQueryParam();
        queryParam.setPageNo(pageNo);
        queryParam.setPageSize(pageSize);
        queryParam.setSeckillId(seckillId);
        return this.seckillApplyManager.list(queryParam);
    }


    @ApiOperation(value = "Add flash sale application", response = SeckillApplyDO.class)
    @PostMapping
    public List<SeckillApplyDO> add(@Valid @RequestBody @NotEmpty(message = "Application parameters are empty") List<SeckillApplyDO> seckillApplyList) {

        SeckillApplyDO applyDO = seckillApplyList.get(0);
        this.verifyParam(seckillApplyList, applyDO.getSeckillId());
        this.seckillApplyManager.addApply(seckillApplyList);

        return seckillApplyList;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete the flash sale application")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "To delete flash sale application primary key", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.seckillApplyManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a flash sale application")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "To query flash sale application primary key", required = true, dataType = "int", paramType = "path")
    })
    public SeckillApplyDO get(@PathVariable Integer id) {
        SeckillApplyDO seckillApply = this.seckillApplyManager.getModel(id);
        // Verify unauthorized operations
        if (seckillApply == null) {
            throw new NoPermissionException("Have the right to operate");
        }

        return seckillApply;
    }


    /**
     * Verify the correctness of the parameters
     *
     * @param applyDOList
     * @param seckillId   Flash salesid
     */
    private void verifyParam(List<SeckillApplyDO> applyDOList, Integer seckillId) {


        // Read all time sets according to flash sale activity ID
        List<SeckillRangeDO> list = this.seckillRangeManager.getList(seckillId);
        List<Integer> rangIdList = new ArrayList<>();

        for (SeckillRangeDO seckillRangeDO : list) {
            rangIdList.add(seckillRangeDO.getRangeTime());
        }

        /**
         * Store merchandise for the eventidIs used to judge that the same commodity cannot participate in a certain activity repeatedly
         */
        Map<Integer, Integer> map = new HashMap<>();

        for (SeckillApplyDO applyDO : applyDOList) {

            if (applyDO.getSeckillId() == null || applyDO.getSeckillId().intValue() == 0) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Flash salesIDParameters of the abnormal");
            } else {
                SeckillDO seckillVO = this.seckillManager.getModel(seckillId);

                // Last time for application
                long applyEndTime = seckillVO.getApplyEndTime();

                // Current server time
                long nowTime = DateUtil.getDateline();

                // Application cannot be made because the current time is longer than the last application time
                if (nowTime > applyEndTime) {
                    throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The deadline for application has passed");
                }

            }

            if (applyDO.getTimeLine() == null) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Time parameter anomaly");
            } else {

                // Determines if the moment set for this activity contains the moment to be added, if not, the moment parameter is abnormal
                if (!rangIdList.contains(applyDO.getTimeLine())) {
                    throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Time parameter anomaly");
                }
            }

            if (applyDO.getStartDay() == null || applyDO.getStartDay().intValue() == 0) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The activity start time parameter is abnormal");
            }

            if (applyDO.getGoodsId() == null || applyDO.getGoodsId().intValue() == 0) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "productIDParameters of the abnormal");
            }

            if (StringUtil.isEmpty(applyDO.getGoodsName())) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The commodity name parameter is abnormal");
            }

            if (applyDO.getPrice() == null || applyDO.getPrice() < 0) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Snap up price parameter cannot be less than0");
            }

            if (applyDO.getSoldQuantity() == null || applyDO.getSoldQuantity().intValue() <= 0) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Short selling quantity parameter should not be less than0");
            }

            if (applyDO.getPrice() > applyDO.getOriginalPrice()) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The activity price cannot exceed the original price of the goods");
            }

            if (map.get(applyDO.getGoodsId()) != null) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, applyDO.getGoodsName() + ",The product cannot participate in activities of multiple time periods at the same time");
            }

            map.put(applyDO.getGoodsId(), applyDO.getGoodsId());

        }
    }


}
