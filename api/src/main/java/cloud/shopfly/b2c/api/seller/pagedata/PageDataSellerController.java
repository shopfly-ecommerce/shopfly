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
package cloud.shopfly.b2c.api.seller.pagedata;

import cloud.shopfly.b2c.core.pagedata.constraint.annotation.ClientAppType;
import cloud.shopfly.b2c.core.pagedata.constraint.annotation.PageType;
import cloud.shopfly.b2c.core.pagedata.model.PageData;
import cloud.shopfly.b2c.core.pagedata.service.PageDataManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Floor controller
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 16:39:22
 */
@RestController
@RequestMapping("/seller/pages")
@Api(description = "Floor relatedAPI")
@Validated
public class PageDataSellerController {

    @Autowired
    private PageDataManager pageManager;


    @PutMapping(value = "/{page_id}")
    @ApiOperation(value = "Modify the floor", response = PageData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public PageData edit(@Valid PageData page, @PathVariable("page_id") Integer pageId) {

        this.pageManager.edit(page, pageId);

        return page;
    }

    @GetMapping(value = "/{page_id}")
    @ApiOperation(value = "Querying a floor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_id", value = "Primary key of the floor to be queried", required = true, dataType = "int", paramType = "path")
    })
    public PageData get(@PathVariable("page_id") Integer pageId) {

        PageData page = this.pageManager.getModel(pageId);

        return page;
    }


    @PutMapping(value = "/{client_type}/{page_type}")
    @ApiOperation(value = "Modify floors using client type and page type", response = PageData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_type", value = "Type of the client to be queriedAPP/WAP/PC", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "page_type", value = "Type of page to queryINDEX Home/SPECIAL project", required = true, dataType = "string", paramType = "path")
    })
    public PageData editByType(@Valid PageData pageData, @ClientAppType @PathVariable("client_type") String clientType, @PageType @PathVariable("page_type") String pageType) {

        pageData.setClientType(clientType);
        pageData.setPageType(pageType);
        this.pageManager.editByType(pageData);

        return pageData;
    }

    @GetMapping(value = "/{client_type}/{page_type}")
    @ApiOperation(value = "Query a floor using client type and page type")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_type", value = "Type of the client to be queriedAPP/WAP/PC", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "page_type", value = "Type of page to queryINDEX Home/SPECIAL project", required = true, dataType = "string", paramType = "path")
    })
    public PageData getByType(@ClientAppType @PathVariable("client_type") String clientType, @PageType @PathVariable("page_type") String pageType) {

        PageData page = this.pageManager.getByType(clientType,pageType);

        return page;
    }

}
