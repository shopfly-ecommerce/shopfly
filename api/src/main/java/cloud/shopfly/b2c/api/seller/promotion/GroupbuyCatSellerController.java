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

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyCatDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyCatManager;
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
import java.util.List;

/**
 * Group purchase classification controller
 *
 * @author Snow create in 2018/7/12
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/seller/promotion/group-buy-cats")
@Api(description = "Group purchase classification relatedAPI")
@Validated
public class GroupbuyCatSellerController {

    @Autowired
    private GroupbuyCatManager groupbuyCatManager;

    @ApiOperation(value	= "Query the group purchase category list", response = GroupbuyGoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "parent_id",	value =	"The parent categoryid", dataType = "int",	paramType =	"path"),
    })
    @GetMapping("/{parent_id}/children")
    public List<GroupbuyCatDO> list(@ApiIgnore @PathVariable("parent_id") Integer parentId)	{
        if(parentId == null){
            parentId = 0;
        }
        List<GroupbuyCatDO> list = this.groupbuyCatManager.getList(parentId);
        return list;
    }

    @ApiOperation(value	= "Query the group purchase category list", response = GroupbuyCatDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "page_no",	value =	"The page number",	dataType = "int",	paramType =	"query"),
            @ApiImplicitParam(name	= "page_size",	value =	"Display quantity per page", dataType = "int",	paramType =	"query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize)	{
        return	this.groupbuyCatManager.list(pageNo,pageSize);
    }


    @ApiOperation(value	= "Add group Purchase category", response = GroupbuyCatDO.class)
    @PostMapping
    public GroupbuyCatDO add(@Valid GroupbuyCatDO groupbuyCat)	{

        this.groupbuyCatManager.add(groupbuyCat);

        return	groupbuyCat;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value	= "Modify group purchase classification", response = GroupbuyCatDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "id",	value =	"A primary key",	required = true, dataType = "int",	paramType =	"path")
    })
    public	GroupbuyCatDO edit(@Valid GroupbuyCatDO groupbuyCat, @PathVariable Integer id) {

        this.groupbuyCatManager.edit(groupbuyCat,id);

        return	groupbuyCat;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value	= "Delete group purchase category")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "id",	value =	"The group purchase classification key to delete",	required = true, dataType = "int",	paramType =	"path")
    })
    public	String	delete(@PathVariable Integer id) {
        this.groupbuyCatManager.delete(id);
        return "";
    }


    @GetMapping(value =	"/{id}")
    @ApiOperation(value	= "Query a group purchase category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",	value = "To query the group purchase classification key",	required = true, dataType = "int",	paramType = "path")
    })
    public	GroupbuyCatDO get(@PathVariable	Integer	id)	{
        GroupbuyCatDO groupbuyCat = this.groupbuyCatManager.getModel(id);
        return	groupbuyCat;
    }

}
