/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.goods;

import dev.shopflix.core.goods.model.dos.DraftGoodsDO;
import dev.shopflix.core.goods.model.dos.GoodsDO;
import dev.shopflix.core.goods.model.dto.GoodsDTO;
import dev.shopflix.core.goods.model.vo.DraftGoodsVO;
import dev.shopflix.core.goods.model.vo.GoodsParamsGroupVO;
import dev.shopflix.core.goods.model.vo.GoodsSkuVO;
import dev.shopflix.core.goods.service.DraftGoodsManager;
import dev.shopflix.core.goods.service.DraftGoodsParamsManager;
import dev.shopflix.core.goods.service.DraftGoodsSkuManager;
import dev.shopflix.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 草稿商品控制器
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 10:40:34
 */
@RestController
@RequestMapping("/seller/goods/draft-goods")
@Api(description = "草稿商品相关API")
public class DraftGoodsSellerController {

    @Autowired
    private DraftGoodsManager draftGoodsManager;
    @Autowired
    private DraftGoodsParamsManager draftGoodsParamsManager;
    @Autowired
    private DraftGoodsSkuManager draftGoodsSkuManager;


    @ApiOperation(value = "查询草稿商品列表", response = DraftGoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字查询", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "shop_cat_path", value = "店铺分类path，如0|10|", required = false, dataType = "int", paramType = "query"),
    })
    @GetMapping
    public Page list(@ApiIgnore @NotEmpty(message = "页码不能为空") Integer pageNo,
                     @ApiIgnore @NotEmpty(message = "每页数量不能为空") Integer pageSize,
                     String keyword, @ApiIgnore String shopCatPath) {

        return this.draftGoodsManager.list(pageNo, pageSize, keyword, shopCatPath);
    }

    @ApiOperation(value = "添加商品", response = DraftGoodsDO.class)
    @ApiImplicitParam(name = "goods", value = "商品信息", required = true, dataType = "GoodsDTO", paramType = "body")
    @PostMapping
    public DraftGoodsDO add(@ApiIgnore @RequestBody GoodsDTO goods) {

        DraftGoodsDO draftGoods = this.draftGoodsManager.add(goods);

        return draftGoods;
    }

    @PutMapping(value = "/{draft_goods_id}")
    @ApiOperation(value = "修改草稿商品", response = DraftGoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "draft_goods_id", value = "主键", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "goods", value = "商品信息", required = true, dataType = "GoodsDTO", paramType = "body")
    })
    public DraftGoodsDO edit(@RequestBody GoodsDTO goods, @PathVariable("draft_goods_id") Integer draftGoodsId) {

        DraftGoodsDO draftGoods = this.draftGoodsManager.edit(goods, draftGoodsId);

        return draftGoods;
    }


    @DeleteMapping(value = "/{draft_goods_ids}")
    @ApiOperation(value = "删除草稿商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "draft_goods_ids", value = "要删除的草稿商品主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable("draft_goods_ids") Integer[] draftGoodsIds) {

        this.draftGoodsManager.delete(draftGoodsIds);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个草稿商品,商家编辑草稿商品使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的草稿商品主键", required = true, dataType = "int", paramType = "path")
    })
    public DraftGoodsVO get(@PathVariable Integer id) {
        return this.draftGoodsManager.getVO(id);
    }

    @GetMapping(value = "/{draft_goods_id}/params")
    @ApiOperation(value = "查询草稿商品关联的参数，包括没有添加的参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "draft_goods_id", value = "要删除的草稿商品主键", required = true, dataType = "int", paramType = "path")
    })
    public List<GoodsParamsGroupVO> queryDraftParam(@PathVariable("draft_goods_id") Integer draftGoodsId) {

        DraftGoodsDO draftGoods = this.draftGoodsManager.getModel(draftGoodsId);

        List<GoodsParamsGroupVO> list = draftGoodsParamsManager.getParamByCatAndDraft(draftGoods.getCategoryId(), draftGoodsId);

        return list;

    }

    @ApiOperation(value = "查询草稿箱商品sku信息", notes = "商家中心编辑草稿箱商品时获得sku信息")
    @ApiImplicitParam(name = "draft_goods_id", value = "草稿商品id", required = true, dataType = "int", paramType = "path")
    @GetMapping(value = "/{draft_goods_id}/skus")
    public List<GoodsSkuVO> queryByDraftGoodsid(@PathVariable("draft_goods_id") Integer draftGoodsId) throws Exception {

        List<GoodsSkuVO> list = draftGoodsSkuManager.getSkuList(draftGoodsId);

        return list;
    }

    @ApiOperation(value = "草稿箱商品上架接口", notes = "草稿箱商品上架时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "draft_goods_id", value = "草稿商品id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "goods", value = "商品信息", required = true, dataType = "GoodsDTO", paramType = "body")
    })
    @PutMapping(value = "/{draft_goods_id}/market")
    public GoodsDO addMarket(@ApiIgnore @Valid @RequestBody GoodsDTO goodsVO, @PathVariable("draft_goods_id") Integer draftGoodsId) {

        GoodsDO goods = draftGoodsManager.addMarket(goodsVO, draftGoodsId);

        return goods;
    }


}
