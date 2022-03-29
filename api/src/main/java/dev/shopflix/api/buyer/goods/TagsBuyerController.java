/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.goods;

import dev.shopflix.core.goods.constraint.annotation.MarkType;
import dev.shopflix.core.goods.model.dos.TagsDO;
import dev.shopflix.core.goods.model.vo.GoodsSelectLine;
import dev.shopflix.core.goods.service.TagsManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品标签控制器
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
@RestController
@RequestMapping("/goods")
@Api(description = "标签商品相关API")
@Validated
public class TagsBuyerController {

    @Autowired
    private TagsManager tagsManager;

    @ApiOperation(value = "查询标签商品列表", response = TagsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mark", value = "hot热卖 new新品 recommend推荐", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "num", value = "查询数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/tags/{mark}/goods")
    public List<GoodsSelectLine> list(Integer num, @MarkType @PathVariable String mark) {

        if (num == null) {
            num = 5;
        }

        return tagsManager.queryTagGoods(num, mark);
    }

}