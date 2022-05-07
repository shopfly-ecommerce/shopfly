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

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyActiveVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyActiveManager;
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

/**
 * Group purchase activity table controller
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 15:46:51
 */
@RestController
@RequestMapping("/seller/promotion/group-buy-actives")
@Api(description = "Group purchase activity table relatedAPI")
@Validated
public class GroupbuyActiveSellerController {

	@Autowired
	private GroupbuyActiveManager groupbuyActiveManager;

	@ApiOperation(value	= "Query group purchase activity list")
	@ApiImplicitParams({
		 	@ApiImplicitParam(name	= "page_no",	value =	"The page number",	dataType = "int",	paramType =	"query"),
		 	@ApiImplicitParam(name	= "page_size",	value =	"Display quantity per page", dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page<GroupbuyActiveVO> list(@ApiIgnore  Integer pageNo, @ApiIgnore Integer pageSize)	{
		Page<GroupbuyActiveVO> page = this.groupbuyActiveManager.list(pageNo,pageSize);
		return page;

	}


	@ApiOperation(value	= "Add group purchase activity table", response = GroupbuyActiveDO.class)
	@ApiImplicitParam(name = "activeDO", value = "Group purchase information", required = true, dataType = "GroupbuyActiveDO", paramType = "body")
	@PostMapping
	public GroupbuyActiveDO add(@ApiIgnore @Valid  @RequestBody GroupbuyActiveDO activeDO)	{

		this.verifyParam(activeDO.getStartTime(),activeDO.getEndTime(),activeDO.getJoinEndTime());
		this.groupbuyActiveManager.add(activeDO);
		return	activeDO;
	}


	@ApiOperation(value	= "Modify the group purchase activity table", response = GroupbuyActiveDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name	= "id",	value =	"A primary key",	required = true, dataType = "int",	paramType =	"path")
	})
	@PutMapping(value = "/{id}")
	public GroupbuyActiveDO edit(@Valid @RequestBody GroupbuyActiveDO activeDO, @PathVariable Integer id) {
		this.verifyParam(activeDO.getStartTime(),activeDO.getEndTime(),activeDO.getJoinEndTime());
		this.groupbuyActiveManager.edit(activeDO,id);
		return	activeDO;
	}


	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "Delete the group purchase activity table")
	@ApiImplicitParams({
			@ApiImplicitParam(name	= "id",	value =	"The group purchase activity table primary key to delete",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {
		this.groupbuyActiveManager.delete(id);
		return "";
	}


	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "Query a group purchase activity list")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id",	value = "To query the group purchase activity table primary key",	required = true, dataType = "int",	paramType = "path")
	})
	public GroupbuyActiveDO get(@PathVariable	Integer	id)	{

		GroupbuyActiveDO groupbuyActive = this.groupbuyActiveManager.getModel(id);

		return	groupbuyActive;
	}

	/**
	 * Validate parameter
	 * @param startTime	Activity start time
	 * @param endTime	End time
	 * @param joinEndTime	Deadline for registration
	 */
	private void verifyParam(long startTime,long endTime,long joinEndTime){

		long nowTime  = DateUtil.getDateline();

		// If the activity start time is less than the present time
		if(joinEndTime < nowTime){
			throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"The registration deadline must be longer than the current time");
		}

		// If the activity starts before the registration deadline
		if (startTime < joinEndTime) {
			throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The start time must be longer than the deadline for registration");
		}

		// The start time cannot be later than the end time
		if (startTime > endTime ) {
			throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"The start time cannot be later than the end time");
		}

	}

}
