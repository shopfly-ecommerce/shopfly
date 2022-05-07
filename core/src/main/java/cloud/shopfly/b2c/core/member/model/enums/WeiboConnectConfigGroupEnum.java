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
 * @author zjp
 * @version v7.0
 * @Description Microblogging trusted login parameter group enumeration class
 * @ClassName WeiboConnectConfigGroupEnum
 * @since v7.0 In the afternoon8:05 2018/6/28
 */
public enum WeiboConnectConfigGroupEnum {
    /**
     * Webpage parameters（PC，WAP, wechat web terminal）
     */
    pc("Webpage parameters（PC，WAP, wechat web terminal）"),
    /**
     * native-APPparameter
     */
    app("native-APPparameter"),
    /**
     * RN-APPparameter
     */
    rn("RN-APPparameter");


    private String text;

    WeiboConnectConfigGroupEnum(String text) {
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String value() {
        return this.name();
    }
}
