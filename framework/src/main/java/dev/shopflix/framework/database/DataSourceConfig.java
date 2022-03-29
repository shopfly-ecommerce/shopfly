/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.database;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import dev.shopflix.framework.database.impl.DaoSupportImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by kingapex on 2018/3/6.
 * 数据源配置
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/6
 */
@Configuration
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
public class DataSourceConfig {

    /*----------------------------------------------------------------------------*/
    /*                           DaoSupport配置                                    */
    /*----------------------------------------------------------------------------*/

    /**
     * 商品daoSupport
     * @param jdbcTemplate 商品jdbcTemplate
     * @return
     */
    @Bean(name = "goodsDaoSupport")
    @Primary
    public DaoSupport goodsDaoSupport(@Qualifier("goodsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        DaoSupport daosupport = new DaoSupportImpl(jdbcTemplate);
        return daosupport;
    }

    /**
     * 交易daoSupport
     * @param jdbcTemplate 交易jdbcTemplate
     * @return
     */
    @Bean(name = "tradeDaoSupport")
    public DaoSupport tradeDaoSupport(@Qualifier("tradeJdbcTemplate") JdbcTemplate jdbcTemplate) {
        DaoSupport daosupport = new DaoSupportImpl(jdbcTemplate);
        return daosupport;
    }



    /**
     * 会员daoSupport
     * @param jdbcTemplate 会员jdbcTemplate
     * @return
     */
    @Bean(name = "memberDaoSupport")
    public DaoSupport memberDaoSupport(@Qualifier("memberJdbcTemplate") JdbcTemplate jdbcTemplate) {
        DaoSupport daosupport = new DaoSupportImpl(jdbcTemplate);
        return daosupport;
    }

    /**
     * 系统daoSupport
     * @param jdbcTemplate 系统 jdbcTemplate
     * @return
     */
    @Bean(name = "systemDaoSupport")
    public DaoSupport systemDaoSupport(@Qualifier("systemJdbcTemplate") JdbcTemplate jdbcTemplate) {
        DaoSupport daosupport = new DaoSupportImpl(jdbcTemplate);
        return daosupport;
    }


    /**
     * 统计 daoSupport
     * @param jdbcTemplate 统计jdbcTemplate
     * @return
     */
    @Bean(name = "sssDaoSupport")
    public DaoSupport sssDaoSupport(@Qualifier("sssJdbcTemplate") JdbcTemplate jdbcTemplate) {
        DaoSupport daosupport = new DaoSupportImpl(jdbcTemplate);
        return daosupport;
    }


    /**
     * 分销 daoSupport
     * @param jdbcTemplate 分销jdbcTemplate
     * @return
     */
    @Bean(name = "distributionDaoSupport")
    public DaoSupport distributionDaoSupport(@Qualifier("distributionJdbcTemplate") JdbcTemplate jdbcTemplate) {
        DaoSupport daosupport = new DaoSupportImpl(jdbcTemplate);
        return daosupport;
    }



    /*----------------------------------------------------------------------------*/
    /*                           JdbcTemplate 配置                                */
    /*----------------------------------------------------------------------------*/

    /**
     * 商品jdbcTemplate
     * @param dataSource 商品数据源
     * @return
     */
    @Bean(name = "goodsJdbcTemplate")
    @Primary
    public JdbcTemplate goodsJdbcTemplate(
            @Qualifier("goodsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    /**
     * 交易jdbcTemplate
     * @param dataSource 交易数据源
     * @return
     */
    @Bean(name = "tradeJdbcTemplate")
    public JdbcTemplate tradeJdbcTemplate(
            @Qualifier("tradeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    /**
     * 会员jdbcTemplate
     * @param dataSource 会员数据源
     * @return
     */
    @Bean(name = "memberJdbcTemplate")
    public JdbcTemplate memberJdbcTemplate(
            @Qualifier("memberDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    /**
     * 系统jdbcTemplate
     * @param dataSource 系统数据源
     * @return
     */
    @Bean(name = "systemJdbcTemplate")
    public JdbcTemplate systemJdbcTemplate(
            @Qualifier("systemDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 统计jdbcTemplate
     * @param dataSource 统计数据源
     * @return
     */
    @Bean(name = "sssJdbcTemplate")
    public JdbcTemplate sssJdbcTemplate(
            @Qualifier("sssDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean(name = "distributionJdbcTemplate")
    public JdbcTemplate distributionJdbcTemplate(
            @Qualifier("distributionDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }




    /*----------------------------------------------------------------------------*/
    /*                           数据源配置                                        */
    /*----------------------------------------------------------------------------*/


    /**
     * 商品数据源
     * @return
     */
    @Bean(name = "goodsDataSource")
    @Qualifier("goodsDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.druid.goods")
    public DataSource goodsDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 交易数据源
     * @return
     */
    @Bean(name = "tradeDataSource")
    @Qualifier("tradeDataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid.trade")
    public DataSource tradeDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 会员数据源
     * @return
     */
    @Bean(name = "memberDataSource")
    @Qualifier("memberDataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid.member")
    public DataSource memberDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 系统数据源
     * @return
     */
    @Bean(name = "systemDataSource")
    @Qualifier("systemDataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid.system")
    public DataSource systemDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 统计数据源
     * @return
     */
    @Bean(name = "sssDataSource")
    @Qualifier("sssDataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid.sss")
    public DataSource sssDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    /**
     * 分销数据源
     * @return
     */
    @Bean(name = "distributionDataSource")
    @Qualifier("distributionDataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid.distribution")
    public DataSource distributionDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /*----------------------------------------------------------------------------*/
    /*                           事务配置                                         */
    /*----------------------------------------------------------------------------*/

    /**
     * 会员事务
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager memberTransactionManager(@Qualifier("memberDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 商品事务
     * @param dataSource
     * @return
     */
    @Bean
    @Primary
    public PlatformTransactionManager goodsTransactionManager(@Qualifier("goodsDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 交易事务
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager tradeTransactionManager(@Qualifier("tradeDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 系统事务
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager systemTransactionManager(@Qualifier("systemDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 统计事务
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager sssTransactionManager(@Qualifier("sssDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     * 分销事务
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager distributionTransactionManager(@Qualifier("distributionDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
