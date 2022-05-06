/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.goods;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsVO;
import cloud.shopfly.b2c.core.goods.service.GoodsManager;
import cloud.shopfly.b2c.core.goods.service.GoodsQueryManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品控制器
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
@RestController
@RequestMapping("/seller/goods")
@Api(description = "商品相关API")
@Validated
@Scope("request")
public class GoodsSellerController {

    @Autowired
    private GoodsQueryManager goodsQueryManager;

    @Autowired
    private GoodsManager goodsManager;


    @ApiOperation(value = "查询商品列表", response = GoodsDO.class)
    @GetMapping
    public Page list(GoodsQueryParam param, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        return this.goodsQueryManager.list(param);
    }

    @ApiOperation(value = "查询预警商品列表", response = GoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "查询关键字", required = false, dataType = "string", paramType = "query")
    })
    @GetMapping("/warning")
    public Page warningList(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String keyword) {

        return this.goodsQueryManager.warningGoodsList(pageNo, pageSize, keyword);

    }

    @ApiOperation(value = "添加商品", response = GoodsDO.class)
    @ApiImplicitParam(name = "goods", value = "商品信息", required = true, dataType = "GoodsDTO", paramType = "body")
    @PostMapping
    public GoodsDO add(@ApiIgnore @Valid @RequestBody GoodsDTO goods) {

        GoodsDO goodsDO = this.goodsManager.add(goods);

        return goodsDO;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改商品", response = GoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "goods", value = "商品信息", required = true, dataType = "GoodsDTO", paramType = "body")
    })
    public GoodsDO edit(@Valid @RequestBody GoodsDTO goods, @PathVariable Integer id) {

        GoodsDO goodsDO = this.goodsManager.edit(goods, id);

        return goodsDO;
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个商品,编辑时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的商品主键", required = true, dataType = "int", paramType = "path")})
    public GoodsVO get(@PathVariable Integer id) {

        GoodsVO goods = this.goodsQueryManager.queryGoods(id);

        return goods;
    }

    @ApiOperation(value = "商家下架商品", notes = "商家下架商品时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "商品ID集合", required = true, paramType = "path", dataType = "int", allowMultiple = true)
    })
    @PutMapping(value = "/{goods_ids}/under")
    public String underGoods(@PathVariable("goods_ids") Integer[] goodsIds, String reason) {

        if (StringUtil.isEmpty(reason)) {
            reason = "自行下架，无原因";
        }
        this.goodsManager.under(goodsIds, reason);

        return null;
    }

    @ApiOperation(value = "商家将商品放入回收站", notes = "下架的商品才能放入回收站")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "商品ID集合", required = true, paramType = "path", dataType = "int", allowMultiple = true)
    })
    @PutMapping(value = "/{goods_ids}/recycle")
    public String deleteGoods(@PathVariable("goods_ids") Integer[] goodsIds) {

        this.goodsManager.inRecycle(goodsIds);

        return null;
    }

    @ApiOperation(value = "商家还原商品", notes = "商家回收站回收商品时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "商品ID集合", required = true, paramType = "path", dataType = "int", allowMultiple = true)
    })
    @PutMapping(value = "/{goods_ids}/revert")
    public String revertGoods(@PathVariable("goods_ids") Integer[] goodsIds) {

        this.goodsManager.revert(goodsIds);

        return null;
    }

    @ApiOperation(value = "商家彻底删除商品", notes = "商家回收站删除商品时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "商品ID集合", required = true, paramType = "path", dataType = "int", allowMultiple = true)
    })
    @DeleteMapping(value = "/{goods_ids}")
    public String cleanGoods(@PathVariable("goods_ids") Integer[] goodsIds) {

        this.goodsManager.delete(goodsIds);

        return "";
    }


    @GetMapping(value = "/{goods_ids}/details")
    @ApiOperation(value = "查询多个商品的基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "要查询的商品主键", required = true, dataType = "int", paramType = "path", allowMultiple = true)})
    public List<GoodsSelectLine> getGoodsDetail(@PathVariable("goods_ids") Integer[] goodsIds) {

        return this.goodsQueryManager.query(goodsIds);
    }

}
