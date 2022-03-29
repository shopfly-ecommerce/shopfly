/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.vo;

import dev.shopflix.core.system.model.enums.ProgressEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 任务进度
 *
 * @author kingapex
 * @version v1.0
 * @since v1.0
 * 2015年5月13日 下午5:54:43
 */
public class TaskProgress implements Serializable {


    public static final String PROCESS = "PROCESS_";

    /**
     * id
     */
    private String id;
    /**
     * 百分比
     */
    private double sumPer;
    /**
     * 每步占比
     */
    private double stepPer;
    /**
     * 正在生成的内容
     */
    private String text;
    /**
     * 生成状态
     */
    private String taskStatus;
    /**
     * 任务总数
     */
    private int taskTotal;

    /**
     * 消息
     */
    private String message;

    /**
     * 构造时要告诉任务总数，以便计算每步占比
     *
     * @param total
     */
    public TaskProgress(int total) {

        /** 计算每步的百分比 */
        this.taskTotal = total;
        this.taskStatus = ProgressEnum.DOING.name();
        BigDecimal b1 = new BigDecimal("100");
        BigDecimal b2 = new BigDecimal("" + taskTotal);
        stepPer = b1.divide(b2, 5, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 完成一步
     */
    public void step(String text) {

        this.sumPer += this.stepPer;
        this.text = text;
    }

    /**
     * 成功
     */
    public void success() {
        this.sumPer = 100;
        this.text = "完成";
        this.taskStatus = ProgressEnum.SUCCESS.name();
    }

    /**
     * 失败
     *
     * @param text
     */
    public void fail(String text,String message) {
        this.taskStatus = ProgressEnum.EXCEPTION.name();
        this.message=message;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public void setId(String id) {
        this.id = id;
    }

    public static String getPROCESS() {
        return PROCESS;
    }

    public String getId() {
        return id;
    }

    public double getSumPer() {
        return sumPer;
    }

    public void setSumPer(double sumPer) {
        this.sumPer = sumPer;
    }

    public double getStepPer() {
        return stepPer;
    }

    public void setStepPer(double stepPer) {
        this.stepPer = stepPer;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal(int taskTotal) {
        this.taskTotal = taskTotal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
