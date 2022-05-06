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
package cloud.shopfly.b2c.core.goods.model.vo;

import cloud.shopfly.b2c.core.goods.model.dos.BrandDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品快照vo
 * @date 2018/8/1 15:57
 * @since v7.0.0
 */
public class GoodsSnapshotVO {


    private GoodsDO goods;

    private List<GoodsParamsGroupVO> paramList;

    private BrandDO brandDO;

    private CategoryDO categoryDO;

    private List<GoodsGalleryDO> galleryList;

    public GoodsSnapshotVO() {
    }

    public GoodsSnapshotVO(GoodsDO goods, List<GoodsParamsGroupVO> paramList, BrandDO brandDO, CategoryDO categoryDO, List<GoodsGalleryDO> galleryList) {
        this.goods = goods;
        this.paramList = paramList;
        this.brandDO = brandDO;
        this.categoryDO = categoryDO;
        this.galleryList = galleryList;
    }


    public GoodsDO getGoods() {
        return goods;
    }

    public void setGoods(GoodsDO goods) {
        this.goods = goods;
    }

    public List<GoodsParamsGroupVO> getParamList() {
        return paramList;
    }

    public void setParamList(List<GoodsParamsGroupVO> paramList) {
        this.paramList = paramList;
    }

    public BrandDO getBrandDO() {
        return brandDO;
    }

    public void setBrandDO(BrandDO brandDO) {
        this.brandDO = brandDO;
    }

    public CategoryDO getCategoryDO() {
        return categoryDO;
    }

    public void setCategoryDO(CategoryDO categoryDO) {
        this.categoryDO = categoryDO;
    }

    public List<GoodsGalleryDO> getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(List<GoodsGalleryDO> galleryList) {
        this.galleryList = galleryList;
    }


    @Override
    public String toString() {
        return "GoodsSnapshotVO{" +
                "goods=" + goods +
                ", paramList=" + paramList +
                ", brandDO=" + brandDO +
                ", categoryDO=" + categoryDO +
                ", galleryList=" + galleryList +
                '}';
    }
}
