/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.database;

/**
 * 元数据构建器
 * 构建模型构建执行sql的构建器
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
public interface SqlMetaBuilder {

    /**
     * 通过一个模型，获取新增数据的元数据信息
     * @param <T> model的类型
     * @param model 模型
     * @return insert的元数据
     */
    <T> DataMeta insert(T model);

    /**
     * 通过一个模型，获取修改数据的元数据信息
     * @param <T>  model的类型
     * @param model 模型
     * @param id 主键值
     * @return update的元数据
     */
    <T> DataMeta update(T model, Integer id);

    /**
     * 通过Class，获取查询一行数据的sql
     * @param clazz 模型的类型
     * @return 查询一个模型的sql，其中已经拼接好根据主键查询的的where条件
     */
    String queryForModel(Class clazz);

    /**
     * 通过Class，获取删除一行数据的sql
     * @param clazz 模型的类型
     * @return 删除的sql语句，其中已经拼接好根据主键删除的where条件
     */
    String delete(Class clazz);
}
