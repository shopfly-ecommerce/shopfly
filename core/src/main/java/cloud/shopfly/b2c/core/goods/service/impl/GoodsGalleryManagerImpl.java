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
package cloud.shopfly.b2c.core.goods.service.impl;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.client.system.UploadFactoryClient;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsSettingVO;
import cloud.shopfly.b2c.core.goods.service.GoodsGalleryManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Commodity album business class
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-21 11:39:54
 */
@Service
public class GoodsGalleryManagerImpl implements GoodsGalleryManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private SettingClient settingClient;

    @Autowired
    private UploadFactoryClient uploadFactoryClient;


    @Override
    public List<GoodsGalleryDO> list(Integer goodsId) {

        String sql = "select gg.* from es_goods_gallery gg where gg.goods_id = ? ORDER BY gg.isdefault desc,sort asc";
        List<GoodsGalleryDO> result = this.daoSupport.queryForList(sql, GoodsGalleryDO.class, goodsId);

        return result;
    }

    @Override
    public GoodsGalleryDO getGoodsGallery(String origin) {
        GoodsGalleryDO goodsGallery = new GoodsGalleryDO();

        String photoSizeSettingJson = settingClient.get(SettingGroup.GOODS);

        GoodsSettingVO photoSizeSetting = JsonUtil.jsonToObject(photoSizeSettingJson, GoodsSettingVO.class);

        // The thumbnail
        String thumbnail = uploadFactoryClient.getUrl(origin, photoSizeSetting.getThumbnailWidth(), photoSizeSetting.getThumbnailHeight());
        // insets
        String small = uploadFactoryClient.getUrl(origin, photoSizeSetting.getSmallWidth(), photoSizeSetting.getSmallHeight());
        // A larger version
        String big = uploadFactoryClient.getUrl(origin, photoSizeSetting.getBigWidth(), photoSizeSetting.getBigHeight());
        // The assignment
        goodsGallery.setBig(big);
        goodsGallery.setSmall(small);
        goodsGallery.setThumbnail(thumbnail);
        goodsGallery.setOriginal(origin);

        return goodsGallery;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public GoodsGalleryDO add(GoodsGalleryDO goodsGallery) {
        this.daoSupport.insert(goodsGallery);

        return goodsGallery;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void add(List<GoodsGalleryDO> goodsGalleryList, Integer goodsId) {

        int i = 0;
        for (GoodsGalleryDO origin : goodsGalleryList) {
            // Gets an album with all thumbnails
            GoodsGalleryDO galley = this.getGoodsGallery(origin.getOriginal());
            galley.setGoodsId(goodsId);
            galley.setSort(i);
            /** By default the first one is the default image*/
            if (i == 0) {
                galley.setIsdefault(1);
            } else {
                galley.setIsdefault(0);
            }
            i++;
            this.add(galley);
        }

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void edit(List<GoodsGalleryDO> goodsGalleryList, Integer goodsId) {

        // Delete the product album information that is not used
        this.delNoUseGalley(goodsGalleryList, goodsId);
        int i = 0;
        // If the id passed by the foreground is not -1, the photo of the product is added to the photo album of the product
        for (GoodsGalleryDO goodsGallery : goodsGalleryList) {
            // Already picture and default
            if (goodsGallery.getImgId() != -1 && i == 0) {
                // Set this image as the default
                this.daoSupport.execute("update es_goods_gallery set isdefault = 1 where img_id = ? ", goodsGallery.getImgId());
                // Set other images not to default
                this.daoSupport.execute("update es_goods_gallery set isdefault = 0 where img_id != ? and goods_id = ? ", goodsGallery.getImgId(),goodsId);
                GoodsGalleryDO temp = this.getModel(goodsGallery.getImgId());
                goodsGallery.setBig(temp.getBig());
                goodsGallery.setOriginal(temp.getOriginal());
                goodsGallery.setSmall(temp.getSmall());
                goodsGallery.setThumbnail(temp.getThumbnail());
                goodsGallery.setTiny(temp.getTiny());
            }
            // New image
            if (goodsGallery.getImgId() == -1) {
                // Gets an album with all thumbnails
                GoodsGalleryDO galley = this.getGoodsGallery(goodsGallery.getOriginal());
                galley.setGoodsId(goodsId);
                galley.setSort(i);
                // By default the first one is the default image
                if (i == 0) {
                    galley.setIsdefault(1);
                    this.daoSupport.execute("update es_goods_gallery set isdefault = 0 where goods_id = ? ", goodsId);
                } else {
                    galley.setIsdefault(0);
                }
                this.daoSupport.insert(galley);
                BeanUtils.copyProperties(galley,goodsGallery);
            }
            this.daoSupport.execute("update es_goods_gallery set sort = ? where img_id = ? ", i, goodsGallery.getImgId());
            i++;
        }
    }

    @Override
    public GoodsGalleryDO getModel(Integer id) {
        return this.daoSupport.queryForObject(GoodsGalleryDO.class, id);
    }

    /**
     * Delete the product album information that is not used
     *
     * @param galleryList Photo album
     * @param goodsId     productid
     */
    private void delNoUseGalley(List<GoodsGalleryDO> galleryList, Integer goodsId) {
        // Concatenate the incoming commodity picture ID
        List<Object> imgIds = new ArrayList<>();
        String[] temp = new String[galleryList.size()];
        if (galleryList.size() > 0) {
            for (int i = 0; i < galleryList.size(); i++) {
                imgIds.add(galleryList.get(i).getImgId());
                temp[i] = "?";
            }
        }
        String str = StringUtil.arrayToString(temp, ",");
        imgIds.add(goodsId);
        // Delete the pictures that are not in this product album
        this.daoSupport.execute("delete from es_goods_gallery where img_id not in(" + str + ") and goods_id = ?", imgIds.toArray());
    }
}
