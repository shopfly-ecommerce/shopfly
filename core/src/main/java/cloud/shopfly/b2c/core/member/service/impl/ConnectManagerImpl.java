/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.service.impl;

import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.ConnectDO;
import cloud.shopfly.b2c.core.member.model.dos.ConnectSettingDO;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dto.ConnectSettingDTO;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.vo.*;
import cloud.shopfly.b2c.core.member.plugin.alipay.AlipayAbstractConnectLoginPlugin;
import cloud.shopfly.b2c.core.member.plugin.wechat.WechatAbstractConnectLoginPlugin;
import cloud.shopfly.b2c.core.member.plugin.weibo.WeiboAbstractConnectLoginPlugin;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.framework.util.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import cloud.shopfly.b2c.core.base.service.FileManager;
import cloud.shopfly.b2c.core.base.service.SmsManager;
import cloud.shopfly.b2c.core.distribution.util.ShortUrlGenerator;
import dev.shopflix.core.member.model.vo.*;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.framework.ShopflixConfig;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.*;


/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录业务类
 * @ClassName ConnectManagerImpl
 * @since v7.0 下午8:55 2018/6/6
 */
@Service
public class ConnectManagerImpl implements ConnectManager {

    @Autowired

    private DaoSupport memberDaoSupport;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private Cache cache;

    @Autowired
    private DomainHelper domainHelper;

    @Autowired
    private ShopflixConfig shopflixConfig;

    @Autowired
    private SettingClient settingClient;

    @Autowired
    private SmsManager smsManager;

    @Autowired
    private WechatAbstractConnectLoginPlugin wechatAbstractConnectLoginPlugin;

    @Autowired
    private cloud.shopfly.b2c.core.member.plugin.qq.QQConnectLoginPlugin QQConnectLoginPlugin;

    @Autowired
    private WeiboAbstractConnectLoginPlugin weiboAbstractConnectLoginPlugin;

    @Autowired
    private AlipayAbstractConnectLoginPlugin alipayAbstractConnectLoginPlugin;

    @Autowired
    private Debugger debugger;

    private static Long time = 2592000L;


