/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.statistics;

import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.core.statistics.StatisticsErrorCode;
import cloud.shopfly.b2c.core.statistics.StatisticsException;
import cloud.shopfly.b2c.core.statistics.model.vo.MultipleChart;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.ReportsStatisticsManager;
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
import java.util.*;

/**
 * 商家统计 运营报告
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018年4月18日下午4:28:23
 */
@Api(description = "商家统计 运营报告")
@RestController
@RequestMapping("/seller/statistics/reports")
public class ReportsStatisticsSellerController {

    @Autowired
    private ReportsStatisticsManager reportsStatisticsManager;

    @ApiOperation(value = "销售统计  下单金额图表数据", response = MultipleChart.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "日期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")})
    @GetMapping("/sales_money")
    public MultipleChart getSalesMoney(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.reportsStatisticsManager.getSalesMoney(searchCriteria);
    }

    @ApiOperation(value = "销售统计 下单量图表数据", response = MultipleChart.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")})
    @GetMapping("/sales_num")
    public MultipleChart getSalesNum(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.reportsStatisticsManager.getSalesNum(searchCriteria);
    }

    @ApiOperation(value = "销售统计  表格数据", response = Page.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_no", value = "当前页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "页面数据容量", dataType = "int", paramType = "query")})
    @GetMapping("/sales_page")
    public Page getSalesPage(@Valid @ApiIgnore SearchCriteria searchCriteria, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.reportsStatisticsManager.getSalesPage(searchCriteria, pageNo, pageSize);
    }

    @ApiOperation(value = "销售统计  数据小结", response = HashMap.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")})
    @GetMapping("/sales_summary")
    public Map getSalesSummary(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.reportsStatisticsManager.getSalesSummary(searchCriteria);
    }

    @ApiOperation(value = "区域分析", response = ArrayList.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "数据类型", dataType = "String", paramType = "query",required = true,allowableValues = "ORDER_MEMBER_NUM,ORDER_PRICE,ORDER_NUM")
    })
    @GetMapping("/regions/data")
    public List regionMemberMap(@Valid @ApiIgnore SearchCriteria searchCriteria,@ApiIgnore String type) {
        if (null == type || "".equals(type)){
            throw new StatisticsException(StatisticsErrorCode.E801.code(), "请指定需要的数据");
        }
        return this.reportsStatisticsManager.regionsMap(searchCriteria, type);
    }

    @ApiOperation(value = "购买分析 客单价分布", response = SimpleChart.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "ranges", value = "价格区间，不传有默认值", dataType = "int", paramType = "query", allowMultiple = true)})
    @GetMapping("/purchase/ranges")
    public SimpleChart orderDistribution(@Valid @ApiIgnore SearchCriteria searchCriteria, @ApiIgnore @RequestParam(value = "ranges", required = false) Integer[] ranges) {

        // 如果为空，设默认值
        if (null == ranges || ranges.length == 0) {
            ranges = new Integer[5];
            ranges[0] = 0;
            ranges[1] = 500;
            ranges[2] = 1000;
            ranges[3] = 1500;
            ranges[4] = 2000;
        }

        // 转为list，去除null，数组转换的集合有限制，只能用这种笨办法
        List<Integer> list = Arrays.asList(ranges);
        List<Integer> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                newList.add(list.get(i));
            }
        }
        Integer[] newRanges = newList.toArray(new Integer[newList.size()]);

        if (newRanges.length < 2) {
            throw new StatisticsException(StatisticsErrorCode.E801.code(), "应至少上传两个数字，才可构成价格区间");
        }
        return this.reportsStatisticsManager.orderDistribution(searchCriteria, newRanges);
    }

    @ApiOperation(value = "购买分析 购买时段分布", response = SimpleChart.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "周期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")})
    @GetMapping("/purchase/period")
    public SimpleChart purchasePeriod(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.reportsStatisticsManager.purchasePeriod(searchCriteria);
    }


}
