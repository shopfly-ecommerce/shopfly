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
package cloud.shopfly.b2c.core.system.model.vo;

import java.util.List;

/**
 * Menu permissionsvo Parameter transfer and display for permission menu
 *
 * @author zh
 * @version v7.0
 * @date 18/6/25 In the morning9:59
 * @since v7.0
 */

public class Menus {
    /**
     * The menu title
     */
    private String title;
    /**
     * Menu unique identifier
     */
    private String identifier;
    /**
     * Whether this menu is selected
     */
    private boolean checked;
    /**
     * Menu permission expression
     */
    private String authRegular;

    /**
     * Sub menu
     */
    private List<Menus> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Menus> getChildren() {
        return children;
    }

    public void setChildren(List<Menus> children) {
        this.children = children;
    }

    public String getAuthRegular() {
        return authRegular;
    }

    public void setAuthRegular(String authRegular) {
        this.authRegular = authRegular;
    }

    @Override
    public String toString() {
        return "Menus{" +
                "title='" + title + '\'' +
                ", identifier='" + identifier + '\'' +
                ", checked=" + checked +
                ", authRegular='" + authRegular + '\'' +
                ", children=" + children +
                '}';
    }
}
