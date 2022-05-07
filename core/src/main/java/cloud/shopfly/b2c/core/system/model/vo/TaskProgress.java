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

import cloud.shopfly.b2c.core.system.model.enums.ProgressEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Task schedule
 *
 * @author kingapex
 * @version v1.0
 * @since v1.0
 * 2015years5month13On the afternoon5:54:43
 */
public class TaskProgress implements Serializable {


    public static final String PROCESS = "PROCESS_";

    /**
     * id
     */
    private String id;
    /**
     * The percentage
     */
    private double sumPer;
    /**
     * Each accounted for
     */
    private double stepPer;
    /**
     * Content being generated
     */
    private String text;
    /**
     * Generate state
     */
    private String taskStatus;
    /**
     * The total number of tasks
     */
    private int taskTotal;

    /**
     * The message
     */
    private String message;

    /**
     * The total number of tasks is told during construction so that the proportion of each step can be calculated
     *
     * @param total
     */
    public TaskProgress(int total) {

        /** Calculate the percentage of each step*/
        this.taskTotal = total;
        this.taskStatus = ProgressEnum.DOING.name();
        BigDecimal b1 = new BigDecimal("100");
        BigDecimal b2 = new BigDecimal("" + taskTotal);
        stepPer = b1.divide(b2, 5, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * Complete the step
     */
    public void step(String text) {

        this.sumPer += this.stepPer;
        this.text = text;
    }

    /**
     * successful
     */
    public void success() {
        this.sumPer = 100;
        this.text = "complete";
        this.taskStatus = ProgressEnum.SUCCESS.name();
    }

    /**
     * failure
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
