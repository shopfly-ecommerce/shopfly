/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.base.model.dos.SensitiveWords;
import dev.shopflix.core.base.service.SensitiveWordsManager;
import dev.shopflix.core.statistics.util.DateUtil;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 敏感词业务类
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-02 11:30:59
 */
@Service
public class SensitiveWordsManagerImpl implements SensitiveWordsManager {

	@Autowired
	
	private DaoSupport daoSupport;
	@Autowired
	private Cache cache;
	
	@Override
	public Page list(int page, int pageSize, String keyword){

		StringBuffer sqlBuffer = new StringBuffer("select * from es_sensitive_words  ");

		List<Object> term = new ArrayList<>();
		if(keyword!=null){
			sqlBuffer.append("where word_name like ? ");
			term.add("%"+keyword+"%");
		}

		Page webPage = this.daoSupport.queryForPage(sqlBuffer.toString(),page, pageSize , SensitiveWords.class,term.toArray());
		
		return webPage;
	}
	
	@Override
	@Transactional(value = "systemTransactionManager",propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public SensitiveWords add(SensitiveWords	sensitiveWords)	{

		sensitiveWords.setIsDelete(1);
		sensitiveWords.setCreateTime(DateUtil.getDateline());

		this.daoSupport.insert(sensitiveWords);

		cache.remove(CachePrefix.SENSITIVE_WORDS.getPrefix());
		
		return sensitiveWords;
	}
	
	@Override
	@Transactional(value = "systemTransactionManager",propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	SensitiveWords  edit(SensitiveWords	sensitiveWords,Integer id){
		this.daoSupport.update(sensitiveWords, id);
		cache.remove(CachePrefix.SENSITIVE_WORDS.getPrefix());
		return sensitiveWords;
	}
	
	@Override
	@Transactional(value = "systemTransactionManager",propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	void delete( Integer id)	{
		String sql = "update es_sensitive_words set is_delete = 0 where id = ?";
		this.daoSupport.execute(sql,id);
		cache.remove(CachePrefix.SENSITIVE_WORDS.getPrefix());
	}
	
	@Override
	public	SensitiveWords getModel(Integer id)	{
		return this.daoSupport.queryForObject(SensitiveWords.class, id);
	}

	@Override
	public List<String> listWords() {

		String sql = "select * from es_sensitive_words where is_delete = 1";

		List<SensitiveWords> list = this.daoSupport.queryForList(sql,SensitiveWords.class);

		List<String> words = new ArrayList<>();
		if(list !=null ){

			for(SensitiveWords word:list){
				words.add(word.getWordName());
			}

		}
		return words;
	}
}
