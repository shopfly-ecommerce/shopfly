/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.service;

import com.enation.app.javashop.core.member.model.dos.ConnectDO;
import com.enation.app.javashop.core.member.model.dos.ConnectSettingDO;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.model.dto.ConnectSettingDTO;
import com.enation.app.javashop.core.member.model.enums.ConnectTypeEnum;
import com.enation.app.javashop.core.member.model.vo.ConnectSettingVO;
import com.enation.app.javashop.core.member.model.vo.ConnectVO;
import com.enation.app.javashop.core.member.model.vo.MemberVO;
import com.enation.app.javashop.core.member.service.impl.AbstractConnectLoginPlugin;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录业务类
 * @ClassName ConnectManager
 * @since v7.0 下午8:54 2018/6/6
 */
public interface ConnectManager {

    /**
     * 微信授权
     */
    void wechatAuth();

    /**
     * 微信授权回调
     */
    void wechatAuthCallBack();

    /**
     * 微信绑定登录
     *
     * @param uuid 客户端唯一标示
     * @return
     */
    Map bindLogin(String uuid);

    /**
     * 绑定账号
     *
     * @param name        用户名
     * @param password    密码
     * @param connectUuid 联合登录uuid
     * @param uuid        uuid
     * @return
     */
    Map bind(String name, String password, String connectUuid, String uuid);

    /**
     * 会员中心绑定账号
     *
     * @param uuid 唯一号
     * @param uid  用户id
     * @return
     */
    Map bind(String uuid, Integer uid);

    /**
     * 发起信任登录
     *
     * @param type
     * @param port
     * @param member
     */
    void initiate(String type, String port, String member);

    /**
     * 信任登录回调
     *
     * @param type
     * @param member
     * @param uuid
     * @return
     */
    MemberVO callBack(String type, String member, String uuid);

    /**
     * 注册会员并绑定
     *
     * @param uuid
     */
    void registerBind(String uuid);

    /**
     * 会员解除绑定
     *
     * @param type 登录类型
     */
    void unbind(String type);

    /**
     * 会员绑定openid
     *
     * @param uuid
     * @return
     */
    Map openidBind(String uuid);


    /**
     * 获取app联合登录所需参数
     *
     * @param type
     * @return
     */
    String getParam(String type);

    /**
     * 检测openid是否绑定
     *
     * @param type
     * @param openid
     * @return
     */
    Map checkOpenid(String type, String openid);

    /**
     * 发送手机校验验证码
     *
     * @param mobile
     */
    void sendCheckMobileSmsCode(String mobile);

    /**
     * WAP手机号绑定
     *
     * @param mobile
     * @param uuid
     * @return
     */
    Map mobileBind(String mobile, String uuid);


    /**
     * 获取会员绑定列表
     *
     * @return
     */
    List<ConnectVO> get();

    /**
     * 获取后台信任登录参数
     *
     * @return
     */
    List<ConnectSettingVO> list();

    /**
     * 保存信任登录信息
     *
     * @param connectSettingDTO
     * @return
     */
    ConnectSettingDTO save(ConnectSettingDTO connectSettingDTO);

    /**
     * 获取授权登录参数
     *
     * @param type 授权登录类型
     * @return
     */
    ConnectSettingDO get(String type);

    /**
     * 根据type获取相应的插件类
     *
     * @param type
     * @return
     */
    AbstractConnectLoginPlugin getConnectionLogin(ConnectTypeEnum type);

    /**
     * 微信退出解绑操作
     */
    void wechatOut();

    /**
     * ios APP 第三方登录获取授权url
     *
     * @return
     */
    String getAliInfo();


    /**
     * app用户绑定
     *
     * @param member
     * @param openid
     * @param type
     * @param uuid
     * @return
     */
    Map appBind(Member member, String openid, String type, String uuid);

    /**
     * 初始化配置参数
     *
     * @return
     */
    Map initConnectSetting();

    /**
     * 小程序登录
     *
     * @param content
     * @param uuid
     * @return
     */
    Map miniProgramLogin(String content, String uuid);

    /**
     * 解密
     *
     * @param code
     * @param encryptedData
     * @param uuid
     * @param iv
     * @return
     */
    Map decrypt(String code, String encryptedData, String uuid, String iv);

    /**
     * 获取微信小程序码
     *
     * @param accessTocken
     * @return
     */
    String getWXACodeUnlimit(String accessTocken, int goodsId);

    /**
     * 获取 联合登录对象
     *
     * @param memberId  会员id
     * @param unionType 类型
     * @return ConnectDO
     */
    ConnectDO getConnect(Integer memberId, String unionType);

    /**
     * 解密，获取信息
     *
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    JSONObject getUserInfo(String encryptedData, String sessionKey, String iv);
}
