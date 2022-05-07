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

import cloud.shopfly.b2c.core.pagedata.model.vo.ArticleCategoryVO;
import cloud.shopfly.b2c.core.pagedata.service.ArticleCategoryManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Article classification controller
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-11 15:01:32
 */
@RestController
@RequestMapping("/pages/article-categories")
@Api(description = "Article classification correlationAPI")
public class ArticleCategoryBaseController {

    @Autowired
    private ArticleCategoryManager articleCategoryManager;


    @ApiOperation(value = "Inquire categories and the following articles", response = ArticleCategoryVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_type", value = "Classification type：Help center, mall announcements, fixed location, mall promotions, others", required = true, dataType = "string", paramType = "query",allowableValues = "HELP,NOTICE,POSITION,PROMOTION,OTHER"),
    })
    @GetMapping
    public ArticleCategoryVO getArticle(@ApiIgnore String categoryType) {

        return this.articleCategoryManager.getCategoryAndArticle(categoryType);
    }

}
