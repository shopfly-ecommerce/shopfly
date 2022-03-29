/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.trade;

import dev.shopflix.core.member.model.vo.SalesVO;
import dev.shopflix.core.member.service.MemberSalesManager;
import dev.shopflix.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品交易controller
 * @date 2018/8/15 15:51
 * @since v7.0.0
 */
@Api(description = "商品交易模块")
@RestController
@RequestMapping("/trade/goods")
public class GoodsSalesBuyerController {

    @Autowired
    private MemberSalesManager memberSalesManager;

    @ApiOperation(value = "查询某商品的销售记录", response = SalesVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goods_id", value = "商品ID", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping("/{goods_id}/sales")
    public Page salesList(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @PathVariable("goods_id") Integer goodsId) {
        return this.memberSalesManager.list(pageSize, pageNo, goodsId);
    }


}
