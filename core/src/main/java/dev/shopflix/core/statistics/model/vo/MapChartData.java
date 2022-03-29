/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 地图统计vo
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-11 上午10:02
 */

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MapChartData implements Serializable {
    @ApiModelProperty("地名")
    private String[] name;
    @ApiModelProperty("对应的数据")
    private String[] data;

    public MapChartData() {

    }

    public MapChartData(String[] name, String[] data) {
        this.name = name;
        this.data = data;

    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MapChartData{" +
                "name=" + Arrays.toString(name) +
                ", data=" + Arrays.toString(data) +
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

        MapChartData that = (MapChartData) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(name, that.name)) {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(name);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

}
