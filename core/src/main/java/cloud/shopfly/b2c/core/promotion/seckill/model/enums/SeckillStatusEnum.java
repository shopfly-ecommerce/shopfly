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
 * 限时活动状态
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年12月15日 下午3:32:43
 */
public enum SeckillStatusEnum {

    /**
     * 编辑中
     */
    EDITING("编辑中"),

    /**
     * 已发布
     */
    RELEASE("已发布"),

    /**
     * 已关闭
     */
    CLOSED("已关闭"),
    /**
     * 已结束
     */
    OVER("已结束");

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
