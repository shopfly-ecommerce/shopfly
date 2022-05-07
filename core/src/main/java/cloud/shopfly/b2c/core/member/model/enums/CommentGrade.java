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
package cloud.shopfly.b2c.core.member.model.enums;

/**
 * @author fk
 * @version v1.0
 * @Description: Enumeration of comment scores
 * @date 2018/5/3 11:12
 * @since v7.0.0
 */
public enum CommentGrade {

    /**
     * Praise
     */
    good("Praise"),
    /**
     * In the evaluation of
     */
    neutral("In the evaluation of"),
    /**
     * Bad review
     */
    bad("Bad review");

    private String description;

    CommentGrade(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
    public String value(){
        return this.name();
    }
}
