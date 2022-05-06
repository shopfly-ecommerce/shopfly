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

import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.CommentReply;
import cloud.shopfly.b2c.core.member.service.CommentReplyManager;
import cloud.shopfly.b2c.core.member.service.MemberCommentManager;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
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
 * 评论回复业务类
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 16:34:50
 */
@Service
public class CommentReplyManagerImpl implements CommentReplyManager {

	@Autowired
	
	private DaoSupport daoSupport;

	@Autowired
	private MemberCommentManager memberCommentManager;
	
	@Override
	public Page list(int page, int pageSize){
		
		String sql = "select * from es_comment_reply  ";
		Page webPage = this.daoSupport.queryForPage(sql,page, pageSize , CommentReply.class );
		
		return webPage;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public CommentReply add(CommentReply commentReply)	{
		this.daoSupport.insert(commentReply);
		
		return commentReply;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public CommentReply edit(CommentReply commentReply, Integer id){
		this.daoSupport.update(commentReply, id);
		return commentReply;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
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
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
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
