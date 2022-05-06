/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 菜单管理业务类
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
        //对菜单的唯一标识做校验
        Menu valMenu = this.getMenuByIdentifier(menuVO.getIdentifier());
        if (valMenu != null) {
            throw new ServiceException(SystemErrorCode.E913.code(), "菜单唯一标识重复");
        }
        //判断父级菜单是否有效
        Menu parentMenu = this.getModel(menuVO.getParentId());
        if (!menuVO.getParentId().equals(0) && parentMenu == null) {
            throw new ResourceNotFoundException("父级菜单不存在");
        }
        //校验菜单级别是否超出限制
        if (!menuVO.getParentId().equals(0) && parentMenu.getGrade() >= 3) {
            throw new ServiceException(SystemErrorCode.E914.code(), "菜单级别最多为3级");
        }
        //对菜单名称进行重复校验
        String sql = "select * from es_menu where title = ?";
        Menu menuResult = this.systemDaoSupport.queryForObject(sql, Menu.class, menuVO.getTitle());
        if (menuResult != null && menuResult.getDeleteFlag().equals(-1)) {
            BeanUtil.copyProperties(menuVO, menuResult);
            menuResult.setDeleteFlag(0);
            return this.updateMenu(menuResult);
        }else if (menuResult != null){
            throw new ServiceException(SystemErrorCode.E925.code(), "菜单名称重复");
        }else {
            //执行保存操作
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
        //校验当前菜单是否存在
        Menu valMenu = this.getModel(id);
        if (valMenu == null) {
            throw new ResourceNotFoundException("当前菜单不存在");
        }
        //校验菜单唯一标识重复
        valMenu = this.getMenuByIdentifier(menu.getIdentifier());
        if (valMenu != null && !valMenu.getId().equals(id)) {
            throw new ServiceException(SystemErrorCode.E913.code(), "菜单唯一标识重复");
        }
        //对菜单名称进行重复校验
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
            throw new ServiceException(SystemErrorCode.E925.code(), "菜单名称重复");
        }
        menu.setId(id);
        //执行修改
        return this.updateMenu(menu);
    }

    /**
     * 执行修改菜单操作
     *
     * @param menu 菜单对象
     * @return 菜单对象
     */
    private Menu updateMenu(Menu menu) {
        //判断父级菜单是否有效
        Menu parentMenu = this.getModel(menu.getParentId());
        if (!menu.getParentId().equals(0) && parentMenu == null) {
            throw new ResourceNotFoundException("父级菜单不存在");
        }
        //校验菜单级别是否超出限制
        if (!menu.getParentId().equals(0) && parentMenu.getGrade() >= 3) {
            throw new ServiceException(SystemErrorCode.E914.code(), "菜单级别最多为3级");
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
            throw new ResourceNotFoundException("当前菜单不存在");
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
     * 在一个集合中查找子
     *
     * @param menuList 所有菜单集合
     * @param parentid 父id
     * @return 找到的子集合
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
