/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.goods.service.impl;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.message.CategoryChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryBrandDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategorySpecDO;
import cloud.shopfly.b2c.core.goods.model.dos.SpecificationDO;
import cloud.shopfly.b2c.core.goods.model.vo.CategoryPluginVO;
import cloud.shopfly.b2c.core.goods.model.vo.CategoryVO;
import cloud.shopfly.b2c.core.goods.service.CategoryManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 商品分类业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0 2018-03-15 17:22:06
 */
@Service
public class CategoryManagerImpl implements CategoryManager {

    @Autowired

    private DaoSupport goodsDaoSupport;

    @Autowired
    private Cache cache;

    @Autowired
    private MessageSender messageSender;

    private final String CATEGORY_CACHE_ALL = CachePrefix.GOODS_CAT.getPrefix() + "ALL";


    @Override
    public List<CategoryVO> listAllChildren(Integer parentId) {
        // 从缓存取所有的分类
        List<CategoryDO> list = (List<CategoryDO>) cache.get(CATEGORY_CACHE_ALL);
        if (list == null) {

            // 调用初始化分类缓存方法
            list = initCategory();
        }
        List<CategoryVO> topCatList = new ArrayList<CategoryVO>();

        for (CategoryDO cat : list) {
            CategoryVO categoryVo = new CategoryVO(cat);
            if (cat.getParentId().compareTo(parentId) == 0) {
                List<CategoryVO> children = this.getChildren(list, cat.getCategoryId());
                categoryVo.setChildren(children);
                topCatList.add(categoryVo);
            }
        }
        return topCatList;
    }

    @Override
    public CategoryDO getModel(Integer id) {

        return this.goodsDaoSupport.queryForObject(CategoryDO.class, id);
    }

    @Override
    public List<CategoryDO> getCategory(Integer categoryId) {

        String sql = "select * from es_category  where parent_id=? order by category_order asc";

        return this.goodsDaoSupport.queryForList(sql, CategoryDO.class, categoryId);

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CategoryDO add(CategoryDO category) {
        CategoryDO parent = null;
        //不能添加重复的分类名称
        String sqlQuery = "select * from es_category where name = ? ";
        List list = this.goodsDaoSupport.queryForList(sqlQuery, category.getName());
        if(StringUtil.isNotEmpty(list)){
            throw new ServiceException(GoodsErrorCode.E300.code(), "该分类名称已存在");
        }

        // 非顶级分类
        if (category.getParentId() != null && category.getParentId() != 0) {
            parent = this.getModel(category.getParentId());
            if (parent == null) {
                throw new ServiceException(GoodsErrorCode.E300.code(), "父分类不存在");
            }
            // 替换catPath 根据catPath规则来匹配级别
            String catPath = parent.getCategoryPath().replace("|", ",");
            String[] str = catPath.split(",");
            // 如果当前的catPath length 大于4 证明当前分类级别大于五级 提示
            if (str.length >= 4) {
                throw new ServiceException(GoodsErrorCode.E300.code(), "最多为三级分类,添加失败");
            }
        }

        this.goodsDaoSupport.insert(category);
        int categoryId = this.goodsDaoSupport.getLastId("");
        category.setCategoryId(categoryId);

        String sql = "";
        // 判断是否是顶级类似别，如果parentid为空或为0则为顶级类似别
        // 注意末尾都要加|，以防止查询子孙时出错
        // 不是顶级类别，有父
        if (parent != null) {
            category.setCategoryPath(parent.getCategoryPath() + categoryId + "|");
        } else {// 是顶级类别
            category.setCategoryPath("0|" + categoryId + "|");
        }

        sql = "update es_category set  category_path=? where  category_id=?";
        this.goodsDaoSupport.execute(sql, category.getCategoryPath(), categoryId);

        cache.remove(CATEGORY_CACHE_ALL);

        CategoryChangeMsg categoryChangeMsg = new CategoryChangeMsg(categoryId, CategoryChangeMsg.ADD_OPERATION);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CATEGORY_CHANGE, AmqpExchange.GOODS_CATEGORY_CHANGE + "_ROUTING", categoryChangeMsg));

