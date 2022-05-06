/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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