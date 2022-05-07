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
package cloud.shopfly.b2c.core.statistics.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Multi-data statistics chartVO
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-09 In the morning10:51
 */

@JsonNaming(value = MultipleChart.class)
public class MultipleChart extends BaseChart implements Serializable {

    @ApiModelProperty(value = "Table data")
    private List<ChartSeries> series;

    public List<ChartSeries> getSeries() {
        return series;
    }

    public void setSeries(List<ChartSeries> series) {
        this.series = series;
    }

    public MultipleChart() {

    }

    public MultipleChart(List<ChartSeries> series, String[] xAxis, String[] yAxis) {
        super();
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.series = series;
    }

    @Override
    public String[] getxAxis() {
        return xAxis;
    }

    @Override
    public void setxAxis(String[] xAxis) {
        this.xAxis = xAxis;
    }

    @Override
    public String[] getyAxis() {
        return yAxis;
    }

    @Override
    public void setyAxis(String[] yAxis) {
        this.yAxis = yAxis;
    }

    @Override
    public String toString() {
        return "MultipleChart{" +
                "series=" + series +
                ", xAxis=" + Arrays.toString(xAxis) +
                ", yAxis=" + Arrays.toString(yAxis) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultipleChart that = (MultipleChart) o;

        return series != null ? series.equals(that.series) : that.series == null;
    }

    @Override
    public int hashCode() {
        return series != null ? series.hashCode() : 0;
    }

}
