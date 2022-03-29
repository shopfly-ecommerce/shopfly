/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.statistics;

import dev.shopflix.core.base.SearchCriteria;
import dev.shopflix.core.statistics.model.vo.SimpleChart;
import dev.shopflix.core.statistics.service.PageViewStatisticManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 商家中心 流量分析
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018年3月19日上午8:35:47
 */

@Api(description = "商家统计 流量分析")
@RestController
@RequestMapping("/seller/statistics/page_view")
public class PageViewStatisticSellerController {

    @Autowired
    private PageViewStatisticManager pageViewStatisticManager;

    @ApiOperation(value = "获取商品访问量数据，只取前30", response = SimpleChart.class)
    @GetMapping("/goods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份，默认当前年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份，默认当前月份", dataType = "int", paramType = "query")})
    public SimpleChart getGoods(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.pageViewStatisticManager.countGoods(searchCriteria);
    }

}
