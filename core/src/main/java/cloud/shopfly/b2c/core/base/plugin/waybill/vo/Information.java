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
package cloud.shopfly.b2c.core.base.plugin.waybill.vo;

/**
 * 电子面单收发件人的信息实体
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017年8月12日 下午6:39:22
 */
public class Information {
    /**
     * 收发件人公司
     */
    private String company;
    /**
     * 收发件人
     */
    private String name;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String tel;
    /**
     * 收发件省（如广东省，不要缺少“省”）
     */
    private String provinceName;
    /**
     * 收发件市（如深圳市，不要缺少“市”
     */
    private String cityName;
    /**
     * 收发件区（如福田区，不要缺少“区”或“县”）
     */
    private String expAreaName;
    /**
     * 收发件详细地址
     */
    private String address;
    /**
     * 收发件人邮编
     */
    private String postCode;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getExpAreaName() {
        return expAreaName;
    }

    public void setExpAreaName(String expAreaName) {
        this.expAreaName = expAreaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "Information{" +
                "company='" + company + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", tel='" + tel + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", expAreaName='" + expAreaName + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
