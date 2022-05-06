/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.vo.SalesVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 会员销售记录
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/6/29 上午9:31
 * @Description:
 *
 */
public interface MemberSalesManager {


    /**
     * 商品销售记录
     * @param pageSize
     * @param pageNo
     * @param goodsId
     * @return
     */
    Page<SalesVO> list(Integer pageSize, Integer pageNo, Integer goodsId);


}
