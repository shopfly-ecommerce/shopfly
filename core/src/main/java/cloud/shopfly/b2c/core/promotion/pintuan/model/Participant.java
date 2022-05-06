/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.pintuan.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by kingapex on 2019-01-24.
 * 参团者
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-24
 */
public class Participant {

    public Participant() {
        isMaster = 0;
    }

    @ApiModelProperty(name = "id", value = "会员id")
    private Integer id;

    @ApiModelProperty(name = "name", value = "会员名")
    private String name;

    @ApiModelProperty(name = "face", value = "头像")
    private String face;

    @ApiModelProperty(name = "is_master", value = "是否是团长,1是，0否")
    private Integer isMaster;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(Integer isMaster) {
        this.isMaster = isMaster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Participant that = (Participant) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getName(), that.getName())
                .append(getFace(), that.getFace())
                .append(getIsMaster(), that.getIsMaster())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getName())
                .append(getFace())
                .append(getIsMaster())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", face='" + face + '\'' +
                ", isMaster=" + isMaster +
                '}';
    }
}
