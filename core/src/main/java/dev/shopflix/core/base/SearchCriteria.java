/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base;

import dev.shopflix.core.statistics.StatisticsException;
import dev.shopflix.core.statistics.model.enums.QueryDateType;
import dev.shopflix.framework.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 搜索参数
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/28 下午5:09
 */
@ApiModel
public class SearchCriteria implements Serializable {

    private static final long serialVersionUID = -1570682583055253820L;

    @ApiModelProperty(name = "cycle_type", value = "周期 YEAR:年 MONTH:月", required = true, allowableValues = "YEAR,MONTH")
    private String cycleType;

    @ApiModelProperty(name = "year",value = "年份", example = "2016")
    private Integer year;

    @ApiModelProperty(name = "month",value = "月份", example = "11")
    private Integer month;

    @ApiModelProperty(name = "category_id",value = "分类id 0全部", example = "0")
    private Integer categoryId;


    /**
     * @param cycleType 日期类型
     * @param year      年
     * @param month     月
     */
    public static void checkDataParams(String cycleType, Integer year, Integer month) throws StatisticsException {
        if (cycleType == null || year == null) {
            throw new StatisticsException("日期类型及年份不可为空");
        }
        if (cycleType.equals(QueryDateType.MONTH.value()) && month == null) {
            throw new StatisticsException("按月查询时，月份不可为空");
        }
    }

    /**
     * 校验参数
     *
     * @param searchCriteria 参数对象
     * @param checkDate      校验日期
     * @param checkCategory  校验分类
     * @throws StatisticsException 抛出自定义统计异常
     */
    public static void checkDataParams(SearchCriteria searchCriteria, boolean checkDate, boolean checkCategory) throws StatisticsException {

        if (checkDate) {
            if (searchCriteria.getCycleType() == null || searchCriteria.getYear() == null) {
                throw new StatisticsException("日期类型及年份不可为空");
            }
            if (searchCriteria.getCycleType().equals(QueryDateType.MONTH.value()) && searchCriteria.getMonth() == null) {
                throw new StatisticsException("按月查询时，月份不可为空");
            }
        }
        if (checkCategory) {
            if (searchCriteria.getCategoryId() == null) {
                throw new StatisticsException("商品分类不可为空");
            }
        }
    }

    public SearchCriteria() {
    }

    public static Integer[] defaultPrice() {
        Integer[] prices = new Integer[5];
        prices[0] = 0;
        prices[1] = 100;
        prices[2] = 1000;
        prices[3] = 10000;
        prices[4] = 100000;
        return prices;
    }

    public SearchCriteria(SearchCriteria searchCriteria) {
        if (StringUtil.isEmpty(searchCriteria.getCycleType())) {
            searchCriteria.setCycleType(QueryDateType.YEAR.name());
        }
        if (searchCriteria.getYear() == null) {
            LocalDate localDate = LocalDate.now();
            searchCriteria.setYear(localDate.getYear());
        }
        if (searchCriteria.getMonth() == null) {
            LocalDate localDate = LocalDate.now();
            searchCriteria.setMonth(localDate.getMonthValue());
        }
        if (searchCriteria.getCategoryId() == null) {
            searchCriteria.setCategoryId(0);
        }

        this.setCategoryId(searchCriteria.getCategoryId());
        this.setYear(searchCriteria.getYear());
        this.setMonth(searchCriteria.getMonth());
        this.setCycleType(searchCriteria.getCycleType());

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCycleType() {
        return cycleType;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
