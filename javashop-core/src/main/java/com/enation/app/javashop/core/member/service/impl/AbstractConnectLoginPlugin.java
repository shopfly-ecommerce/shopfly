/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.service.impl;

import com.enation.app.javashop.core.base.DomainHelper;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.model.vo.Auth2Token;
import com.enation.app.javashop.core.member.model.vo.ConnectSettingVO;
import com.enation.app.javashop.core.member.service.ConnectManager;
import com.enation.app.javashop.framework.cache.Cache;
import com.enation.app.javashop.framework.context.ThreadContextHolder;
import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.logs.Debugger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录插件基类
 * @ClassName AbstractConnectLoginPlugin
 * @since v7.0 上午10:46 2018/6/5
 */

@Component
public abstract class AbstractConnectLoginPlugin {

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    protected Cache cache;

    @Autowired
    private DomainHelper domainHelper;


    @Autowired
    protected Debugger debugger;


    /**
     * 初始化信息登录的参数
     */
    protected Map initConnectSetting() {

        return connectManager.initConnectSetting();
    }

    /**
     * 获取授权登录的url
     *
     * @return
     */
    public abstract String getLoginUrl();

    /**
     * 登录成功后的回调方法
     *
     * @return
     */
    public abstract Auth2Token loginCallback();

    /**
     * 填充会员信息
     *
     * @param auth2Token
     * @param member
     * @return
     */
    public abstract Member fillInformation(Auth2Token auth2Token, Member member);

    /**
     * 存储中间页信息及拼接回调地址
     *
     * @param type 登录类型
     * @return
     */
    protected String getCallBackUrl(String type) {
        //拼接回调域名
        String domain = domainHelper.getCallback();
        String calback;
        //如果是会员中心进行绑定,回调地址为会员中心回调地址
        if (UserContext.getBuyer() != null ) {

            if(!"QQ".equals(type)){
                calback = domain + "/passport/account-binder/" + type + "/callback?uid=" + UserContext.getBuyer().getUid();
            }else{
                if (isWap()) {
                    calback = domain + "/passport/connect/wap/" + type + "/callback?uid=" + UserContext.getBuyer().getUid();
                } else {
                    calback = domain + "/passport/connect/pc/" + type + "/callback?uid=" + UserContext.getBuyer().getUid();
                }
            }
        } else {
            if (isWap()) {
                calback = domain + "/passport/connect/wap/" + type + "/callback";
            } else {
                calback = domain + "/passport/connect/pc/" + type + "/callback";
            }
        }
        debugger.log("生成callback：", calback);

        return calback;

    }

    /**
     * 拼装配置参数
     *
     * @return
     */
    public abstract ConnectSettingVO assembleConfig();

    /**
     * 判断是否是wap访问
     *
     * @return 是否是wap
     */
    private boolean isWap() {
        boolean flag = false;

        String header = ThreadContextHolder.getHttpRequest().getHeader("User-Agent");
        if (header == null) {
            return flag;
        }

        String[] keywords = {"Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser", "Mobile"};
        for (String s : keywords) {
            if (header.contains(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
