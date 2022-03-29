/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.statistics.service.impl;

import com.enation.app.javashop.core.base.SearchCriteria;
import com.enation.app.javashop.core.statistics.StatisticsErrorCode;
import com.enation.app.javashop.core.statistics.StatisticsException;
import com.enation.app.javashop.core.statistics.model.vo.ChartSeries;
import com.enation.app.javashop.core.statistics.model.vo.SimpleChart;
import com.enation.app.javashop.core.statistics.service.GoodsFrontStatisticsManager;
import com.enation.app.javashop.core.statistics.util.StatisticsUtil;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 商家中心，商品分析统计 接口实现体
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0 2018/3/28 上午9:49
 */
@Service
public class GoodsFrontStatisticsManagerImpl implements GoodsFrontStatisticsManager {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    @Qualifier("sssDaoSupport")
    private DaoSupport daoSupport;

    /**
     * 获取商品详情
     *
     * @param pageNo    当前页码
     * @param pageSize  每页数据量
     * @param catId     商品分类id
     * @param goodsName 商品名称
     * @return Page 分页数据
     */
    @Override
    public Page getGoodsDetail(Integer pageNo, Integer pageSize, Integer catId, String goodsName) {

        try {
            // 参数列表
            ArrayList<Object> paramList = new ArrayList<>(16);
            // 获得今天23:59:59的时间戳
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DATE);
            long endTime = DateUtil.toDate(year + "-" + month + "-" + day + " 23:59:59", "yyyy-MM-dd HH:mm:ss")
                    .getTime() / 1000;

            // 获取30天前23:59:59的时间戳
            long startTime = endTime - 2592000;

            // 时间戳转换为字符串
            String startDate = String.valueOf(startTime);
            String endDate = String.valueOf(endTime);
            // 加入参数
            paramList.add(startDate);
            paramList.add(endDate);

            // 如果商品名不为空，则模糊查询商品名
            String nameWhere = "";
            if (goodsName != null && !"".equals(goodsName)) {
                nameWhere = " AND oi.goods_name LIKE ? ";
                paramList.add("%" + goodsName + "%");
            }

            // 商品分类路径
            String catPath;

            // 如果分类id为0，则查询全部分类，不为0，则于数据库中查询路径，如果没有此分类，或有重复数据，则返回空数据
            if (catId == 0) {
                catPath = "0";
            } else {
                List<Map<String,Object>> categoryPath = this.daoSupport.queryForList(
                        "SELECT DISTINCT gd.category_path FROM es_sss_goods_data gd WHERE gd.category_id = " + catId);
                if (null == categoryPath || 0 == categoryPath.size() || 1 < categoryPath.size()) {
                    return new Page(pageNo, 0L, pageSize, new ArrayList());
                }
                Map<String, Object> map = categoryPath.get(0);
                catPath = map.get("category_path").toString();
            }

            // 拼接Sql 店铺id，已完成的订单，时间范围，商品名称，分类路径，按商品Id分组
            String sql = "SELECT goods_id,goods_name , SUM(goods_num) AS numbers,ROUND(price,2) AS price,ROUND(price * SUM(goods_num),2) AS total_price" +
                    " FROM es_sss_order_goods_data oi WHERE oi.order_sn IN (SELECT sn FROM es_sss_order_data o WHERE o.create_time >= ? AND o.create_time <= ? ) " + nameWhere + " AND oi.category_path like '%" + catPath + "%' "
                    + "  GROUP BY goods_id ,goods_name,price ";

            return StatisticsUtil.getDataPage(this.daoSupport, year, sql, pageNo, pageSize, paramList.toArray());
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }
    }


    /**
     * 价格区间
     *
     * @param sections       区间List 格式：0 100 200
     * @param searchCriteria 时间，分类id，店铺id
     * @return SimpleChart 简单图表数据
     */
    @Override
    public SimpleChart getGoodsPriceSales(List<Integer> sections, SearchCriteria searchCriteria) {

        // 验证参数
        SearchCriteria.checkDataParams(searchCriteria, true, false);

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();
        Integer catId = searchCriteria.getCategoryId();

        try {
            // 获取开始时间和结束时间
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);

            // 参数集合
            ArrayList<Object> paramList = new ArrayList<>();

            // 时间条件字符串
            String dateWhere = "";

            dateWhere += " o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);

            // 商品分类
            String catPath;
            if (null == catId || catId == 0) {
                catPath = "0";
            } else {
                catPath = this.daoSupport.queryForString(
                        "SELECT DISTINCT(gd.category_path) FROM es_sss_goods_data gd WHERE gd.category_id = " + catId);
            }

            // 拼接CASE语句
            String caseStatement = getGoodsPriceSqlCaseStatement(sections);
            String sql = "SELECT SUM(oi.goods_num) AS goods_num, " + caseStatement
                    + "AS elt_data FROM es_sss_order_goods_data oi left join es_sss_order_data o on oi.order_sn=o.sn WHERE "
                    + dateWhere + " AND oi.category_path like '%" + catPath + "%'" + " GROUP BY oi.price";

            String mainSql = "SELECT SUM(t1.goods_num) goods_num, t1.elt_data FROM(" + sql
                    + ") t1 GROUP BY t1.elt_data ORDER BY t1.elt_data";

            List<Map<String, Object>> data = this.daoSupport.queryForList(mainSql, paramList.toArray());

            // 将数据重新排序
            data = StatisticsUtil.getInstance().fitOrderPriceData(data, sections);

            // 图表数据
            String[] chartData = new String[data.size()];

            // 数据名称，就是区间
            String[] localName = new String[data.size()];

            int i = 0;
            for (Map map : data) {
                chartData[i] = null == map.get("goods_num") ? "0" : map.get("goods_num").toString();
                localName[i] = map.get("elt_data").toString();
                i++;
            }

            ChartSeries chartSeries = new ChartSeries("价格销量分析", chartData, localName);

            return new SimpleChart(chartSeries, localName, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }
    }

    /**
     * 商品获取下单金额前30
     *
     * @param topNum         top数
     * @param searchCriteria 时间相关参数
     * @return Page 分页对象
     */
    @Override
    public Page getGoodsOrderPriceTopPage(int topNum, SearchCriteria searchCriteria) {

        SearchCriteria.checkDataParams(searchCriteria, true, false);
        try {

            // 参数集合
            ArrayList<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.name());

            // 如果排名没有值，默认为30
            if (topNum == 0) {
                topNum = 30;
            }
            // 获取时间戳
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(searchCriteria.getCycleType(),
                    searchCriteria.getYear(), searchCriteria.getMonth());

            // 时间条件
            String dateWhere = "";
            dateWhere += "AND o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);
            // 拼接Sql 店铺id，已完成订单，时间范围，按商品id分组，按总价格排序
            String sql = "FROM es_sss_order_goods_data WHERE order_sn IN (SELECT sn FROM es_sss_order_data o WHERE "
                    + " order_status = ? " + dateWhere + ") GROUP BY goods_id  ";

            String selectPage = "SELECT goods_name,goods_id,price,SUM(goods_num) * price AS sum "
                    + sql;

            String selectCount = "SELECT count(*) from (" + selectPage + ") t0";

            selectPage += " ORDER BY SUM(goods_num) * price DESC";

            return StatisticsUtil.getDataPage(this.daoSupport, searchCriteria.getYear(), selectPage, selectCount, 1, topNum, paramList.toArray());
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }
    }

    /**
     * 下单商品数量前30，分页数据
     *
     * @param topNum         名次 默认为30
     * @param searchCriteria 时间相关参数
     * @return Page 分页对象
     */
    @Override
    public Page getGoodsNumTopPage(int topNum, SearchCriteria searchCriteria) {

        SearchCriteria.checkDataParams(searchCriteria, true, false);

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();

        try {

            // 参数集合
            ArrayList<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.name());

            // 如果排名没有值
            if (topNum == 0) {
                topNum = 30;
            }

            // 时间条件
            String dateWhere = "";
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);

            // 如果包含开始时间条件
            dateWhere += "and o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);

            // 拼接Sql 店铺id，已完成订单，时间范围，按商品id和商品名称分组，按商品数量降序排序
            String sql = "FROM es_sss_order_goods_data WHERE order_sn IN (SELECT sn FROM es_sss_order_data o WHERE "
                    + " order_status = ? " + dateWhere + ") GROUP BY goods_id,goods_name";
            String selectPage = "SELECT goods_name,goods_id,SUM(goods_num) as all_num " + sql;

            String selectCount = "select count(*) from (" + selectPage + ") t0";
            selectPage += " ORDER BY SUM(goods_num) DESC";

            return StatisticsUtil.getDataPage(this.daoSupport, searchCriteria.getYear(), selectPage, selectCount, 1, topNum, paramList.toArray());
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }
    }

    /**
     * 获取商品下单金额前30，图表数据
     *
     * @param topNum         top数
     * @param searchCriteria 时间相关参数
     * @return SimpleChart 简单图表数据
     */
    @Override
    public SimpleChart getGoodsOrderPriceTop(Integer topNum, SearchCriteria searchCriteria) {

        SearchCriteria.checkDataParams(searchCriteria, true, false);
        try {
            // 获取当前登陆的会员店铺id
            ArrayList<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.name());

            // 如果排名没有值
            if (topNum == 0) {
                topNum = 30;
            }
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(searchCriteria.getCycleType(),
                    searchCriteria.getYear(), searchCriteria.getMonth());

            // 时间条件
            String dateWhere = "";
            dateWhere += "AND o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);
            // 拼接Sql 店铺id，已完成订单，时间范围，按商品id分组，按总金额排序
            String sql = "FROM es_sss_order_goods_data WHERE order_sn IN (SELECT sn FROM es_sss_order_data o WHERE "
                    + " order_status = ? " + dateWhere + "" + ") GROUP BY goods_id  ";

            String selectPage = "SELECT goods_name,goods_id,price,SUM(goods_num) * price AS sum "
                    + sql;

            selectPage += " ORDER BY SUM(goods_num) * price DESC LIMIT " + topNum;

            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), selectPage, paramList.toArray());

            // 图表数据，下单金额
            String[] data = new String[list.size()];

            // 数据名称，即商品名称
            String[] localName = new String[list.size()];

            // x轴刻度
            String[] xAxis = new String[list.size()];

            // 如果数据大于0，则遍历
            int dataNum = list.size();
            if (list.size() > 0) {
                for (int i = 0; i < dataNum; i++) {
                    Map map = list.get(i);
                    data[i] = map.get("sum").toString();
                    localName[i] = map.get("goods_name").toString();
                    xAxis[i] = i + 1 + "";
                }
            }

            ChartSeries chartSeries = new ChartSeries("总金额", data, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }

    }

    /**
     * 获取商品购买数量前30
     *
     * @param topNum         top数
     * @param searchCriteria 时间相关参数
     * @return SimpleChart 简单图表数据
     */
    @Override
    public SimpleChart getGoodsNumTop(Integer topNum, SearchCriteria searchCriteria) {

        SearchCriteria.checkDataParams(searchCriteria, true, false);

        String cycleType = searchCriteria.getCycleType();
        Integer year = searchCriteria.getYear();
        Integer month = searchCriteria.getMonth();

        try {
            // 获取当前登陆的会员店铺id
            ArrayList<Object> paramList = new ArrayList<>();
            paramList.add(OrderStatusEnum.COMPLETE.name());

            // 如果排名没有值
            if (topNum == 0) {
                topNum = 30;
            }

            // 时间条件
            String dateWhere = "";
            long[] times = StatisticsUtil.getInstance().getStartTimeAndEndTime(cycleType, year, month);

            // 如果包含开始时间条件
            dateWhere += "AND o.create_time >= ? ";
            paramList.add(times[0]);
            dateWhere += " AND o.create_time <= ? ";
            paramList.add(times[1]);

            // 拼接Sql
            String sql = "FROM es_sss_order_goods_data WHERE order_sn IN (SELECT sn FROM es_sss_order_data o WHERE "
                    + " order_status = ? " + dateWhere + ") GROUP BY goods_id,goods_name";
            String selectCharts = "SELECT goods_name,goods_id,SUM(goods_num) as all_num " + sql;

            selectCharts += " ORDER BY SUM(goods_num) DESC LIMIT " + topNum;

            //获取数据
            List<Map<String, Object>> list = StatisticsUtil.getDataList(this.daoSupport, searchCriteria.getYear(), selectCharts, paramList.toArray());

            // 图表数据，即商品被购买的数量
            String[] chartData = new String[list.size()];

            // 数据名称，即商品名称
            String[] localName = new String[list.size()];

            //x轴刻度
            String[] xAxis = new String[list.size()];

            // 如果list有数据，则遍历
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = list.get(i);
                    chartData[i] = map.get("all_num").toString();
                    localName[i] = map.get("goods_name").toString();
                    xAxis[i] = i + 1 + "";
                }
            }

            ChartSeries chartSeries = new ChartSeries("下单量", chartData, localName);

            return new SimpleChart(chartSeries, xAxis, new String[0]);
        } catch (Exception e) {
            logger.error(e);
            throw new StatisticsException(StatisticsErrorCode.E810.code(), "业务异常");
        }
    }

    /**
     * 生成价格销量统计的SQL CASE语句
     *
     * @param ranges 整数集合
     * @return SQL CASE Statement
     */
    private static String getGoodsPriceSqlCaseStatement(List<Integer> ranges) {
        if (null == ranges || ranges.size() == 0) {
            return "0";
        }
        // 由大到小排序
        StatisticsUtil.sortRanges(ranges);

        StringBuilder sb = new StringBuilder("(case ");
        sb.append("when oi.price > ").append(ranges.get(0)).append(" then '").append(ranges.get(0)).append("+' ");
        for (int i = 0; i < ranges.size(); ) {
            Integer num = ranges.get(i);
            Integer nextNum;
            if (i < ranges.size() - 1) {
                nextNum = ranges.get(i + 1);
                sb.append("when oi.price >= ").append(nextNum).append(" and oi.price <= ").append(num).append(" then '")
                        .append(nextNum).append("~").append(num).append("' ");
                i+=2;
            }
        }
        sb.append("else '0' end ) ");
        return sb.toString();
    }

}