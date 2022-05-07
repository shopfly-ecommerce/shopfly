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
package cloud.shopfly.b2c.core.client.system;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: Administrator Role
 * @date 2018/9/26 14:10
 * @since v7.0.0
 */
public interface RoleClient {


    /**
     * Obtain the permission mapping table of all roles
     *
     * @return Permission comparison table
     */
    Map<String, List<String>> getRoleMap();
}
