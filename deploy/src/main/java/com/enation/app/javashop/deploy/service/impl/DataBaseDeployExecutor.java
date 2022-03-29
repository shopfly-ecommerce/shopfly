/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service.impl;

import com.enation.app.javashop.deploy.enums.ServiceType;
import com.enation.app.javashop.deploy.model.Database;
import com.enation.app.javashop.deploy.model.Deploy;
import com.enation.app.javashop.deploy.service.DatabaseManager;
import com.enation.app.javashop.deploy.service.DeployExecutor;
import com.enation.app.javashop.deploy.service.DeployManager;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.impl.DaoSupportImpl;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.util.FileUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.*;
import java.util.List;

/**
 * Created by kingapex on 2018/5/14.
 * 数据库部署执行器实现
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/14
 */
@Service
public class DataBaseDeployExecutor implements DeployExecutor {


    @Autowired
    private DatabaseManager databaseManager;

    @Autowired
    private DeployManager deployManager;


    @Override
    public void deploy(Integer deployId) {

        Deploy deploy = this.deployManager.getModel(deployId);

        //类型名即是目录名
        String deployType = deploy.getDeployType();

        List<Database> dbList = databaseManager.list(deployId);
        for (Database database : dbList) {
            importSql(deployEnum(deployType), database);
        }
    }

    @Override
    public String getType() {
        return "database";
    }

    private String deployEnum(String deployType) {
        if("无示例数据".equals(deployType)){
            return "basic";
        }else if ("带示例数据".equals(deployType)) {
            return "standard";
        } else{
            return deployType;
        }
    }

    public void importRegionSQl(int deployId) {
        Database database = getSystemDataBase(deployId);
        String relativePath = "scheme/regions.sql";
        JdbcTemplate jdbcTemplate = createJdbcTemplate(database);
        executeSql(relativePath,jdbcTemplate);
    }


    private Database getSystemDataBase(int deployId) {
        List<Database> dbList = databaseManager.list(deployId);
        for (Database database : dbList) {
            String serviceType = database.getServiceType();

            //找到系统服务
            if (ServiceType.SYSTEM.name().equals(serviceType)) {
                return database;
            }
        }
        throw  new RuntimeException("未找到系统库");
    }

    /**
     * 导入一个数据库的sql文件
     * 这个sql文件会在部署类型下
     * 以database的ServiceType 文件名
     *
     * @param deployType 部署类型
     * @param database   数据库
     */
    public void importSql(String deployType, Database database) {

        String serviceType = database.getServiceType().toLowerCase();
        String relativePath = "scheme/" + deployType + "/" + serviceType + ".sql";
        JdbcTemplate jdbcTemplate = createJdbcTemplate(database);
        executeSql(relativePath,jdbcTemplate);

    }

    /**
     * 执行sql脚本
     * @param relativePath sql的相对位置(相对resource）
     */
    private void executeSql(String relativePath, JdbcTemplate jdbcTemplate ) {

        //标识是否找到要执行的sql文件
        boolean exist = false;

        String sqlContent = null;

        InputStream inputStream = null;


        //先尝试在fat jar的home目录查找
        //执行方案的目录为 fat jar root path  + scheme
        String homePath = getHomePath();
        String sqlPath = homePath + "/" + relativePath;
        File file = new File(sqlPath);
        try {
            inputStream = new FileInputStream(file);
            exist = true;
            System.out.println(" 在 " + file + "中找到执行sql");
        } catch (FileNotFoundException e) {
            exist = false;
        }

        //如果不存在，尝试在resources下查找
        if (!exist) {

            try {
                Resource resource = new ClassPathResource(relativePath);
                inputStream = resource.getInputStream();
                System.out.println(" 在 resources/" + relativePath + "中找到执行sql");

                exist = true;
            } catch (IOException e) {
                exist = false;
            }
        }

        //如果最终找不到抛出异常给上层
        if (!exist) {
            throw new ServiceException("000", "sql文件【" + relativePath + "】找不到");
        }

        sqlContent = FileUtil.readStreamToString(inputStream);



        String[] sqlArray = sqlContent.split(";\n");


        for (String sql : sqlArray) {
            //跳过为空的sql
            if (StringUtil.isEmpty(sql)) {
                continue;
            }

            jdbcTemplate.execute(sql);
        }

    }

    /**
     * 获取fat jar所在目录
     *
     * @return
     */
    private String getHomePath() {

        ApplicationHome home = new ApplicationHome(this.getClass());

        File jarDir = home.getDir();
        return jarDir.getPath();

    }

    /**
     * 根据数据库配置创建 JdbcTemplate
     *
     * @param database 据库配置
     * @return daoSupport
     */
    private JdbcTemplate createJdbcTemplate(Database database) {

        String url = "jdbc:mysql://" + database.getDbIp() + ":" + database.getDbPort() + "/" + database.getDbName() + "?useUnicode=true&characterEncoding=utf8&";
        DataSource dataSource = DataSourceBuilder.create()
                .type(com.alibaba.druid.pool.DruidDataSource.class)
                .url(url)
                .password(database.getDbPassword())
                .username(database.getDbUsername()).build();


        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);


        return jdbcTemplate;
    }

    public static void main(String[] args) {

        Database database = new Database();
        database.setDbIp("127.0.0.1");
        database.setDbUsername("root");
        database.setDbPort("3306");
        database.setDbName("temp");
        database.setDbPassword("kingapex");


        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + database.getDbIp() + ":" + database.getDbPort() + "/" + database.getDbName() + "?useUnicode=true&characterEncoding=utf8&";
        DataSource dataSource = DataSourceBuilder.create()
                .type(com.alibaba.druid.pool.DruidDataSource.class)
                .url(url)
                .password(database.getDbPassword())
                .username(database.getDbUsername()).build();


        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        DaoSupport daoSupport = new DaoSupportImpl(jdbcTemplate);
        String sqlFile = FileUtil.read("/Users/kingapex/Downloads/v7_goods.sql", "UTF-8");

        String[] sqlArray = sqlFile.split(";");


        for (String sql : sqlArray) {
            if (StringUtil.isEmpty(sql)) {
                continue;
            }

            jdbcTemplate.execute(sql);
        }


    }


}
