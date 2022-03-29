/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.seckill.service;

import com.enation.app.javashop.core.promotion.seckill.model.dos.SeckillRangeDO;
import com.enation.app.javashop.core.promotion.seckill.model.vo.TimeLineVO;
import com.enation.app.javashop.framework.database.Page;

import java.util.List;

/**
 * 限时抢购时刻业务层
 * @author Snow
 * @version v2.0.0
 * @since v7.0.0
 * 2018-04-02 18:24:47
 */
public interface SeckillRangeManager	{

	/**
	 * 查询限时抢购时刻列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return Page
	 */
	Page list(int page, int pageSize);


	/**
	* 修改限时抢购时刻
	* @param seckillRange 限时抢购时刻
	* @param id 限时抢购时刻主键
	* @return SeckillRange 限时抢购时刻
	*/
	SeckillRangeDO edit(SeckillRangeDO seckillRange, Integer id);

	/**
	 * 删除限时抢购时刻
	 * @param id 限时抢购时刻主键
	 */
	void delete(Integer id);

	/**
	 * 获取限时抢购时刻
	 * @param id 限时抢购时刻主键
	 * @return SeckillRange  限时抢购时刻
	 */
	SeckillRangeDO getModel(Integer id);

	/**
	 * 根据时刻的集合，入库
	 * @param list
	 * @param seckillId
	 */
	void addList(List<Integer> list, Integer seckillId);

	/**
	 * 根据限时抢购活动ID，读取此时刻集合
	 * @param seckillId
	 * @return
	 */
	List<SeckillRangeDO> getList(Integer seckillId);

	/**
	 * 读取当期那秒杀时刻列表
	 * @return
	 */
	List<TimeLineVO> readTimeList();

}
