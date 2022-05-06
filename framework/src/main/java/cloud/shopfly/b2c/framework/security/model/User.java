/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.security.model;

import cloud.shopfly.b2c.framework.auth.AuthUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 * Created by kingapex on 2018/3/8.
 *
 * @author kingapex
 * @version 1.0
 * @since 6.4.0
 * 2018/3/8
 */
public class User implements AuthUser {

    /**
     * 会员id
     */
    private Integer uid;

    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 用户名
     */
    private String username;
    /**
     * 角色
     */
    private List<String> roles;

    public User() {

        roles = new ArrayList<>();
        this.test(String.class);
    }

    /**
     * 为用户定义角色
     *
     * @param roles 角色集合
     */
    public void add(String... roles) {
        for (String role : roles) {
            this.roles.add(role);
        }
    }

    void test(Class tClass) {


    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<String> roles) {
        this.roles= roles;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