        return category;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CategoryDO edit(CategoryDO category, Integer id) {
        CategoryDO parent = null;

        CategoryDO catTemp = this.getModel(id);

        //不能添加重复的分类名称
        String sqlQuery = "select * from es_category where name = ? and category_id != ? ";
        List listQuery = this.goodsDaoSupport.queryForList(sqlQuery, category.getName(),id);
        if(StringUtil.isNotEmpty(listQuery)){
            throw new ServiceException(GoodsErrorCode.E300.code(), "该分类名称已存在");
        }

        // 如果有子分类则不能更换上级分类
        // 更换上级分类
        if (!category.getParentId().equals(catTemp.getParentId())) {
            // 查看是否有子分类
            List list = this.list(id, null);
            if (list != null && list.size() > 0) {
                throw new ServiceException(GoodsErrorCode.E300.code(), "当前分类有子分类，不能更换上级分类");
            } else {
                parent = this.getModel(category.getParentId());
                if (parent == null) {
                    throw new ServiceException(GoodsErrorCode.E300.code(), "父分类不存在");
                }
                // 替换catPath 根据catPath规则来匹配级别
                String catPath = parent.getCategoryPath().replace("|", ",");
                String[] str = catPath.split(",");
                // 如果当前的catPath length 大于4 证明当前分类级别大于五级 提示
                if (str.length >= 4) {
                    throw new ServiceException(GoodsErrorCode.E300.code(), "最多为三级分类,添加失败");
                }
                category.setCategoryPath(parent.getCategoryPath() + category.getCategoryId() + "|");
            }
        }

        this.goodsDaoSupport.update(category, id);

        cache.remove(CATEGORY_CACHE_ALL);

        CategoryChangeMsg categoryChangeMsg = new CategoryChangeMsg(id, CategoryChangeMsg.UPDATE_OPERATION);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CATEGORY_CHANGE, AmqpExchange.GOODS_CATEGORY_CHANGE + "_ROUTING", categoryChangeMsg));

        return category;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        List<CategoryVO> list = this.listAllChildren(id);
        if (list != null && list.size() > 0) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "此类别下存在子类别不能删除");
        }
        // 查询某商品分类的商品
        String goodsSql = "select count(0) from es_goods where category_id = ? and disabled != -1";
        Integer count = this.goodsDaoSupport.queryForInt(goodsSql, id);

        if (count > 0) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "此类别下存在商品不能删除");
        }

        // 缓存
        cache.remove(CachePrefix.GOODS_CAT.getPrefix() + id);
        cache.remove(CATEGORY_CACHE_ALL);

        this.goodsDaoSupport.delete(CategoryDO.class, id);
        this.goodsDaoSupport.execute("delete from es_category_brand where category_id = ?",id);
        this.goodsDaoSupport.execute("delete from es_category_spec where category_id = ?",id);
    }

    @Override
    public List<CategoryBrandDO> saveBrand(Integer categoryId, Integer[] chooseBrands) {
        CategoryDO category = this.getModel(categoryId);
        if (category == null) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "该分类不存在");
        }
        List<CategoryBrandDO> res = new ArrayList<>();
        if(chooseBrands==null){
            this.goodsDaoSupport.execute("delete from es_category_brand where category_id = ?", categoryId);
            return res;
        }


        //查看所选品牌是否存在
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(chooseBrands, term);
        Integer count = this.goodsDaoSupport.queryForInt("select count(0) from es_brand where brand_id in(" + str + ")", term.toArray());

        if (count < chooseBrands.length) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "品牌传参错误");
        }
        String sql = "delete from es_category_brand where category_id = ?";
        this.goodsDaoSupport.execute(sql, categoryId);


        for (int i = 0; i < chooseBrands.length; i++) {
            CategoryBrandDO categoryBrand = new CategoryBrandDO(categoryId, chooseBrands[i]);
            this.goodsDaoSupport.insert(categoryBrand);

            res.add(categoryBrand);
        }

        return res;

    }

    @Override
    public List<CategorySpecDO> saveSpec(Integer categoryId, Integer[] chooseSpecs) {

        //修改规格前判断规格有没有商品，如果有则不允许删除
        this.checkOldSpecs(categoryId,chooseSpecs);

        CategoryDO category = this.getModel(categoryId);
        if (category == null) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "该分类不存在");
        }
        List<CategorySpecDO> res = new ArrayList<>();
        if(chooseSpecs==null){
            this.goodsDaoSupport.execute("delete from es_category_spec where category_id = ?", categoryId);
            return res;
        }

        //查看所选规格是否存在
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(chooseSpecs, term);
        Integer count = this.goodsDaoSupport.queryForInt("select count(0) from es_specification where spec_id in(" + str + ")", term.toArray());

        if (count < chooseSpecs.length) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "规格绑定传参错误");
        }

        String sql = "delete from es_category_spec where category_id = ?";
        this.goodsDaoSupport.execute(sql, categoryId);


        for (Integer specId : chooseSpecs) {
            CategorySpecDO categorySpec = new CategorySpecDO(categoryId, specId);
            this.goodsDaoSupport.insert(categorySpec);
            res.add(categorySpec);
        }
        return res;

    }

    /**
     * 判断规格有没有商品，如果有则不允许删除
     * @param categoryId 分类id
     * @param chooseSpecs 新规格id数组
     */
    private void checkOldSpecs(Integer categoryId,Integer[] chooseSpecs) {
        //查询出旧的分类和规则关系数据
        List<CategorySpecDO> oldCategorySpecDOList = goodsDaoSupport.queryForList("select * from es_category_spec where category_id = ?",CategorySpecDO.class,categoryId);
        List<Integer> newSpecList = null;
        if (null == chooseSpecs){
            newSpecList = Lists.newArrayList();
        }else{
            newSpecList = Arrays.asList(chooseSpecs);
        }
        List<Integer> newDeleteSpecList = Lists.newArrayList();
        //查询出将被删除的分类下的规则
        if (oldCategorySpecDOList!=null && oldCategorySpecDOList.size()>0){
            for (CategorySpecDO item :oldCategorySpecDOList) {
                if (!newSpecList.contains(item.getSpecId())){
                    newDeleteSpecList.add(item.getSpecId());
                }
            }
        }
        //判断将被删除的规则是否正在被使用，如果正在被使用则不允许删除
        if (newDeleteSpecList.size()>0){
            for (Integer specId :newDeleteSpecList) {
                Integer count = goodsDaoSupport.queryForInt("select count(0) from es_goods_sku where category_id = ? and specs like '%"+specId+"%'",categoryId);
                if (count>0){
                    SpecificationDO specificationDO = goodsDaoSupport.queryForObject(SpecificationDO.class,specId);
                    throw new ServiceException(GoodsErrorCode.E300.code(),"解绑的规格正被商品使用，如"+specificationDO.getSpecName());
                }
            }
        }
    }

    /**
     * 初始化分类缓存
     *
     * @return
     */
    private List<CategoryDO> initCategory() {
        List<CategoryDO> list = this.getCategoryList();
        if (list != null && list.size() > 0) {
            for (CategoryDO cat : list) {
                cache.put(CachePrefix.GOODS_CAT.getPrefix() + cat.getCategoryId(), cat);
            }
            cache.put(CATEGORY_CACHE_ALL, list);
        }
        return list;
    }

    /**
     * 查询分类列表
     *
     * @return
     */
    private List<CategoryDO> getCategoryList() {
        // 不能修改为缓存读取
        String sql = "select * from es_category order by category_order asc";
        List<CategoryDO> list = this.goodsDaoSupport.queryForList(sql, CategoryDO.class);
        return list;
    }

    /**
     * 得到当前分类的子孙
     *
     * @param catList  分类集合
     * @param parentid 父分类id
     * @return 带子分类的集合
     */
    private List<CategoryVO> getChildren(List<CategoryDO> catList, Integer parentid) {
        List<CategoryVO> children = new ArrayList<CategoryVO>();
        for (CategoryDO cat : catList) {
            CategoryVO categoryVo = new CategoryVO(cat);
            if (cat.getParentId().compareTo(parentid) == 0) {
                categoryVo.setChildren(this.getChildren(catList, cat.getCategoryId()));
                children.add(categoryVo);
            }
        }
        return children;
    }

    @Override
    public List list(Integer parentId, String format) {
        if (parentId == null) {
            return null;
        }
        String sql = "select c.* from es_category c  where c.parent_id = ? order by c.category_order asc";
        if (format != null) {
            List<CategoryPluginVO> catlist = this.goodsDaoSupport.queryForList(sql, CategoryPluginVO.class, parentId);
            return catlist;
        } else {
            List<CategoryVO> catlist = this.goodsDaoSupport.queryForList(sql, CategoryVO.class, parentId);

            for (CategoryVO categoryVo : catlist) {
                List<CategoryVO> listAllChildren = this.listChildren(categoryVo.getCategoryId());
                if (listAllChildren.size() > 0) {
                    categoryVo.setChildren(listAllChildren);
                }
            }
            return catlist;
        }
    }

    /**
     * 获取某个类别的所有子类
     *
     * @param parentId
     * @return
     */
    private List<CategoryVO> listChildren(Integer parentId) {
        // 从缓存取所有的分类
        List<CategoryDO> list = (List<CategoryDO>) cache.get(CATEGORY_CACHE_ALL);
        if (list == null) {
            // 调用初始化分类缓存方法
            list = initCategory();
        }
        List<CategoryVO> topCatList = new ArrayList<CategoryVO>();

        for (CategoryDO cat : list) {
            CategoryVO categoryVo = new CategoryVO(cat);
            if (cat.getParentId().compareTo(parentId) == 0) {
                topCatList.add(categoryVo);
            }
        }
        return topCatList;
    }

}
