/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 商品相册业务类
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

        //缩略图
        String thumbnail = uploadFactoryClient.getUrl(origin, photoSizeSetting.getThumbnailWidth(), photoSizeSetting.getThumbnailHeight());
        //小图
        String small = uploadFactoryClient.getUrl(origin, photoSizeSetting.getSmallWidth(), photoSizeSetting.getSmallHeight());
        //大图
        String big = uploadFactoryClient.getUrl(origin, photoSizeSetting.getBigWidth(), photoSizeSetting.getBigHeight());
        //赋值
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
            // 获取带所有缩略的相册
            GoodsGalleryDO galley = this.getGoodsGallery(origin.getOriginal());
            galley.setGoodsId(goodsId);
            galley.setSort(i);
            /** 默认第一个为默认图片 */
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

        // 删除没有用到的商品相册信息
        this.delNoUseGalley(goodsGalleryList, goodsId);
        int i = 0;
        // 如果前台传入id不为-1，则新增商品图片到此商品的相册中 添加相册
        for (GoodsGalleryDO goodsGallery : goodsGalleryList) {
            //已有图片且默认
            if (goodsGallery.getImgId() != -1 && i == 0) {
                //将此图片设置为默认
                this.daoSupport.execute("update es_goods_gallery set isdefault = 1 where img_id = ? ", goodsGallery.getImgId());
                //将其他图片设置为不默认
                this.daoSupport.execute("update es_goods_gallery set isdefault = 0 where img_id != ? and goods_id = ? ", goodsGallery.getImgId(),goodsId);
                GoodsGalleryDO temp = this.getModel(goodsGallery.getImgId());
                goodsGallery.setBig(temp.getBig());
                goodsGallery.setOriginal(temp.getOriginal());
                goodsGallery.setSmall(temp.getSmall());
                goodsGallery.setThumbnail(temp.getThumbnail());
                goodsGallery.setTiny(temp.getTiny());
            }
            //新增的图片
            if (goodsGallery.getImgId() == -1) {
                //获取带所有缩略的相册
                GoodsGalleryDO galley = this.getGoodsGallery(goodsGallery.getOriginal());
                galley.setGoodsId(goodsId);
                galley.setSort(i);
                // 默认第一个为默认图片
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
     * 删除没有用到的商品相册信息
     *
     * @param galleryList 商品相册
     * @param goodsId     商品id
     */
    private void delNoUseGalley(List<GoodsGalleryDO> galleryList, Integer goodsId) {
        // 将传入的商品图片id进行拼接
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
        // 删除掉不在此商品相册中得图片
        this.daoSupport.execute("delete from es_goods_gallery where img_id not in(" + str + ") and goods_id = ?", imgIds.toArray());
    }
}
