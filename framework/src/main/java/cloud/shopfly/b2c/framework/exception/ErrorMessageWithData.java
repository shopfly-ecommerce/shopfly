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
package cloud.shopfly.b2c.framework.exception;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 带数据的错误消息
 * Created by kingapex on 2018/3/13.
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public class ErrorMessageWithData  extends  ErrorMessage{

    private  Object data;


    public ErrorMessageWithData(String code, String message,Object data) {
        super(code, message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }


    public ErrorMessageWithData() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        ErrorMessageWithData that = (ErrorMessageWithData) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(data, that.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(data)
                .toHashCode();
    }
}
