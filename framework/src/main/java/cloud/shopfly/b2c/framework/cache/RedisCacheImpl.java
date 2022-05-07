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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redisthecacheimplementation
 *
 * @author fk
 * @version v6.4
 * @since v6.4 2017years9month25On the afternoon4:32:49
 */
@Component
public class RedisCacheImpl implements Cache {

    @Autowired
    private RedisTemplate redisTemplate;


    public RedisCacheImpl() {

    }

    @Override
    public Object get(Object key) {

        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public List multiGet(Collection  keys) {
       return redisTemplate.opsForValue().multiGet(keys);

    }


    @Override
    public void multiSet(Map map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public void multiDel(Collection keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public void put(Object key, Object value) {

        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(Object key, Object value, int exp) {

        redisTemplate.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
    }

    @Override
    public void remove(Object key) {

        redisTemplate.delete(key);
    }

    /**
     * delete
     *
     * @param key
     */
    @Override
    public void vagueDel(Object key) {
        Set<String> keys = redisTemplate.keys(key + "*");
        redisTemplate.delete(keys);
    }

    @Override
    public void clear() {

        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    @Override
    public void putHash(Object key, Object hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    @Override
    public void putAllHash(Object key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public Object getHash(Object key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Map<Object, Object> getHash(Object key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

}
