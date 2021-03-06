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
package cloud.shopfly.b2c.core.base;

import cloud.shopfly.b2c.core.statistics.StatisticsException;
import cloud.shopfly.b2c.core.statistics.model.enums.QueryDateType;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The search parameters
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/28 In the afternoon5:09
 */
@ApiModel
public class SearchCriteria implements Serializable {

    private static final long serialVersionUID = -1570682583055253820L;

    @ApiModelProperty(name = "cycle_type", value = "cycleYEAR:yearsMONTH:month", required = true, allowableValues = "YEAR,MONTH")
    private String cycleType;

    @ApiModelProperty(name = "year",value = "year", example = "2016")
    private Integer year;

    @ApiModelProperty(name = "month",value = "in", example = "11")
    private Integer month;

    @ApiModelProperty(name = "category_id",value = "Categoriesid 0all", example = "0")
    private Integer categoryId;


    /**
     * @param cycleType The date type
     * @param year      years
     * @param month     month
     */
    public static void checkDataParams(String cycleType, Integer year, Integer month) throws StatisticsException {
        if (cycleType == null || year == null) {
            throw new StatisticsException("Date type and year cannot be empty");
        }
        if (cycleType.equals(QueryDateType.MONTH.value()) && month == null) {
            throw new StatisticsException("When the query is performed by month, the month cannot be empty");
        }
    }

    /**
     * Calibration parameters
     *
     * @param searchCriteria Parameter object
     * @param checkDate      Check the date
     * @param checkCategory  Check classification
     * @throws StatisticsException Throw custom statistical exceptions
     */
    public static void checkDataParams(SearchCriteria searchCriteria, boolean checkDate, boolean checkCategory) throws StatisticsException {

        if (checkDate) {
            if (searchCriteria.getCycleType() == null || searchCriteria.getYear() == null) {
                throw new StatisticsException("Date type and year cannot be empty");
            }
            if (searchCriteria.getCycleType().equals(QueryDateType.MONTH.value()) && searchCriteria.getMonth() == null) {
                throw new StatisticsException("When the query is performed by month, the month cannot be empty");
            }
        }
        if (checkCategory) {
            if (searchCriteria.getCategoryId() == null) {
                throw new StatisticsException("The category of goods cannot be empty");
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
