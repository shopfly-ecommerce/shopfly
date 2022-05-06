/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.elasticsearch;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * Created by kingapex on 2019-02-13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-13
 */
public interface EsTemplateBuilder {

     /**
      * 构建 es template
      * @return
      */
     ElasticsearchTemplate build();


}
