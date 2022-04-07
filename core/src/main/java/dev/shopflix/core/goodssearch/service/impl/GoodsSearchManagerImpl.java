/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goodssearch.service.impl;

import dev.shopflix.core.client.system.HotkeywordClient;
import dev.shopflix.core.goods.model.dos.BrandDO;
import dev.shopflix.core.goods.model.dos.CategoryDO;
import dev.shopflix.core.goods.model.vo.CategoryVO;
import dev.shopflix.core.goods.service.BrandManager;
import dev.shopflix.core.goods.service.CategoryManager;
import dev.shopflix.core.goods.util.CatUrlUtils;
import dev.shopflix.core.goods.util.Separator;

import dev.shopflix.core.goodssearch.model.*;
import dev.shopflix.core.goodssearch.service.GoodsSearchManager;
import dev.shopflix.core.goodssearch.util.HexUtil;
import dev.shopflix.core.goodssearch.util.SelectorUtil;
import dev.shopflix.core.goodssearch.util.SortContainer;
import dev.shopflix.core.pagedata.model.HotKeyword;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.elasticsearch.EsConfig;
import dev.shopflix.framework.elasticsearch.EsSettings;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.StringUtil;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.InternalAggregations;
import org.elasticsearch.search.aggregations.bucket.nested.InternalNested;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 基于es的商品检索
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年9月18日 上午11:42:06
 */
@Service
public class GoodsSearchManagerImpl implements GoodsSearchManager {

    @Autowired
    protected CategoryManager categoryManager;

    @Autowired
    protected BrandManager brandManager;

    @Autowired
    protected DaoSupport daoSupport;

    @Autowired
    protected EsConfig esConfig;

    @Autowired
    protected ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private HotkeywordClient hotkeywordClient;


