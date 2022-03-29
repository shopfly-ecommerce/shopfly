/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base;

/**
 * 图片验证码业务类型
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月19日 下午4:35:32
 */
public enum SceneType {

    /**
     * 验证码登录
     */
    LOGIN("验证码登录"),
    /**
     * 手机注册
     */
    REGISTER("手机注册"),
    /**
     * 找回密码
     */
    FIND_PASSWORD("找回密码"),
    /**
     * 绑定手机
     */
    BIND_MOBILE("绑定手机"),
    /**
     * 修改密码
     */
    MODIFY_PASSWORD("修改密码"),
    /**
     * 添加店员
     */
    ADD_CLERK("添加店员"),
    /**
     * 验证手机
     */
    VALIDATE_MOBILE("验证手机");

    private String description;

    SceneType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
