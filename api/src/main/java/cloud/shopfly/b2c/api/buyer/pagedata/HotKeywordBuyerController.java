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

import cloud.shopfly.b2c.core.pagedata.model.HotKeyword;
import cloud.shopfly.b2c.core.pagedata.service.HotKeywordManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 热门关键字控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-04 10:43:23
 */
@RestController
@RequestMapping("/pages/hot-keywords")
@Api(description = "热门关键字相关API")
public class HotKeywordBuyerController {

    @Autowired
    private HotKeywordManager hotKeywordManager;


    @ApiOperation(value = "查询热门关键字列表", response = HotKeyword.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "查询的数量", required = true, dataType = "int", paramType = "query"),
    })
    @GetMapping
    public List<HotKeyword> list(Integer num) {

        return this.hotKeywordManager.listByNum(num);
    }


}