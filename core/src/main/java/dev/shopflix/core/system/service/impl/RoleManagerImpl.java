/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.system.SystemErrorCode;
import dev.shopflix.core.system.model.dos.RoleDO;
import dev.shopflix.core.system.model.vo.Menus;
import dev.shopflix.core.system.model.vo.RoleVO;
import dev.shopflix.core.system.service.RoleSeller;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 角色表业务类
 *
 * @author kingapex
 * @version v1.0.0
 * @since v7.0.0
 * 2018-04-17 16:48:27
 */
@Service
public class RoleManagerImpl implements RoleSeller {

    @Autowired
    
    private DaoSupport systemDaoSupport;

    @Autowired
    private Cache cache;

    @Override
    public Page list(int page, int pageSize) {
        String sql = "select role_id,role_name,role_describe from es_role ";
        Page webPage = this.systemDaoSupport.queryForPage(sql, page, pageSize, RoleDO.class);
        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RoleVO add(RoleVO role) {
        RoleDO roleDO = new RoleDO();
        roleDO.setRoleName(role.getRoleName());
        roleDO.setAuthIds(JsonUtil.objectToJson(role.getMenus()));
        roleDO.setRoleDescribe(role.getRoleDescribe());
        this.systemDaoSupport.insert(roleDO);
        role.setRoleId(systemDaoSupport.getLastId("es_role"));
        //删除缓存中角色所拥有的菜单权限
        cache.remove(CachePrefix.ADMIN_URL_ROLE.getPrefix());
        return role;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RoleVO edit(RoleVO role, Integer id) {
        //校验权限是否存在
        RoleDO roleDO = this.getModel(id);
        if (roleDO == null) {
            throw new ResourceNotFoundException("此角色不存在");
        }
        roleDO.setRoleName(role.getRoleName());
        roleDO.setAuthIds(JsonUtil.objectToJson(role.getMenus()));
        roleDO.setRoleDescribe(role.getRoleDescribe());
        this.systemDaoSupport.update(roleDO, id);
        role.setRoleId(id);
        //删除缓存中角色所拥有的菜单权限
        cache.remove(CachePrefix.ADMIN_URL_ROLE.getPrefix());
        return role;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        RoleDO roleDO = this.getModel(id);
        if (roleDO == null) {
            throw new ResourceNotFoundException("此角色不存在");
        }

        //查看角色下是否有管理员，有管理员则不能删除
        String sql = "select * from es_admin_user where role_id = ? and user_state != -1";
        List list = this.systemDaoSupport.queryForList(sql, id);
        if (StringUtil.isNotEmpty(list)) {
            throw new ServiceException(SystemErrorCode.E924.code(), "该角色下有管理员，请删除管理员后再删除角色");
        }

        this.systemDaoSupport.delete(RoleDO.class, id);
    }

    @Override
    public RoleDO getModel(Integer id) {
        return this.systemDaoSupport.queryForObject(RoleDO.class, id);
    }

    @Override
    public Map<String, List<String>> getRoleMap() {

        Map<String, List<String>> roleMap = new HashMap<>(16);
        String sql = "select * from es_role";
        List<RoleDO> roles = this.systemDaoSupport.queryForList(sql, RoleDO.class);
        for (int i = 0; i < roles.size(); i++) {
            List<Menus> menusList = JsonUtil.jsonToList(roles.get(i).getAuthIds(), Menus.class);
            if (menusList != null && menusList.size() > 0) {
                List<String> authList = new ArrayList<>();
                //递归查询角色所拥有的菜单权限
                this.getChildren(menusList, authList);
                roleMap.put(roles.get(i).getRoleName(), authList);
                cache.put(CachePrefix.ADMIN_URL_ROLE.getPrefix(), roleMap);
            }
        }
        return roleMap;
    }

    /**
     * 递归将此角色锁拥有的菜单权限保存到list
     *
     * @param menuList 菜单集合
     * @param authList 权限组集合
     */
    private void getChildren(List<Menus> menuList, List<String> authList) {
        for (Menus menus : menuList) {
            //将此角色拥有的菜单权限放入list中
            if (menus.isChecked()) {
                authList.add(menus.getAuthRegular());
            }
            if (!menus.getChildren().isEmpty()) {
                getChildren(menus.getChildren(), authList);
            }
        }
    }

    @Override
    public List<String> getRoleMenu(Integer id) {
        RoleDO roleDO = this.getModel(id);
        if (roleDO == null) {
            throw new ResourceNotFoundException("此角色不存在");
        }
        List<Menus> menusList = JsonUtil.jsonToList(roleDO.getAuthIds(), Menus.class);
        List<String> authList = new ArrayList<>();
        //筛选菜单
        this.reset(menusList, authList);
        return authList;
    }

    /**
     * 筛选checked为true的菜单
     *
     * @param menuList 菜单集合
     */
    private void reset(List<Menus> menuList, List<String> authList) {
        for (Menus menus : menuList) {
            //将此角色拥有的菜单权限放入list中
            if (menus.isChecked()) {
                authList.add(menus.getIdentifier());
            }
            if (!menus.getChildren().isEmpty()) {
                reset(menus.getChildren(), authList);
            }
        }
    }

    @Override
    public RoleVO getRole(Integer id) {
        return new RoleVO(this.getModel(id));
    }
}

