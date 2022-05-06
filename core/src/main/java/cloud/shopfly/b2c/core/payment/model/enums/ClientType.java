/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.model.enums;

/**
 * 支付客户端类型
 *
 * @author fk
 * @version v6.4
 * @since v6.4 2017年10月17日 上午10:49:25
 */
public enum ClientType {

    /**
     * pc客户端
     */
    PC("pc_config", "PC"),
    /**
     * wap
     */
    WAP("wap_config", "WAP"),
    /**
     * 原生
     */
    NATIVE("app_native_config", "APP"),
    /**
     * RN
     */
    REACT("app_react_config", "APP"),
    /**
     * 微信小程序
     */
    MINI("mini_config", "MINI");

    private String dbColumn;
    private String client;

    ClientType(String dbColumn, String client) {
        this.dbColumn = dbColumn;
        this.client = client;

    }

    public String getDbColumn() {
        return dbColumn;
    }

    public void setDbColumn(String dbColumn) {
        this.dbColumn = dbColumn;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String value() {
        return this.name();
    }


}
