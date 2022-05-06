/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.exchange.service.impl;

import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeCat;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.promotion.exchange.service.ExchangeCatManager;
import cloud.shopfly.b2c.core.promotion.exchange.service.ExchangeGoodsManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分兑换分类业务类
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-29 16:56:22
 */
@Service
public class ExchangeCatManagerImpl implements ExchangeCatManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private ExchangeGoodsManager exchangeGoodsManager;

    @Override
    public List<ExchangeCat> list(Integer parentId) {

        String sql = "select * from es_exchange_cat where parent_id = ? order by category_order asc ";
        List<ExchangeCat> list = this.daoSupport.queryForList(sql, ExchangeCat.class, parentId);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ExchangeCat add(ExchangeCat exchangeCat) {

        //检测名称是否重复
        this.check(exchangeCat,null);

        this.daoSupport.insert(exchangeCat);

        return exchangeCat;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ExchangeCat edit(ExchangeCat exchangeCat, Integer id) {

        //检测名称是否重复
        this.check(exchangeCat,id);

        this.daoSupport.update(exchangeCat, id);
        return exchangeCat;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {

        //删除积分分类，需要判断分类下是否有积分商品
        List<ExchangeDO> list = exchangeGoodsManager.getModelByCategoryId(id);
        if (StringUtil.isNotEmpty(list)) {
            throw new ServiceException(GoodsErrorCode.E300.code(), "此类别下存在商品不能删除");
        }

        this.daoSupport.delete(ExchangeCat.class, id);
    }

    @Override
    public ExchangeCat getModel(Integer id) {

        return this.daoSupport.queryForObject(ExchangeCat.class, id);

    }

    /**
     * 检查添加编辑的合法性
     *
     * @param exchangeCat
     * @param id
     */
    private void check(ExchangeCat exchangeCat, Integer id) {

        String sql = "select * from es_exchange_cat where name = ? ";
        List term = new ArrayList();
        term.add(exchangeCat.getName());
        if (id != null) {
            sql += " and category_id != ? ";
            term.add(id);
        }

        List list = this.daoSupport.queryForList(sql, term.toArray());
        if (list.size() > 0) {
            throw new ServiceException(PromotionErrorCode.E407.code(), "积分分类名称重复");
        }

        if (exchangeCat.getParentId() == null) {
            exchangeCat.setParentId(0);
        }
        if (exchangeCat.getListShow() == null) {
            exchangeCat.setListShow(1);
        }
    }
}
