package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.Country;

import java.util.List;

public interface CountryManager {

    /**
     * read all country
     * @return all country order by name asc
     */
    List<Country> allCountry();


}
