/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.passport.service.impl;


import cloud.shopfly.b2c.core.member.model.dto.LoginUserDTO;
import cloud.shopfly.b2c.core.member.model.dto.QQUserDTO;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.passport.service.LoginManager;
import cloud.shopfly.b2c.core.passport.service.LoginQQManager;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.HttpUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;


/**
 * QQ统一登陆服务实现
 * @author cs
 * @since v1.0
 * @version 7.2.2
 * 2020/09/24
 */
@Service
public class LoginQQManagerImpl implements LoginQQManager {

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    private LoginManager loginManager;

    private static final Integer QQ_TOKEN_VAILD_TIME_APP = 60*60*24*90;


    @Override
    public Map qqWapLogin(String accessToken, String uuid) {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO = getUnionInfo(loginUserDTO,accessToken);
        loginUserDTO.setUuid(uuid);
        loginUserDTO.setTokenOutTime(null);
        loginUserDTO.setRefreshTokenOutTime(null);
        loginUserDTO.setOpenType(ConnectTypeEnum.QQ_OPENID);
        loginUserDTO.setUnionType(ConnectTypeEnum.QQ);
        loginUserDTO = getQQUserInfo(loginUserDTO,accessToken);
        return loginManager.loginByUnionId(loginUserDTO);
    }


    @Override
    public Map qqAppLogin(String uuid, QQUserDTO qqUserDTO) {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO = getUnionInfo(loginUserDTO,qqUserDTO.getAccesstoken());
        loginUserDTO.setUuid(uuid);
        loginUserDTO.setTokenOutTime(QQ_TOKEN_VAILD_TIME_APP);
        loginUserDTO.setRefreshTokenOutTime(QQ_TOKEN_VAILD_TIME_APP);
        loginUserDTO.setOpenid(qqUserDTO.getOpenid());
        loginUserDTO.setOpenType(ConnectTypeEnum.QQ_APP);
        loginUserDTO.setUnionType(ConnectTypeEnum.QQ);
        loginUserDTO.setHeadimgurl(qqUserDTO.getHeadimgurl());
        loginUserDTO.setNickName(qqUserDTO.getNickname());
        if ("男".equals(qqUserDTO.getGender())){
            loginUserDTO.setSex(1);
        }else{
            loginUserDTO.setSex(0);
        }
        loginUserDTO.setCountry(qqUserDTO.getCountry());
        loginUserDTO.setCity(qqUserDTO.getCity());
        return loginManager.loginByUnionId(loginUserDTO);
    }

    @Override
    public String getAppid() {
        Map<String, String> map = connectManager.initConnectSetting();
        return map.get("qq_pc_app_id");
    }



    private LoginUserDTO getQQUserInfo(LoginUserDTO loginUserDTO,String accessToken){
        Map<String, String> map = connectManager.initConnectSetting();
        StringBuffer userBuffer = new StringBuffer("https://graph.qq.com/user/get_user_info?");
        userBuffer.append("access_token=").append(accessToken);
        userBuffer.append("&openid=").append(loginUserDTO.getOpenid());
        userBuffer.append("&oauth_consumer_key=").append(map.get("qq_pc_app_id"));
        userBuffer.append("&format=json");
        String retJson = HttpUtils.doGet(userBuffer.toString(), "UTF-8", 1000, 1000);
        JSONObject jsonObject = JSONObject.fromObject(retJson);
        if (jsonObject.getInt("ret")!=0){
            throw new ServiceException("403","获取用户信息失败",retJson);
        }
        loginUserDTO.setHeadimgurl(jsonObject.getString("figureurl_qq"));
        loginUserDTO.setNickName(jsonObject.getString("nickname"));
        if ("男".equals(jsonObject.getString("gender"))){
            loginUserDTO.setSex(1);
        }else{
            loginUserDTO.setSex(0);
        }
        loginUserDTO.setCountry(jsonObject.getString("country"));
        loginUserDTO.setCity(jsonObject.getString("city"));
        return loginUserDTO;
    }


    private LoginUserDTO getUnionInfo(LoginUserDTO loginUserDTO,String accessToken){
        StringBuffer unionIdBuffer = new StringBuffer("https://graph.qq.com/oauth2.0/me?");
        unionIdBuffer.append("access_token=").append(accessToken);
        unionIdBuffer.append("&unionid=1&fmt=json");
        String retJson = HttpUtils.doGet(unionIdBuffer.toString(), "UTF-8", 100, 1000);
        if (retJson.indexOf("unionid")==-1){
            throw new ServiceException("403","fail to get unionid",retJson);
        }
        JSONObject jsonObject = JSONObject.fromObject(retJson);
        loginUserDTO.setUnionid(jsonObject.getString("unionid"));
        loginUserDTO.setOpenid(jsonObject.getString("openid"));
        return loginUserDTO;
    }
}
