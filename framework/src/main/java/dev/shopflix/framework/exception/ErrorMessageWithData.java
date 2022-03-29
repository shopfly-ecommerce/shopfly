/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.exception;

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
