/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.service.impl;

import dev.shopflix.core.goods.GoodsErrorCode;
import dev.shopflix.core.goods.model.dos.TagGoodsDO;
import dev.shopflix.core.goods.model.dos.TagsDO;
import dev.shopflix.core.goods.model.vo.GoodsSelectLine;
import dev.shopflix.core.goods.service.TagsManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品标签业务类
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
@Service
public class TagsManagerImpl implements TagsManager {

    @Autowired

    private DaoSupport daoSupport;


    @Override
    public List<GoodsSelectLine> queryTagGoods(Integer num, String mark) {
        String sql = "select g.goods_id,g.goods_name,g.price,g.sn,g.thumbnail,g.big,g.quantity,g.buy_count from es_tag_goods r "
                + " inner join es_goods g on g.goods_id=r.goods_id "
                + " inner join es_tags t on t.tag_id = r.tag_id"
                + " where g.disabled=1 and g.market_enable=1 and t.mark = ? limit 0,? ";

        return this.daoSupport.queryForList(sql, GoodsSelectLine.class, mark, num);
    }

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_tags ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, TagsDO.class);

        return webPage;
    }

    @Override
    public Page queryTagGoods(Integer tagId, Integer pageNo, Integer pageSize) {
        TagsDO tag = this.getModel(tagId);
        if (tag == null ) {
            throw new ServiceException(GoodsErrorCode.E309.code(), "无权操作");
        }

        String sql = "select g.goods_id,g.goods_name,g.price,g.buy_count,g.enable_quantity,g.thumbnail from es_tag_goods r LEFT JOIN es_goods g ON g.goods_id=r.goods_id  "
                + "where g.disabled=1 and g.market_enable=1 and r.tag_id=? ";

        return this.daoSupport.queryForPage(sql, pageNo, pageSize, tagId);
    }

    @Override
    @Transactional(value = "goodsTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveTagGoods(Integer tagId, Integer[] goodsIds) {
        TagsDO tag = this.getModel(tagId);
        if (tag == null ) {
            throw new ServiceException(GoodsErrorCode.E309.code(), "无权操作");
        }

        if(goodsIds[0] != -1){
            List<Object> term = new ArrayList<>();
            String idStr = SqlUtil.getInSql(goodsIds, term);
            Integer count = this.daoSupport.queryForInt("select count(1) from es_goods where goods_id in (" + idStr + ") ",term.toArray());
            if (goodsIds.length != count) {
                throw new ServiceException(GoodsErrorCode.E309.code(), "无权操作");
            }
        }

        //删除
        String sql = "delete from es_tag_goods where tag_id = ?";
        this.daoSupport.execute(sql,tagId);

        if(goodsIds[0] == -1){
            //表示这个标签下不保存商品
            return;
        }
        //添加
        for (Integer goodsId : goodsIds) {
            TagGoodsDO tagGoods = new TagGoodsDO(tagId, goodsId);
            this.daoSupport.insert(tagGoods);
        }
    }

    @Override
    public TagsDO getModel(Integer id) {

        return this.daoSupport.queryForObject(TagsDO.class, id);
    }
}
