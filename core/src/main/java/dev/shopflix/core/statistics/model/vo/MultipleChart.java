/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 多数据统计图VO
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-09 上午10:51
 */

@JsonNaming(value = MultipleChart.class)
public class MultipleChart extends BaseChart implements Serializable {

    @ApiModelProperty(value = "表数据")
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
