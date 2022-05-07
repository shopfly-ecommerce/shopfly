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
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.Menu;
import cloud.shopfly.b2c.core.system.model.vo.MenuVO;
import cloud.shopfly.b2c.core.system.model.vo.MenusVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Menu management business layer
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-19 09:46:02
 */
public interface MenuManager {

    /**
     * Query the menu management list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add Menu Management
     *
     * @param menu Menu management
     * @return Menu Menu management
     */
    Menu add(MenuVO menu);

    /**
     * Modifying Menu Management
     *
     * @param menu Menu management
     * @param id   Menu manages primary keys
     * @return Menu Menu management
     */
    Menu edit(Menu menu, Integer id);

    /**
     * Deleting menu Management
     *
     * @param id Menu manages primary keys
     */
    void delete(Integer id);

    /**
     * Get Menu Management
     *
     * @param id Menu manages primary keys
     * @return MenuVO  Menu management
     */
    Menu getModel(Integer id);

    /**
     * According to theidGet menu collection
     *
     * @param id The menuid
     * @return
     */
    List<MenusVO> getMenuTree(Integer id);

    /**
     * Get Menu Management
     *
     * @param identifier A unique identifier for a menu
     * @return MenuVO  Menu management
     */
    Menu getMenuByIdentifier(String identifier);

}
