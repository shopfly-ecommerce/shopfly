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
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * BaseChart
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-11 上午11:12
 */
public class BaseChart extends PropertyNamingStrategy implements Serializable {


    @ApiModelProperty(value = "x轴 刻度")
    protected String[] xAxis;


    @ApiModelProperty(value = "y轴 刻度")
    protected String[] yAxis;

    public String[] getxAxis() {
        return xAxis;
    }

    public void setxAxis(String[] xAxis) {
        this.xAxis = xAxis;
    }

    public String[] getyAxis() {
        return yAxis;
    }

    public void setyAxis(String[] yAxis) {
        this.yAxis = yAxis;
    }
}
