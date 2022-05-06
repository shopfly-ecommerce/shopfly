package cloud.shopfly.b2c.api.base;

import cloud.shopfly.b2c.core.system.model.dos.Country;
import cloud.shopfly.b2c.core.system.model.dos.State;
import cloud.shopfly.b2c.core.system.service.CountryManager;
import cloud.shopfly.b2c.core.system.service.StateManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * country base controller
 * @author kingapex
 * @version 1.0
 * @data 2022/4/20 16:22
 **/
@RestController
@RequestMapping("/countries")
@Api(description = "国家、洲相关API")
public class CountryBaseController {

    @Autowired
    private CountryManager countryManager;

    @Autowired
    private StateManager stateManager;

    @ApiOperation("获取所有国家")
    @GetMapping()
    public List<Country> all() {

        return countryManager.allCountry();
    }

    @ApiOperation("根据code获取下级地区")
    @GetMapping("/{code}/states")
    public List<State> state(@PathVariable String code) {

        return stateManager.stateOfCountry(code);
    }

}
