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

/**
 * SuccessMessage
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-11 In the morning9:03
 */
public class SuccessMessage {

    private Object message;

    public SuccessMessage() {
    }

    public SuccessMessage(Object message ) {

        this.message = message;

    }


    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "SuccessMessage{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SuccessMessage that = (SuccessMessage) o;

        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }
}
