/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.enums;

/**
 * @author fk
 * @version v1.0
 * @Description: 评论评分枚举
 * @date 2018/5/3 11:12
 * @since v7.0.0
 */
public enum CommentGrade {

    /**
     * 好评
     */
    good("好评"),
    /**
     * 中评
     */
    neutral("中评"),
    /**
     * 差评
     */
    bad("差评");

    private String description;

    CommentGrade(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
    public String value(){
        return this.name();
    }
}
