/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.model.vo;

/**
 * SuccessMessage
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-11 上午9:03
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
