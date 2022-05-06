/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.base.context;

import cloud.shopfly.b2c.core.client.system.RegionsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by kingapex on 2018/5/2.
 * 注册自定义的地区格式化器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/2
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RegionsClient regionsClient;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new RegionFormatAnnotationFormatterFactory());
        registry.addFormatter(new RegionFormatter(regionsClient));
    }


}

