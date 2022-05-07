/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
import cloud.shopfly.b2c.framework.ShopflyConfig;
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
 * Platform administrator business class
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
    private ShopflyConfig shopflyConfig;
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
            throw new ServiceException(SystemErrorCode.E917.code(), "The password format is incorrect");
        }
        // Verify whether the user name is duplicated
        AdminUser user = this.systemDaoSupport.queryForObject("select * from es_admin_user where username = ? and user_state = 0", AdminUser.class, adminUserVO.getUsername());
        if (user != null) {
            throw new ServiceException(SystemErrorCode.E915.code(), "Duplicate administrator name");
        }
        // If you are not a super administrator, check whether the permission exists
        if (!adminUserVO.getFounder().equals(1)) {
            RoleDO roleDO = roleManager.getModel(adminUserVO.getRoleId());
            if (roleDO == null) {
                throw new ResourceNotFoundException("The owning permission does not exist");
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
        // Verify that the administrator to be modified exists
        AdminUser adminUser = this.getModel(id);
        if (adminUser == null) {
            throw new ResourceNotFoundException("The current administrator does not exist");
        }
        // If the administrator is changed from a super administrator to a common administrator, check whether the administrator is the last super administrator
        if (adminUser.getFounder().equals(1) && !adminUserVO.getFounder().equals(1)) {
            String sql = "select * from es_admin_user where founder = 1 and user_state = 0";
            List<AdminUser> adminUsers = this.systemDaoSupport.queryForList(sql, AdminUser.class);
            if (adminUsers.size() <= 1) {
                throw new ServiceException(SystemErrorCode.E916.code(), "You must retain a super administrator");
            }
        }
        if (!adminUserVO.getFounder().equals(1)) {
            RoleDO roleDO = roleManager.getModel(adminUserVO.getRoleId());
            if (roleDO == null) {
                throw new ResourceNotFoundException("The owning permission does not exist");
            }
        } else {
            adminUserVO.setRoleId(0);
        }
        // Old password of administrator
        String password = adminUser.getPassword();
        // Verify whether the administrator changes the password
        if (!StringUtil.isEmpty(adminUserVO.getPassword())) {
            boolean bool = Pattern.matches("[a-fA-F0-9]{32}", adminUserVO.getPassword());
            if (!bool) {
                throw new ServiceException(SystemErrorCode.E917.code(), "The password format is incorrect");
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
        // Verify that the current administrator exists
        AdminUser adminUser = this.getModel(id);
        if (adminUser == null) {
            throw new ResourceNotFoundException("The current administrator does not exist");
        }
        // Verify that the administrator to be deleted is the last super administrator
        String sql = "select * from es_admin_user where founder = 1 and user_state = 0";
        List<AdminUser> adminUsers = this.systemDaoSupport.queryForList(sql, AdminUser.class);
        if (adminUsers.size() <= 1 && adminUser.getFounder().equals(1)) {
            throw new ServiceException(SystemErrorCode.E916.code(), "You must retain a super administrator");
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
            throw new ServiceException(SystemErrorCode.E918.code(), "The administrator account password is incorrect");
        }
        AdminLoginVO adminLoginVO = new AdminLoginVO();
        adminLoginVO.setUid(adminUser.getId());
        adminLoginVO.setUsername(name);
        adminLoginVO.setDepartment(adminUser.getDepartment());
        adminLoginVO.setFace(adminUser.getFace());
        adminLoginVO.setRoleId(adminUser.getRoleId());
        adminLoginVO.setFounder(adminUser.getFounder());
        // Set the validity period of the access token to keep the administrator online
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

            // Get uuid
            String uuid = ThreadContextHolder.getHttpRequest().getHeader("uuid");
            // Obtain an administrator by ID To verify whether the current administrator exists
            AdminUser adminUser = this.getModel(uid);
            if (adminUser == null) {
                throw new ResourceNotFoundException("The current administrator does not exist");
            }
            // Obtain the Token again
            Token token = createToken(adminUser);

            String newAccessToken = token.getAccessToken();
            String newRefreshToken = token.getRefreshToken();

            Map map = new HashMap(16);
            map.put("accessToken", newAccessToken);
            map.put("refreshToken", newRefreshToken);

            return JsonUtil.objectToJson(map);

        }
        throw new ResourceNotFoundException("The current administrator does not exist");
    }

    /**
     * createtoken
     *
     * @param adminUser The administrator
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
