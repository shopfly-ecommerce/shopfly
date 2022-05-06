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
package cloud.shopfly.b2c.core.member.service;

import java.util.List;
import java.util.Map;

/**
 * 评论图片业务层
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 14:11:46
 */
public interface CommentGalleryManager {


    /**
     * 添加评论图片
     *
     * @param commentId 评论ID
     * @param list      图片集合
     * @param imgBelong 图片所属 0：初评，1：追评
     */
    void add(Integer commentId, List<String> list, Integer imgBelong);

    /**
     * 获取评论图片
     *
     * @param commentIds 评论ID组
     * @param imgBelong  评论图片所属 0：初评，1：追评
     * @return
     */
    Map<Integer, List<String>> getGalleryByCommentIds(List<Integer> commentIds, Integer imgBelong);
}