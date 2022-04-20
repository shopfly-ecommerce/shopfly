package dev.shopflix.core.system.service.impl;

import dev.shopflix.core.system.model.dos.Country;
import dev.shopflix.core.system.service.CountryManager;
import dev.shopflix.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Country Manager
 * @author kingapex
 * @version 1.0
 * @data 2022/4/20 15:08
 **/
@Service
public class CountryManagerImpl implements CountryManager {

    @Autowired
    private DaoSupport daoSupport;

    @Override
    public List<Country> allCountry() {
        List<Country> list = daoSupport.queryForList("select * from es_countries order by name asc ", Country.class);
        return list;
    }
}
