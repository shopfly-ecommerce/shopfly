/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.service.impl;

import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.dos.AdminUser;
import cloud.shopfly.b2c.core.system.model.dos.RoleDO;
import cloud.shopfly.b2c.core.system.model.dto.AdminUserDTO;
import cloud.shopfly.b2c.core.system.model.vo.AdminLoginVO;
import cloud.shopfly.b2c.core.system.model.vo.AdminUserVO;
import cloud.shopfly.b2c.core.system.service.AdminUserManager;
import cloud.shopfly.b2c.core.system.service.RoleSeller;
import cloud.shopfly.b2c.framework.ShopflixConfig;
import cloud.shopfly.b2c.framework.auth.Token;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.TokenManager;
import cloud.shopfly.b2c.framework.security.model.Admin;
import cloud.shopfly.b2c.framework.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 平台管理员业务类
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
@Service
public class AdminUserManagerImpl implements AdminUserManager {

    @Autowired
    private DaoSupport systemDaoSupport;
    @Autowired
    private RoleSeller roleManager;
    @Autowired
    private ShopflixConfig shopflixConfig;
    @Autowired
    private Cache cache;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public Page list(int page, int pageSize) {
        String sql = "select u.*,r.role_name from es_admin_user u left join es_role r ON u.role_id=r.role_id where u.user_state=0";
        Page webPage = this.systemDaoSupport.queryForPage(sql, page, pageSize, AdminUserDTO.class);
        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AdminUser add(AdminUserVO adminUserVO) {
        boolean bool = Pattern.matches("[a-fA-F0-9]{32}", adminUserVO.getPassword());
        if (!bool) {
            throw new ServiceException(SystemErrorCode.E917.code(), "密码格式不正确");
        }
        //校验用户名称是否重复
        AdminUser user = this.systemDaoSupport.queryForObject("select * from es_admin_user where username = ? and user_state = 0", AdminUser.class, adminUserVO.getUsername());
        if (user != null) {
            throw new ServiceException(SystemErrorCode.E915.code(), "管理员名称重复");
        }
        //不是超级管理员的情况下再校验权限是否存在
        if (!adminUserVO.getFounder().equals(1)) {
            RoleDO roleDO = roleManager.getModel(adminUserVO.getRoleId());
            if (roleDO == null) {
                throw new ResourceNotFoundException("所属权限不存在");
            }
        }
        String password = adminUserVO.getPassword();
        AdminUser adminUser = new AdminUser();
        BeanUtil.copyProperties(adminUserVO, adminUser);
        adminUser.setPassword(StringUtil.md5(password + adminUser.getUsername().toLowerCase()));
        adminUser.setDateLine(DateUtil.getDateline());
        adminUser.setUserState(0);
        this.systemDaoSupport.insert(adminUser);
        adminUser.setId(systemDaoSupport.getLastId("es_admin_user"));
        return adminUser;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AdminUser edit(AdminUserVO adminUserVO, Integer id) {
        //对要修改的管理员是否存在进行校验
        AdminUser adminUser = this.getModel(id);
        if (adminUser == null) {
            throw new ResourceNotFoundException("当前管理员不存在");
        }
        //如果修改的是从超级管理员到普通管理员 需要校验此管理员是否是最后一个超级管理员
        if (adminUser.getFounder().equals(1) && !adminUserVO.getFounder().equals(1)) {
            String sql = "select * from es_admin_user where founder = 1 and user_state = 0";
            List<AdminUser> adminUsers = this.systemDaoSupport.queryForList(sql, AdminUser.class);
            if (adminUsers.size() <= 1) {
                throw new ServiceException(SystemErrorCode.E916.code(), "必须保留一个超级管理员");
            }
        }
        if (!adminUserVO.getFounder().equals(1)) {
            RoleDO roleDO = roleManager.getModel(adminUserVO.getRoleId());
            if (roleDO == null) {
                throw new ResourceNotFoundException("所属权限不存在");
            }
        } else {
            adminUserVO.setRoleId(0);
        }
        //管理员原密码
        String password = adminUser.getPassword();
        //对管理员是否修改密码进行校验
        if (!StringUtil.isEmpty(adminUserVO.getPassword())) {
            boolean bool = Pattern.matches("[a-fA-F0-9]{32}", adminUserVO.getPassword());
            if (!bool) {
                throw new ServiceException(SystemErrorCode.E917.code(), "密码格式不正确");
            }
            adminUserVO.setPassword(StringUtil.md5(adminUserVO.getPassword() + adminUser.getUsername().toLowerCase()));
        } else {
            adminUserVO.setPassword(password);
        }
        adminUserVO.setUsername(adminUser.getUsername());
        BeanUtil.copyProperties(adminUserVO, adminUser);
        this.systemDaoSupport.update(adminUser, id);
        return adminUser;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        //校验当前管理员是否存在
        AdminUser adminUser = this.getModel(id);
        if (adminUser == null) {
            throw new ResourceNotFoundException("当前管理员不存在");
        }
        //校验要删除的管理员是否是最后一个超级管理员
        String sql = "select * from es_admin_user where founder = 1 and user_state = 0";
        List<AdminUser> adminUsers = this.systemDaoSupport.queryForList(sql, AdminUser.class);
        if (adminUsers.size() <= 1 && adminUser.getFounder().equals(1)) {
            throw new ServiceException(SystemErrorCode.E916.code(), "必须保留一个超级管理员");
        }
        this.systemDaoSupport.execute("update es_admin_user set user_state = -1 where id = ?", id);
    }

    @Override
    public AdminUser getModel(Integer id) {
        return this.systemDaoSupport.queryForObject("select * from es_admin_user where user_state = 0 and id = ?", AdminUser.class, id);
    }

    @Override
    public AdminLoginVO login(String name, String password) {
        String sql = "select * from es_admin_user where username = ? and password = ? and user_state = 0";
        AdminUser adminUser = this.systemDaoSupport.queryForObject(sql, AdminUser.class, name, StringUtil.md5(password + name.toLowerCase()));
        if (adminUser == null || !StringUtil.equals(adminUser.getUsername(), name)) {
            throw new ServiceException(SystemErrorCode.E918.code(), "管理员账号密码错误");
        }
        AdminLoginVO adminLoginVO = new AdminLoginVO();
        adminLoginVO.setUid(adminUser.getId());
        adminLoginVO.setUsername(name);
        adminLoginVO.setDepartment(adminUser.getDepartment());
        adminLoginVO.setFace(adminUser.getFace());
        adminLoginVO.setRoleId(adminUser.getRoleId());
        adminLoginVO.setFounder(adminUser.getFounder());
        // 设置访问token的失效时间维持管理员在线状态
        Token token = createToken(adminUser);

        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();

        adminLoginVO.setAccessToken(accessToken);
        adminLoginVO.setRefreshToken(refreshToken);
        return adminLoginVO;
    }

    @Override
    public String exchangeToken(String refreshToken) {
        if (refreshToken != null) {
            Admin admin = tokenManager.parse(Admin.class, refreshToken);

            Integer uid = admin.getUid();

            //获取uuid
            String uuid = ThreadContextHolder.getHttpRequest().getHeader("uuid");
            //根据id获取管理员 校验当前管理员是否存在
            AdminUser adminUser = this.getModel(uid);
            if (adminUser == null) {
                throw new ResourceNotFoundException("当前管理员不存在");
            }
            //重新获取token
            Token token = createToken(adminUser);

            String newAccessToken = token.getAccessToken();
            String newRefreshToken = token.getRefreshToken();

            Map map = new HashMap(16);
            map.put("accessToken", newAccessToken);
            map.put("refreshToken", newRefreshToken);

            return JsonUtil.objectToJson(map);

        }
        throw new ResourceNotFoundException("当前管理员不存在");
    }

    /**
     * 创建token
     *
     * @param adminUser 管理员
     * @return
     */
    private Token createToken(AdminUser adminUser) {
        Admin admin = new Admin();
        admin.setUid(adminUser.getId());
        admin.setUsername(adminUser.getUsername());
        if (adminUser.getFounder().equals(1)) {
            admin.add("SUPER_ADMIN");
        } else {
            RoleDO roleDO = this.roleManager.getModel(adminUser.getRoleId());
            admin.add(roleDO.getRoleName());
        }

        return tokenManager.create(admin);

    }

    @Override
    public void logout(Integer uid) {
        String uuid = ThreadContextHolder.getHttpRequest().getHeader("uuid");
        cache.remove(TokenKeyGenerate.generateAdminRefreshToken(uuid, uid));
        cache.remove(TokenKeyGenerate.generateAdminAccessToken(uuid, uid));
    }
}
