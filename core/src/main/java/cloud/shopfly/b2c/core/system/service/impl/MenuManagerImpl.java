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
import cloud.shopfly.b2c.core.system.model.dos.Menu;
import cloud.shopfly.b2c.core.system.model.vo.MenuVO;
import cloud.shopfly.b2c.core.system.model.vo.MenusVO;
import cloud.shopfly.b2c.core.system.service.MenuManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu management business class
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-19 09:46:02
 */
@Service
public class MenuManagerImpl implements MenuManager {

    @Autowired
    
    private DaoSupport systemDaoSupport;


    @Override
    public Page list(int page, int pageSize) {
        String sql = "select * from es_menu";
        Page webPage = this.systemDaoSupport.queryForPage(sql, page, pageSize, Menu.class);
        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Menu add(MenuVO menuVO) {
        // Validates the unique identifier of the menu
        Menu valMenu = this.getMenuByIdentifier(menuVO.getIdentifier());
        if (valMenu != null) {
            throw new ServiceException(SystemErrorCode.E913.code(), "The menu uniquely identifies duplication");
        }
        // Check whether the parent menu is valid
        Menu parentMenu = this.getModel(menuVO.getParentId());
        if (!menuVO.getParentId().equals(0) && parentMenu == null) {
            throw new ResourceNotFoundException("The parent menu does not exist");
        }
        // Verify that menu levels exceed limits
        if (!menuVO.getParentId().equals(0) && parentMenu.getGrade() >= 3) {
            throw new ServiceException(SystemErrorCode.E914.code(), "The maximum menu level is3level");
        }
        // Check menu names repeatedly
        String sql = "select * from es_menu where title = ?";
        Menu menuResult = this.systemDaoSupport.queryForObject(sql, Menu.class, menuVO.getTitle());
        if (menuResult != null && menuResult.getDeleteFlag().equals(-1)) {
            BeanUtil.copyProperties(menuVO, menuResult);
            menuResult.setDeleteFlag(0);
            return this.updateMenu(menuResult);
        }else if (menuResult != null){
            throw new ServiceException(SystemErrorCode.E925.code(), "Duplicate menu names");
        }else {
            // Perform the save operation
            Menu menu = new Menu();
            BeanUtil.copyProperties(menuVO, menu);
            menu.setDeleteFlag(0);
            this.systemDaoSupport.insert(menu);
            menu.setId(systemDaoSupport.getLastId("es_menu"));
            return this.updateMenu(menu);
        }
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Menu edit(Menu menu, Integer id) {
        // Verify whether the current menu exists
        Menu valMenu = this.getModel(id);
        if (valMenu == null) {
            throw new ResourceNotFoundException("The current menu does not exist");
        }
        // The verification menu uniquely identifies duplication
        valMenu = this.getMenuByIdentifier(menu.getIdentifier());
        if (valMenu != null && !valMenu.getId().equals(id)) {
            throw new ServiceException(SystemErrorCode.E913.code(), "The menu uniquely identifies duplication");
        }
        // Check menu names repeatedly
        boolean bool = false;
        String sql = "select * from es_menu where title = ?";
        List<Menu> menus = this.systemDaoSupport.queryForList(sql, Menu.class, menu.getTitle());
        for (Menu me : menus) {
            if (!me.getId().equals(id)) {
                bool = true;
                continue;
            }
        }
        if (bool) {
            throw new ServiceException(SystemErrorCode.E925.code(), "Duplicate menu names");
        }
        menu.setId(id);
        // Implement changes
        return this.updateMenu(menu);
    }

    /**
     * Modify the menu
     *
     * @param menu The menu object
     * @return The menu object
     */
    private Menu updateMenu(Menu menu) {
        // Check whether the parent menu is valid
        Menu parentMenu = this.getModel(menu.getParentId());
        if (!menu.getParentId().equals(0) && parentMenu == null) {
            throw new ResourceNotFoundException("The parent menu does not exist");
        }
        // Verify that menu levels exceed limits
        if (!menu.getParentId().equals(0) && parentMenu.getGrade() >= 3) {
            throw new ServiceException(SystemErrorCode.E914.code(), "The maximum menu level is3level");
        }
        String menuPath = null;
        if (menu.getParentId().equals(0)) {
            menuPath = "," + menu.getId() + ",";
        } else {
            menuPath = parentMenu.getPath() + menu.getId() + ",";
        }
        String subMenu = menuPath.substring(0, menuPath.length() - 1);
        String[] menus = subMenu.substring(1).split(",");
        menu.setGrade(menus.length);
        menu.setPath(menuPath);
        this.systemDaoSupport.update(menu, menu.getId());
        return menu;
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        Menu menu = this.getModel(id);
        if (menu == null) {
            throw new ResourceNotFoundException("The current menu does not exist");
        }
        this.systemDaoSupport.execute("update es_menu set delete_flag = -1 where id = ?", id);
        this.systemDaoSupport.execute("update es_menu set delete_flag = -1 where parent_id = ?", id);
        this.systemDaoSupport.execute("update es_menu as e1 set e1.delete_flag = -1 where e1.parent_id in ( select em.id from (select * from es_menu) as em where  em.parent_id =  ?)", id);
    }

    @Override
    public Menu getModel(Integer id) {
        return this.systemDaoSupport.queryForObject("select * from es_menu where id = ? and delete_flag = ?", Menu.class, id, 0);
    }


    @Override
    public List<MenusVO> getMenuTree(Integer id) {
        List<MenusVO> menuList = this.systemDaoSupport.queryForList("select * from es_menu where delete_flag = '0' order by id asc", MenusVO.class);
        List<MenusVO> topMenuList = new ArrayList<MenusVO>();
        for (MenusVO menu : menuList) {
            if (menu.getParentId().compareTo(id) == 0) {
                List<MenusVO> children = this.getChildren(menuList, menu.getId());
                menu.setChildren(children);
                topMenuList.add(menu);
            }
        }
        return topMenuList;
    }

    /**
     * Find children in a collection
     *
     * @param menuList Collection of all menus
     * @param parentid The fatherid
     * @return Subset found
     */
    private List<MenusVO> getChildren(List<MenusVO> menuList, Integer parentid) {
        List<MenusVO> children = new ArrayList<MenusVO>();
        for (MenusVO menu : menuList) {
            if (menu.getParentId().compareTo(parentid) == 0) {
                menu.setChildren(this.getChildren(menuList, menu.getId()));
                children.add(menu);
            }
        }
        return children;
    }

    @Override
    public Menu getMenuByIdentifier(String identifier) {
        return this.systemDaoSupport.queryForObject("select * from es_menu where delete_flag = '0' and identifier = ?", Menu.class, identifier);
    }
}
