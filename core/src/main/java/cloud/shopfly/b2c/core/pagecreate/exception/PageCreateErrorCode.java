/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.pagecreate.exception;

/**
 * 系统异常
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-10 上午7:14
 */
public enum PageCreateErrorCode {


    //ERRORCODE
    E901("服务器异常"),
    E902("progressid 不能为空"),
    E903("静态页面地址 不能为空"),
    E904("生成静态页参数有误"),
    E905("有静态页生成任务正在进行中，需等待本次任务完成后才能再次生成。");
    private String describe;

    PageCreateErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取统计的异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }


    /**
     * 获取统计的错误消息
     *
     * @return
     */
    public String des() {
        return this.describe;
    }


}
