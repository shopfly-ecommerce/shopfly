/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.base.service;

/**
 * 图片验证码业务层
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018年3月19日 上午9:55:04
 */
public interface CaptchaManager {
    /**
     * 图片验证
     *
     * @param uuid  uid
     * @param code  验证码
     * @param scene 业务类型
     * @return
     */
    boolean valid(String uuid, String code, String scene);

    /**
     * 清除图片验证码
     *
     * @param uuid
     * @param code
     * @param scene
     */
    void deleteCode(String uuid, String code, String scene);

    /**
     * 图片生成
     *
     * @param uuid  uid
     * @param scene 业务类型
     */
    void writeCode(String uuid, String scene);

}
