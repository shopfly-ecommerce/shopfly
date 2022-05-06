package cloud.shopfly.b2c.api.buyer.isr;

import cloud.shopfly.b2c.core.goods.service.GoodsQueryManager;
import cloud.shopfly.b2c.core.pagedata.service.ArticleManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * isr for products and articles
 *
 * @author kingapex
 * @version 1.0
 * @data 2022/4/13 10:05
 **/
@RestController
@RequestMapping("/isr")
@Api(description = "ISR API")
public class IsrController {

    @Autowired
    private GoodsQueryManager goodsQueryManager;

    @Autowired
    private ArticleManager articleManager;

    @ApiOperation(value = "products id ")
    @GetMapping(value = "/products")
    public List<Integer> products() {

        return goodsQueryManager.getAllGoodsId();
    }

    @ApiOperation(value = "articles id ")
    @GetMapping(value = "/articles")
    public List<Integer> articles() {

        return articleManager.getAllArticleIds();
    }

}
