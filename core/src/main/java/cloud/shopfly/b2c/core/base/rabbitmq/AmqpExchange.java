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
 * AMQP消息定义
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
     * PC首页变化消息
     */
    public final static String PC_INDEX_CHANGE = "PC_INDEX_CHANGE";

    /**
     * 移动端首页变化消息
     */
    public final static String MOBILE_INDEX_CHANGE = "MOBILE_INDEX_CHANGE";

    /**
     * 商品变化消息
     */
    public final static String GOODS_CHANGE = "GOODS_CHANGE";

    /**
     * 商品变化消息附带原因
     */
    public final static String GOODS_CHANGE_REASON = "GOODS_CHANGE_REASON";

    /**
     * 帮助变化消息
     */
    public final static String HELP_CHANGE = "HELP_CHANGE";

    /**
     * 索引生成消息
     */
    public final static String INDEX_CREATE = "INDEX_CREATE";

    /**
     * 订单创建消息
     * 没有入库
     */
    public final static String ORDER_CREATE = "ORDER_CREATE";

    /**
     * 入库失败消息
     * 入库失败
     */
    public final static String ORDER_INTODB_ERROR = "ORDER_INTODB_ERROR";

    /**
     * 订单状态变化消息
     * 带入库的
     */
    public final static String ORDER_STATUS_CHANGE = "ORDER_STATUS_CHANGE";

    /**
     * 会员登录消息
     */
    public final static String MEMEBER_LOGIN = "MEMEBER_LOGIN";

    /**
     * 会员注册消息
     */
    public final static String MEMEBER_REGISTER = "MEMEBER_REGISTER";

    /**
     * 分类变更消息
     */
    public final static String GOODS_CATEGORY_CHANGE = "GOODS_CATEGORY_CHANGE";

    /**
     * 售后状态改变消息
     */
    public final static String REFUND_STATUS_CHANGE = "REFUND_STATUS_CHANGE";

    /**
     * 发送站内信息
     */
    public final static String MEMBER_MESSAGE = "MEMBER_MESSAGE";

    /**
     * 发送手机短信消息
     */
    public final static String SEND_MESSAGE = "SEND_MESSAGE";

    /**
     * 邮件发送消息
     */
    public final static String EMAIL_SEND_MESSAGE = "EMAIL_SEND_MESSAGE";

    /**
     * 商品评论消息
     */
    public final static String GOODS_COMMENT_COMPLETE = "GOODS_COMMENT_COMPLETE";
    /**
     * 网上支付
     */
    public final static String ONLINE_PAY = "ONLINE_PAY";

    /**
     * 完善个人资料
     */
    public final static String MEMBER_INFO_COMPLETE = "MEMBER_INFO_COMPLETE";

    /**
     * 站点导航栏变化消息
     */
    public final static String SITE_NAVIGATION_CHANGE = "SITE_NAVIGATION_CHANGE";

    /**
     * 商品收藏
     */
    public final static String GOODS_COLLECTION_CHANGE = "GOODS_COLLECTION_CHANGE";

    /**
     * 商品浏览统计
     */
    public final static String GOODS_VIEW_COUNT = "GOODS_VIEW_COUNT";

    /**
     * 会员资料改变
     */
    public final static String MEMBER_INFO_CHANGE = "MEMBER_INFO_CHANGE";


}
