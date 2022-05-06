/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.halfprice.service;

import cloud.shopfly.b2c.core.promotion.halfprice.model.dos.HalfPriceDO;
import cloud.shopfly.b2c.core.promotion.halfprice.model.vo.HalfPriceVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 第二件半价业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 19:53:42
 */
public interface HalfPriceManager	{

	/**
	 * 查询第二件半价列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param keywords 关键字
	 * @return Page
	 */
	Page list(int page, int pageSize, String keywords);
	/**
	 * 添加第二件半价
	 * @param halfPrice 第二件半价
	 * @return HalfPrice 第二件半价
	 */
	HalfPriceDO add(HalfPriceVO halfPrice);

	/**
	* 修改第二件半价
	* @param halfPrice 第二件半价
	* @param id 第二件半价主键
	* @return HalfPrice 第二件半价
	*/
	HalfPriceDO edit(HalfPriceVO halfPrice, Integer id);

	/**
	 * 删除第二件半价
	 * @param id 第二件半价主键
	 */
	void delete(Integer id);

	/**
	 * 获取第二件半价
	 * @param id 第二件半价主键
	 * @return HalfPrice  第二件半价
	 */
	HalfPriceVO getFromDB(Integer id);

	/**
	 * 验证操作权限<br/>
	 * 如有问题直接抛出权限异常
	 * @param id
	 */
	void verifyAuth(Integer id);

}
