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

import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.CommentReply;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dos.MemberComment;
import cloud.shopfly.b2c.core.member.model.dto.AdditionalCommentDTO;
import cloud.shopfly.b2c.core.member.model.dto.CommentDTO;
import cloud.shopfly.b2c.core.member.model.dto.CommentQueryParam;
import cloud.shopfly.b2c.core.member.model.dto.CommentScoreDTO;
import cloud.shopfly.b2c.core.member.model.enums.CommentGrade;
import cloud.shopfly.b2c.core.member.model.vo.CommentVO;
import cloud.shopfly.b2c.core.member.model.vo.GoodsGrade;
import cloud.shopfly.b2c.core.member.model.vo.MemberCommentCount;
import cloud.shopfly.b2c.core.member.service.CommentReplyManager;
import cloud.shopfly.b2c.core.member.service.MemberCommentManager;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.trade.order.model.enums.CommentStatusEnum;
import cloud.shopfly.b2c.core.base.message.GoodsCommentMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.core.member.service.CommentGalleryManager;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Comment business class
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:19:14
 */
@Service
public class MemberCommentManagerImpl implements MemberCommentManager {

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired
    private CommentGalleryManager commentGalleryManager;
    @Autowired
    private CommentReplyManager commentReplyManager;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private MemberManager memberManager;


