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
package cloud.shopfly.b2c.core.base;

/**
 * 定时任务AMQP消息定义
 *
 * @author kingapex
 * @version 1.0
 * @since 6.4
 * 2017-08-17 18：00
 */
public class JobAmqpExchange {


    /**
     * 每小时执行
     */
    public final static String EVERY_HOUR_EXECUTE = "EVERY_HOUR_EXECUTE";

    /**
     * 每日执行
     */
    public final static String EVERY_DAY_EXECUTE = "EVERY_DAY_EXECUTE";

    /**
     * 每月执行
     */
    public final static String EVERY_MONTH_EXECUTE = "EVERY_MONTH_EXECUTE";

    /**
     * 每年执行
     */
    public final static String EVERY_YEAR_EXECUTE = "EVERY_YEAR_EXECUTE";

    /**
     * 每10分钟执行
     */
    public final static String EVERY_TEN_MINUTES_EXECUTE = "EVERY_TEN_MINUTES_EXECUTE";


}
