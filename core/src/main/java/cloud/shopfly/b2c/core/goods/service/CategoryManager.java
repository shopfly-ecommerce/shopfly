/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.service;


import cloud.shopfly.b2c.core.goods.model.dos.CategoryBrandDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategorySpecDO;
import cloud.shopfly.b2c.core.goods.model.vo.CategoryVO;

import java.util.List;

/**
 * 商品分类业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-15 17:22:06
 */
public interface CategoryManager {

    /**
     * 查询某分类下的子分类
     *
     * @param parentId
     * @param format
     * @return
     */
    List list(Integer parentId, String format);

    /**
     * 查询所有的分类，父子关系
     *
     * @param parentId
     * @return
     */
    List<CategoryVO> listAllChildren(Integer parentId);

    /**
     * 获取商品分类
     *
     * @param id 商品分类主键
     * @return CategoryDO 商品分类
     */
    CategoryDO getModel(Integer id);

    /**
     * 获取某分类下的分类
     *
     * @param categoryId
     * @return
     */
    List<CategoryDO> getCategory(Integer categoryId);

    /**
     * 添加商品分类
     *
     * @param category
     *            商品分类
     * @return Category 商品分类
     */
    CategoryDO add(CategoryDO category);

    /**
     * 修改商品分类
     *
     * @param category
     *            商品分类
     * @param id
     *            商品分类主键
     * @return Category 商品分类
     */
    CategoryDO edit(CategoryDO category, Integer id);

    /**
     * 删除商品分类
     *
     * @param id
     *            商品分类主键
     */
    void delete(Integer id);

    /**
     * 保存分类绑定的品牌
     *
     * @param categoryId
     * @param chooseBrands
     * @return
     */
    List<CategoryBrandDO> saveBrand(Integer categoryId, Integer[] chooseBrands);

    /**
     * 保存分类绑定的规格
     *
     * @param categoryId
     * @param chooseSpecs
     * @return
     */
    List<CategorySpecDO> saveSpec(Integer categoryId, Integer[] chooseSpecs);


}