    @Override
    public Page list(CommentQueryParam param) {

        StringBuffer sqlBuffer = new StringBuffer("SELECT c.* FROM es_member_comment c where c.status = 1 ");
        List<Object> term = new ArrayList<Object>();
        if (param.getGoodsId() != null) {
            sqlBuffer.append(" and  c.goods_id = ?  ");
            term.add(param.getGoodsId());
        }
        if (!StringUtil.isEmpty(param.getGrade())) {
            sqlBuffer.append(" and  c.grade = ? ");
            term.add(param.getGrade());
        }
        if (param.getMemberId() != null) {
            sqlBuffer.append(" and  c.member_id = ? ");
            term.add(param.getMemberId());
        }
        if (param.getReplyStatus() != null) {
            sqlBuffer.append(" and  c.reply_status = ? ");
            term.add(param.getReplyStatus());
        }
        if (!StringUtil.isEmpty(param.getGoodsName())) {
            sqlBuffer.append(" and  c.goods_name like ? ");
            term.add("%" + param.getGoodsName() + "%");
        }
        if (!StringUtil.isEmpty(param.getMemberName())) {
            sqlBuffer.append(" and  c.member_name like ? ");
            term.add("%" + param.getMemberName() + "%");
        }
        if (!StringUtil.isEmpty(param.getContent())) {
            sqlBuffer.append(" and  c.content like ? ");
            term.add("%" + param.getContent() + "%");
        }
        if (!StringUtil.isEmpty(param.getKeyword())) {
            sqlBuffer.append(" and  (c.content like ? or c.goods_name like ?)");
            term.add("%" + param.getKeyword() + "%");
            term.add("%" + param.getKeyword() + "%");
        }
        if (param.getHaveImage() != null && param.getHaveImage()) {
            sqlBuffer.append(" and c.have_image = 1 ");
        }

        if (param.getHaveImage() != null && !param.getHaveImage()) {
            sqlBuffer.append(" and c.have_image = 0 ");
        }

        sqlBuffer.append(" order by c.create_time desc ");

        Page<CommentVO> webPage = this.daoSupport.queryForPage(sqlBuffer.toString(), param.getPageNo(), param.getPageSize(), CommentVO.class, term.toArray());

        List<CommentVO> list = webPage.getData();
        if (StringUtil.isNotEmpty(list)) {
            List<Integer> commentIds = new ArrayList<>();
            List<Integer> commentReplyIds = new ArrayList<>();
            List<Integer> addCommentIds = new ArrayList<>();
            // Find the id of the comment with the picture and the reply
            for (CommentVO comment : list) {
                // Find the comment ID with the image in the initial comment
                if (comment.getHaveImage() == 1) {
                    commentIds.add(comment.getCommentId());
                }
                if (comment.getReplyStatus() == 1) {
                    commentReplyIds.add(comment.getCommentId());
                }
                // Find the comment ID that contains the image
                if (comment.getAdditionalStatus()!=null && comment.getAdditionalStatus() == 1 && comment.getAdditionalHaveImage()!=null && comment.getAdditionalHaveImage() == 1) {
                    addCommentIds.add(comment.getCommentId());
                }
            }
            // Query the corresponding initial comment album
            if (StringUtil.isNotEmpty(commentIds)) {
                Map<Integer, List<String>> map = this.commentGalleryManager.getGalleryByCommentIds(commentIds, 0);
                for (CommentVO comment : list) {
                    if (comment.getHaveImage() == 1) {
                        comment.setImages(map.get(comment.getCommentId()));
                    }
                }
            }

            // Query the corresponding follow-up comment album
            if (StringUtil.isNotEmpty(addCommentIds)) {
                Map<Integer, List<String>> map = this.commentGalleryManager.getGalleryByCommentIds(addCommentIds, 1);
                for (CommentVO comment : list) {
                    if (comment.getAdditionalStatus() == 1 && comment.getAdditionalHaveImage() == 1) {
                        comment.setAddImages(map.get(comment.getCommentId()));
                    }
                }
            }

            // Query reply
            if (StringUtil.isNotEmpty(commentReplyIds)) {
                Map<Integer, CommentReply> map = this.commentReplyManager.getReply(commentReplyIds);
                for (CommentVO comment : list) {
                    if (comment.getReplyStatus() == 1) {
                        comment.setReply(map.get(comment.getCommentId()));
                    }
                }
            }

        }

        return new Page(param.getPageNo(), webPage.getDataTotal(), param.getPageSize(), list);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberComment add(CommentScoreDTO comment, Permission permission) {

        OrderDetailDTO orderDetail = orderClient.getModel(comment.getOrderSn());
        // Non-existent order/not my order
        if (Permission.BUYER.equals(permission)) {
            Buyer member = UserContext.getBuyer();
            if (orderDetail == null || !member.getUid().equals(orderDetail.getMemberId())) {
                throw new ServiceException(MemberErrorCode.E200.code(), "Have no legal power");
            }
        }

        if (!orderDetail.getOrderOperateAllowableVO().getAllowComment()) {
            throw new ServiceException(MemberErrorCode.E200.code(), "Have no legal power");
        }
        // Add comments
        this.add(comment.getComments(), orderDetail);

        // Change the comment status of the order to synchronize the changes and avoid duplicate comments
        orderClient.updateOrderCommentStatus(comment.getOrderSn(), CommentStatusEnum.FINISHED.name());

        return null;
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberComment edit(MemberComment memberComment, Integer id) {
        this.daoSupport.update(memberComment, id);
        return memberComment;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        String sql = "update es_member_comment set status  = 0  where comment_id = ? ";
        this.daoSupport.execute(sql, id);
    }

    @Override
    public MemberComment getModel(Integer id) {
        return this.daoSupport.queryForObject(MemberComment.class, id);
    }

    @Override
    public List<GoodsGrade> queryGoodsGrade() {

        String sql = " select goods_id, sum( CASE grade WHEN '" + CommentGrade.good.name() + "' THEN 1 ELSE 0  END ) /count(*) good_rate " +
                " from es_member_comment where status = 1 group by goods_id";

        List<GoodsGrade> goodsList = this.daoSupport.queryForList(sql, GoodsGrade.class);

        return goodsList;
    }

    @Override
    public Integer getGoodsCommentCount(Integer goodsId) {
        String sql = "SELECT COUNT(0) FROM es_member_comment where goods_id = ? and status = 1";
        return this.daoSupport.queryForInt(sql, goodsId);
    }

    @Override
    public void autoGoodComments(List<OrderDetailDTO> detailDTOList) {

        // Query expired without comment order
        List<OrderDetailDTO> list = detailDTOList;

        // Circular order items automatically give favorable comments
        if (StringUtil.isNotEmpty(list)) {
            for (OrderDetailDTO orderDetail : list) {
                // Add an item score
                List<OrderSkuDTO> skuList = orderDetail.getOrderSkuList();
                List<CommentDTO> commentList = new ArrayList<>();

                for (OrderSkuDTO sku : skuList) {
                    CommentDTO comment = new CommentDTO();
                    comment.setSkuId(sku.getSkuId());
                    comment.setGrade(CommentGrade.good.name());
                    comment.setContent("This product is praised by default");
                    comment.setImages(null);
                    commentList.add(comment);
                }
                this.add(commentList, orderDetail);
            }
        }
    }

    @Override
    public MemberCommentCount count(Integer goodsId) {

        String sql = "select count(1) count,grade,have_image from es_member_comment where goods_id = ? group by grade,have_image";
        List<Map> list = this.daoSupport.queryForList(sql, goodsId);

        Integer allCount = 0;
        Integer goodCount = 0;
        Integer neutralCount = 0;
        Integer badCount = 0;
        Integer imageCount = 0;

        if (StringUtil.isNotEmpty(list)) {
            for (Map map : list) {
                String grade = map.get("grade").toString();
                Integer count = Integer.valueOf(map.get("count").toString());
                allCount += count;
                switch (grade) {
                    case "good":
                        goodCount += count;
                        break;
                    case "neutral":
                        neutralCount += count;
                        break;
                    case "bad":
                        badCount += count;
                        break;
                    default:
                        break;
                }
                // Number of comments on images
                Integer haveImage = (Integer) map.get("have_image");
                if (haveImage == 1) {
                    imageCount += count;
                }
            }
        }

        return new MemberCommentCount(allCount, goodCount, neutralCount, badCount, imageCount);
    }


    @Override
    public void editComment(Integer memberId, String face) {
        this.daoSupport.execute("update es_member_comment set member_face = ? where member_id = ?", face, memberId);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<AdditionalCommentDTO> additionalComments(List<AdditionalCommentDTO> comments, Permission permission) {
        for (AdditionalCommentDTO commentDTO : comments) {
            MemberComment memberComment = this.getModel(commentDTO.getCommentId());

            // If a members comment is empty or the comment has been deleted or has been added, the following comment is not allowed to be added
            if (memberComment == null || memberComment.getStatus().intValue() == 0 || memberComment.getAdditionalStatus().intValue() == 1) {
                throw new ServiceException(MemberErrorCode.E200.code(), "Have no legal power");
            }

            // Verify permissions
            if (Permission.BUYER.equals(permission)) {
                Buyer member = UserContext.getBuyer();
                if (!member.getUid().equals(memberComment.getMemberId())) {
                    throw new ServiceException(MemberErrorCode.E200.code(), "Have no legal power");
                }
            }

            if (StringUtil.isEmpty(commentDTO.getContent())) {
                throw new ServiceException(MemberErrorCode.E201.code(), "The appended comment content cannot be empty");
            }

            memberComment.setAdditionalStatus(1);
            memberComment.setAdditionalContent(commentDTO.getContent());
            memberComment.setAdditionalTime(DateUtil.getDateline());
            if (commentDTO.getImages() != null && commentDTO.getImages().size() != 0) {
                memberComment.setAdditionalHaveImage(1);
            } else {
                memberComment.setAdditionalHaveImage(0);
            }

            this.daoSupport.update(memberComment, memberComment.getCommentId());

            if (commentDTO.getImages() != null && commentDTO.getImages().size() != 0) {
                // Add images
                this.commentGalleryManager.add(memberComment.getCommentId(), commentDTO.getImages(), 1);
            }
        }
        return comments;
    }

    /**
     * Add comments
     *
     * @param commentList Initiated comments
     * @param orderDetail The order
     */
    private void add(List<CommentDTO> commentList, OrderDetailDTO orderDetail) {

        Map<Integer, Object> skuMap = new HashMap<Integer, Object>(orderDetail.getOrderSkuList().size());
        // Put the product loop into the map
        for (OrderSkuDTO sku : orderDetail.getOrderSkuList()) {
            skuMap.put(sku.getSkuId(), sku);
        }

        for (CommentDTO comment : commentList) {
            OrderSkuDTO product = (OrderSkuDTO) skuMap.get(comment.getSkuId());
            if (product == null) {
                throw new ServiceException(MemberErrorCode.E200.code(), "Have no legal power");
            }
            MemberComment memberComment = new MemberComment();
            BeanUtils.copyProperties(comment, memberComment);
            Member member = memberManager.getModel(orderDetail.getMemberId());
            memberComment.setMemberFace(member.getFace());
            memberComment.setGoodsId(product.getGoodsId());
            memberComment.setCreateTime(DateUtil.getDateline());
            memberComment.setMemberId(orderDetail.getMemberId());
            memberComment.setStatus(1);
            memberComment.setReplyStatus(0);
            memberComment.setGoodsName(product.getName());
            memberComment.setMemberName(member.getUname());
            memberComment.setOrderSn(orderDetail.getSn());

            // Is there a picture
            memberComment.setHaveImage(StringUtil.isNotEmpty(comment.getImages()) ? 1 : 0);

            if (CommentGrade.good.name().equals(comment.getGrade()) && StringUtil.isEmpty(memberComment.getContent())) {

                memberComment.setContent("This review defaults to favorable reviews！！");
            }

            if (!CommentGrade.good.name().equals(comment.getGrade()) && StringUtil.isEmpty(memberComment.getContent())) {

                throw new ServiceException(MemberErrorCode.E201.code(), "Mandatory for non-favorable comments");
            }

            this.daoSupport.insert(memberComment);

            int commentId = this.daoSupport.getLastId("es_member_comment");

            // Add images
            this.commentGalleryManager.add(commentId, comment.getImages(), 0);

            // Send a message
            memberComment.setCommentId(commentId);
            GoodsCommentMsg goodsCommentMsg = new GoodsCommentMsg();
            goodsCommentMsg.setComment(memberComment);
            this.messageSender.send(new MqMessage(AmqpExchange.GOODS_COMMENT_COMPLETE, AmqpExchange.GOODS_COMMENT_COMPLETE + "_ROUTING",
                    goodsCommentMsg));

        }
    }
}