    public GoodsSearchManagerImpl() {
         System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Override
    public Page search(GoodsSearchDTO goodsSearch) {

        Integer pageNo = goodsSearch.getPageNo();
        Integer pageSize = goodsSearch.getPageSize();

        SearchRequestBuilder searchRequestBuilder;
        try {
            searchRequestBuilder = this.createQuery(goodsSearch);
            //设置分页信息
            searchRequestBuilder.setFrom((pageNo - 1) * pageSize).setSize(pageSize);
            // 设置是否按查询匹配度排序
            searchRequestBuilder.setExplain(true);
            SearchResponse response = searchRequestBuilder.execute().actionGet();

            SearchHits searchHits = response.getHits();
            List<GoodsSearchLine> resultlist = new ArrayList<>();
            for (SearchHit hit : searchHits) {
                Map<String, Object> map = hit.getSource();
                GoodsSearchLine goodsSearchLine = new GoodsSearchLine();
                goodsSearchLine.setName(map.get("goodsName").toString());
                goodsSearchLine.setDiscountPrice(StringUtil.toDouble(map.get("discountPrice").toString()));
                goodsSearchLine.setThumbnail(map.get("thumbnail").toString());
                goodsSearchLine.setPrice(StringUtil.toDouble(map.get("price").toString(), 0d));
                goodsSearchLine.setGoodsId(Integer.parseInt(map.get("goodsId").toString()));
                goodsSearchLine.setSmall(map.get("small").toString());
                goodsSearchLine.setCommentNum(Integer.parseInt(map.get("commentNum").toString()));
                goodsSearchLine.setBuyCount(Integer.parseInt(map.get("buyCount").toString()));
                goodsSearchLine.setGrade(StringUtil.toDouble(map.get("grade").toString(), 0d));
                resultlist.add(goodsSearchLine);
            }
            Page webPage = new Page<>(pageNo, searchHits.getTotalHits(), pageSize, resultlist);

            return webPage;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Page(pageNo, 0L, pageSize, new ArrayList());

    }

    @Override
    public Map<String, Object> getSelector(GoodsSearchDTO goodsSearch) {
        SearchRequestBuilder searchRequestBuilder;
        try {
            searchRequestBuilder = this.createQuery(goodsSearch);
            //分类
            AggregationBuilder categoryTermsBuilder = AggregationBuilders.terms("categoryAgg").field("categoryId").size(Integer.MAX_VALUE);
            //品牌
            AggregationBuilder brandTermsBuilder = AggregationBuilders.terms("brandAgg").field("brand").size(Integer.MAX_VALUE);
            //参数
            AggregationBuilder valuesBuilder = AggregationBuilders.terms("valueAgg").field("params.value").size(Integer.MAX_VALUE);
            AggregationBuilder paramsNameBuilder = AggregationBuilders.terms("nameAgg").field("params.name").subAggregation(valuesBuilder).size(Integer.MAX_VALUE);
            AggregationBuilder avgBuild = AggregationBuilders.nested("paramsAgg", "params").subAggregation(paramsNameBuilder);

            searchRequestBuilder.addAggregation(categoryTermsBuilder);
            searchRequestBuilder.addAggregation(brandTermsBuilder);
            searchRequestBuilder.addAggregation(avgBuild);

            SearchResponse sr = searchRequestBuilder.execute().actionGet();
            Map<String, Aggregation> aggMap = sr.getAggregations().asMap();

            Map<String, Object> map = new HashMap<>(16);

            //分类
            LongTerms categoryTerms = (LongTerms) aggMap.get("categoryAgg");
            List<LongTerms.Bucket> categoryBuckets = categoryTerms.getBuckets();

            List<CategoryVO> allCatList = this.categoryManager.listAllChildren(0);

            List<SearchSelector> catDim = SelectorUtil.createCatSelector(categoryBuckets, allCatList, goodsSearch.getCategory());
            map.put("cat", catDim);
            String catPath = null;
            if (goodsSearch.getCategory() != null) {
                CategoryDO cat = categoryManager.getModel(goodsSearch.getCategory());
                String path = cat.getCategoryPath();
                catPath = path.replace("|", Separator.SEPARATOR_PROP_VLAUE).substring(0, path.length() - 1);
            }

            List<SearchSelector> selectedCat = CatUrlUtils.getCatDimSelected(categoryBuckets, allCatList, catPath);
            //已经选择的分类
            map.put("selected_cat", selectedCat);

            //品牌
            LongTerms brandTerms = (LongTerms) aggMap.get("brandAgg");
            List<LongTerms.Bucket> brandBuckets = brandTerms.getBuckets();
            List<BrandDO> brandList = brandManager.getAllBrands();
            List<SearchSelector> brandDim = SelectorUtil.createBrandSelector(brandBuckets, brandList);
            map.put("brand", brandDim);

            //参数
            InternalNested paramsAgg = (InternalNested) aggMap.get("paramsAgg");
            InternalAggregations paramTerms = paramsAgg.getAggregations();
            Map<String, Aggregation> nameMap = paramTerms.asMap();
            StringTerms nameTerms = (StringTerms) nameMap.get("nameAgg");


            Iterator<StringTerms.Bucket> paramBucketIt = nameTerms.getBuckets().iterator();


            List<PropSelector> paramDim = SelectorUtil.createParamSelector(paramBucketIt);
            map.put("prop", paramDim);


            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>(16);
    }


    /**
     * 构建查询条件
     *
     * @return
     * @throws Exception
     */
    protected SearchRequestBuilder createQuery(GoodsSearchDTO goodsSearch) throws Exception {


        String keyword = goodsSearch.getKeyword();
        Integer cat = goodsSearch.getCategory();
        Integer brand = goodsSearch.getBrand();
        String price = goodsSearch.getPrice();
        SearchRequestBuilder searchRequestBuilder = elasticsearchTemplate.getClient().prepareSearch(esConfig.getIndexName()+"_"+ EsSettings.GOODS_INDEX_NAME);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 关键字检索
        if (!StringUtil.isEmpty(keyword)) {
            QueryStringQueryBuilder queryString = new QueryStringQueryBuilder(keyword).field("goodsName");
            queryString.defaultOperator(Operator.AND);
            queryString.analyzer("ik_max_word");
            boolQueryBuilder.must(queryString);
        }
        // 品牌搜素
        if (brand != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("brand", brand));
        }
        // 分类检索
        if (cat != null) {

            CategoryDO category = categoryManager.getModel(cat);
            if (category == null) {
                throw new ServiceException("", "该分类不存在");
            }

            boolQueryBuilder.must(QueryBuilders.wildcardQuery("categoryPath", HexUtil.encode(category.getCategoryPath()) + "*"));
        }

        // 参数检索
        String prop = goodsSearch.getProp();
        if (!StringUtil.isEmpty(prop)) {
            String[] propArray = prop.split(Separator.SEPARATOR_PROP);
            for (String p : propArray) {
                String[] onpropAr = p.split(Separator.SEPARATOR_PROP_VLAUE);
                String name = onpropAr[0];
                String value = onpropAr[1];
                boolQueryBuilder.must(QueryBuilders.nestedQuery("params", QueryBuilders.termQuery("params.name", name), ScoreMode.None));
                boolQueryBuilder.must(QueryBuilders.nestedQuery("params", QueryBuilders.termQuery("params.value", value), ScoreMode.None));
            }
        }

        //价格搜索
        if (!StringUtil.isEmpty(price)) {
            String[] pricear = price.split(Separator.SEPARATOR_PROP_VLAUE);
            double min = StringUtil.toDouble(pricear[0], 0.0);
            double max = Integer.MAX_VALUE;

            if (pricear.length == 2) {
                max = StringUtil.toDouble(pricear[1], Double.MAX_VALUE);
            }
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").from(min).to(max).includeLower(true).includeUpper(true));
        }

        // 删除的商品不显示
        boolQueryBuilder.must(QueryBuilders.termQuery("disabled", "1"));
        // 未上架的商品不显示
        boolQueryBuilder.must(QueryBuilders.termQuery("marketEnable", "1"));

        searchRequestBuilder.setQuery(boolQueryBuilder);

        //排序
        String sortField = goodsSearch.getSort();

        String sortId = "goodsId";

        SortOrder sort = SortOrder.DESC;

        if (sortField != null) {

            Map<String, String> sortMap = SortContainer.getSort(sortField);

            sortId = sortMap.get("id");

            // 如果是默认排序
            if ("def".equals(sortId)) {
                sortId = "goodsId";
            }
            if ("buynum".equals(sortId)) {
                sortId = "buyCount";
            }

            if ("desc".equals(sortMap.get("def_sort"))) {
                sort = SortOrder.DESC;
            } else {
                sort = SortOrder.ASC;
            }
        }


        // 根据评论数量排序
        if ("grade".equals(sortId)) {
            searchRequestBuilder.addSort("commentNum", sort);
        } else {
            searchRequestBuilder.addSort(sortId, sort);
        }

        return searchRequestBuilder;


    }

    @Override
    public List<GoodsWords> getGoodsWords(String keyword) {

        String sql = "select words,goods_num from es_goods_words " +
                "where words like ? or quanpin like ? or szm like ? order by goods_num desc";
        return (List<GoodsWords>) this.daoSupport
                .queryForPage(sql, 1, 15, GoodsWords.class, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%").getData();
    }

    @Override
    public Page recommendGoodsList(GoodsSearchDTO goodsSearch) {
        List<HotKeyword> hotKeywords = hotkeywordClient.listByNum(1);
        String keywords = "";
        if(StringUtil.isNotEmpty(hotKeywords)){
            keywords = hotKeywords.get(0).getHotName();
        }
        goodsSearch.setKeyword(keywords);
        return search(goodsSearch);
    }
}