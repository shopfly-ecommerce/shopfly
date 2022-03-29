/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.pagecreate.model;

/**
 * 场景枚举
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-02 下午3:40
 */
public enum PageCreateEnum {

    //PAGE CREATE ENUM
    INDEX("首页"), GOODS("商品页"),HELP("帮助页面");

    String scene;

    PageCreateEnum(String scene) {
        this.scene = scene;
    }

    public String getScene() {
        return scene;
    }

}
