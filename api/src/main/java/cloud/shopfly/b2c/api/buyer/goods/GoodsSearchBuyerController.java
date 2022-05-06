/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.buyer.goods;

import cloud.shopfly.b2c.core.goodssearch.model.GoodsSearchDTO;
import cloud.shopfly.b2c.core.goodssearch.model.GoodsWords;
import cloud.shopfly.b2c.core.goodssearch.service.GoodsSearchManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品全文检索
 * @date 2018/6/1915:55
 * @since v7.0.0
 */
@RestController
@RequestMapping("/goods/search")
@Api(description = "商品检索相关API")
public class GoodsSearchBuyerController {

    @Autowired
    private GoodsSearchManager goodsSearchManager;

    @ApiOperation(value = "查询商品列表")
    @GetMapping
    public Page searchGoods(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, GoodsSearchDTO goodsSearch){

        goodsSearch.setPageNo(pageNo);
        goodsSearch.setPageSize(pageSize);

        return goodsSearchManager.search(goodsSearch);
    }

    @ApiOperation(value = "查询商品选择器")
    @GetMapping("/selector")
    public Map searchGoodsSelector(GoodsSearchDTO goodsSearch){

        return goodsSearchManager.getSelector(goodsSearch);
    }

    @ApiOperation(value = "查询商品分词对应数量")
    @ApiImplicitParam(name = "keyword", value = "搜索关键字", required = true, dataType = "string", paramType = "query")
    @GetMapping("/words")
    public List<GoodsWords> searchGoodsWords(String keyword){

        return goodsSearchManager.getGoodsWords(keyword);
    }

    @ApiOperation(value = "获取'为你推荐'商品列表")
    @GetMapping("/recommend")
    public Page recommendGoodsList(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize){
        GoodsSearchDTO goodsSearch = new GoodsSearchDTO();
        goodsSearch.setPageNo(pageNo);
        goodsSearch.setPageSize(pageSize);

        return goodsSearchManager.recommendGoodsList(goodsSearch);
    }
}
