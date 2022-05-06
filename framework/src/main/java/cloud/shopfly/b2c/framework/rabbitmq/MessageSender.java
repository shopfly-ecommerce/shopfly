/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.rabbitmq;

/**
 * mq的publisher
 *
 * @author fk
 * @version v7.2.0
 * @since v7.2.0
 * 2020-06-15 21:50:52
 */
public interface MessageSender {


    /**
     * 发布
     * @param message
     */
    void send(MqMessage message);

}
