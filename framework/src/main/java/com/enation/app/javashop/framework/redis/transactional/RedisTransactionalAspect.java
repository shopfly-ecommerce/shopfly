/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.redis.transactional;


import java.util.concurrent.TimeUnit;


import com.enation.app.javashop.framework.util.StringUtil;
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
 * Redis事务切面控制
 * @author kingapex
 * @version 2.0:使用 Redisson
 * @since 6.4.1
 * 2017年6月9日上午10:13:27
 */
@Aspect
@Component
public class RedisTransactionalAspect {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  

	@Autowired
	private RedissonClient redissonClient;


	/**
	 * 对事务注解进行切面
	 * @param pjd 切点
	 * @param transactional 事务注解
	 * @return 原方法返回值
	 * @throws Throwable 可能存在的异常
	 */
	@Around(value = "@annotation(transactional)")
	public Object aroundMethod(ProceedingJoinPoint pjd,  RedisTransactional transactional) throws Throwable {

		String lockName  = transactional.lockName();

		if(StringUtil.isEmpty(lockName)) {
			//生成 lock key name
			CodeSignature signature = (CodeSignature) pjd.getSignature();
			lockName = signature.toLongString();
		}

		//获取锁
		RLock lock = redissonClient.getLock(lockName);
		String tname  = Thread.currentThread().getName();
		try {
			//上锁

			//获取锁的最长时间
			int acquireTimeout = transactional.acquireTimeout();

			//锁的超时间
			int lockTime  = transactional.lockTimeout();

			//如果没指定超时时间则直接上锁
			if(lockTime == 0 && acquireTimeout ==0 ) {
				lock.lock();

			}

			//如果指定了超时间则尝试上锁
			if(acquireTimeout!=0  && lockTime!=0){
				boolean lockResult     = lock.tryLock(acquireTimeout,lockTime, TimeUnit.SECONDS);

				if(!lockResult ) {
					throw new RuntimeException(lockName + " 获取锁失败");
				}


			}

			//如果指定了 锁的超时间，但没有指定获取锁的时间
			if(acquireTimeout==0  && lockTime!=0){
				lock.lock(lockTime, TimeUnit.SECONDS);

			}


			//执行切面方法
			Object result = pjd.proceed();
			return result;

		} catch (Throwable e) {
			if( logger.isErrorEnabled()){
				this.logger.error("redis事务失败",e);
			}
			throw e;
		} finally {
			//解锁
			lock.unlock();
		}
	}


}
