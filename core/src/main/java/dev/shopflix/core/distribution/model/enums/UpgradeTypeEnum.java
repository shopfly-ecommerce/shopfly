/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.model.enums;

/**
 * 模版切换状态枚举
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/25 上午11:39
 */
public enum UpgradeTypeEnum {
    //提现状态
    MANUAL("手动"), AUTOMATIC("自动");

    private String name;

    UpgradeTypeEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
