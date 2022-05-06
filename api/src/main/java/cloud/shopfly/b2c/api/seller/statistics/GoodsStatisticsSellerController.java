/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.statistics;

import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.core.statistics.StatisticsErrorCode;
import cloud.shopfly.b2c.core.statistics.StatisticsException;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.GoodsFrontStatisticsManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 商家中心，商品分析
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018年4月18日上午11:57:48
 */
@Api(description = "商家统计 商品分析")
@RestController
@RequestMapping("/seller/statistics/goods")
public class GoodsStatisticsSellerController {

    @Autowired
    private GoodsFrontStatisticsManager goodsFrontStatisticsManager;

    @ApiOperation(value = "商品详情，获取近30天销售数据", response = Page.class)
    @GetMapping(value = "/goods_detail")
    @ApiImplicitParams({@ApiImplicitParam(name = "page_no", value = "当前页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "页面大小", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "category_id", value = "商品分类id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "goods_name", value = "商品名称", dataType = "String", paramType = "query")})
    public Page getGoodsSalesDetail(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore Integer categoryId, @ApiIgnore String goodsName) {

        if (null == categoryId) {
            throw new StatisticsException(StatisticsErrorCode.E801.code(), "商品分类id不可为空");
        }
        return this.goodsFrontStatisticsManager.getGoodsDetail(pageNo, pageSize, categoryId, goodsName);
    }

    @ApiOperation(value = "价格销量", response = SimpleChart.class)
    @GetMapping(value = "/price_sales")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sections", value = "价格区间，不传有默认值", dataType = "int", paramType = "query", required = true, allowMultiple = true),
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "category_id", value = "商品分类id", dataType = "int", paramType = "query")
    })
    public SimpleChart getGoodsPriceSales(@ApiIgnore @Valid SearchCriteria searchCriteria, @ApiIgnore @RequestParam(value = "sections", required = false) ArrayList<Integer> sections) {

        // 如果价格空间为空，则添加默认值
        if (null == sections || sections.size() == 0) {
            sections = new ArrayList<>();
            sections.add(0);
            sections.add(500);
            sections.add(500);
            sections.add(1000);
            sections.add(1000);
            sections.add(1500);
            sections.add(1500);
            sections.add(2000);
        }

        // 去除null值
        sections.removeIf(Objects::isNull);
        if (sections.size() < 2) {
            throw new StatisticsException(StatisticsErrorCode.E801.code(), "应至少上传两个数字，才可构成价格区间");
        }
        return this.goodsFrontStatisticsManager.getGoodsPriceSales(sections, searchCriteria);
    }

    @ApiOperation(value = "下单金额排行前30商品，分页数据", response = Page.class)
    @GetMapping(value = "/order_price_page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")})
    public Page getGoodsOrderPriceTopPage(@ApiIgnore SearchCriteria searchCriteria) {
        // 排名名次 默认30
        Integer topNum = 30;
        // 获取数据
        return this.goodsFrontStatisticsManager.getGoodsOrderPriceTopPage(topNum, searchCriteria);
    }

    @ApiOperation(value = "下单商品数量排行前30，分页数据", response = Page.class)
    @GetMapping(value = "/order_num_page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")})
    public Page getGoodsNumTop(@ApiIgnore SearchCriteria searchCriteria) {
        return this.goodsFrontStatisticsManager.getGoodsNumTopPage(30, searchCriteria);
    }

    @ApiOperation(value = "下单金额排行前30商品，图表数据", response = SimpleChart.class)
    @GetMapping(value = "/order_price")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")})
    public SimpleChart getGoodsOrderPriceTopChart(@ApiIgnore SearchCriteria searchCriteria) {

        // 排名名次 默认30
        Integer topNum = 30;

        // 2.获取数据
        return this.goodsFrontStatisticsManager.getGoodsOrderPriceTop(topNum, searchCriteria);
    }

    @ApiOperation(value = "下单商品数量排行前30，图表数据", response = SimpleChart.class)
    @GetMapping(value = "/order_num")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")})
    public SimpleChart getGoodsNumTopChart(@ApiIgnore SearchCriteria searchCriteria) {
        // 获取数据
        return this.goodsFrontStatisticsManager.getGoodsNumTop(30, searchCriteria);
    }

}
