/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.service.impl;

import dev.shopflix.core.goods.GoodsErrorCode;
import dev.shopflix.core.goods.model.dos.CategorySpecDO;
import dev.shopflix.core.goods.model.dos.SpecValuesDO;
import dev.shopflix.core.goods.model.dos.SpecificationDO;
import dev.shopflix.core.goods.model.vo.SelectVO;
import dev.shopflix.core.goods.model.vo.SpecificationVO;
import dev.shopflix.core.goods.service.CategoryManager;
import dev.shopflix.core.goods.service.SpecificationManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规格项业务类
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 09:31:27
 */
@Service
public class SpecificationManagerImpl implements SpecificationManager {

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired
    private CategoryManager categoryManager;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_specification  where disabled = 1 order by spec_id desc";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, SpecificationDO.class);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SpecificationDO add(SpecificationDO specification) {


        //如果是管理端添加的规格，则验证管理端的对个名称是否重复
        String sql = "select * from es_specification  where disabled = 1 and spec_name = ? ";
        List list = this.daoSupport.queryForList(sql, specification.getSpecName());

        if (list.size() > 0) {
            throw new ServiceException(GoodsErrorCode.E305.code(), "规格名称重复");
        }


        specification.setDisabled(1);
        this.daoSupport.insert(specification);
        specification.setSpecId(this.daoSupport.getLastId(""));

        return specification;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SpecificationDO edit(SpecificationDO specification, Integer id) {

        SpecificationDO model = this.getModel(id);
        if (model == null) {
            throw new ServiceException(GoodsErrorCode.E305.code(), "规格不存在");
        }

        String sql = "select * from es_specification  where disabled = 1 and spec_name = ? and spec_id!=? ";
        List list = this.daoSupport.queryForList(sql, specification.getSpecName(),id);

        if (list.size() > 0) {
            throw new ServiceException(GoodsErrorCode.E305.code(), "规格名称重复");
        }

        this.daoSupport.update(specification, id);
        return specification;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer[] ids) {

        List<Object> term = new ArrayList<>();
        String idsStr = SqlUtil.getInSql(ids, term);
        //查看是否已经有分类绑定了该规格
        String sql = "select * from es_category_spec where spec_id in (" + idsStr + ")";
        List<CategorySpecDO> list = this.daoSupport.queryForList(sql, CategorySpecDO.class, term.toArray());
        if (list.size() > 0) {

            throw new ServiceException(GoodsErrorCode.E305.code(), "有分类已经绑定要删除的规格，请先解绑分类规格");
        }

        sql = " update es_specification set disabled = 0 where spec_id in (" + idsStr + ")";

        this.daoSupport.execute(sql, term.toArray());
    }

    @Override
    public SpecificationDO getModel(Integer id) {

        return this.daoSupport.queryForObject(SpecificationDO.class, id);
    }

    @Override
    public List<SelectVO> getCatSpecification(Integer categoryId) {

        String sql = "select s.spec_id id,s.spec_name text,  "
                + "case category_id when ? then true else false end selected  "
                + "from es_specification s left join  es_category_spec cs "
                + "on s.spec_id=cs.spec_id and category_id=? where s.disabled=1";

        return this.daoSupport.queryForList(sql, SelectVO.class, categoryId, categoryId);
    }


    @Override
    public List<SpecificationVO> querySpec(Integer categoryId) {
        //查询规格
        String sql = "select s.spec_id,s.spec_name "
                + "from es_specification s inner join es_category_spec cs on s.spec_id=cs.spec_id "
                + "where cs.category_id = ? ";
        List<SpecificationVO> specList = this.daoSupport.queryForList(sql, SpecificationVO.class, categoryId);

        //没有规格
        if (specList == null || specList.size() == 0) {
            return new ArrayList<>();
        }
        //封装规格id的集合
        String[] temp = new String[specList.size()];
        List<Object> specIdList = new ArrayList<>();

        for (int i = 0; i < specList.size(); i++) {
            specIdList.add(specList.get(i).getSpecId());
            temp[i] = "?";
        }
        String str = StringUtil.arrayToString(temp, ",");

        String sqlValue = "select * from es_spec_values where spec_id in (" + str + ")";
        //查询到的是所有规格的规格值
        List<SpecValuesDO> valueList = this.daoSupport.queryForList(sqlValue, SpecValuesDO.class, specIdList.toArray());

        Map<Integer, List<SpecValuesDO>> map = new HashMap<>(valueList.size());
        for (SpecValuesDO specValue : valueList) {

            List<SpecValuesDO> list = map.get(specValue.getSpecId());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(specValue);
            map.put(specValue.getSpecId(), list);
        }
        //赋值规格值
        for (SpecificationVO vo : specList) {
            vo.setValueList(map.get(vo.getSpecId()));
        }

        return specList;

    }
}
