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
package cloud.shopfly.b2c.api.base;

import cloud.shopfly.b2c.core.pagedata.model.Article;
import cloud.shopfly.b2c.core.pagedata.model.vo.ArticleDetail;
import cloud.shopfly.b2c.core.pagedata.service.ArticleManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Article controller
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 10:43:18
 */
@RestController
@RequestMapping("/pages")
@Api(description = "The article relatedAPI")
public class ArticleBuyerController {

    @Autowired
    private ArticleManager articleManager;


    @ApiOperation(value = "Queries a list of articles at a location", response = ArticleDetail.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "position", value = "Article display position:Registration agreement, settlement agreement, platform contact information, group purchase activity agreement,other", required = true, dataType = "string", paramType = "query",allowableValues = "REGISTRATION_AGREEMENT,COOPERATION_AGREEMENT,CONTACT_INFORMATION,GROUP_BUY_AGREEMENT,OTHER")
    })
    @GetMapping("/articles")
    public List<Article> list(String position) {

        return this.articleManager.listByPosition(position);
    }

    @GetMapping(value = "/articles/{id}")
    @ApiOperation(value = "Query an article")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the article to be queried", required = true, dataType = "int", paramType = "path")
    })
    public Article get(@PathVariable Integer id) {

        Article article = this.articleManager.getModel(id);

        return article;
    }

    @ApiOperation(value = "Query an article at a location", response = ArticleDetail.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "position", value = "Article display position,Registration agreement, settlement agreement, platform contact information, etc", required = true, dataType = "string", paramType = "path",allowableValues = "REGISTRATION_AGREEMENT,COOPERATION_AGREEMENT,CONTACT_INFORMATION,OTHER")
    })
    @GetMapping("/{position}/articles")
    public Article getOne(@PathVariable String position) {

        List<Article> list = this.articleManager.listByPosition(position);
        return list.get(0);
    }

    @ApiOperation(value = "Query the list of articles under a category type", response = ArticleDetail.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_type", value = "Classification type,Help center, mall announcements, fixed location, mall promotions, others", required = true, dataType = "string", paramType = "path",allowableValues = "HELP,NOTICE,POSITION,PROMOTION,OTHER"),
    })
    @GetMapping("/article-categories/{category_type}/articles")
    public List<Article> listByCategoryType(@PathVariable("category_type") String categoryType) {

        return this.articleManager.listByCategoryType(categoryType);
    }

}
