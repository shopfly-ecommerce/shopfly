/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.RateAreaDO;
import cloud.shopfly.b2c.core.system.model.vo.RateAreaVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 区域相关接口
 * @author cs
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-28 21:44:49
 */
public interface RateAreaManager {


	/**
	 * 区域列表
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page list(String name, Integer pageNo, Integer pageSize);

	/**
	 * 新增区域
	 * @param rateAreaVO
	 * @return
	 */
	RateAreaDO add(RateAreaVO rateAreaVO);


	/**
	 * 修改区域
	 * @param rateAreaVO
	 * @return
	 */
	RateAreaDO edit(RateAreaVO rateAreaVO);

	/**
	 * 删除区域
	 * @param rateAreaId
	 */
	void delete(Integer rateAreaId);

	/**
	 * 查询区域详情
	 * @param rateAreaId
	 * @return
	 */
	RateAreaVO getFromDB(Integer rateAreaId);
}