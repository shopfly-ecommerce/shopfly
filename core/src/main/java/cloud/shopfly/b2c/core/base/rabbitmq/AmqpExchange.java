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
package cloud.shopfly.b2c.core.base.rabbitmq;

/**
 * AMQPThe message definition
 *
 * @author kingapex
 * @version 1.0
 * @since 6.4
 * 2017-08-17 18：00
 */
public class AmqpExchange {


    /**
     * TEST
     */
    public final static String TEST_EXCHANGE = "TEST_EXCHANGE_FANOUT";


    /**
     * PCHome Page Change message
     */
    public final static String PC_INDEX_CHANGE = "PC_INDEX_CHANGE";

    /**
     * Change message on the home page of the mobile terminal
     */
    public final static String MOBILE_INDEX_CHANGE = "MOBILE_INDEX_CHANGE";

    /**
     * Commodity change message
     */
    public final static String GOODS_CHANGE = "GOODS_CHANGE";

    /**
     * Commodity change information is accompanied by reasons
     */
    public final static String GOODS_CHANGE_REASON = "GOODS_CHANGE_REASON";

    /**
     * Help change messages
     */
    public final static String HELP_CHANGE = "HELP_CHANGE";

    /**
     * Index generation message
     */
    public final static String INDEX_CREATE = "INDEX_CREATE";

    /**
     * Order creation message
     * Not put in storage
     */
    public final static String ORDER_CREATE = "ORDER_CREATE";

    /**
     * Failed to import message
     * Storage failure
     */
    public final static String ORDER_INTODB_ERROR = "ORDER_INTODB_ERROR";

    /**
     * Order status change message
     * With the received
     */
    public final static String ORDER_STATUS_CHANGE = "ORDER_STATUS_CHANGE";

    /**
     * Member login message
     */
    public final static String MEMEBER_LOGIN = "MEMEBER_LOGIN";

    /**
     * Member Registration Message
     */
    public final static String MEMEBER_REGISTER = "MEMEBER_REGISTER";

    /**
     * Classified change message
     */
    public final static String GOODS_CATEGORY_CHANGE = "GOODS_CATEGORY_CHANGE";

    /**
     * After-sales status change message
     */
    public final static String REFUND_STATUS_CHANGE = "REFUND_STATUS_CHANGE";

    /**
     * Sends in-station messages
     */
    public final static String MEMBER_MESSAGE = "MEMBER_MESSAGE";

    /**
     * Send SMS messages
     */
    public final static String SEND_MESSAGE = "SEND_MESSAGE";

    /**
     * Sending messages by mail
     */
    public final static String EMAIL_SEND_MESSAGE = "EMAIL_SEND_MESSAGE";

    /**
     * Product Review messages
     */
    public final static String GOODS_COMMENT_COMPLETE = "GOODS_COMMENT_COMPLETE";
    /**
     * Online payment
     */
    public final static String ONLINE_PAY = "ONLINE_PAY";

    /**
     * Improve personal data
     */
    public final static String MEMBER_INFO_COMPLETE = "MEMBER_INFO_COMPLETE";

    /**
     * The site navigation bar changes messages
     */
    public final static String SITE_NAVIGATION_CHANGE = "SITE_NAVIGATION_CHANGE";

    /**
     * Goods collection
     */
    public final static String GOODS_COLLECTION_CHANGE = "GOODS_COLLECTION_CHANGE";

    /**
     * Merchandise Browsing statistics
     */
    public final static String GOODS_VIEW_COUNT = "GOODS_VIEW_COUNT";

    /**
     * Change of Member information
     */
    public final static String MEMBER_INFO_CHANGE = "MEMBER_INFO_CHANGE";


}
