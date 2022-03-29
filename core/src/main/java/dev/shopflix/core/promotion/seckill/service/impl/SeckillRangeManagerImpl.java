/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.seckill.service.impl;

import dev.shopflix.core.promotion.seckill.model.dos.SeckillRangeDO;
import dev.shopflix.core.promotion.seckill.model.vo.TimeLineVO;
import dev.shopflix.core.promotion.seckill.service.SeckillRangeManager;
import dev.shopflix.core.promotion.tool.support.PromotionCacheKeys;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 限时抢购时刻业务类
 *
 * @author Snow
 * @version v2.0.0
 * @since v7.0.0
 * 2018-04-02 18:24:47
 */
@Service
public class SeckillRangeManagerImpl implements SeckillRangeManager {

    @Autowired
    @Qualifier("tradeDaoSupport")
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

        //读取系统时间的时刻
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        List<TimeLineVO> list = new ArrayList<>();

        //距离时刻的集合
        List<Long> distanceTimeList = new ArrayList<>();

        //未开始的活动
        for (Map.Entry<Integer, List> entry : map.entrySet()) {
            //大于当前的小时数
            if (entry.getKey() > hour) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(new Date());

                //当前时间的秒数
                long currentTime = DateUtil.getDateline();
                //限时抢购的时刻
                long timeLine = DateUtil.getDateline(date + " " + entry.getKey(), "yyyy-MM-dd HH");
                long distanceTime = timeLine - currentTime < 0 ? 0 : timeLine - currentTime;
                distanceTimeList.add(distanceTime);

                TimeLineVO timeLineVO = new TimeLineVO();
                timeLineVO.setTimeText(entry.getKey() + "");
                timeLineVO.setDistanceTime(distanceTime);
                list.add(timeLineVO);
            }
        }

        //正在进行中的活动的时刻
        int currentTime = -1;

        //正在进行的活动读取
        for (Map.Entry<Integer, List> entry : map.entrySet()) {
            //如果有时间相等的则直接将当前时间，设为正在进行中活动的时刻
            if (entry.getKey() == hour) {
                currentTime = hour;
                break;
            }

            //大于循环前面的时间,小于当前的时间
            if (entry.getKey() > currentTime && entry.getKey() <= hour) {
                currentTime = entry.getKey();
            }
        }

        //距离时刻的数据
        Long[] distanceTimes = new Long[distanceTimeList.size()];
        //排序
        distanceTimeList.toArray(distanceTimes);
        long distanceTime = distanceTimes.length > 1 ? distanceTimes[0] : 0;

        //如果当前时间大于等于0
        if (currentTime >= 0) {
            //正在进行中的活动
            TimeLineVO timeLine = new TimeLineVO();
            timeLine.setTimeText(currentTime + "");
            timeLine.setDistanceTime(0L);
            timeLine.setNextDistanceTime(distanceTime);
            list.add(0, timeLine);
        }

        return list;
    }


}
