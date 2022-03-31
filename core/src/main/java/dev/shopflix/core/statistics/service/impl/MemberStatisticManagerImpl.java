/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.service.impl;

import dev.shopflix.core.base.SearchCriteria;
import dev.shopflix.core.statistics.StatisticsErrorCode;
import dev.shopflix.core.statistics.StatisticsException;
import dev.shopflix.core.statistics.model.enums.QueryDateType;
import dev.shopflix.core.statistics.model.vo.ChartSeries;
import dev.shopflix.core.statistics.model.vo.SimpleChart;
import dev.shopflix.core.statistics.service.MemberStatisticManager;
import dev.shopflix.core.statistics.util.ChartUtil;
import dev.shopflix.core.statistics.util.DataDisplayUtil;
import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;
import dev.shopflix.core.trade.order.model.enums.PayStatusEnum;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * 会员统计 实现类
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/28 下午5:11
 */
@Service
public class MemberStatisticManagerImpl implements MemberStatisticManager {

    protected final Log logger = org.apache.commons.logging.LogFactory.getLog(this.getClass());
    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public SimpleChart getIncreaseMember(SearchCriteria searchCriteria) {
        //参数校验
        searchCriteria = new SearchCriteria(searchCriteria);
        try {
            //获取结果数量
            Integer resultSize = DataDisplayUtil.getResultSize(searchCriteria);

            Page page = this.getIncreaseMemberPage(searchCriteria);
            List<Map<String, Object>> result = page.getData();

            String[] nowData = new String[resultSize];

            //生成图标数据
            int i = 0;
            for (Map<String, Object> mrd : result) {
                nowData[i] = mrd.get("num").toString();
                i++;
            }
            ChartSeries chartSeries = new ChartSeries("新增会员数量", nowData);
            return new SimpleChart(chartSeries, ChartUtil.structureXAxis(searchCriteria.getCycleType(), searchCriteria.getYear(), searchCriteria.getMonth()), new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }


    @Override
    public Page getIncreaseMemberPage(SearchCriteria searchCriteria) {
        //参数校验
        searchCriteria = new SearchCriteria(searchCriteria);
        try {

            //获取结果数量
            Integer resultSize = DataDisplayUtil.getResultSize(searchCriteria);
            //获取结果数量
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);
            long[] lasttimestamp = DataDisplayUtil.getLastStartTimeAndEndTime(searchCriteria);

            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();
            /*
             * 参数
             */
            List<Object> lastParams = new ArrayList<>();

            //判断sql日期分组条件
            String circle;
            if (Objects.equals(searchCriteria.getCycleType(), QueryDateType.YEAR.name())) {
                circle = "%m";
            } else {
                circle = "%d";
            }

            StringBuffer sql = new StringBuffer("SELECT COUNT(em.member_id) AS num , em.e_regtime AS time  FROM ( SELECT m.member_id,m.create_time, FROM_UNIXTIME(m.create_time, ?)  AS e_regtime FROM es_sss_member_register_data m WHERE  m.create_time >= ? AND  m.create_time <= ? ) em GROUP BY em.e_regtime");
            params.add(circle);
            params.add(timestamp[0]);
            params.add(timestamp[1]);
            lastParams.add(circle);
            lastParams.add(lasttimestamp[0]);
            lastParams.add(lasttimestamp[1]);

            List<Map<String, Object>> list = this.daoSupport.queryForList(sql.toString(), params.toArray());
            List<Map<String, Object>> lastList = this.daoSupport.queryForList(sql.toString(), lastParams.toArray());

            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            //将上周期的数据放到map中，以time为key
            Map lastMap = new HashMap();
            for (Map<String, Object> mrd2 : lastList) {

                lastMap.put(Integer.parseInt(mrd2.get("time").toString()),mrd2.get("num"));
            }

            //生成对比数据
            Map<Integer,Map<String, Object>> map = new HashMap();
            for (Map<String, Object> mrd : list) {
                Integer key = Integer.parseInt(mrd.get("time").toString());
                if (lastMap.get(key)!=null) {
                    double value = Double.parseDouble(mrd.get("num").toString());
                    double lastValue = Double.parseDouble(lastMap.get(key).toString());
                    double num = ((value - lastValue) / lastValue) * 100;
                    String result = df.format(num) + "%";
                    mrd.put("last_num", lastMap.get(key)==null?0:lastMap.get(key));
                    mrd.put("growth", result);
                }else{
                    mrd.put("last_num", lastMap.get(key)==null?0:lastMap.get(key));
                    mrd.put("growth", 0);
                }
                mrd.put("time",Integer.parseInt(mrd.get("time").toString()));
                map.put(key,mrd);
            }
            //最终结果 数据填充
            List<Map<String, Object>> result = new ArrayList<>();

            for (int i = 1; i <= resultSize; i++) {
                Map<String, Object> mrd = null;

                if(map.get(i)==null){
                    mrd = new HashMap<>();
                    mrd.put("time",i);
                    mrd.put("num",0);
                    mrd.put("last_num", lastMap.get(i)==null ? 0:lastMap.get(i));
                    if(lastMap.get(i)==null){

                        mrd.put("growth", 0);
                    }else{
                        double num = ((0 - Double.parseDouble(lastMap.get(i).toString())) / Double.parseDouble(lastMap.get(i).toString())) * 100;
                        mrd.put("growth", df.format(num) + "%");
                    }
                }else{
                    mrd = map.get(i);
                }
                result.add(mrd);
            }

            return new Page(1, (long) resultSize, resultSize, result);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getMemberOrderQuantity(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);

        try {
            Page page = this.getMemberOrderQuantityPage(searchCriteria);
            List<Map<String, Object>> list = page.getData();
            int dataLength = 10;
            String[] xAxis = new String[dataLength], localName = new String[dataLength], data = new String[dataLength];

            for (int i = 0; i < dataLength; i++) {
                if (null != list && i < list.size()) {
                    Map map = list.get(i);
                    data[i] = map.get("order_num").toString();
                    localName[i] = map.get("member_name").toString();
                } else {
                    data[i] = "0";
                    localName[i] = "无";
                }
                xAxis[i] = i + 1 + "";
            }

            ChartSeries chartSeries = new ChartSeries("会员下单量", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }


    @Override
    public Page getMemberOrderQuantityPage(SearchCriteria searchCriteria) {

        searchCriteria = new SearchCriteria(searchCriteria);

        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);

            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT count(0) AS order_num,m.`member_name` AS member_name "
                    + " FROM es_sss_order_data o INNER JOIN es_sss_member_register_data m ON o.`buyer_id` = m.`member_id` "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ? AND (o.order_status = '"
                    + OrderStatusEnum.COMPLETE.name() + "'	OR o.pay_status = '" + PayStatusEnum.PAY_YES.name() + "') ");
            params.add(timestamp[0]);
            params.add(timestamp[1]);

            sql.append(" GROUP BY member_name ORDER BY order_num DESC,member_name asc ");
            List<Map> list = this.daoSupport.queryForListPage(sql.toString(), 1, 10, params.toArray());
            return new Page(1, (long) list.size(), 10, list);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getMemberGoodsNum(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {

            Page page = this.getMemberGoodsNumPage(searchCriteria);
            List<Map<String, Object>> list = page.getData();

            int dataLength = 10;
            String[] xAxis = new String[dataLength], localName = new String[dataLength], data = new String[dataLength];

            for (int i = 0; i < dataLength; i++) {
                if (null != list && i < list.size()) {
                    Map map = list.get(i);
                    data[i] = map.get("goods_num").toString();
                    localName[i] = map.get("member_name").toString();
                } else {
                    data[i] = "0";
                    localName[i] = "无";
                }
                xAxis[i] = i + 1 + "";
            }

            ChartSeries chartSeries = new ChartSeries("会员下单商品数", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }


    @Override
    public Page getMemberGoodsNumPage(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {
            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);

            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT sum(o.`goods_num`) AS goods_num,m.`member_name` AS member_name "
                    + " FROM es_sss_order_data o INNER JOIN es_sss_member_register_data m ON o.`buyer_id` = m.`member_id` "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ?  AND (o.order_status = '"
                    + OrderStatusEnum.COMPLETE.name() + "'	OR o.pay_status = '" + PayStatusEnum.PAY_YES.name() + "') ");
            params.add(timestamp[0]);
            params.add(timestamp[1]);

            sql.append(" GROUP BY member_name ORDER BY goods_num DESC ");

            List<Map> list = this.daoSupport.queryForListPage(sql.toString(), 1, 10, params.toArray());

            return new Page(1, (long) list.size(), 10, list);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public SimpleChart getMemberMoney(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {
            Page page = this.getMemberMoneyPage(searchCriteria);
            List<Map<String, Object>> list = page.getData();
            int dataLength = 10;
            String[] xAxis = new String[dataLength], localName = new String[dataLength], data = new String[dataLength];

            for (int i = 0; i < dataLength; i++) {
                if (null != list && i < list.size()) {
                    Map map = list.get(i);
                    data[i] = map.get("total_money").toString();
                    localName[i] = map.get("member_name").toString();
                } else {
                    data[i] = "0";
                    localName[i] = "无";
                }
                xAxis[i] = i + 1 + "";
            }

            ChartSeries chartSeries = new ChartSeries("会员下单金额", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (StatisticsException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }
    }

    @Override
    public Page getMemberMoneyPage(SearchCriteria searchCriteria) {
        searchCriteria = new SearchCriteria(searchCriteria);
        try {

            long[] timestamp = DataDisplayUtil.getStartTimeAndEndTime(searchCriteria);

            /*
             * 参数
             */
            List<Object> params = new ArrayList<>();

            /*
             * 查询sql
             */
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT SUM(o.`order_price`) AS total_money,m.`member_name` AS member_name "
                    + " FROM es_sss_order_data o INNER JOIN es_sss_member_register_data m ON o.`buyer_id` = m.`member_id` "
                    + " WHERE o.`create_time` >= ? AND o.`create_time` <= ?  AND (o.order_status = '"
                    + OrderStatusEnum.COMPLETE.name() + "'	OR o.pay_status = '" + PayStatusEnum.PAY_YES.name() + "') ");
            params.add(timestamp[0]);
            params.add(timestamp[1]);
            sql.append(" GROUP BY member_name ORDER BY total_money DESC ");

            List<Map> list = this.daoSupport.queryForListPage(sql.toString(), 1, 10, params.toArray());

            return new Page(1, (long) list.size(), 10, list);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), StatisticsErrorCode.E810.des());
        }

    }
}
