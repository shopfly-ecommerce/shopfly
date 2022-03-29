/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.database;

/**
 * 列数据
 * @author Snow create in 2018/3/28
 * @version v2.0
 * @since v7.0.0
 */
public class ColumnMeta {

    /**
     * 字段名
     */
    private Object[] names;

    /**
     * 字段值
     */
    private Object[] values;

    public Object[] getNames() {
        return names;
    }

    public void setNames(Object[] names) {
        this.names = names;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }
}
