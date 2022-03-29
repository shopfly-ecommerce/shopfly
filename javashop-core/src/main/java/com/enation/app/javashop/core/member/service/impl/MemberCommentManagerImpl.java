/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.service.impl;

import com.enation.app.javashop.core.base.message.GoodsCommentMsg;
import com.enation.app.javashop.core.base.rabbitmq.AmqpExchange;
import com.enation.app.javashop.core.client.trade.OrderClient;
import com.enation.app.javashop.core.goods.model.enums.Permission;
import com.enation.app.javashop.core.member.MemberErrorCode;
import com.enation.app.javashop.core.member.model.dos.CommentReply;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.model.dos.MemberComment;
import com.enation.app.javashop.core.member.model.dto.AdditionalCommentDTO;
import com.enation.app.javashop.core.member.model.dto.CommentDTO;
import com.enation.app.javashop.core.member.model.dto.CommentQueryParam;
import com.enation.app.javashop.core.member.model.dto.CommentScoreDTO;
import com.enation.app.javashop.core.member.model.enums.CommentGrade;
import com.enation.app.javashop.core.member.model.vo.CommentVO;
import com.enation.app.javashop.core.member.model.vo.GoodsGrade;
import com.enation.app.javashop.core.member.model.vo.MemberCommentCount;
import com.enation.app.javashop.core.member.service.CommentGalleryManager;
import com.enation.app.javashop.core.member.service.CommentReplyManager;
import com.enation.app.javashop.core.member.service.MemberCommentManager;
import com.enation.app.javashop.core.member.service.MemberManager;
import com.enation.app.javashop.core.trade.order.model.enums.CommentStatusEnum;
import com.enation.app.javashop.core.trade.sdk.model.OrderDetailDTO;
import com.enation.app.javashop.core.trade.sdk.model.OrderSkuDTO;
import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.security.model.Buyer;
import com.enation.app.javashop.framework.util.DateUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import com.enation.app.javashop.framework.rabbitmq.MessageSender;
import com.enation.app.javashop.framework.rabbitmq.MqMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:19:14
 */
@Service
public class MemberCommentManagerImpl implements MemberCommentManager {

    @Autowired
    @Qualifier("memberDaoSupport")
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
            // 找出有图片和回复过的评论id
            for (CommentVO comment : list) {
                //找出初评含有图片的评论id
                if (comment.getHaveImage() == 1) {
                    commentIds.add(comment.getCommentId());
                }
                if (comment.getReplyStatus() == 1) {
                    commentReplyIds.add(comment.getCommentId());
                }
                //找出追评含有图片的评论id
                if (comment.getAdditionalStatus()!=null && comment.getAdditionalStatus() == 1 && comment.getAdditionalHaveImage()!=null && comment.getAdditionalHaveImage() == 1) {
                    addCommentIds.add(comment.getCommentId());
                }
            }
            // 查询相应的初评相册
            if (StringUtil.isNotEmpty(commentIds)) {
                Map<Integer, List<String>> map = this.commentGalleryManager.getGalleryByCommentIds(commentIds, 0);
                for (CommentVO comment : list) {
                    if (comment.getHaveImage() == 1) {
                        comment.setImages(map.get(comment.getCommentId()));
                    }
                }
            }

            // 查询相应的追评相册
            if (StringUtil.isNotEmpty(addCommentIds)) {
                Map<Integer, List<String>> map = this.commentGalleryManager.getGalleryByCommentIds(addCommentIds, 1);
                for (CommentVO comment : list) {
                    if (comment.getAdditionalStatus() == 1 && comment.getAdditionalHaveImage() == 1) {
                        comment.setAddImages(map.get(comment.getCommentId()));
                    }
                }
            }

            // 查询回复
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
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberComment add(CommentScoreDTO comment, Permission permission) {

        OrderDetailDTO orderDetail = orderClient.getModel(comment.getOrderSn());
        // 不存在的订单/不是我的订单
        if (Permission.BUYER.equals(permission)) {
            Buyer member = UserContext.getBuyer();
            if (orderDetail == null || !member.getUid().equals(orderDetail.getMemberId())) {
                throw new ServiceException(MemberErrorCode.E200.code(), "没有权限");
            }
        }

        if (!orderDetail.getOrderOperateAllowableVO().getAllowComment()) {
            throw new ServiceException(MemberErrorCode.E200.code(), "没有权限");
        }
        // 添加评论
        this.add(comment.getComments(), orderDetail);

        // 更改订单的评论状态，同步更改 ，避免重复评论
        orderClient.updateOrderCommentStatus(comment.getOrderSn(), CommentStatusEnum.FINISHED.name());

        return null;
    }


    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberComment edit(MemberComment memberComment, Integer id) {
        this.daoSupport.update(memberComment, id);
        return memberComment;
    }

