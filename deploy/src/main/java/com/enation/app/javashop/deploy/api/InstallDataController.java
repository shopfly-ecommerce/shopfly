/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.api;

import com.enation.app.javashop.deploy.config.EsConfig;
import com.enation.app.javashop.deploy.config.MysqlConfig;
import com.enation.app.javashop.deploy.model.Database;
import com.enation.app.javashop.deploy.model.Elasticsearch;
import com.enation.app.javashop.deploy.service.DatabaseManager;
import com.enation.app.javashop.deploy.service.impl.DataBaseDeployExecutor;
import com.enation.app.javashop.deploy.service.impl.EsDeployExecutor;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配合devops工具使用的api
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2021/1/21
 */
@RestController
@RequestMapping("/data/installer")
public class InstallDataController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MysqlConfig mysqlConfig;

    @Autowired
    private DataBaseDeployExecutor dataBaseDeployExecutor;

    @GetMapping("/db")
    public String installDatabase() {
        try {

            String[] databaseNames = {"system"
                    ,"goods"
                    ,"member"
                    ,"trade"
                    ,"statistics"
                    ,"distribution"
                    ,"xxl_job"};

            String port = mysqlConfig.getPort() == null ? "3306" : "" + mysqlConfig.getPort();
            for (String dbName : databaseNames) {
                Database database  = new Database();
                database.setDbIp(mysqlConfig.getHost());
                database.setDbUsername(mysqlConfig.getUsername());
                database.setDbPassword(mysqlConfig.getPassword());
                database.setDbPort(port);
                database.setDbName(dbName);
                database.setServiceType(dbName);
                dataBaseDeployExecutor.importSql("standard", database);
            }

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @Autowired
    EsDeployExecutor esDeployExecutor;

    @Autowired
    EsConfig esConfig;

    @GetMapping("/es")
    public String installEs() {

        //elasticsearch连接有时出错，重试4次
        for (int i = 0; i <= 4; i++) {
            try {
                Elasticsearch elasticsearch = new Elasticsearch();
                elasticsearch.setIndexName(esConfig.getIndexName());
                elasticsearch.setClusterName(esConfig.getClusterName());
                elasticsearch.setClusterNodes(esConfig.getClusterNodes());

                esDeployExecutor.index(elasticsearch);
                return "ok";
            } catch (NoNodeAvailableException e) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }

        }

        return "error";
    }
}
