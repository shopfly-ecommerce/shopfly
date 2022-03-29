/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.model.vo;

import java.io.Serializable;

/**
 * 短信业务传递参数使用vo
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018年3月25日 下午3:20:17
 */
public class SmsSendVO implements Serializable {

    private static final long serialVersionUID = -6222644764379603685L;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 手机短信内容
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
