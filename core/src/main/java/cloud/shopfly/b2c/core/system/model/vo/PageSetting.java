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

import javax.validation.constraints.NotEmpty;

/**
 * 静态页设置
 *
 * @author chopper
 * @version v7.0
 * @date 18/5/30 下午3:08
 * @since v7.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageSetting {
    /**
     * 静态也地址
     */
    @ApiModelProperty(name = "static_page_address", value = "站点地址")
    @NotEmpty
    private String staticPageAddress;
    /**
     * wap站点静态页地址
     */
    @ApiModelProperty(name = "static_page_wap_address", value = "wap站点地址")
    @NotEmpty
    private String staticPageWapAddress;

    public String getStaticPageAddress() {
        return staticPageAddress;
    }

    public void setStaticPageAddress(String staticPageAddress) {
        this.staticPageAddress = staticPageAddress;
    }


    public String getStaticPageWapAddress() {
        return staticPageWapAddress;
    }

    public void setStaticPageWapAddress(String staticPageWapAddress) {
        this.staticPageWapAddress = staticPageWapAddress;
    }

    @Override
    public String toString() {
        return "PageSetting{" +
                "staticPageAddress='" + staticPageAddress + '\'' +
                ", staticPageWapAddress='" + staticPageWapAddress + '\'' +
                '}';
    }
}
