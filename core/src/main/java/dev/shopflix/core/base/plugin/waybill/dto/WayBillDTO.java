/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.plugin.waybill.dto;

/**
 * 电子面单dto
 *
 * @author zh
 * @version v7.0
 * @date 18/6/11 下午5:03
 * @since v7.0
 */

public class WayBillDTO {
    /**
     * 电子面单模板
     */
    private String template;
    /**
     * 物流公司编码
     */
    private String code;


    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "WayBillDTO{" +
                "template='" + template + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
