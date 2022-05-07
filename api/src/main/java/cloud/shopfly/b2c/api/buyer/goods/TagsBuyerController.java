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
package cloud.shopfly.b2c.api.buyer.goods;

import cloud.shopfly.b2c.core.goods.constraint.annotation.MarkType;
import cloud.shopfly.b2c.core.goods.model.dos.TagsDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.core.goods.service.TagsManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Commodity label controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
@RestController
@RequestMapping("/goods")
@Api(description = "Label commodity correlationAPI")
@Validated
public class TagsBuyerController {

    @Autowired
    private TagsManager tagsManager;

    @ApiOperation(value = "Example Query the list of labeled commodities", response = TagsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mark", value = "hotsellingnewNew productrecommendrecommended", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "num", value = "The number of queries", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/tags/{mark}/goods")
    public List<GoodsSelectLine> list(Integer num, @MarkType @PathVariable String mark) {

        if (num == null) {
            num = 5;
        }

        return tagsManager.queryTagGoods(num, mark);
    }

}
