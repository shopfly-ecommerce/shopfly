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
package cloud.shopfly.b2c.api.buyer.promotion;

import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeCat;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.exchange.model.dto.ExchangeQueryParam;
import cloud.shopfly.b2c.core.promotion.exchange.service.ExchangeCatManager;
import cloud.shopfly.b2c.core.promotion.exchange.service.ExchangeGoodsManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Integral commodity correlationAPI
 *
 * @author Snow create in 2018/7/23
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/promotions/exchange")
@Api(description = "Integral commodity correlationAPI")
@Validated
public class ExchangeBuyerController {

    @Autowired
    private ExchangeCatManager exchangeCatManager;

    @Autowired
    private ExchangeGoodsManager exchangeGoodsManager;

    @ApiOperation(value = "Query integral classification collection")
    @GetMapping("/cats")
    public List<ExchangeCat> getCat(){
        List<ExchangeCat> catList = this.exchangeCatManager.list(0);
        return catList;
    }


    @ApiOperation(value = "Query integral goods")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "cat_id", value = "Integral classificationid",dataType = "int",paramType =	"query"),
            @ApiImplicitParam(name	= "page_no", value = "The page number", dataType = "int",	paramType =	"query"),
            @ApiImplicitParam(name	= "page_size", value = "A number of", dataType = "int",	paramType =	"query")
    })
    @GetMapping("/goods")
    public Page<ExchangeDO> list(@ApiIgnore Integer catId, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        ExchangeQueryParam param = new ExchangeQueryParam();
        param.setCatId(catId);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        param.setStartTime(DateUtil.getDateline());
        param.setEndTime(DateUtil.getDateline());
        Page webPage = this.exchangeGoodsManager.list(param);
        return webPage;
    }

}
