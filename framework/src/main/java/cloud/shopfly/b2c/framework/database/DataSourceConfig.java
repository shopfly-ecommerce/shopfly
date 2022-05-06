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
package cloud.shopfly.b2c.framework.database;

import cloud.shopfly.b2c.framework.database.impl.DaoSupportImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class DataSourceConfig {

    /*----------------------------------------------------------------------------*/
    /*                           DaoSupport配置                                    */
    /*----------------------------------------------------------------------------*/

    /**
     * 商品daoSupport
     * @param jdbcTemplate 商品jdbcTemplate
     * @return
     */
    @Bean
    public DaoSupport daoSupport( JdbcTemplate jdbcTemplate) {
        DaoSupport daosupport = new DaoSupportImpl(jdbcTemplate);
        return daosupport;
    }


    /*----------------------------------------------------------------------------*/
    /*                           JdbcTemplate 配置                                */
    /*----------------------------------------------------------------------------*/

    /**
     * 商品jdbcTemplate
     * @param dataSource 数据源
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
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
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
