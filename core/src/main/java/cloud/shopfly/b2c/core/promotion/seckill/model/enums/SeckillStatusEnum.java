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
package cloud.shopfly.b2c.core.promotion.seckill.model.enums;

/**
 * Limited time activity state
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017years12month15On the afternoon3:32:43
 */
public enum SeckillStatusEnum {

    /**
     * In the editor
     */
    EDITING("In the editor"),

    /**
     * The published
     */
    RELEASE("The published"),

    /**
     * closed
     */
    CLOSED("closed"),
    /**
     * Has ended
     */
    OVER("Has ended");

    private String description;

    SeckillStatusEnum(String description) {
        this.description = description;

    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
