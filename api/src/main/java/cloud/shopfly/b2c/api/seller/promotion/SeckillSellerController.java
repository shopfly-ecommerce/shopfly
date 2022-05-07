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

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillDTO;
import cloud.shopfly.b2c.core.promotion.seckill.model.enums.SeckillStatusEnum;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillVO;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.util.DateUtil;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Flash sale activity controller
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 17:30:23
 */
@RestController
@RequestMapping("/seller/promotion/seckills")
@Api(description = "Flash sale relatedAPI")
@Validated
public class SeckillSellerController {

    @Autowired
    private SeckillManager seckillManager;


    @ApiOperation(value = "Query flash sale lists", response = SeckillDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keywords", value = "keyword", dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page<SeckillVO> list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize,String keywords) {

        return this.seckillManager.list(pageNo, pageSize, keywords);
    }


    @ApiOperation(value = "Add flash sale to store", response = SeckillVO.class)
    @PostMapping
    public SeckillDTO add(@Valid @RequestBody SeckillDTO seckill) {
        this.verifyParam(seckill);
        seckill.setSeckillStatus(SeckillStatusEnum.EDITING.name());
        this.seckillManager.add(seckill);
        return seckill;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify flash sale to store", response = SeckillDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public SeckillDTO edit(@Valid @RequestBody SeckillDTO seckill, @PathVariable @NotNull(message = "flashIDParameter error") Integer id) {
        this.verifyParam(seckill);
        this.seckillManager.edit(seckill, id);
        return seckill;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete flash sale to store")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Flash store to delete home key", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.seckillManager.delete(id);

        return "";
    }

    @DeleteMapping(value = "/{id}/close")
    @ApiOperation(value = "Close flash sales")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "To close the flash store store main key", required = true, dataType = "int", paramType = "path")
    })
    public String close(@PathVariable Integer id) {

        this.seckillManager.close(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a flash sale to store")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Flash sale to query into the main key", required = true, dataType = "int", paramType = "path")
    })
    public SeckillDTO get(@PathVariable Integer id) {
        SeckillDTO seckillVO = this.seckillManager.getModelAndRange(id);
        return seckillVO;
    }

    @GetMapping(value = "/{id}/seckill-applys")
    @ApiOperation(value = "Query a flash sale for an item that has been applied for storage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Flash sale to query into the main key", required = true, dataType = "int", paramType = "path")
    })
    public SeckillVO getApply(@PathVariable Integer id) {
        SeckillVO seckillVO = this.seckillManager.getModelAndApplys(id);
        return seckillVO;
    }


    @ApiOperation(value = "Launch a flash sale")
    @PostMapping("/{seckill_id}/release")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seckill_id", value = "Flash sale to query into the main key", required = true, dataType = "int", paramType = "path")
    })
    public SeckillDTO publish(@Valid @RequestBody SeckillDTO seckill, @ApiIgnore @PathVariable("seckill_id") Integer seckillId) {

        this.verifyParam(seckill);
        // Post status
        seckill.setSeckillStatus(SeckillStatusEnum.RELEASE.name());
        if (seckillId == null || seckillId == 0) {
            seckillManager.add(seckill);
        } else {
            seckillManager.edit(seckill, seckillId);
        }

        return seckill;
    }

    /**
     * Validate parameter
     *
     * @param seckillVO
     */
    private void verifyParam(SeckillDTO seckillVO) {
        // Gets the start time of the activity
        long startDay = seckillVO.getStartDay();

        // Gets the time of 0 o clock on the day the event starts
        String startDate = DateUtil.toString(startDay, "yyyy-MM-dd");
        long startTime = DateUtil.getDateline(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

        // Get the registration deadline
        long applyTime = seckillVO.getApplyEndTime();

        // Get the current time
        long currentTime = DateUtil.getDateline();

        // Get the start time of the day
        String currentDay = DateUtil.toString(currentTime, "yyyy-MM-dd");
        long currentStartTime = DateUtil.getDateline(currentDay + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

        // The activity time is shorter than the start time of the day
        if (startDay < currentStartTime) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The active time cannot be smaller than the current time");
        }
        // The deadline for registration is less than the current time
        if (applyTime < currentTime) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The registration deadline cannot be less than the current time");
        }
        // The deadline for registration is longer than the start time of the event
        if (applyTime > startTime) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The deadline for registration should not be longer than the start time of the activity");
        }

        List<Integer> termList = new ArrayList<>();
        for (Integer time : seckillVO.getRangeList()) {
            if (termList.contains(time)) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The value of the panic buying interval cannot be repeated");
            }
            // The value of the rush zone is not in the range of 0 to 23
            if (time < 0 || time > 23) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The buying interval must be within0Point to the23The hour of one");
            }
            termList.add(time);
        }
    }

}
