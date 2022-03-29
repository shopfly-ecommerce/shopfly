/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.seckill.service;

import com.enation.app.javashop.core.promotion.seckill.model.dos.SeckillDO;
import com.enation.app.javashop.core.promotion.seckill.model.dto.SeckillDTO;
import com.enation.app.javashop.core.promotion.seckill.model.vo.SeckillGoodsVO;
import com.enation.app.javashop.core.promotion.seckill.model.vo.SeckillVO;
import com.enation.app.javashop.framework.database.Page;

/**
 * 限时抢购入库业务层
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 10:32:36
 */
public interface SeckillManager	{

	/**
	 * 查询限时抢购入库列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @param keywords 关键字
	 * @return Page
	 */
	Page list(int page, int pageSize, String keywords);

	/**
	 * 添加限时抢购入库
	 * @param seckill 限时抢购入库
	 * @return Seckill 限时抢购入库
	 */
	SeckillDTO add(SeckillDTO seckill);

	/**
	* 修改限时抢购入库
	* @param seckill 限时抢购入库
	* @param id 限时抢购入库主键
	* @return Seckill 限时抢购入库
	*/
	SeckillDTO edit(SeckillDTO seckill, Integer id);

	/**
	 * 删除限时抢购入库
	 * @param id 限时抢购入库主键
	 */
	void delete(Integer id);

	/**
	 * 获取限时抢购入库
	 * @param id 限时抢购入库主键
	 * @return Seckill  限时抢购入库
	 */
	SeckillDTO getModelAndRange(Integer id);

	/**
	 * 获取限时抢购入库
	 * @param id 限时抢购入库主键
	 * @return Seckill  限时抢购入库
	 */
	SeckillVO getModelAndApplys(Integer id);


	/**
	 * 获取限时抢购入库
	 * @param id 限时抢购入库主键
	 * @return Seckill  限时抢购入库
	 */
	SeckillDO getModel(Integer id);

	/**
	 * 根据商品ID，读取限时秒杀的活动信息
	 * @param goodsId
	 * @return
	 */
	SeckillGoodsVO getSeckillGoods(Integer goodsId);


	/**
	 * 审核申请
	 * @param applyId	申请ID
	 */
	void reviewApply(Integer applyId);

	/**
	 * 关闭某限时抢购
	 * @param id
	 */
    void close(Integer id);
}
