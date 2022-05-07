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
package cloud.shopfly.b2c.core.base.service.impl;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.model.dos.SensitiveWords;
import cloud.shopfly.b2c.core.base.service.SensitiveWordsManager;
import cloud.shopfly.b2c.core.statistics.util.DateUtil;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Sensitive word business class
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
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public SensitiveWords add(SensitiveWords	sensitiveWords)	{

		sensitiveWords.setIsDelete(1);
		sensitiveWords.setCreateTime(DateUtil.getDateline());

		this.daoSupport.insert(sensitiveWords);

		cache.remove(CachePrefix.SENSITIVE_WORDS.getPrefix());
		
		return sensitiveWords;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public	SensitiveWords  edit(SensitiveWords	sensitiveWords,Integer id){
		this.daoSupport.update(sensitiveWords, id);
		cache.remove(CachePrefix.SENSITIVE_WORDS.getPrefix());
		return sensitiveWords;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
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
