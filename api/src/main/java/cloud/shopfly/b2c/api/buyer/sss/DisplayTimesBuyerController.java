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
package cloud.shopfly.b2c.api.buyer.sss;

import cloud.shopfly.b2c.core.statistics.service.DisplayTimesManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Access Statistics
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/7 In the morning8:17
 */

@RestController
@RequestMapping("/view")
@Api(description = "Access Statistics")
public class DisplayTimesBuyerController {

    @Autowired
    private DisplayTimesManager displayTimesManager;

    @GetMapping()
    @ApiOperation(value = "To access the page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "urladdress", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, dataType = "String", paramType = "query")
    })
    public void view(String url, String uuid) {
        displayTimesManager.view(url, uuid);
    }


}
