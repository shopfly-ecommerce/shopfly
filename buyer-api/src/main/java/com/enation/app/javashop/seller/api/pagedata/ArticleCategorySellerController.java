/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.pagedata;

import com.enation.app.javashop.core.pagedata.model.ArticleCategory;
import com.enation.app.javashop.core.pagedata.model.vo.ArticleCategoryVO;
import com.enation.app.javashop.core.pagedata.service.ArticleCategoryManager;
import com.enation.app.javashop.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 文章分类控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-11 15:01:32
 */
@RestController
@RequestMapping("/seller/pages/article-categories")
@Api(description = "文章分类相关API")
public class ArticleCategorySellerController {

    @Autowired
    private ArticleCategoryManager articleCategoryManager;


    @ApiOperation(value = "查询文章一级分类列表", response = ArticleCategory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "分类名称", dataType = "string", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String name) {

        return this.articleCategoryManager.list(pageNo, pageSize, name);
    }

    @ApiOperation(value = "查询文章二级分类列表", response = ArticleCategory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "一级文章分类id", required = true, dataType = "int", paramType = "path"),
    })
    @GetMapping("/{id}/children")
    public List<ArticleCategory> list(@PathVariable Integer id) {

        return this.articleCategoryManager.listChildren(id);
    }


    @ApiOperation(value = "添加文章分类", response = ArticleCategory.class)
    @PostMapping
    public ArticleCategory add(@Valid ArticleCategory articleCategory) {

        this.articleCategoryManager.add(articleCategory);

        return articleCategory;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改文章分类", response = ArticleCategory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public ArticleCategory edit(@Valid ArticleCategory articleCategory, @PathVariable Integer id) {

        this.articleCategoryManager.edit(articleCategory, id);

        return articleCategory;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除文章分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的文章分类主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.articleCategoryManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个文章分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的文章分类主键", required = true, dataType = "int", paramType = "path")
    })
    public ArticleCategory get(@PathVariable Integer id) {

        ArticleCategory articleCategory = this.articleCategoryManager.getModel(id);

        return articleCategory;
    }


    @ApiOperation(value = "查询文章分类树", response = ArticleCategory.class)
    @GetMapping("/childrens")
    public List<ArticleCategoryVO> getMenuTree() {
        return this.articleCategoryManager.getCategoryTree();
    }

}
