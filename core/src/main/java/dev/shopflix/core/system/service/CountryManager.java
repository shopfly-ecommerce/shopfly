package dev.shopflix.core.system.service;

import dev.shopflix.core.system.model.dos.Country;

import java.util.List;

public interface CountryManager {

    /**
     * read all country
     * @return all country order by name asc
     */
    List<Country> allCountry();


}
