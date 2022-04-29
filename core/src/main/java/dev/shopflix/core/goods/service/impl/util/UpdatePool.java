/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.service.impl.util;

import dev.shopflix.framework.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 更新次数池<br/>
 * 条件有：缓冲区大小，缓冲次数，缓冲时间<br/>
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-21
 */

public class UpdatePool {

    /**
     * 最后的更新时间
     */
    private int updateTime;

    /**
     * 缓冲对象
     */
    private List targetList;

    /**
     * 初始时间
     */
    private long startTime;

    /**
     * 缓冲次数
     */
    private int maxUpdateTime;

    /**
     * 缓冲区大小
     */
    private int maxPoolSize;

    /**
     * 缓冲时间（秒数）
     */
    private int maxLazySecond;

    /**
     * 默认值
     */
    private final int defaultMaxUpdateTime =100;
    private final int defaultMaxPoolSize =100;
    private final int defaultmaxLazySecond  =3600;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    public UpdatePool(Integer maxUpdateTime, Integer maxPoolSize, Integer maxLazySecond) {

        this.maxUpdateTime = maxUpdateTime==null?defaultMaxUpdateTime:maxUpdateTime;
        this.maxPoolSize = maxPoolSize==null?defaultMaxPoolSize:maxPoolSize;
        this.maxLazySecond = maxLazySecond==null?defaultmaxLazySecond:maxLazySecond;
        reset();

    }

    /**
     * 更新一次
     * @param list 更新对象列表
     * @return
     */
    public boolean oneTime(List list) {
        list.forEach(o -> {
            putTarget(o);
        });

        return oneTime();
    }

    /**
     * 更新一次
     * @param target 更新对象
     * @return
     */
    public boolean oneTime(Object target) {
        putTarget(target);
        return oneTime();
    }


    private boolean oneTime() {

        //第一次更新记录开始时间
        if (updateTime == 0) {
            startTime = DateUtil.getDateline();
        }

        updateTime++;
        if (updateTime >= maxUpdateTime) {
            if (logger.isDebugEnabled()) {
                logger.debug("updateTime >= maxUpdateTime");
            }
            return true;
        }


        if (targetList.size() >= maxPoolSize) {
            if (logger.isDebugEnabled()) {
                logger.debug("targetList.size() >= maxPoolSize");
            }
            return true;
        }

        long distance = DateUtil.getDateline() - startTime;

        if (distance >= maxLazySecond) {
            if (logger.isDebugEnabled()) {
                logger.debug("distance >= maxLazySecond");
            }
            return true;
        }

        return false;
    }


    public List getTargetList() {
        return targetList;
    }


    public void reset() {
        updateTime = 0;
        targetList = new ArrayList<String>();
        startTime = 0;
    }

    private void putTarget(Object target) {

        //如果不存在存入，否则跳过
        for (Object o : targetList) {
            if (o.equals(target)) {
                return;
            }
        }

        targetList.add(target);
    }



    @Override
    public String toString() {
        return "UpdatePool{" +
                "updateTime=" + updateTime +
                ", targetList=" + targetList +
                ", startTime=" + startTime +
                ", maxUpdateTime=" + maxUpdateTime +
                ", maxPoolSize=" + maxPoolSize +
                ", maxLazySecond=" + maxLazySecond +
                '}';
    }
}
