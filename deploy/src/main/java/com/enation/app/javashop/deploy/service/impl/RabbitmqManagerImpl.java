/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service.impl;

import com.enation.app.javashop.deploy.model.Rabbitmq;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.deploy.model.Rabbitmq;
import com.enation.app.javashop.deploy.service.RabbitmqManager;

/**
 * rabbitmq业务类
 *
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2018-05-04 20:31:41
 */
@Service
public class RabbitmqManagerImpl implements RabbitmqManager {

    @Autowired
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_rabbitmq  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, Rabbitmq.class);

        return webPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Rabbitmq add(Rabbitmq rabbitmq) {
        this.daoSupport.insert(rabbitmq);

        return rabbitmq;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Rabbitmq edit(Rabbitmq rabbitmq, Integer id) {
        this.daoSupport.update(rabbitmq, id);
        return rabbitmq;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(Rabbitmq.class, id);
    }

    @Override
    public Rabbitmq getModel(Integer id) {

        return this.daoSupport.queryForObject(Rabbitmq.class, id);
    }

    @Override
    public Rabbitmq getByDeployId(Integer deployId) {

        String sql = "select * from es_rabbitmq  where deploy_id=?";

        return daoSupport.queryForObject(sql, Rabbitmq.class, deployId);
    }


    @Override
    public void initRabbitMq(Integer delpoyId) {
        Rabbitmq rabbitmq = new Rabbitmq();
        rabbitmq.setHost("127.0.0.1");
        rabbitmq.setPort("5672");
        rabbitmq.setUsername("guest");
        rabbitmq.setPassword("guest");
        rabbitmq.setHost("/");
        rabbitmq.setDeployId(delpoyId);
        this.add(rabbitmq);
    }

    @Override
    public boolean testConnection(Rabbitmq rabbitmq) {
        try {
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmq.getHost(), Integer.valueOf(rabbitmq.getPort()));
            connectionFactory.setUsername(rabbitmq.getUsername());
            connectionFactory.setPassword(rabbitmq.getPassword());
            connectionFactory.setConnectionTimeout(30000);
            connectionFactory.createConnection();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
