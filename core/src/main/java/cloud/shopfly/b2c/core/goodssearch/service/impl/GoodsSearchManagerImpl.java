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
package cloud.shopfly.b2c.core.goodssearch.service.impl;

import cloud.shopfly.b2c.core.client.system.HotkeywordClient;
import cloud.shopfly.b2c.core.goodssearch.model.*;
import cloud.shopfly.b2c.core.goodssearch.util.HexUtil;
import cloud.shopfly.b2c.core.goodssearch.util.SelectorUtil;
import cloud.shopfly.b2c.core.goodssearch.util.SortContainer;
import cloud.shopfly.b2c.core.pagedata.model.HotKeyword;
import cloud.shopfly.b2c.core.goods.model.dos.BrandDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.vo.CategoryVO;
import cloud.shopfly.b2c.core.goods.service.BrandManager;
import cloud.shopfly.b2c.core.goods.service.CategoryManager;
import cloud.shopfly.b2c.core.goods.util.CatUrlUtils;
import cloud.shopfly.b2c.core.goods.util.Separator;

import cloud.shopfly.b2c.core.goodssearch.model.*;
import cloud.shopfly.b2c.core.goodssearch.service.GoodsSearchManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.elasticsearch.EsConfig;
import cloud.shopfly.b2c.framework.elasticsearch.EsSettings;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;
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
 * Based on theesProduct search
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017years9month18The morning of11:42:06
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
            // Setting paging Information
            searchRequestBuilder.setFrom((pageNo - 1) * pageSize).setSize(pageSize);
            // Set whether to sort by query match
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
            // Categories
            AggregationBuilder categoryTermsBuilder = AggregationBuilders.terms("categoryAgg").field("categoryId").size(Integer.MAX_VALUE);
            // brand
            AggregationBuilder brandTermsBuilder = AggregationBuilders.terms("brandAgg").field("brand").size(Integer.MAX_VALUE);
            // parameter
            AggregationBuilder valuesBuilder = AggregationBuilders.terms("valueAgg").field("params.value").size(Integer.MAX_VALUE);
            AggregationBuilder paramsNameBuilder = AggregationBuilders.terms("nameAgg").field("params.name").subAggregation(valuesBuilder).size(Integer.MAX_VALUE);
            AggregationBuilder avgBuild = AggregationBuilders.nested("paramsAgg", "params").subAggregation(paramsNameBuilder);

            searchRequestBuilder.addAggregation(categoryTermsBuilder);
            searchRequestBuilder.addAggregation(brandTermsBuilder);
            searchRequestBuilder.addAggregation(avgBuild);

            SearchResponse sr = searchRequestBuilder.execute().actionGet();
            Map<String, Aggregation> aggMap = sr.getAggregations().asMap();

            Map<String, Object> map = new HashMap<>(16);

            // Categories
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
            // Categories that have been selected
            map.put("selected_cat", selectedCat);

            // brand
            LongTerms brandTerms = (LongTerms) aggMap.get("brandAgg");
            List<LongTerms.Bucket> brandBuckets = brandTerms.getBuckets();
            List<BrandDO> brandList = brandManager.getAllBrands();
            List<SearchSelector> brandDim = SelectorUtil.createBrandSelector(brandBuckets, brandList);
            map.put("brand", brandDim);

            // parameter
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
     * Build query criteria
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

        // Keyword search
        if (!StringUtil.isEmpty(keyword)) {
            QueryStringQueryBuilder queryString = new QueryStringQueryBuilder(keyword).field("goodsName");
            queryString.defaultOperator(Operator.AND);
            queryString.analyzer("ik_max_word");
            boolQueryBuilder.must(queryString);
        }
        // Brand search
        if (brand != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("brand", brand));
        }
        // Classification retrieval
        if (cat != null) {

            CategoryDO category = categoryManager.getModel(cat);
            if (category == null) {
                throw new ServiceException("", "The category does not exist");
            }

            boolQueryBuilder.must(QueryBuilders.wildcardQuery("categoryPath", HexUtil.encode(category.getCategoryPath()) + "*"));
        }

        // Parameter retrieval
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

        // Price search
        if (!StringUtil.isEmpty(price)) {
            String[] pricear = price.split(Separator.SEPARATOR_PROP_VLAUE);
            double min = StringUtil.toDouble(pricear[0], 0.0);
            double max = Integer.MAX_VALUE;

            if (pricear.length == 2) {
                max = StringUtil.toDouble(pricear[1], Double.MAX_VALUE);
            }
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").from(min).to(max).includeLower(true).includeUpper(true));
        }

        // Deleted items are not displayed
        boolQueryBuilder.must(QueryBuilders.termQuery("disabled", "1"));
        // Items not on the shelves will not be displayed
        boolQueryBuilder.must(QueryBuilders.termQuery("marketEnable", "1"));

        searchRequestBuilder.setQuery(boolQueryBuilder);

        // sort
        String sortField = goodsSearch.getSort();

        String sortId = "goodsId";

        SortOrder sort = SortOrder.DESC;

        if (sortField != null) {

            Map<String, String> sortMap = SortContainer.getSort(sortField);

            sortId = sortMap.get("id");

            // If its sort by default
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


        // Sort by number of comments
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
