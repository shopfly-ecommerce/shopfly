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
package cloud.shopfly.b2c.core.promotion.seckill.service.impl;

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillRangeDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.TimeLineVO;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillRangeManager;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionCacheKeys;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Flash sale time business class
 *
 * @author Snow
 * @version v2.0.0
 * @since v7.0.0
 * 2018-04-02 18:24:47
 */
@Service
public class SeckillRangeManagerImpl implements SeckillRangeManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private Cache cache;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_seckill_range  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, SeckillRangeDO.class);

        return webPage;
    }

    @Override
    public SeckillRangeDO edit(SeckillRangeDO seckillRange, Integer id) {
        this.daoSupport.update(seckillRange, id);
        return seckillRange;
    }

    @Override
    public void delete(Integer id) {
        this.daoSupport.delete(SeckillRangeDO.class, id);
    }

    @Override
    public SeckillRangeDO getModel(Integer id) {
        return this.daoSupport.queryForObject(SeckillRangeDO.class, id);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class, RuntimeException.class})
    public void addList(List<Integer> list, Integer seckillId) {
        String sql = "delete from es_seckill_range where seckill_id=?";
        this.daoSupport.execute(sql, seckillId);
        List<Integer> termList = new ArrayList<>();
        for (Integer time : list) {
            termList.add(time);
            SeckillRangeDO seckillRangeDO = new SeckillRangeDO();
            seckillRangeDO.setSeckillId(seckillId);
            seckillRangeDO.setRangeTime(time);
            this.daoSupport.insert(seckillRangeDO);
        }
    }


    @Override
    public List<SeckillRangeDO> getList(Integer seckillId) {

        String sql = "select * from es_seckill_range where seckill_id=?";
        List<SeckillRangeDO> list = this.daoSupport.queryForList(sql, SeckillRangeDO.class, seckillId);
        return list;
    }


    @Override
    public List<TimeLineVO> readTimeList() {
        long today = DateUtil.getDateline(DateUtil.toString(new Date(), "yyyy-MM-dd"));

        String redisKey = PromotionCacheKeys.getSeckillKey(DateUtil.toString(DateUtil.getDateline(), "yyyyMMdd"));
        Map<Integer, List> map = this.cache.getHash(redisKey);

        if (map.isEmpty()) {
            map =  new LinkedHashMap<>();
            String sql = "select DISTINCT sr.*,s.* from es_seckill_range sr left join  es_seckill s on sr.seckill_id=s.seckill_id " +
                    " inner join es_seckill_apply sa on sa.seckill_id = sr.seckill_id and sa.time_line = sr.range_time WHERE s.start_day = ?  order by sr.range_time ";
            List<SeckillRangeDO> list = this.daoSupport.queryForList(sql, SeckillRangeDO.class, today);

            if (list.isEmpty()) {
                return new ArrayList<TimeLineVO>();
            }

            for (SeckillRangeDO rangeDO : list) {
                map.put(rangeDO.getRangeTime(), null);
            }
        }

        // The time when the system time is read
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        List<TimeLineVO> list = new ArrayList<>();

        // Set of distance moments
        List<Long> distanceTimeList = new ArrayList<>();

        // Uninitiated activities
        for (Map.Entry<Integer, List> entry : map.entrySet()) {
            // Greater than the current hour
            if (entry.getKey() > hour) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(new Date());

                // Number of seconds of the current time
                long currentTime = DateUtil.getDateline();
                // Flash sale time
                long timeLine = DateUtil.getDateline(date + " " + entry.getKey(), "yyyy-MM-dd HH");
                long distanceTime = timeLine - currentTime < 0 ? 0 : timeLine - currentTime;
                distanceTimeList.add(distanceTime);

                TimeLineVO timeLineVO = new TimeLineVO();
                timeLineVO.setTimeText(entry.getKey() + "");
                timeLineVO.setDistanceTime(distanceTime);
                list.add(timeLineVO);
            }
        }

        // A moment of ongoing activity
        int currentTime = -1;

        // Active read in progress
        for (Map.Entry<Integer, List> entry : map.entrySet()) {
            // If the time is equal, set the current time to the time when the activity is in progress
            if (entry.getKey() == hour) {
                currentTime = hour;
                break;
            }

            // Greater than the time before the loop and less than the current time
            if (entry.getKey() > currentTime && entry.getKey() <= hour) {
                currentTime = entry.getKey();
            }
        }

        // Distance time data
        Long[] distanceTimes = new Long[distanceTimeList.size()];
        // sort
        distanceTimeList.toArray(distanceTimes);
        long distanceTime = distanceTimes.length > 1 ? distanceTimes[0] : 0;

        // If the current time is greater than or equal to 0
        if (currentTime >= 0) {
            // Ongoing activities
            TimeLineVO timeLine = new TimeLineVO();
            timeLine.setTimeText(currentTime + "");
            timeLine.setDistanceTime(0L);
            timeLine.setNextDistanceTime(distanceTime);
            list.add(0, timeLine);
        }

        return list;
    }


}
