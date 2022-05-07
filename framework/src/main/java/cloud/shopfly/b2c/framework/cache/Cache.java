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
package cloud.shopfly.b2c.framework.cache;


import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Cache interface
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
	 * @param keys  To query thekeyA collection of
	 * @return
	 */
	List  multiGet(Collection keys);

	/**
	 * batchset
	 * @param map
	 */
	void multiSet(Map map);


	/**
	 * Batch delete
	 * @param keys Want to delete thekeyA collection of
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
	 * Write to the cache
	 * @param key
	 * @param value
	 * @param exp	Timeout period, in seconds
	 */
	void put(Object key, T value, int exp);

	/**
	 * delete
	 * @param key
	 */
	void remove(Object key);

	/**
	 * delete
	 * @param key
	 */
	void vagueDel(Object key);

	/**
	 * Clear the cache
	 */
	void clear();


	/**
	 * Write to the cache
	 * @param key		The cachekey
	 * @param hashKey	The cachehashKey
	 * @param hashValue hashvalue
	 */
	void putHash(Object key,Object hashKey,Object hashValue);

	/**
	 * Play cache to write content
	 * @param key
	 * @param map
	 */
	void putAllHash(Object key,Map map);

	/**
	 * Read cache value
	 * @param key
	 * @param hashKey
	 * @return
	 */
	T getHash(Object key,Object hashKey);

	/**
	 * Read cache value
	 * @param key
	 * @return
	 */
	Map<Object,Object> getHash(Object key);
}
