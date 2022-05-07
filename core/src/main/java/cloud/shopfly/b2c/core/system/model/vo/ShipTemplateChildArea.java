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

import java.io.Serializable;
import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: Deliver template subtitle version region
 * @date 2018/10/24 16:12
 * @since v7.0.0
 */
public class ShipTemplateChildArea implements Serializable {

    private String name;

    private List<ShipTemplateChildArea> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShipTemplateChildArea> getChildren() {
        return children;
    }

    public void setChildren(List<ShipTemplateChildArea> children) {
        this.children = children;
    }
}
