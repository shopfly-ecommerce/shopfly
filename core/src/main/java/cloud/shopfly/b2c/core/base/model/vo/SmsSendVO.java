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
package cloud.shopfly.b2c.core.base.model.vo;

import java.io.Serializable;

/**
 * SMS service transfer parametersvo
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018years3month25On the afternoon3:20:17
 */
public class SmsSendVO implements Serializable {

    private static final long serialVersionUID = -6222644764379603685L;
    /**
     * Mobile phone number
     */
    private String mobile;
    /**
     * Text Message Content
     */
    private String content;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SmsSendVO [mobile=" + mobile + ", context=" + content + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SmsSendVO other = (SmsSendVO) obj;
        if (content == null) {
            if (other.content != null) {
                {
                    return false;
                }
            }
        } else if (!content.equals(other.content)) {
            return false;
        }
        if (mobile == null) {
            if (other.mobile != null) {
                {
                    return false;
                }
            }
        } else if (!mobile.equals(other.mobile)) {
            return false;
        }
        return true;
    }


}