    /**
     * 日志记录
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void wechatAuth() {
        try {
            ConnectTypeEnum connectTypeEnum = ConnectTypeEnum.valueOf(ConnectTypeEnum.WECHAT.value());
            AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);
            ThreadContextHolder.getHttpResponse().sendRedirect(connectionLogin.getLoginUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map bind(String uuid, Integer uid) {
        //获取当前的用户信息
        Member member = memberManager.getModel(uid);
        //对会员的状态进行校验，已禁用的会员不允许绑定
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E124.code(), "当前会员已禁用");
        }
        Map map = new HashMap(4);
        Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);
        if (auth2Token == null) {
            throw new ServiceException(MemberErrorCode.E133.name(), "redis授权信息不存在");
        }
        String sql = "select * from es_connect where union_id = ? and union_type = ?";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, auth2Token.getUnionid(), auth2Token.getType());
        //如果会员授权登录信息union_id不为空则给予相应提示,询问是否更换
        if (connectDO != null && !StringUtil.isEmpty(connectDO.getUnionId())) {
            map.put("result", "existed");
        } else {
            //根据如果会员授权信息存在则进行更新操作，不存在则进行添加操作
            sql = "select * from es_connect where member_id = ? and union_type = ?";
            connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, member.getMemberId(), auth2Token.getType());
            if (connectDO == null) {
                connectDO = new ConnectDO();
                connectDO.setMemberId(member.getMemberId());
                connectDO.setUnionType(auth2Token.getType());
                connectDO.setUnionId(auth2Token.getUnionid());
                connectDO.setUnboundTime(DateUtil.getDateline());
                this.memberDaoSupport.insert(connectDO);
            } else {
                sql = "update es_connect set union_id = ? where id = ?";
                this.memberDaoSupport.execute(sql, auth2Token.getUnionid(), connectDO.getId());
            }
            map.put("result", "bind_success");
        }
        return map;
    }

    @Override
    public void wechatAuthCallBack() {
        try {
            //cookie 有效期: 30天
            final int maxAge = 30 * 24 * 60 * 60;
            //生成uuid
            String uuid = UUID.randomUUID().toString();
            //根据类型获取对应的插件

            AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(ConnectTypeEnum.WECHAT);
            //获取微信授权信息
            Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);

            if (auth2Token == null) {
                auth2Token = connectionLogin.loginCallback();
                //当需要测试时，打开此注释，注释掉上一句
                //auth2Token = new Auth2Token();
                //auth2Token.setUnionid("unionId123 ");
                //auth2Token.setOpneId("openid123");
                //auth2Token.setAccessToken("accessToken123");
                // auth2Token.setType(ConnectTypeEnum.WECHAT.value());
            }
            //将授权信息放入缓存,缓存失效时间为30天
            cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token, maxAge);
            if (logger.isDebugEnabled()) {
                this.logger.debug(new Date() + " " + uuid + " 授权信息放到缓存存中，失效时间为30天");
            }
            //将标示存入cookie
            Cookie cookie = new Cookie("uuid_connect", uuid);
            cookie.setDomain(domainHelper.getTopDomain());
            cookie.setPath("/");
            cookie.setMaxAge(maxAge);


            Cookie wechatCookie = new Cookie("is_wechat_auth", "1");
            wechatCookie.setDomain(domainHelper.getTopDomain());
            wechatCookie.setPath("/");
            wechatCookie.setMaxAge(maxAge);


            Cookie uuidCookie = new Cookie("uuid", uuid);
            uuidCookie.setDomain(domainHelper.getTopDomain());
            uuidCookie.setPath("/");
            //30天
            uuidCookie.setMaxAge(maxAge);

            ThreadContextHolder.getHttpResponse().addCookie(wechatCookie);
            ThreadContextHolder.getHttpResponse().addCookie(cookie);
            ThreadContextHolder.getHttpResponse().addCookie(uuidCookie);
            //跳转到首页
            ThreadContextHolder.getHttpResponse().sendRedirect(domainHelper.getMobileDomain());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("微信信任登录回调出错", e);
            }
        }
    }


    @Override
    public Map bindLogin(String uuid) {
        Map map = new HashMap(3);
        Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);
        if (auth2Token == null) {
            if (logger.isDebugEnabled()) {
                this.logger.debug(new Date() + " " + uuid + " 授权信息失效");
            }


            cleanCookie();
            throw new ServiceException(MemberErrorCode.E133.name(), "授权超时，请重新授权");
        }
        String sql = "select * from es_connect where union_type = ? and  union_id = ? ";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, ConnectTypeEnum.WECHAT.value(), auth2Token.getUnionid());

        //检验第三方登录信息是否存在，存在的话根据memberId获取会员信息
        if (connectDO != null) {
            Member member = memberManager.getModel(connectDO.getMemberId());

            //检验会员是否存在，存在的话执行登录操作
            if (member != null) {
                MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);
                map.put("access_token", memberVO.getAccessToken());
                map.put("refresh_token", memberVO.getRefreshToken());
                map.put("uid", memberVO.getUid());
            }
        }
        return map;
    }

    @Override
    public void initiate(String type, String port, String member) {
        try {
            ConnectTypeEnum connectTypeEnum = ConnectTypeEnum.valueOf(type);

            AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);
            if (connectionLogin == null) {
                throw new ServiceException(MemberErrorCode.E130.name(), "不支持的登录方式");
            }
            debugger.log("根据类型[" + type + "]调起登录插件：[" + connectionLogin + "]");
            String loginUrl = connectionLogin.getLoginUrl();
            debugger.log("跳转url为：");
            debugger.log(loginUrl);
            ThreadContextHolder.getHttpResponse().sendRedirect(loginUrl);

        } catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            throw new ServiceException(MemberErrorCode.E131.name(), "联合登录失败");
        }
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberVO callBack(String type, String mem, String uuid) {
        //根据回调获取对应的插件，并获取相应的openid
        ConnectTypeEnum connectTypeEnum = ConnectTypeEnum.valueOf(type);
        AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);

        debugger.log("调起插件:" + connectionLogin);
        Auth2Token auth2Token = connectionLogin.loginCallback();
        //根据openid查询是否有会员绑定过
        String sql = "select * from es_connect where union_type = ? and  union_id = ? ";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, type, auth2Token.getUnionid());

        debugger.log("获取绑定信息:" + connectDO);

        Member member = null;
        if (connectDO != null) {
            member = memberManager.getModel(connectDO.getMemberId());
            debugger.log("获取绑定的会员:" + member);

        }
        //将信任登录的相关信息存入redis中
        auth2Token.setType(type);
        cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token, shopflixConfig.getCaptchaTimout());

        debugger.log("将token信息写入缓存，超时时间为：" + shopflixConfig.getCaptchaTimout());

        if (logger.isDebugEnabled()) {
            this.logger.debug(new Date() + " " + uuid + " 登录授权，授权时间为" + shopflixConfig.getCaptchaTimout());
        }
        //如果在会员中心绑定账号，不需要返回新的会员信息
        if ("member".equals(mem)) {
            return null;
        }
        MemberVO memberVO = null;
        if (member != null) {
            debugger.log("进行会员登录操作");
            memberVO = memberManager.connectLoginHandle(member, uuid);
            debugger.log("生成vo:");
            debugger.log(memberVO.toString());

        }
        return memberVO;

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void registerBind(String uuid) {
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());

        //获取redis中存储的数据 填充会员信息关联相应的登录方式id
        Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);
        if (auth2Token == null) {
            if (logger.isDebugEnabled()) {
                this.logger.debug(new Date() + " " + uuid + " 自动登录授权找不到");
            }
            cleanCookie();
            throw new ServiceException(MemberErrorCode.E133.name(), "授权超时，请重新授权");
        }
        ConnectTypeEnum connectTypeEnum = ConnectTypeEnum.valueOf(auth2Token.getType());
        AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);
        //更新会员信息
        member = connectionLogin.fillInformation(auth2Token, member);
        memberManager.edit(member, buyer.getUid());
        //组织数据保存新人登录信息
        ConnectDO connectDO = new ConnectDO();
        connectDO.setMemberId(member.getMemberId());
        connectDO.setUnionType(auth2Token.getType());
        connectDO.setUnionId(auth2Token.getUnionid());
        this.memberDaoSupport.insert(connectDO);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void unbind(String type) {
        Integer memberId = UserContext.getBuyer().getUid();

        String sql = "select * from es_connect where member_id = ? and union_type = ?";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, memberId, type);

        if (connectDO == null) {
            throw new ServiceException(MemberErrorCode.E134.name(), "会员未绑定相关账号");
        }
        //30天内不可重复解绑
        /*if (DateUtil.getDateline() - (connectDO.getUnboundTime() == null ? 0 : connectDO.getUnboundTime()) < time) {
            throw new ServiceException(MemberErrorCode.E135.name(), "30天内不可重复解绑");
        }*/

