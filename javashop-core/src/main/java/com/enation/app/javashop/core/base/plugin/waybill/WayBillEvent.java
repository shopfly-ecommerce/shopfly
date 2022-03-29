/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.plugin.waybill;

import com.enation.app.javashop.core.base.model.vo.ConfigItem;

import java.util.List;
import java.util.Map;

/**
 * 电子面单参数借口
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017年8月10日 下午2:29:05
 */
public interface WayBillEvent {


    /**
     * 配置各个电子面单的参数
     *
     * @return 在页面加载的电子面单参数
     */
    List<ConfigItem> definitionConfigItem();

    /**
     * 获取插件ID
     *
     * @return
     */
    String getPluginId();

    /**
     * 创建电子面单
     *
     * @param orderSn 订单编号
     * @param logId   物流公司id
     * @param config  参数配置
     * @return
     * @throws Exception
     */
    String createPrintData(String orderSn, Integer logId, Map config) throws Exception;

    /**
     * 获取插件名称
     *
     * @return 插件名称
     */
    String getPluginName();

    /**
     * 电子面单是否开启
     *
     * @return 0 不开启  1 开启
     */
    Integer getOpen();

}
