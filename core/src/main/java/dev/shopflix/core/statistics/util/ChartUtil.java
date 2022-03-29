/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.util;

import dev.shopflix.core.statistics.model.enums.QueryDateType;

import java.util.Objects;

/**
 * 返回chart vo 的工具类
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-04-10 上午11:18
 */
public class ChartUtil {


    /**
     * 构造x轴刻度
     *
     * @param circle 类型
     * @param year   年
     * @param month  月
     * @return x刻度
     */
    public static String[] structureXAxis(String circle, Integer year, Integer month) {
        String[] xAxis = new String[Objects.equals(circle, QueryDateType.YEAR.name()) ? 12 : DataDisplayUtil.getMonthDayNum(month, year)];

        for (int i = 0; i < xAxis.length; i++) {
            xAxis[i] = i + 1 + "";
        }

        return xAxis;

    }

    /**
     * 构造分组
     *
     * @param args 参数
     * @return 分组数组
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
