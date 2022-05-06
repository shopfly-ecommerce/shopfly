/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.groupbuy.model.vo;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.enums.GroupBuyStatusEnum;
import cloud.shopfly.b2c.framework.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 团购活动VO
 *
 * @author Snow create in 2018/6/13
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
public class GroupbuyActiveVO extends GroupbuyActiveDO {

    @ApiModelProperty(name = "status_text", value = "状态值")
    private String statusText;

    @ApiModelProperty(name = "status", value = "活动状态标识,expired表示已失效")
    private String status;

    public String getStatusText() {
        Long now = DateUtil.getDateline();
        if (this.getStartTime() > now) {
            return GroupBuyStatusEnum.NOT_BEGIN.getStatus();
        } else if (this.getStartTime() < now && this.getEndTime() > now) {
            return GroupBuyStatusEnum.CONDUCT.getStatus();
        } else {
            return GroupBuyStatusEnum.OVERDUE.getStatus();
        }
    }

    public String getStatus() {
        Long now = DateUtil.getDateline();
        if (this.getStartTime() > now) {
            return null;
        } else if (this.getStartTime() < now && this.getEndTime() > now) {
            return null;
        } else {
            return "expired";
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return "GroupbuyActiveVO{" +
                "statusText='" + statusText + '\'' +
                '}';
    }
}
