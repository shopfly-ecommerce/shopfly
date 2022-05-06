/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.service;

import cloud.shopfly.b2c.core.goods.model.dos.SpecificationDO;
import cloud.shopfly.b2c.core.goods.model.vo.SelectVO;
import cloud.shopfly.b2c.core.goods.model.vo.SpecificationVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 规格项业务层
 * 
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-20 09:31:27
 */
public interface SpecificationManager {

	/**
	 * 查询规格项列表
	 * 
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @return Page
	 */
	Page list(int page, int pageSize);

	/**
	 * 添加规格项
	 * 
	 * @param specification
	 *            规格项
	 * @return Specification 规格项
	 */
	SpecificationDO add(SpecificationDO specification);

	/**
	 * 修改规格项
	 * 
	 * @param specification
	 *            规格项
	 * @param id
	 *            规格项主键
	 * @return Specification 规格项
	 */
	SpecificationDO edit(SpecificationDO specification, Integer id);

	/**
	 * 删除规格项
	 * 
	 * @param ids
	 *            规格项主键
	 */
	void delete(Integer[] ids);

	/**
	 * 获取规格项
	 * 
	 * @param id
	 *            规格项主键
	 * @return Specification 规格项
	 */
	SpecificationDO getModel(Integer id);

	/**
	 * 查询分类绑定的规格，系统规格
	 * 
	 * @param categoryId
	 * @return
	 */
	List<SelectVO> getCatSpecification(Integer categoryId);

	/**
	 * 商家查询某分类的规格
	 *
	 * @param categoryId
	 * @return
	 */
	List<SpecificationVO> querySpec(Integer categoryId);

}