/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 通用统计图VO
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-09 上午10:51
 */
@JsonNaming(value = SimpleChart.class)
public class SimpleChart extends BaseChart implements Serializable {

    @ApiModelProperty(value = "表数据")
    private ChartSeries series;

    public SimpleChart() {
    }

    public SimpleChart(ChartSeries series, String[] xAxis, String[] yAxis) {
        super();
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.series = series;
    }

    public ChartSeries getSeries() {
        return series;
    }

    public void setSeries(ChartSeries series) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleChart that = (SimpleChart) o;

        return series != null ? series.equals(that.series) : that.series == null;
    }

    @Override
    public String toString() {
        return "SimpleChart{" +
                "series=" + series.toString() +
                ", xAxis=" + Arrays.toString(xAxis) +
                ", yAxis=" + Arrays.toString(yAxis) +
                '}';
    }

    @Override
    public int hashCode() {
        return series != null ? series.hashCode() : 0;
    }
}
