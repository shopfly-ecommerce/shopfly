/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.base.context;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * kingapex on 2018/5/2.
 * 地区格式化工厂
 * 被@RegionFormat注解的属性，会接受一个地区id转换为地区对象
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/2
 */
public class RegionFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<RegionFormat> {

    /**
     * 获取被注解对象的类型
     *
     * @return
     */
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(asList(Region.class));
    }

    /**
     * 获取输出对象
     *
     * @param annotation 注解实例
     * @param fieldType  被注解字段的类型
     * @return 地区格式化后输出的对象
     */
    @Override
    public Printer<?> getPrinter(RegionFormat annotation, Class<?> fieldType) {
        return new RegionFormatter();
    }

    /**
     * 获取解析器
     *
     * @param annotation 注解实例
     * @param fieldType  被注解字段的类型
     * @return 地区格式化后对象
     */
    @Override
    public Parser<?> getParser(RegionFormat annotation, Class<?> fieldType) {
        return new RegionFormatter();
    }
}
