/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.enums;

/**
 * 通知类型 枚举
 *
 * @author zh
 * @version v7.0
 * @since v7.0 2017年10月17日 上午10:49:25
 */
public enum DisplayTypeEnum {
    /**
     * 通知:消息送达到用户设备后，由友盟SDK接管处理并在通知栏上显示通知内容。
     */
    NOTIFICATION {
        @Override
        public String getValue() {
            return "notification";
        }
    },
    /**
     * 消息:消息送达到用户设备后，消息内容透传给应用自身进行解析处理。
     */
    MESSAGE {
        @Override
        public String getValue() {
            return "message";
        }
    };

    public abstract String getValue();


}
