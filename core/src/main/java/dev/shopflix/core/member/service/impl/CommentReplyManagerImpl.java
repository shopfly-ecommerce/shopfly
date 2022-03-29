/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.service.impl;

import dev.shopflix.core.goods.model.enums.Permission;
import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.member.model.dos.CommentReply;
import dev.shopflix.core.member.service.CommentReplyManager;
import dev.shopflix.core.member.service.MemberCommentManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
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
 * 评论回复业务类
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 16:34:50
 */
@Service
public class CommentReplyManagerImpl implements CommentReplyManager {

	@Autowired
	@Qualifier("memberDaoSupport")
	private DaoSupport daoSupport;

	@Autowired
	private MemberCommentManager memberCommentManager;
	
	@Override
	public Page list(int page, int pageSize){
		
		String sql = "select * from es_comment_reply  ";
		Page webPage = this.daoSupport.queryForPage(sql,page, pageSize ,CommentReply.class );
		
		return webPage;
	}
	
	@Override
	@Transactional(value = "memberTransactionManager",propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public CommentReply add(CommentReply commentReply)	{
		this.daoSupport.insert(commentReply);
		
		return commentReply;
	}
	
	@Override
	@Transactional(value = "memberTransactionManager",propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public CommentReply edit(CommentReply commentReply, Integer id){
		this.daoSupport.update(commentReply, id);
		return commentReply;
	}
	
	@Override
	@Transactional(value = "memberTransactionManager",propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	void delete( Integer id)	{
		this.daoSupport.delete(CommentReply.class,	id);
	}
	
	@Override
	public CommentReply getModel(Integer id)	{
		return this.daoSupport.queryForObject(CommentReply.class, id);
	}

	@Override
	public Map<Integer, CommentReply> getReply(List<Integer> commentIds) {

		Integer[] commentIdArray = new Integer[commentIds.size()];
		commentIds.toArray(commentIdArray);
		List<Object> term = new ArrayList<>();
		String str = SqlUtil.getInSql(commentIdArray, term);
		//目前商城只支持单次回复，一次对话
		String sql = "select * from es_comment_reply where comment_id in (" + str + ") and parent_id = 0 ";
		// 查询评论回复
		List<CommentReply> resList = this.daoSupport.queryForList(sql,CommentReply.class, term.toArray());

		Map<Integer, CommentReply> resMap = new HashMap<>(resList.size());

		for(CommentReply reply : resList){
			resMap.put(reply.getCommentId(),reply);
		}
		return resMap;
	}

	@Override
	@Transactional(value = "memberTransactionManager",propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public CommentReply replyComment(Integer commentId, String reply, Permission permission) {
		String sql = "select * from es_comment_reply where comment_id = ? and parent_id = 0 ";
		List list = this.daoSupport.queryForList(sql, commentId);
		if(StringUtil.isNotEmpty(list)){
			throw new ServiceException(MemberErrorCode.E200.code(),"不能重复回复");
		}
		CommentReply commentReply = new CommentReply();
		commentReply.setCommentId(commentId);
		commentReply.setContent(reply);
		commentReply.setCreateTime(DateUtil.getDateline());
		commentReply.setParentId(0);
		commentReply.setRole(permission.name());

		this.daoSupport.insert(commentReply);
		commentReply.setReplyId(this.daoSupport.getLastId(""));
		//更改评论的状态为已回复
		sql = "update es_member_comment set reply_status = 1 where comment_id = ? ";
		this.daoSupport.execute(sql,commentId);
		return commentReply;
	}
}
