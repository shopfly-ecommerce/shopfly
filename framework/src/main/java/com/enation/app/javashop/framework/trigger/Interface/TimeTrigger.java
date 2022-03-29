/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.trigger.Interface;

/**
 * 延时执行接口
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/13 下午8:13
 */
public interface TimeTrigger {

    /**
     * 添加延时任务
     *
     * @param executerName 执行器beanid
     * @param param        执行参数
     * @param triggerTime  执行时间 时间戳 秒为单位
     * @param uniqueKey    如果是一个 需要有 修改/取消 延时任务功能的延时任务，<br/>
     *                     请填写此参数，作为后续删除，修改做为唯一凭证 <br/>
     *                     建议参数为：PINTUAZN_{ACTIVITY_ID} 例如 pintuan_123<br/>
     *                     业务内全局唯一
     */
    void add(String executerName, Object param, Long triggerTime, String uniqueKey);

    /**
     * 修改延时任务
     *
     * @param executerName   执行器beanid
     * @param param          执行参数
     * @param triggerTime    执行时间 时间戳 秒为单位
     * @param oldTriggerTime 旧的任务执行时间
     * @param uniqueKey      添加任务时的唯一凭证
     */
    void edit(String executerName, Object param, Long oldTriggerTime, Long triggerTime, String uniqueKey);

    /**
     * 删除延时任务
     *
     * @param executerName 执行器
     * @param triggerTime  执行时间
     * @param uniqueKey    添加任务时的唯一凭证
     */
    void delete(String executerName, Long triggerTime, String uniqueKey);
}
