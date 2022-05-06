/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.tool.support;

import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;

import java.util.List;

/**
 * sku 名称生成工具
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-03-05
 */

public class SkuNameUtil {

    public  static  String createSkuName(String specs) {
        if (StringUtil.isEmpty(specs)) {
            return "";
        }
        List<SpecValueVO> specList  = JsonUtil.jsonToList(specs, SpecValueVO.class);

        StringBuffer skuName = new StringBuffer();
        specList.forEach(specValueVO -> {
            skuName.append(" ");
            skuName.append(specValueVO.getSpecValue());
        });
        return skuName.toString();
    }

}
