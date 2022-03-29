/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.minus.service;

import dev.shopflix.core.promotion.minus.model.vo.MinusVO;
import dev.shopflix.framework.database.Page;

/**
 * 单品立减接口
 * @author mengyuanming
 * @version v1.0
 * @since v6.4.0
 * @date 2017年8月18日下午9:20:46
 *
 */
public interface MinusManager {


	/**
	 * 根据id获取单品立减商品
	 * @param minusId 单品立减活动Id
	 * @return MinusVO
	 */
	MinusVO getFromDB(Integer minusId);

	/**
	 * 查询单品立减列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param keywords 关键字
	 * @return Page
	 */
	Page list(int page, int pageSize, String keywords);

	/**
	 * 添加单品立减
	 * @param minus 单品立减
	 * @return Minus 单品立减
	 */
	MinusVO add(MinusVO minus);

	/**
	 * 修改单品立减
	 * @param minus 单品立减
	 * @param id 单品立减主键
	 * @return Minus 单品立减
	 */
	MinusVO edit(MinusVO minus, Integer id);

	/**
	 * 根据id删除单品立减商品
	 * @param minusId 单品立减活动对象id
	 * 1.根据活动id删除esMinus中的数据
	 * 2.调用promotionGoodsManager中的删除方法，删除esPromotionGoods表中的数据
	 * 3.删除Redis中的活动实例对象
	 *
	 */
	void delete(Integer minusId);


	/**
	 * 验证操作权限<br/>
	 * 如有问题直接抛出权限异常
	 * @param minusId
	 */
	void verifyAuth(Integer minusId);

}
