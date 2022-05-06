/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 站点设置
 *
 * @author zh
 * @version v7.0
 * @date 18/5/30 下午3:08
 * @since v7.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SiteSetting {
    /**
     * 站点名称
     */
    @ApiModelProperty(name = "site_name", value = "站点名称")
    private String siteName;
    /**
     * 网站标题
     */
    @ApiModelProperty(name = "title", value = "网站标题")
    private String title;
    /**
     * 网站关键字
     */
    @ApiModelProperty(name = "keywords", value = "网站关键字")
    private String keywords;
    /**
     * 网站描述
     */
    @ApiModelProperty(name = "descript", value = "网站描述")
    private String descript;
    /**
     * 网站是否开启，0开启，-1关闭
     */
    @ApiModelProperty(name = "siteon", value = "网站是否开启，1开启，0关闭")
    @Min(message = "必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "网站是否关闭不能为空")
    private Integer siteon;
    /**
     * 关闭原因
     */
    @ApiModelProperty(name = "close_reson", value = "关闭原因")
    private String closeReson;
    /**
     * 网站logo
     */
    @ApiModelProperty(name = "logo", value = "网站logo")
    private String logo;

    /**
     * 加密秘钥
     */
    @ApiModelProperty(name = "global_auth_key", value = "加密秘钥")
    private String globalAuthKey;
    /**
     * 默认图片
     */
    @ApiModelProperty(name = "default_img", value = "默认图片")
    private String defaultImg;
    /**
     * 测试模式
     */
    @ApiModelProperty(name = "test_mode", value = "测试模式,1为开启,0为关闭")
    @Min(message = "必须为数字且,1为开启,0为关闭", value = 0)
    @Max(message = "必须为数字且,1为开启,0为关闭", value = 1)
    @NotNull(message = "是否为测试模式不能为空")
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
