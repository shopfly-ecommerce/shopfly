/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.trigger.Interface;

/**
 * 延时任务执行器接口
 * @author liushuai
 * @version v1.0
 * @since v7.0
 * 2019/2/13 下午5:32
 * @Description:
 *
 */
public interface TimeTriggerExecuter {


    /**
     * 执行任务
     * @param object 任务参数
     */
    void execute(Object object);

}
