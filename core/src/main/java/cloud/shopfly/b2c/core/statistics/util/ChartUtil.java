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
package cloud.shopfly.b2c.core.statistics.util;

import cloud.shopfly.b2c.core.statistics.model.enums.QueryDateType;

import java.util.Objects;

/**
 * returnchart vo The utility class
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-10 In the morning11:18
 */
public class ChartUtil {


    /**
     * structurexAxis calibration
     *
     * @param circle type
     * @param year   years
     * @param month  month
     * @return xcalibration
     */
    public static String[] structureXAxis(String circle, Integer year, Integer month) {
        String[] xAxis = new String[Objects.equals(circle, QueryDateType.YEAR.name()) ? 12 : DataDisplayUtil.getMonthDayNum(month, year)];

        for (int i = 0; i < xAxis.length; i++) {
            xAxis[i] = i + 1 + "";
        }

        return xAxis;

    }

    /**
     * Construction group
     *
     * @param args parameter
     * @return Grouping array
     */
    public static String[] structureArray(String... args) {
        String[] legend = new String[args.length];
        int i = 0;
        for (String str : args) {
            legend[i] = str;
            i++;
        }
        return legend;

    }


}
