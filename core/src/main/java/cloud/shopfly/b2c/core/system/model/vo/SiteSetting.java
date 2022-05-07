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
package cloud.shopfly.b2c.core.system.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * General
 *
 * @author zh
 * @version v7.0
 * @date 18/5/30 In the afternoon3:08
 * @since v7.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SiteSetting {
    /**
     * The site name
     */
    @ApiModelProperty(name = "site_name", value = "The site name")
    private String siteName;
    /**
     * Site title
     */
    @ApiModelProperty(name = "title", value = "Site title")
    private String title;
    /**
     * Site keywords
     */
    @ApiModelProperty(name = "keywords", value = "Site keywords")
    private String keywords;
    /**
     * Description
     */
    @ApiModelProperty(name = "descript", value = "Description")
    private String descript;
    /**
     * If the website is open,0Open,-1close
     */
    @ApiModelProperty(name = "siteon", value = "If the website is open,1Open,0close")
    @Min(message = "The value must be a number and,1To open,0To shut down", value = 0)
    @Max(message = "The value must be a number and,1To open,0To shut down", value = 1)
    @NotNull(message = "Whether the site is closed cannot be empty")
    private Integer siteon;
    /**
     * Close the reason
     */
    @ApiModelProperty(name = "close_reson", value = "Close the reason")
    private String closeReson;
    /**
     * Sitelogo
     */
    @ApiModelProperty(name = "logo", value = "Sitelogo")
    private String logo;

    /**
     * Add the secret key
     */
    @ApiModelProperty(name = "global_auth_key", value = "Add the secret key")
    private String globalAuthKey;
    /**
     * Default image
     */
    @ApiModelProperty(name = "default_img", value = "Default image")
    private String defaultImg;
    /**
     * Test mode
     */
    @ApiModelProperty(name = "test_mode", value = "Test mode,1To open,0To shut down")
    @Min(message = "The value must be a number and,1To open,0To shut down", value = 0)
    @Max(message = "The value must be a number and,1To open,0To shut down", value = 1)
    @NotNull(message = "Test mode Cannot be null")
    public Integer testMode;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Integer getSiteon() {
        return siteon;
    }

    public void setSiteon(Integer siteon) {
        this.siteon = siteon;
    }

    public String getCloseReson() {
        return closeReson;
    }

    public void setCloseReson(String closeReson) {
        this.closeReson = closeReson;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGlobalAuthKey() {
        return globalAuthKey;
    }

    public void setGlobalAuthKey(String globalAuthKey) {
        this.globalAuthKey = globalAuthKey;
    }

    public String getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(String defaultImg) {
        this.defaultImg = defaultImg;
    }

    public Integer getTestMode() {
        return testMode;
    }

    public void setTestMode(Integer testMode) {
        this.testMode = testMode;
    }

    @Override
    public String toString() {
        return "SiteSetting{" +
                "siteName='" + siteName + '\'' +
                ", title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", descript='" + descript + '\'' +
                ", siteon=" + siteon +
                ", closeReson='" + closeReson + '\'' +
                ", logo='" + logo + '\'' +
                ", globalAuthKey='" + globalAuthKey + '\'' +
                ", defaultImg='" + defaultImg + '\'' +
                ", testMode=" + testMode +
                '}';
    }
}
