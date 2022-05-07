/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.base.context;


/**
 * Region object
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/2
 */
public class Region {
    /**
     * cityid
     */
    private Integer cityId;
    /**
     * The town ofid
     */
    private Integer townId;
    /**
     * countyid
     */
    private Integer countyId;
    /**
     * provinceid
     */
    private Integer provinceId;
    /**
     * The name of the province
     */
    private String province;
    /**
     * The name of the county
     */
    private String county;
    /**
     * The city name
     */
    private String city;
    /**
     * Name of the town
     */
    private String town;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "Region{" +
                "cityId=" + cityId +
                ", townId=" + townId +
                ", countyId=" + countyId +
                ", provinceId=" + provinceId +
                ", province=" + province +
                ", county=" + county +
                ", city=" + city +
                ", town=" + town +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Region region = (Region) o;

        if (cityId != null ? !cityId.equals(region.cityId) : region.cityId != null) {
            return false;
        }
        if (townId != null ? !townId.equals(region.townId) : region.townId != null) {
            return false;
        }
        if (countyId != null ? !countyId.equals(region.countyId) : region.countyId != null) {
            return false;
        }
        if (provinceId != null ? !provinceId.equals(region.provinceId) : region.provinceId != null) {
            return false;
        }
        if (province != null ? !province.equals(region.province) : region.province != null) {
            return false;
        }
        if (county != null ? !county.equals(region.county) : region.county != null) {
            return false;
        }
        if (city != null ? !city.equals(region.city) : region.city != null) {
            return false;
        }
        return town != null ? town.equals(region.town) : region.town == null;
    }

    @Override
    public int hashCode() {
        int result = cityId != null ? cityId.hashCode() : 0;
        result = 31 * result + (townId != null ? townId.hashCode() : 0);
        result = 31 * result + (countyId != null ? countyId.hashCode() : 0);
        result = 31 * result + (provinceId != null ? provinceId.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (county != null ? county.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        return result;
    }
}
