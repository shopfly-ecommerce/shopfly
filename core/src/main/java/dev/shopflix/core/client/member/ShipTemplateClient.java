/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.member;

import dev.shopflix.core.system.model.vo.ShipTemplateVO;

/**
 * @version v7.0
 * @Description: 店铺运费模版Client默认实现
 * @Author: zjp
 * @Date: 2018/7/25 16:20
 */
public interface ShipTemplateClient {
    /**
     * 获取运费模版
     * @param id 运费模版主键
     * @return ShipTemplate  运费模版
     */
    ShipTemplateVO get(Integer id);

}
