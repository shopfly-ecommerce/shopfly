/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 配送模板字幕版地区
 * @date 2018/10/24 16:12
 * @since v7.0.0
 */
public class ShipTemplateChildArea implements Serializable {

    private String name;

    private List<ShipTemplateChildArea> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShipTemplateChildArea> getChildren() {
        return children;
    }

    public void setChildren(List<ShipTemplateChildArea> children) {
        this.children = children;
    }
}
