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
 * Commodity classification business class
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
        // Cache access all categories
        List<CategoryDO> list = (List<CategoryDO>) cache.get(CATEGORY_CACHE_ALL);
        if (list == null) {

            // Call the initialization class cache method
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
        // You cannot add duplicate category names
        String sqlQuery = "select * from es_category where name = ? ";
        List list = this.goodsDaoSupport.queryForList(sqlQuery, category.getName());
        if(StringUtil.isNotEmpty(list)){
            throw new ServiceException(GoodsErrorCode.E300.code(), "The category name already exists");
        }

        // Non-top-level classification
        if (category.getParentId() != null && category.getParentId() != 0) {
            parent = this.getModel(category.getParentId());
            if (parent == null) {
                throw new ServiceException(GoodsErrorCode.E300.code(), "The parent category does not exist");
            }
            // Replace catPath to match levels according to catPath rules
            String catPath = parent.getCategoryPath().replace("|", ",");
            String[] str = catPath.split(",");
            // If the current catPath length is greater than 4, the current classification level is greater than 5
            if (str.length >= 4) {
                throw new ServiceException(GoodsErrorCode.E300.code(), "It is at most tertiary classification,Add failure");
            }
        }

        this.goodsDaoSupport.insert(category);
        int categoryId = this.goodsDaoSupport.getLastId("");
        category.setCategoryId(categoryId);

        String sql = "";
        // Check whether it is a top-level similarity. If parentid is empty or 0, it is a top-level similarity
        // Note at the end of all want to add |, an error occurred when sons in order to prevent the query
        // Its not a top-level category, it has a parent
        if (parent != null) {
            category.setCategoryPath(parent.getCategoryPath() + categoryId + "|");
        } else {// Is the top-level category
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

        // You cannot add duplicate category names
        String sqlQuery = "select * from es_category where name = ? and category_id != ? ";
        List listQuery = this.goodsDaoSupport.queryForList(sqlQuery, category.getName(),id);
        if(StringUtil.isNotEmpty(listQuery)){
            throw new ServiceException(GoodsErrorCode.E300.code(), "The category name already exists");
        }

        // If there are subcategories, you cannot change the parent category
        // Replacement of superior classification
        if (!category.getParentId().equals(catTemp.getParentId())) {
            // Check to see if there are subcategories
            List list = this.list(id, null);
            if (list != null && list.size() > 0) {
                throw new ServiceException(GoodsErrorCode.E300.code(), "The current category has subcategories and cannot be replaced");
            } else {
                parent = this.getModel(category.getParentId());
                if (parent == null) {
                    throw new ServiceException(GoodsErrorCode.E300.code(), "The parent category does not exist");
                }
                // Replace catPath to match levels according to catPath rules
                String catPath = parent.getCategoryPath().replace("|", ",");
                String[] str = catPath.split(",");
                // If the current catPath length is greater than 4, the current classification level is greater than 5
                if (str.length >= 4) {
                    throw new ServiceException(GoodsErrorCode.E300.code(), "It is at most tertiary classification,Add failure");
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
            throw new ServiceException(GoodsErrorCode.E300.code(), "Subcategories exist under this category and cannot be deleted");
        }
        // Query the commodities of a commodity category
        String goodsSql = "select count(0) from es_goods where category_id = ? and disabled != -1";
        Integer count = this.goodsDaoSupport.queryForInt(goodsSql, id);

        if (count > 0) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "Items in this category cannot be deleted");
        }

        // The cache
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
            throw new ServiceException(GoodsErrorCode.E300.code(), "The category does not exist");
        }
        List<CategoryBrandDO> res = new ArrayList<>();
        if(chooseBrands==null){
            this.goodsDaoSupport.execute("delete from es_category_brand where category_id = ?", categoryId);
            return res;
        }


        // Check whether the selected brand exists
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(chooseBrands, term);
        Integer count = this.goodsDaoSupport.queryForInt("select count(0) from es_brand where brand_id in(" + str + ")", term.toArray());

        if (count < chooseBrands.length) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "Brand parameter transfer error");
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

        // Before modifying the specifications, check whether the specifications exist. If yes, the specifications cannot be deleted
        this.checkOldSpecs(categoryId,chooseSpecs);

        CategoryDO category = this.getModel(categoryId);
        if (category == null) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "The category does not exist");
        }
        List<CategorySpecDO> res = new ArrayList<>();
        if(chooseSpecs==null){
            this.goodsDaoSupport.execute("delete from es_category_spec where category_id = ?", categoryId);
            return res;
        }

        // Check whether the selected specifications exist
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(chooseSpecs, term);
        Integer count = this.goodsDaoSupport.queryForInt("select count(0) from es_specification where spec_id in(" + str + ")", term.toArray());

        if (count < chooseSpecs.length) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "Specifications binding parameter transmission error");
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
     * Check whether the specification has goods, if there is not allowed to delete
     * @param categoryId Categoriesid
     * @param chooseSpecs The new specificationsidAn array of
     */
    private void checkOldSpecs(Integer categoryId,Integer[] chooseSpecs) {
        // Query old classification and rule relational data
        List<CategorySpecDO> oldCategorySpecDOList = goodsDaoSupport.queryForList("select * from es_category_spec where category_id = ?",CategorySpecDO.class,categoryId);
        List<Integer> newSpecList = null;
        if (null == chooseSpecs){
            newSpecList = Lists.newArrayList();
        }else{
            newSpecList = Arrays.asList(chooseSpecs);
        }
        List<Integer> newDeleteSpecList = Lists.newArrayList();
        // Query the rules of the category to be deleted
        if (oldCategorySpecDOList!=null && oldCategorySpecDOList.size()>0){
            for (CategorySpecDO item :oldCategorySpecDOList) {
                if (!newSpecList.contains(item.getSpecId())){
                    newDeleteSpecList.add(item.getSpecId());
                }
            }
        }
        // Determines whether the rule to be deleted is in use. If it is in use, the deletion is not allowed
        if (newDeleteSpecList.size()>0){
            for (Integer specId :newDeleteSpecList) {
                Integer count = goodsDaoSupport.queryForInt("select count(0) from es_goods_sku where category_id = ? and specs like '%"+specId+"%'",categoryId);
                if (count>0){
                    SpecificationDO specificationDO = goodsDaoSupport.queryForObject(SpecificationDO.class,specId);
                    throw new ServiceException(GoodsErrorCode.E300.code(),"Unbound specifications are being used by goods such as"+specificationDO.getSpecName());
                }
            }
        }
    }

    /**
     * Initialize the class cache
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
     * Querying a Category List
     *
     * @return
     */
    private List<CategoryDO> getCategoryList() {
        // Cannot be changed to cache read
        String sql = "select * from es_category order by category_order asc";
        List<CategoryDO> list = this.goodsDaoSupport.queryForList(sql, CategoryDO.class);
        return list;
    }

    /**
     * Get the descendants of the current classification
     *
     * @param catList  Classified collection
     * @param parentid The parent categoryid
     * @return A collection of tape classifications
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
     * Gets all subclasses of a category
     *
     * @param parentId
     * @return
     */
    private List<CategoryVO> listChildren(Integer parentId) {
        // Cache access all categories
        List<CategoryDO> list = (List<CategoryDO>) cache.get(CATEGORY_CACHE_ALL);
        if (list == null) {
            // Call the initialization class cache method
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
