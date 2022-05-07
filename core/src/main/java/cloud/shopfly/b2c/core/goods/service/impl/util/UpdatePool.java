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
package cloud.shopfly.b2c.core.goods.service.impl.util;

import cloud.shopfly.b2c.framework.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Update count pool<br/>
 * Conditions have：Buffer size, buffer times, buffer time<br/>
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-21
 */

public class UpdatePool {

    /**
     * Last update time
     */
    private int updateTime;

    /**
     * The buffer object
     */
    private List targetList;

    /**
     * Initial time
     */
    private long startTime;

    /**
     * Number of buffer
     */
    private int maxUpdateTime;

    /**
     * Buffer size
     */
    private int maxPoolSize;

    /**
     * Buffer time（Number of seconds）
     */
    private int maxLazySecond;

    /**
     * The default value
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
     * Updated once
     * @param list Update object list
     * @return
     */
    public boolean oneTime(List list) {
        list.forEach(o -> {
            putTarget(o);
        });

        return oneTime();
    }

    /**
     * Updated once
     * @param target Update the object
     * @return
     */
    public boolean oneTime(Object target) {
        putTarget(target);
        return oneTime();
    }


    private boolean oneTime() {

        // The start time of the first record update
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

        // If no save exists, skip otherwise
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
