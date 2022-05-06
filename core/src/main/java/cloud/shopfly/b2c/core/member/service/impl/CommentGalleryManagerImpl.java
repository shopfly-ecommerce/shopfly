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
package cloud.shopfly.b2c.core.member.service.impl;

import cloud.shopfly.b2c.core.member.model.dos.CommentGallery;
import cloud.shopfly.b2c.core.member.service.CommentGalleryManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论图片业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 14:11:46
 */
@Service
public class CommentGalleryManagerImpl implements CommentGalleryManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void add(Integer commentId, List<String> list, Integer imgBelong) {

        if (StringUtil.isNotEmpty(list)) {
            int i = 0;
            for (String image : list) {
                CommentGallery commentGallery = new CommentGallery();
                commentGallery.setCommentId(commentId);
                commentGallery.setOriginal(image);
                commentGallery.setSort(i);
                commentGallery.setImgBelong(imgBelong);
                this.daoSupport.insert(commentGallery);
                i++;
            }
        }
    }

    @Override
    public Map<Integer, List<String>> getGalleryByCommentIds(List<Integer> commentIds, Integer imgBelong) {

        Integer[] list = new Integer[commentIds.size()];
        commentIds.toArray(list);

        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(list, term);
        term.add(imgBelong);

        String sql = "select * from es_comment_gallery where comment_id in (" + str + ") and img_belong = ?";
        List<CommentGallery> resList = this.daoSupport.queryForList(sql, CommentGallery.class, term.toArray());

        Map<Integer, List<String>> resMap = new HashMap<>(resList.size());

        for (CommentGallery image : resList) {
            Integer commentId = image.getCommentId();
            List<String> imageList = resMap.get(commentId);
            if (imageList == null) {
                imageList = new ArrayList<>();
            }
            imageList.add(image.getOriginal());
            resMap.put(commentId, imageList);
        }
        return resMap;
    }
}
