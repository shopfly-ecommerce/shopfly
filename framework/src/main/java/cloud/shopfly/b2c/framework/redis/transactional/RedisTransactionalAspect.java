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


import java.util.concurrent.TimeUnit;


import cloud.shopfly.b2c.framework.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

 

/***
 * RedisTransaction aspect control
 * @author kingapex
 * @version 2.0:useRedisson
 * @since 6.4.1
 * 2017years6month9The morning of10:13:27
 */
@Aspect
@Component
public class RedisTransactionalAspect {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  

	@Autowired
	private RedissonClient redissonClient;


	/**
	 * Facets the transaction annotations
	 * @param pjd Point of tangency
	 * @param transactional Transaction annotations
	 * @return The value returned by the original method
	 * @throws Throwable Possible exceptions
	 */
	@Around(value = "@annotation(transactional)")
	public Object aroundMethod(ProceedingJoinPoint pjd,  RedisTransactional transactional) throws Throwable {

		String lockName  = transactional.lockName();

		if(StringUtil.isEmpty(lockName)) {
			// Generate lock key name
			CodeSignature signature = (CodeSignature) pjd.getSignature();
			lockName = signature.toLongString();
		}

		// Acquiring a lock
		RLock lock = redissonClient.getLock(lockName);
		String tname  = Thread.currentThread().getName();
		try {
			// locked

			// The maximum time to obtain the lock
			int acquireTimeout = transactional.acquireTimeout();

			// Timeout of the lock
			int lockTime  = transactional.lockTimeout();

			// If no timeout period is specified, the lock is directly performed
			if(lockTime == 0 && acquireTimeout ==0 ) {
				lock.lock();

			}

			// If timeout is specified, a lock is attempted
			if(acquireTimeout!=0  && lockTime!=0){
				boolean lockResult     = lock.tryLock(acquireTimeout,lockTime, TimeUnit.SECONDS);

				if(!lockResult ) {
					throw new RuntimeException(lockName + " Failed to obtain the lock");
				}


			}

			// If the elapsed time of the lock is specified, but the time to acquire the lock is not specified
			if(acquireTimeout==0  && lockTime!=0){
				lock.lock(lockTime, TimeUnit.SECONDS);

			}


			// Perform the section method
			Object result = pjd.proceed();
			return result;

		} catch (Throwable e) {
			if( logger.isErrorEnabled()){
				this.logger.error("redisTransaction failure",e);
			}
			throw e;
		} finally {
			// unlock
			lock.unlock();
		}
	}


}
