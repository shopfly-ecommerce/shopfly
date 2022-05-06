package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.State;

import java.util.List;

/**
 * State Manager
 * @author kingapex
 * @data 2022/4/20 15:24
 * @version 1.0
 **/
public interface StateManager {

    /**
     * Query the state of a country
     * @param code country code
     * @return state list
     */
    List<State> stateOfCountry(String code);
}
