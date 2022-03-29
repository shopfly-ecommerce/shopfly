/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.pagedata;

import com.enation.app.javashop.core.pagedata.model.Article;
import com.enation.app.javashop.core.pagedata.model.vo.ArticleDetail;
import com.enation.app.javashop.core.pagedata.service.ArticleManager;
import com.enation.app.javashop.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 文章控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 10:43:18
 */
@RestController
@RequestMapping("/seller/pages/articles")
@Api(description = "文章相关API")
public class ArticleSellerController {

    @Autowired
    private ArticleManager articleManager;


    @ApiOperation(value = "查询文章列表", response = ArticleDetail.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "文章名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "category_id", value = "文章分类", dataType = "string", paramType = "query"),
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String name, @ApiIgnore Integer categoryId) {

        return this.articleManager.list(pageNo, pageSize, name, categoryId);
    }


    @ApiOperation(value = "添加文章", response = Article.class)
    @PostMapping
    public Article add(@Valid Article article) {

        this.articleManager.add(article);

        return article;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改文章", response = Article.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public Article edit(@Valid Article article, @PathVariable Integer id) {

        this.articleManager.edit(article, id);

        return article;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的文章主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.articleManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的文章主键", required = true, dataType = "int", paramType = "path")
    })
    public Article get(@PathVariable Integer id) {

        Article article = this.articleManager.getModel(id);

        return article;
    }

}
