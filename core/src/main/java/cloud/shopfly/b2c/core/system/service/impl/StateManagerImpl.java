package cloud.shopfly.b2c.core.system.service.impl;

import cloud.shopfly.b2c.core.system.model.dos.State;
import cloud.shopfly.b2c.core.system.service.StateManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * StateManager implement
 * @author kingapex
 * @version 1.0
 * @data 2022/4/20 15:26
 **/
@Service
public class StateManagerImpl implements StateManager {

    @Autowired
    private DaoSupport daoSupport;

    @Override
    public List<State> stateOfCountry(String code) {
        List<State> list = daoSupport.queryForList("select * from es_states where country_code=? order by name asc ",State.class,code);
        return list;
    }

}
