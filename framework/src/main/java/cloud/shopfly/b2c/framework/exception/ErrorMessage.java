/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.exception;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 错误消息
 * Created by kingapex on 2018/3/13.
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public class ErrorMessage  {

	private String code;
	private String message;

	public ErrorMessage() {
	}

	public ErrorMessage(String code, String message ) {

		this.code = code;
		this.message = message;

	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ErrorMessage that = (ErrorMessage) o;

		return new EqualsBuilder()
				.append(code, that.code)
				.append(message, that.message)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(code)
				.append(message)
				.toHashCode();
	}

	@Override
	public String toString() {
		return "ErrorMessage{" +
				"code='" + code + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
