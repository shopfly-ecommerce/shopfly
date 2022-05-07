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
package cloud.shopfly.b2c.core.system;


/**
 * Abnormal system Settings
 *
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2018years3month22The morning of10:40:05
 */
public enum SystemErrorCode {

    // Abnormal code of the upload scheme
    E800("This upload scheme already exists"),
    E801("Image format not allowed to upload"),
    E802("Uploading picture failed"),
    E803("Failed to delete image"),
    E804("Failed to send email."),
    E805("District illegal"),
    E806("Floor not found"),
    // Abnormal code of the upload scheme
    E900("This upload scheme already exists"),
    E901("Image format not allowed to upload"),
    E902("Uploading picture failed"),
    E903("Failed to delete image"),
    E904("Failed to send email."),
    E905("District illegal"),
    E906("The invoice content exceeds the limit, up to6a"),
    E907("Duplicate invoice content"),
    // The article uses exception encoding
    E950("Special article classification, cannot be modified"),
    E951("Description Failed to add a category."),
    E952("Special articles cannot be deleted"),
    E953("Site navigation, parameter invalid"),
    E954("Invalid parameter for hot keyword"),
    E955("The argument is invalid"),
    E956("The focus graph parameter is invalid. The parameter is invalid"),
    E908("The push parameter is empty"),
    E909("Failed to push"),
    E910("Single electron plane schemes already exist"),
    E911("Failed to generate electron face order"),
    E912("Single parameter error of electron surface"),
    E913("The menu uniquely identifies duplication"),
    E914("The maximum menu level is3level"),
    E915("The administrator name already exists"),
    E916("You must retain a super administrator"),
    E917("The administrator password cannot be empty"),
    E918("The administrator account or password is incorrect"),
    E919("SMS platform solutions already exist"),
    E920("You must keep an open SMS platform"),
    E921("Old password incorrect"),
    E922("The original password cannot be empty"),
    E923("The new password cannot be empty"),
    E924("Roles cannot be deleted"),
    E925("Duplicate menu names"),
    E926("The demo site disallows this operation"),

    E214("Logistics company error"),
    E226("Freight templates are used")
    ;

    private String describe;

    SystemErrorCode(String des) {
        this.describe = des;
    }

    /**
     * Get exception code
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }


}
