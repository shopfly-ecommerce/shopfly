/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.cache;


import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 缓存接口
 * @author kingapex
 *
 */
public interface Cache<T> {
	
	/**
	 * Get an item from the cache, nontransactionally
	 * @param key
	 * @return the cached object or <tt>null</tt>
	 */
	T get(Object key);


	/**
	 *  multiGet
	 * @param keys  要查询的key集合
	 * @return
	 */
	List  multiGet(Collection keys);

	/**
	 * 批量set
	 * @param map
	 */
	void multiSet(Map map);


	/**
	 * 批量删除
	 * @param keys 要删除的key集合
	 */
	void multiDel(Collection keys);

	/**
	 * Add an item to the cache, nontransactionally, with
	 * failfast semantics
	 * @param key
	 * @param value
	 */
	void put(Object key, T value);
	
	/**
	 * 往缓存中写入内容
	 * @param key
	 * @param value
	 * @param exp	超时时间，单位为秒
	 */
	void put(Object key, T value, int exp);

	/**
	 * 删除
	 * @param key
	 */
	void remove(Object key);

	/**
	 * 删除
	 * @param key
	 */
	void vagueDel(Object key);

	/**
	 * Clear the cache
	 */
	void clear();


	/**
	 * 往缓存中写入内容
	 * @param key		缓存key
	 * @param hashKey	缓存中hashKey
	 * @param hashValue hash值
	 */
	void putHash(Object key,Object hashKey,Object hashValue);

	/**
	 * 玩缓存中写入内容
	 * @param key
	 * @param map
	 */
	void putAllHash(Object key,Map map);

	/**
	 * 读取缓存值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	T getHash(Object key,Object hashKey);

	/**
	 * 读取缓存值
	 * @param key
	 * @return
	 */
	Map<Object,Object> getHash(Object key);
}
