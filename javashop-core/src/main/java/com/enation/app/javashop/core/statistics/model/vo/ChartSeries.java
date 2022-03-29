/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.statistics.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * ChartSeries
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-10 上午7:51
 */

@JsonNaming(value = com.enation.app.javashop.core.statistics.model.vo.ChartSeries.class)
public class ChartSeries extends PropertyNamingStrategy implements Serializable {


    @ApiModelProperty(value = "一组数据 名称")
    private String name;

    @ApiModelProperty(value = "线/柱 数据")
    private String[] data;

    @ApiModelProperty(value = "每个数据 名字")
    private String[] localName;

    public ChartSeries() {

    }

    public ChartSeries(String name, String[] data) {
        this.name = name;
        this.data = data;
        this.localName = new String[0];
    }

    public ChartSeries(String name, String[] data, String[] localName) {
        this.name = name;
        this.data = data;
        this.localName = localName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String[] getLocalName() {
        return localName;
    }

    public void setLocalName(String[] localName) {
        this.localName = localName;
    }

    @Override
    public String toString() {
        return "ChartSeries{" +
                "name='" + name + '\'' +
                ", data=" + Arrays.toString(data) +
                ", localName=" + Arrays.toString(localName) +
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

        ChartSeries that = (ChartSeries) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(data, that.data)) {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(localName, that.localName);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(data);
        result = 31 * result + Arrays.hashCode(localName);
        return result;
    }
}
