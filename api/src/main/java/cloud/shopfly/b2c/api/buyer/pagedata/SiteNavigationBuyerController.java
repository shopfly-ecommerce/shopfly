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
package cloud.shopfly.b2c.api.buyer.pagedata;

import cloud.shopfly.b2c.core.pagedata.model.SiteNavigation;
import cloud.shopfly.b2c.core.pagedata.service.SiteNavigationManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Navigation bar controller
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 17:07:22
 */
@RestController
@RequestMapping("/pages/site-navigations")
@Api(description = "Navigation bar correlationAPI")
public class SiteNavigationBuyerController {

    @Autowired
    private SiteNavigationManager siteNavigationManager;


    @ApiOperation(value = "Query the navigation bar list", response = SiteNavigation.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_type", value = "Client type", required = true, dataType = "string", paramType = "query", allowableValues = "PC,MOBILE"),
    })
    @GetMapping
    public List<SiteNavigation> list(@ApiIgnore String clientType) {

        return this.siteNavigationManager.listByClientType(clientType);
    }
}
