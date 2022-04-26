package dev.shopflix.core.system.model.dos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Table;
import io.swagger.annotations.ApiModel;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 国家
 * @author kingapex
 * @version 1.0
 * @data 2022/4/20 14:55
 **/
@Table(name = "es_states")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Country {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "has_state")
    private Integer hasState;

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

    public Integer getHasState() {
        return hasState;
    }

    public void setHasState(Integer hasState) {
        this.hasState = hasState;
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", hasState=" + hasState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        return new EqualsBuilder().append(code, country.code).append(name, country.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(code).append(name).toHashCode();
    }
}
