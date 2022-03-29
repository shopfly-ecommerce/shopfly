/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.promotion;

import dev.shopflix.core.promotion.exchange.model.dos.ExchangeCat;
import dev.shopflix.core.promotion.exchange.model.dos.ExchangeDO;
import dev.shopflix.core.promotion.exchange.model.dto.ExchangeQueryParam;
import dev.shopflix.core.promotion.exchange.service.ExchangeCatManager;
import dev.shopflix.core.promotion.exchange.service.ExchangeGoodsManager;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.DateUtil;
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
 * 积分商品相关API
 *
 * @author Snow create in 2018/7/23
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/promotions/exchange")
@Api(description = "积分商品相关API")
@Validated
public class ExchangeBuyerController {

    @Autowired
    private ExchangeCatManager exchangeCatManager;

    @Autowired
    private ExchangeGoodsManager exchangeGoodsManager;

    @ApiOperation(value = "查询积分分类集合")
    @GetMapping("/cats")
    public List<ExchangeCat> getCat(){
        List<ExchangeCat> catList = this.exchangeCatManager.list(0);
        return catList;
    }


    @ApiOperation(value = "查询积分商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "cat_id", value = "积分分类id",dataType = "int",paramType =	"query"),
            @ApiImplicitParam(name	= "page_no", value = "页码", dataType = "int",	paramType =	"query"),
            @ApiImplicitParam(name	= "page_size", value = "条数", dataType = "int",	paramType =	"query")
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
