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
 * 2018-04-10 In the morning7:51
 */

@JsonNaming(value = ChartSeries.class)
public class ChartSeries extends PropertyNamingStrategy implements Serializable {


    @ApiModelProperty(value = "A set of data names")
    private String name;

    @ApiModelProperty(value = "line/Column data")
    private String[] data;

    @ApiModelProperty(value = "Each data name")
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
