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

import cloud.shopfly.b2c.core.pagedata.model.Article;
import cloud.shopfly.b2c.core.pagedata.model.vo.ArticleDetail;
import cloud.shopfly.b2c.core.pagedata.service.ArticleManager;
import cloud.shopfly.b2c.framework.database.Page;
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
