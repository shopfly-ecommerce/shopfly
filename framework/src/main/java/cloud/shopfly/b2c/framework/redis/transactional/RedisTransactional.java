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
package cloud.shopfly.b2c.framework.redis.transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisTransactional {

    /**
     * 获取锁的最长等待时间，以秒为单位
     * 如果不指定，则会一直等待
     */
    int acquireTimeout() default 0;


    /**
     * 锁的超时时间，以秒为单位
     * 如果不指定，则不会超时，只能手工解锁时才会释放
     */
    int lockTimeout() default 0 ;


    /**
     * 锁名字，如果不指定，则使用当前方法的全路径名
     * @return
     */
    String lockName() default "";


}
