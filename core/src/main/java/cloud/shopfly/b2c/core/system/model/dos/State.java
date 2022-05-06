package cloud.shopfly.b2c.core.system.model.dos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import io.swagger.annotations.ApiModel;

/**
 * State DO
 * @author kingapex
 * @version 1.0
 * @data 2022/4/20 15:02
 **/
@Table(name = "es_states")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class State {

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "State{" +
                "countryCode='" + countryCode + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
