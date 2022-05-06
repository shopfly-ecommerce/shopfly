/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.auth;

/**
 * Token创建接口
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-06-21
 */
public interface TokenCreater {


    /**
     * 创建token
     * @param user 用户
     * @return token
     */
    Token create(AuthUser user);

}
