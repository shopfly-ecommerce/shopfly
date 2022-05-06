/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * redis的cache实现
 *
 * @author fk
 * @version v6.4
 * @since v6.4 2017年9月25日 下午4:32:49
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
     * 删除
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
