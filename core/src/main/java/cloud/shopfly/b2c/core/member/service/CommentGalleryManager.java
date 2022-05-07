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
 * Comment on the image business layer
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 14:11:46
 */
public interface CommentGalleryManager {


    /**
     * Add comment images
     *
     * @param commentId commentsID
     * @param list      Image collection
     * @param imgBelong Image host0：The intern,1：After a review of the
     */
    void add(Integer commentId, List<String> list, Integer imgBelong);

    /**
     * Get a comment picture
     *
     * @param commentIds commentsIDgroup
     * @param imgBelong  Comment picture ownership0：The intern,1：After a review of the
     * @return
     */
    Map<Integer, List<String>> getGalleryByCommentIds(List<Integer> commentIds, Integer imgBelong);
}
