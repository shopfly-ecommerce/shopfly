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
package cloud.shopfly.b2c.api.seller.pagedata;

import cloud.shopfly.b2c.core.pagedata.model.ArticleCategory;
import cloud.shopfly.b2c.core.pagedata.model.vo.ArticleCategoryVO;
import cloud.shopfly.b2c.core.pagedata.service.ArticleCategoryManager;
import cloud.shopfly.b2c.framework.database.Page;
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
 * Article classification controller
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-11 15:01:32
 */
@RestController
@RequestMapping("/seller/pages/article-categories")
@Api(description = "Article classification correlationAPI")
public class ArticleCategorySellerController {

    @Autowired
    private ArticleCategoryManager articleCategoryManager;


    @ApiOperation(value = "Query the list of first-level articles", response = ArticleCategory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "name", dataType = "string", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String name) {

        return this.articleCategoryManager.list(pageNo, pageSize, name);
    }

    @ApiOperation(value = "Query the list of secondary categories of articles", response = ArticleCategory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Level 1 Article classificationid", required = true, dataType = "int", paramType = "path"),
    })
    @GetMapping("/{id}/children")
    public List<ArticleCategory> list(@PathVariable Integer id) {

        return this.articleCategoryManager.listChildren(id);
    }


    @ApiOperation(value = "Add article categories", response = ArticleCategory.class)
    @PostMapping
    public ArticleCategory add(@Valid ArticleCategory articleCategory) {

        this.articleCategoryManager.add(articleCategory);

        return articleCategory;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify article categories", response = ArticleCategory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public ArticleCategory edit(@Valid ArticleCategory articleCategory, @PathVariable Integer id) {

        this.articleCategoryManager.edit(articleCategory, id);

        return articleCategory;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete article categories")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The primary key of the article category to be deleted", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.articleCategoryManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query an article category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the article category to be queried", required = true, dataType = "int", paramType = "path")
    })
    public ArticleCategory get(@PathVariable Integer id) {

        ArticleCategory articleCategory = this.articleCategoryManager.getModel(id);

        return articleCategory;
    }


    @ApiOperation(value = "Query the article category tree", response = ArticleCategory.class)
    @GetMapping("/childrens")
    public List<ArticleCategoryVO> getMenuTree() {
        return this.articleCategoryManager.getCategoryTree();
    }

}