    @Override
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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

        // 查询过期没有评论订单
        List<OrderDetailDTO> list = detailDTOList;

        // 循环订单的商品自动给好评
        if (StringUtil.isNotEmpty(list)) {
            for (OrderDetailDTO orderDetail : list) {
                //  添加商品评分
                List<OrderSkuDTO> skuList = orderDetail.getOrderSkuList();
                List<CommentDTO> commentList = new ArrayList<>();

                for (OrderSkuDTO sku : skuList) {
                    CommentDTO comment = new CommentDTO();
                    comment.setSkuId(sku.getSkuId());
                    comment.setGrade(CommentGrade.good.name());
                    comment.setContent("此商品默认好评");
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
                //图片评论的数量
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
    @Transactional(value = "memberTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<AdditionalCommentDTO> additionalComments(List<AdditionalCommentDTO> comments, Permission permission) {
        for (AdditionalCommentDTO commentDTO : comments) {
            MemberComment memberComment = this.getModel(commentDTO.getCommentId());

            //如果会员评论为空或者评论已删除或者已经添加过追评，则不允许添加追评
            if (memberComment == null || memberComment.getStatus().intValue() == 0 || memberComment.getAdditionalStatus().intValue() == 1) {
                throw new ServiceException(MemberErrorCode.E200.code(), "没有权限");
            }

            // 验证权限
            if (Permission.BUYER.equals(permission)) {
                Buyer member = UserContext.getBuyer();
                if (!member.getUid().equals(memberComment.getMemberId())) {
                    throw new ServiceException(MemberErrorCode.E200.code(), "没有权限");
                }
            }

            if (StringUtil.isEmpty(commentDTO.getContent())) {
                throw new ServiceException(MemberErrorCode.E201.code(), "追加的评论内容不能为空");
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
                //添加图片
                this.commentGalleryManager.add(memberComment.getCommentId(), commentDTO.getImages(), 1);
            }
        }
        return comments;
    }

    /**
     * 添加评论
     *
     * @param commentList 发起的评论
     * @param orderDetail 订单
     */
    private void add(List<CommentDTO> commentList, OrderDetailDTO orderDetail) {

        Map<Integer, Object> skuMap = new HashMap<Integer, Object>(orderDetail.getOrderSkuList().size());
        // 将product循环放入map
        for (OrderSkuDTO sku : orderDetail.getOrderSkuList()) {
            skuMap.put(sku.getSkuId(), sku);
        }

        for (CommentDTO comment : commentList) {
            OrderSkuDTO product = (OrderSkuDTO) skuMap.get(comment.getSkuId());
            if (product == null) {
                throw new ServiceException(MemberErrorCode.E200.code(), "没有权限");
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

            // 是否有图片
            memberComment.setHaveImage(StringUtil.isNotEmpty(comment.getImages()) ? 1 : 0);

            if (CommentGrade.good.name().equals(comment.getGrade()) && StringUtil.isEmpty(memberComment.getContent())) {

                memberComment.setContent("此评论默认好评！！");
            }

            if (!CommentGrade.good.name().equals(comment.getGrade()) && StringUtil.isEmpty(memberComment.getContent())) {

                throw new ServiceException(MemberErrorCode.E201.code(), "非好评评论必填");
            }

            this.daoSupport.insert(memberComment);

            int commentId = this.daoSupport.getLastId("es_member_comment");

            //添加图片
            this.commentGalleryManager.add(commentId, comment.getImages(), 0);

            // 发消息
            memberComment.setCommentId(commentId);
            GoodsCommentMsg goodsCommentMsg = new GoodsCommentMsg();
            goodsCommentMsg.setComment(memberComment);
            this.messageSender.send(new MqMessage(AmqpExchange.GOODS_COMMENT_COMPLETE, AmqpExchange.GOODS_COMMENT_COMPLETE + "_ROUTING",
                    goodsCommentMsg));

        }
    }
}