        sql = "update es_connect set unbound_time = ? , union_id = ? where id = ?";
        this.memberDaoSupport.execute(sql, DateUtil.getDateline(), "", connectDO.getId());
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map openidBind(String uuid) {
        Map map = new HashMap(4);
        Auth2Token auth2Token = (Auth2Token) cache.get(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid);
        if (auth2Token != null) {
            //检验此微信是否已经绑定过其他的用户
            String sql = "select * from es_connect where union_id = ? and union_type = ?";
            ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, auth2Token.getUnionid(), auth2Token.getType());
            if (connectDO != null) {
                throw new ServiceException(MemberErrorCode.E143.code(), "已绑定其他用户,请解绑后再操作");
            }
            Member model = memberManager.getModel(UserContext.getBuyer().getUid());
            MemberVO memberVO = memberManager.connectLoginHandle(model, uuid);
            map.put("result", "bind_success");
            map.put("access_token", memberVO.getAccessToken());
            map.put("refresh_token", memberVO.getRefreshToken());
            //更新会员openid 如果存在更新此条数据，如果不存在添加一条
            sql = "select * from es_connect where member_id = ? and union_type = ?";
            connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, UserContext.getBuyer().getUid(), auth2Token.getType());
            if (connectDO == null) {
                connectDO = new ConnectDO();
                connectDO.setMemberId(UserContext.getBuyer().getUid());
                connectDO.setUnionId(auth2Token.getUnionid());
                connectDO.setUnionType(auth2Token.getType());
                this.memberDaoSupport.insert(connectDO);
            } else {
                sql = "update es_connect set union_id = ? where id = ?";
                this.memberDaoSupport.execute(sql, auth2Token.getUnionid(), connectDO.getId());
            }
        } else {
            cleanCookie();
            throw new ServiceException(MemberErrorCode.E133.name(), "授权超时，请重新授权");
        }
        return map;
    }


    @Override
    public String getParam(String type) {
        try {

            Map map = new HashMap(16);
            List<ConnectSettingVO> list = this.list();

            for (ConnectSettingVO connectSettingVO : list) {
                if (connectSettingVO.getType().equals(type)) {
                    List<ConnectSettingParametersVO> configList = connectSettingVO.getClientList();
                    for (ConnectSettingParametersVO connectSettingParametersVO : configList) {
                        List<ConnectSettingConfigItem> lists = connectSettingParametersVO.getConfigList();
                        for (ConnectSettingConfigItem connectSettingConfigItem : lists) {
                            map.put(connectSettingConfigItem.getKey(), connectSettingConfigItem.getValue());
                        }
                    }
                }
            }
            String globalAuthKey = JsonUtil.jsonToObject(settingClient.get(SettingGroup.SITE), SiteSetting.class).getGlobalAuthKey();
            return AESUtil.encrypt(map.toString(), globalAuthKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Map checkOpenid(String type, String openid) {

        String sql = "select * from es_connect where union_type = ? and  union_id = ? ";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, type, openid);
        Member member = null;
        if (connectDO != null) {
            member = memberManager.getModel(connectDO.getMemberId());
        }

        Map map = new HashMap(4);
        if (member != null) {
            //会员登录
            MemberVO memberVO = memberManager.loginHandle(member);
            map.put("is_bind", true);
            map.put("access_token", memberVO.getAccessToken());
            map.put("refresh_token", memberVO.getRefreshToken());
            map.put("uid", memberVO.getUid());
        } else {
            map.put("is_bind", false);
        }
        return map;
    }

    @Override
    public void sendCheckMobileSmsCode(String mobile) {
        if (!Validator.isMobile(mobile)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "手机号码格式不正确");
        }
        //校验会员是否存在
        Member member = memberManager.getMemberByMobile(mobile);
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E123.code(), "当前会员不存在");
        }
        //发送验证码短信
        smsManager.sendSmsMessage("校验手机操作", mobile, SceneType.VALIDATE_MOBILE);

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map mobileBind(String mobile, String uuid) {
        //校验会员是否存在
        Member member = memberManager.getMemberByMobile(mobile);
        //校验当前会员是否存在
        if (member == null) {
            throw new ServiceException(MemberErrorCode.E123.code(), "当前会员不存在");
        }
        return this.binding(member, uuid, uuid);

    }

    @Override
    public List<ConnectVO> get() {
        Buyer buyer = UserContext.getBuyer();
        String sql = "select * from es_connect where member_id = ? ";
        List<ConnectDO> connectDOS = this.memberDaoSupport.queryForList(sql, ConnectDO.class, buyer.getUid());
        List<ConnectVO> list = new ArrayList<>();

        for (ConnectTypeEnum connectTypeEnum : ConnectTypeEnum.values()) {
            ConnectVO connectVO = new ConnectVO();
            connectVO.setUnionType(connectTypeEnum.name());
            connectVO.setIsBind(false);
            for (ConnectDO connectDO : connectDOS) {
                if (connectTypeEnum.name().equals(connectDO.getUnionType())) {
                    connectVO.setUnionType(connectDO.getUnionType());
                    if (!StringUtil.isEmpty(connectDO.getUnionId())) {
                        connectVO.setIsBind(true);
                    }

                }
            }
            list.add(connectVO);
        }
        return list;
    }

    @Override
    public List<ConnectSettingVO> list() {
        String sql = "select * from es_connect_setting ";
        List<ConnectSettingVO> connectSetting = this.memberDaoSupport.queryForList(sql, ConnectSettingVO.class);
        //获取已经存在的授权类型
        List<String> list = new ArrayList<>();
        if (connectSetting.size() > 0) {
            for (ConnectSettingVO connectSettingDO : connectSetting) {
                list.add(connectSettingDO.getType());
            }
        } else {
            connectSetting = new ArrayList<>();
        }
        //如果不存在则需重新生成一条数据
        for (ConnectTypeEnum connectTypeEnum : ConnectTypeEnum.values()) {
            if (!list.contains(connectTypeEnum.value())) {
                AbstractConnectLoginPlugin connectionLogin = this.getConnectionLogin(connectTypeEnum);
                if (null == connectionLogin) {
                    continue;
                }
                ConnectSettingVO connectSettingVO = connectionLogin.assembleConfig();
                connectSetting.add(connectSettingVO);
            }
        }
        return connectSetting;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ConnectSettingDTO save(ConnectSettingDTO connectSettingDTO) {

        ConnectSettingDO connectSettingDO = new ConnectSettingDO();
        connectSettingDO.setType(connectSettingDTO.getType());
        connectSettingDO.setName(connectSettingDTO.getName());
        connectSettingDO.setConfig(JsonUtil.objectToJson(connectSettingDTO.getClientList()));
        String sql = "select * from es_connect_setting where type = ? ";
        ConnectSettingDO connectSetting = this.memberDaoSupport.queryForObject(sql, ConnectSettingDO.class, connectSettingDTO.getType());

        if (connectSetting != null) {
            connectSettingDO.setId(connectSettingDTO.getId());
            this.memberDaoSupport.update(connectSettingDO, connectSettingDO.getId());
        } else {
            this.memberDaoSupport.insert(connectSettingDO);
        }
        return connectSettingDTO;
    }

    @Override
    public ConnectSettingDO get(String type) {
        String sql = "select * from es_connect_setting where type = ? ";
        return this.memberDaoSupport.queryForObject(sql, ConnectSettingDO.class, type);
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map bind(String username, String password, String uuidConnect, String uuid) {
        //校验会员账号密码正确性
        Member member = memberManager.validation(username, password);
        return this.binding(member, uuidConnect, uuid);
    }


    /**
     * 根据type获取相应的插件类
     *
     * @param type
     * @return
     */
    @Override
    public AbstractConnectLoginPlugin getConnectionLogin(ConnectTypeEnum type) {

        switch (type) {
            case QQ:
                return QQConnectLoginPlugin;
            case WECHAT:
                return wechatAbstractConnectLoginPlugin;
            case WEIBO:
                return weiboAbstractConnectLoginPlugin;
            case ALIPAY:
                return alipayAbstractConnectLoginPlugin;
            default:
        }
        return null;
    }


    /**
     * 根据登录类型获取会员相应的openid 判断是否绑定返回相应的信息
     *
     * @param member
     * @param uuidConnect
     * @param uuid
     * @return
     */
    private Map binding(Member member, String uuidConnect, String uuid) {
        Map map = this.bind(uuidConnect, member.getMemberId());
        if (!"existed".equals(map.get("result"))) {
            //对会员的状态进行校验，已禁用的会员不允许绑定
            MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);
            map.put("access_token", memberVO.getAccessToken());
            map.put("refresh_token", memberVO.getRefreshToken());
            map.put("uid", memberVO.getUid());
        }
        return map;
    }

    @Override
    public void wechatOut() {
        //解除绑定
        this.unbind(ConnectTypeEnum.WECHAT.value());
        //注销会员
        memberManager.logout(UserContext.getBuyer().getUid());
    }


    @Override
    public String getAliInfo() {
        //将参数值放入map
        Map<String, String> alipayMap = new HashMap<>();
        ConnectSettingDO pay = this.get(ConnectTypeEnum.ALIPAY.value());
        ConnectSettingVO alipay = new ConnectSettingVO();
        BeanUtil.copyProperties(pay, alipay);
        List<ConnectSettingParametersVO> clientList = alipay.getClientList();
        for (ConnectSettingParametersVO connectSettingParametersVO : clientList) {
            List<ConnectSettingConfigItem> lists = connectSettingParametersVO.getConfigList();
            for (ConnectSettingConfigItem connectSettingConfigItem : lists) {
                alipayMap.put(connectSettingConfigItem.getKey(), connectSettingConfigItem.getValue());
            }
        }

        //拼接参数
        String appId = alipayMap.get("alipay_app_app_id");
        String pid = alipayMap.get("alipay_app_pid");
        String targetId = DateUtil.getDateline() + "" + DateUtil.getDateline() + "" + DateUtil.getDateline() + "00";
        Map<String, String> map = new HashMap<>();
        map.put("apiname", "com.alipay.account.auth");
        map.put("method", "alipay.open.auth.sdk.code.get");
        map.put("app_id", appId);
        map.put("app_name", "mc");
        map.put("biz_type", "openservice");
        map.put("pid", pid);
        map.put("product_id", "APP_FAST_LOGIN");
        map.put("scope", "kuaijie");
        map.put("target_id", targetId);
        map.put("auth_type", "AUTHACCOUNT");
        map.put("sign_type", "RSA2");
        //获取生成sign的参数url
        String content = getSignContent(map);
        String sign = "";
        try {
            sign = AlipaySignature.rsaSign(content, alipayMap.get("alipay_app_private_key"), "UTF-8");
            map.put("sign", sign);
            sign = getSignContent(map);
        } catch (Exception e) {
            logger.error("生成支付宝签名错误===" + e.getMessage());
        }
        return sign;
    }

    @Override
    public Map appBind(Member member, String openid, String type, String uuid) {
        //对会员的状态进行校验，已禁用的会员不允许绑定
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E124.code(), "当前会员已禁用");
        }
        Map map = new HashMap(4);

        String sql = "select * from es_connect where member_id = ? and union_type = ?";
        ConnectDO connectDO = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, member.getMemberId(), type);
        MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);
        // 当前会员已经绑定了其他的账号
        if (connectDO != null && !StringUtil.isEmpty(connectDO.getUnionId())) {
            throw new ServiceException(MemberErrorCode.E124.code(), "此账号已被绑定，请先解绑才能继续绑定");
        } else {
            //如果会员授权信息存在则进行更新操作，不存在则进行添加操作
            if (connectDO == null) {
                connectDO = new ConnectDO();
                connectDO.setMemberId(member.getMemberId());
                connectDO.setUnionType(type);
                connectDO.setUnionId(openid);
                this.memberDaoSupport.insert(connectDO);
            } else {
                sql = "update es_connect set  union_id = ? where id = ?";
                this.memberDaoSupport.execute(sql, openid, connectDO.getId());
            }
            map.put("access_token", memberVO.getAccessToken());
            map.put("refresh_token", memberVO.getRefreshToken());
            map.put("uid", memberVO.getUid());
        }
        return map;
    }

    /**
     * 初始化配置参数
     *
     * @return
     */
    @Override
    public Map initConnectSetting() {
        Map map = new HashMap();
        List<ConnectSettingVO> list = this.list();
        for (ConnectSettingVO connectSettingVO : list) {
            List<ConnectSettingParametersVO> configList = connectSettingVO.getClientList();
            for (ConnectSettingParametersVO connectSettingParametersVO : configList) {
                List<ConnectSettingConfigItem> lists = connectSettingParametersVO.getConfigList();
                for (ConnectSettingConfigItem connectSettingConfigItem : lists) {
                    map.put(connectSettingConfigItem.getKey(), connectSettingConfigItem.getValue());
                }
            }
        }
        debugger.log("获取参数：", map.toString());
        return map;

    }

    @Override
    public Map miniProgramLogin(String content, String uuid) {
        Map res = new HashMap(16);
        JSONObject json = JSONObject.fromObject(content);
        //获取不到unionid
        if (json.get("unionid") == null) {
            res.put("autologin", "fail");
            res.put("reson", "unionid_not_found");
            //存储sessionkey
            String sessionKey = json.getString("session_key");
            cache.put(CachePrefix.SESSION_KEY.getPrefix() + uuid, sessionKey);
            return res;
        }
        String unionId = json.getString("unionid");
        if (content != null) {
            //存储uuid和unionId的关系
            Auth2Token auth2Token = new Auth2Token();
            auth2Token.setType(ConnectTypeEnum.WECHAT.value());
            auth2Token.setUnionid(unionId);
            String openid = json.getString("openid");
            //openid用于注册绑定时获取登录的微信的信息
            auth2Token.setOpneId(openid);
            if (logger.isDebugEnabled()) {
                logger.debug("微信小程序登录openId为：" + openid);
            }
            cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token);
        }
        //使用unionid读取数据库绑定数据
        String sql = "select * from es_connect where union_id = ? and union_type = ?";
        ConnectDO connect = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, unionId, ConnectTypeEnum.WECHAT.value());
        //读到了unionid，但没有找到账号
        if (connect == null) {
            res.put("autologin", "fail");
            res.put("reson", "account_not_found");
            return res;
        }
        //验证通过可以正常登录
        Integer memberId = connect.getMemberId();
        Member member = memberManager.getModel(memberId);
        MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);

        res.put("access_token", memberVO.getAccessToken());
        res.put("refresh_token", memberVO.getRefreshToken());
        res.put("uid", memberVO.getUid());
        return res;
    }

    @Override
    public Map decrypt(String code, String encryptedData, String uuid, String iv) {
        Map res = new HashMap(16);

        String sessionKey = (String) cache.get(CachePrefix.SESSION_KEY.getPrefix() + uuid);

        JSONObject userInfo = this.getUserInfo(encryptedData, sessionKey, iv);
        if (userInfo != null) {
            String unionId = (String) userInfo.get("unionId");
            String sql = "select * from es_connect where union_id = ? and union_type = ?";
            ConnectDO connect = this.memberDaoSupport.queryForObject(sql, ConnectDO.class, unionId, ConnectTypeEnum.WECHAT.value());
            //读到了unionid，但没有找到账号
            if (connect == null) {
                res.put("autologin", "fail");
                res.put("reson", "account_not_found");

                //存储uuid和unionId的关系
                Auth2Token auth2Token = new Auth2Token();
                auth2Token.setType(ConnectTypeEnum.WECHAT.value());
                auth2Token.setUnionid(unionId);
                String openId = (String) userInfo.get("openId");
                //openid用于注册绑定时获取微信登录的信息
                auth2Token.setOpneId(openId);
                cache.put(CachePrefix.CONNECT_LOGIN.getPrefix() + uuid, auth2Token);
                return res;
            }
            //验证通过可以正常登录
            Integer memberId = connect.getMemberId();
            Member member = memberManager.getModel(memberId);
            MemberVO memberVO = memberManager.connectLoginHandle(member, uuid);

            res.put("access_token", memberVO.getAccessToken());
            res.put("refresh_token", memberVO.getRefreshToken());
            res.put("uid", memberVO.getUid());
        }
        return res;
    }

    @Override
    public String getWXACodeUnlimit(String accessToken, int goodsId) {
        try {
            String imei = "867186032552993";
            Map<String, Object> params = new HashMap<>();
            Buyer buyer = UserContext.getBuyer();
            int memberId = buyer.getUid();

            //会员短连接缓存key
            String memberSuKey = CachePrefix.MEMBER_SU.getPrefix() + memberId;
            //从缓存中获取会员短连接
            Object memberSu = cache.get(memberSuKey);
            if (memberSu == null) {
                //重新生成会员短连接
                memberSu = this.getSuCode(memberId+"");
                logger.debug("==============根据会员ID生成的短连接为:" + memberSu);
                //将会员短连接放入缓存中
                cache.put(CachePrefix.MEMBER_SU.getPrefix() + memberId, memberSu);
            }
            //以会员短连接为key，将会员ID放入缓存中
            String memberIdCacheKey = CachePrefix.MEMBER_SU.getPrefix() + memberSu;
            cache.put(memberIdCacheKey, memberId);

            //将会员短连接放入二维码中
            params.put("scene", goodsId + "&" + memberSu);
            params.put("page", "goods-module/goods/goods");
            logger.debug(params.toString());
            params.put("width", 430);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            String body = JSONObject.fromObject(params).toString();
            StringEntity entity = new StringEntity(body);
            entity.setContentType("image/png");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();
            String name = imei + ".png";
            FileDTO fileDTO = new FileDTO();
            fileDTO.setStream(inputStream);
            fileDTO.setName(name);
            fileDTO.setExt("png");
            FileVO goods = fileManager.upload(fileDTO, "goods");
            logger.debug("++++++++++++++++++小程序二维码的地址是：++++++++++++" + goods.getUrl());
            return goods.getUrl();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取联合登录对象
     *
     * @param memberId  会员id
     * @param unionType 类型
     * @return ConnectDO
     */
    @Override
    public ConnectDO getConnect(Integer memberId, String unionType) {
        String sql = "select * from es_connect where member_id = ? and union_type = ?";
        List<ConnectDO> connectDOList = memberDaoSupport.queryForList(sql, ConnectDO.class, memberId, unionType);
        if (null == connectDOList || connectDOList.size() == 0) {
            return null;
        }
        return connectDOList.get(0);
    }


    /**
     * 解密，获取信息
     *
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    @Override
    public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取格式化的sign的参数
     *
     * @param sortedParams 生成sign的参数map
     * @return 组织好的参数url
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for (int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            String value = sortedParams.get(key);
            if (StringUtils.areNotEmpty(key, value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                ++index;
            }
        }

        return content.toString();
    }


    /**
     * 清楚cookie
     */
    private void cleanCookie() {
        String main = domainHelper.getTopDomain();
        Cookie user = new Cookie("user", "");
        user.setDomain(main);
        user.setPath("/");
        user.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(user);


        Cookie uuid = new Cookie("uuid", "");
        uuid.setDomain(main);
        uuid.setPath("/");
        uuid.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(uuid);

        Cookie uuidConnect = new Cookie("uuid_connect", "");
        uuidConnect.setDomain(main);
        uuidConnect.setPath("/");
        uuidConnect.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(uuidConnect);

        Cookie accessToken = new Cookie("access_token", "");
        accessToken.setDomain(main);
        accessToken.setPath("/");
        accessToken.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(accessToken);

        Cookie refreshToken = new Cookie("refresh_token", "");
        refreshToken.setDomain(main);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(refreshToken);

        Cookie forward = new Cookie("forward", "");
        forward.setDomain(main);
        forward.setPath("/");
        forward.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(forward);

        Cookie isWechatAuth = new Cookie("is_wechat_auth", "");
        isWechatAuth.setDomain(main);
        isWechatAuth.setPath("/");
        isWechatAuth.setMaxAge(0);
        ThreadContextHolder.getHttpResponse().addCookie(isWechatAuth);


    }

    /**
     * 获取唯一短连接值
     *
     * @param param 参数
     * @return
     */
    private String getSuCode(String param) {
        String[] suArr = ShortUrlGenerator.getShortUrl(param);
        boolean flag = false;
        String result = "";
        for (String su : suArr) {
            if (cache.get(su) == null) {
                flag = true;
                result = su;
            }
        }

        if (!flag) {
            getSuCode(param);
        }

        return result;
    }

}