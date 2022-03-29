/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.plugin.alipay.executor;
/**
 * javashop支付相应对象
 *
 * @author zh
 * @version v7.0
 * @date 18/7/19 下午4:47
 * @since v7.0
 */

import com.alipay.api.AlipayResponse;
import dev.shopflix.core.payment.model.vo.Form;

public class JavaShopPayResponse extends AlipayResponse {
    /**
     * 组织好数据结构的表单信息
     */
    private Form form;


    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return "JavaShopPayResponse{" +
                "form=" + form +
                '}';
    }
}
