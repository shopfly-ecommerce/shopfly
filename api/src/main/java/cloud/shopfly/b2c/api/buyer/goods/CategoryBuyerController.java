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
package cloud.shopfly.b2c.api.buyer.goods;

import cloud.shopfly.b2c.core.goods.model.dos.BrandDO;
import cloud.shopfly.b2c.core.goods.model.vo.BrandVO;
import cloud.shopfly.b2c.core.goods.model.vo.CategoryVO;
import cloud.shopfly.b2c.core.goods.service.BrandManager;
import cloud.shopfly.b2c.core.goods.service.CategoryManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Merchandise classification controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-15 17:22:06
 */
@RestController
@RequestMapping("/goods/categories")
@Api(description = "Commodity classification correlationAPI")
public class CategoryBuyerController {

    @Autowired
    private CategoryManager categoryManager;

    @Autowired
    private BrandManager brandManager;

    @ApiOperation(value = "Home page and other commodity classification data")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent_id", value = "CategoriesidAnd the top of0", required = true, dataType = "int", paramType = "path"),})
    @GetMapping(value = "/{parent_id}/children")
    public List<CategoryVO> list(@PathVariable("parent_id") Integer parentId) {

        List<CategoryVO> catTree = categoryManager.listAllChildren(parentId);

        for (CategoryVO cat : catTree) {
            // The first level category shows the associated brand
            if (cat.getParentId().compareTo(parentId) == 0) {

                List<BrandDO> brandList = brandManager.getBrandsByCategory(cat.getCategoryId());

                List<BrandVO> brandNavList = new ArrayList<>();
                for (BrandDO brand : brandList) {
                    BrandVO brandNav = new BrandVO();
                    BeanUtils.copyProperties(brand, brandNav);
                    brandNavList.add(brandNav);
                }
                cat.setBrandList(brandNavList);
            }
        }

        return catTree;
    }
}
