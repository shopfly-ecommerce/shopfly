/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Version : 80015
 Source Host           : localhost
 Source Database       : test

 Target Server Version : 80015
 File Encoding         : utf-8

 Date: 08/08/2019 14:47:15 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `es_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `es_coupon`;
CREATE TABLE `es_coupon` (
  `title` varchar(20) DEFAULT NULL COMMENT '优惠券标题',
  `coupon_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `coupon_price` decimal(10,2) DEFAULT NULL COMMENT '优惠券面额',
  `coupon_threshold_price` decimal(10,2) DEFAULT NULL COMMENT '优惠券门槛价格',
  `start_time` bigint(11) DEFAULT NULL COMMENT '使用起始时间',
  `end_time` bigint(11) DEFAULT NULL COMMENT '使用截止时间',
  `create_num` int(10) DEFAULT NULL COMMENT '发行量',
  `limit_num` int(10) DEFAULT NULL COMMENT '每人限领数量',
  `used_num` int(10) DEFAULT NULL COMMENT '已被使用的数量',
  `received_num` int(10) DEFAULT NULL COMMENT '已被领取的数量',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='优惠券(es_coupon)';

-- ----------------------------
--  Table structure for `es_exchange`
-- ----------------------------
DROP TABLE IF EXISTS `es_exchange`;
CREATE TABLE `es_exchange` (
  `exchange_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `category_id` int(11) DEFAULT NULL COMMENT '商品所属积分分类',
  `enable_exchange` int(11) DEFAULT NULL COMMENT '是否允许兑换',
  `exchange_money` decimal(20,2) DEFAULT NULL COMMENT '兑换所需金额',
  `exchange_point` int(11) DEFAULT NULL COMMENT '兑换所需积分',
  `goods_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品原价',
  `goods_img` varchar(255) DEFAULT NULL COMMENT '商品图片',
  PRIMARY KEY (`exchange_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='积分兑换(es_exchange)';

-- ----------------------------
--  Table structure for `es_exchange_cat`
-- ----------------------------
DROP TABLE IF EXISTS `es_exchange_cat`;
CREATE TABLE `es_exchange_cat` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父分类',
  `category_path` varchar(255) DEFAULT NULL COMMENT '分类id路径',
  `goods_count` int(11) DEFAULT NULL COMMENT '商品数量',
  `category_order` int(11) DEFAULT NULL COMMENT '分类排序',
  `list_show` int(10) DEFAULT NULL COMMENT '是否在页面上显示',
  `image` varchar(255) DEFAULT NULL COMMENT '分类图片',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='积分兑换分类(es_exchange_cat)';

-- ----------------------------
--  Records of `es_exchange_cat`
-- ----------------------------
BEGIN;
INSERT INTO `es_exchange_cat` VALUES ('1', '食品饮料', '0', '0|1|', '0', '1', '1', 'http://static.b2b2cv2.javamall.com.cn/attachment/goodscat/201504271341534362.jpg'), ('4', '进口食品', '0', '0|4|', '0', '2', '1', 'http://static.b2b2cv2.javamall.com.cn/attachment/goodscat/201504271407214410.jpg'), ('38', '美容化妆', '0', '0|38|', '0', '3', '1', 'http://static.b2b2cv2.javamall.com.cn/attachment/goodscat/201504091539162830.jpg'), ('56', '母婴玩具', '0', '0|56|', '0', '4', '1', 'http://static.b2b2cv2.javamall.com.cn/attachment/goodscat/201504271407092731.jpg'), ('79', '厨房用品', '0', '0|79|', '0', '5', '1', 'http://static.b2b2cv2.javamall.com.cn/attachment/goodscat/201504091539356449.jpg'), ('85', '营养保健', '0', '0|85|', '0', '20', '1', 'http://static.b2b2cv2.javamall.com.cn/attachment/goodscat/201504271407281157.jpg'), ('86', '服装鞋靴', '0', '0|86|', '0', '21', '1', 'http://static.b2b2cv2.javamall.com.cn/attachment/goodscat/201504091539573613.jpg');
COMMIT;

-- ----------------------------
--  Table structure for `es_full_discount`
-- ----------------------------
DROP TABLE IF EXISTS `es_full_discount`;
CREATE TABLE `es_full_discount` (
  `fd_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `full_money` decimal(20,2) DEFAULT NULL COMMENT '优惠门槛金额',
  `minus_value` decimal(20,2) DEFAULT NULL COMMENT '减现金',
  `point_value` int(10) DEFAULT NULL COMMENT '送积分',
  `is_full_minus` int(1) DEFAULT NULL COMMENT '活动是否减现金',
  `is_free_ship` int(1) DEFAULT NULL COMMENT '是否免邮',
  `is_send_point` int(1) DEFAULT NULL COMMENT '是否送积分',
  `is_send_gift` int(1) DEFAULT NULL COMMENT '是否有赠品',
  `is_send_bonus` int(1) DEFAULT NULL COMMENT '是否增优惠券',
  `gift_id` int(10) DEFAULT NULL COMMENT '赠品id',
  `bonus_id` int(10) DEFAULT NULL COMMENT '优惠券id',
  `is_discount` int(1) DEFAULT NULL COMMENT '是否打折',
  `discount_value` decimal(20,2) DEFAULT NULL COMMENT '打多少折',
  `start_time` bigint(20) DEFAULT NULL COMMENT '活动开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '活动结束时间',
  `title` varchar(255) DEFAULT NULL COMMENT '活动标题',
  `range_type` int(1) DEFAULT NULL COMMENT '是否全部商品参与',
  `disabled` int(1) DEFAULT NULL COMMENT '是否停用',
  `description` longtext COMMENT '活动说明',
  PRIMARY KEY (`fd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='满优惠活动(es_full_discount)';

-- ----------------------------
--  Table structure for `es_full_discount_gift`
-- ----------------------------
DROP TABLE IF EXISTS `es_full_discount_gift`;
CREATE TABLE `es_full_discount_gift` (
  `gift_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '赠品id',
  `gift_name` varchar(255) DEFAULT NULL COMMENT '赠品名称',
  `gift_price` decimal(20,2) DEFAULT NULL COMMENT '赠品金额',
  `gift_img` varchar(255) DEFAULT NULL COMMENT '赠品图片',
  `gift_type` int(1) DEFAULT NULL COMMENT '赠品类型',
  `actual_store` int(10) DEFAULT NULL COMMENT '库存',
  `enable_store` int(10) DEFAULT NULL COMMENT '可用库存',
  `create_time` bigint(20) DEFAULT NULL COMMENT '活动时间',
  `goods_id` int(10) DEFAULT NULL COMMENT '活动商品id',
  `disabled` int(1) DEFAULT NULL COMMENT '是否禁用',
  PRIMARY KEY (`gift_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='满优惠赠品(es_full_discount_gift)';

-- ----------------------------
--  Table structure for `es_goods_snapshot`
-- ----------------------------
DROP TABLE IF EXISTS `es_goods_snapshot`;
CREATE TABLE `es_goods_snapshot` (
  `snapshot_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品id',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `sn` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `brand_name` varchar(255) DEFAULT NULL COMMENT '品牌名称',
  `category_name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `goods_type` varchar(20) DEFAULT NULL COMMENT '商品类型',
  `weight` decimal(10,2) DEFAULT NULL COMMENT '重量',
  `intro` longtext COMMENT '商品详情',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `cost` decimal(10,2) DEFAULT NULL COMMENT '商品成本价',
  `mktprice` decimal(10,2) DEFAULT NULL COMMENT '商品市场价',
  `have_spec` smallint(1) DEFAULT NULL COMMENT '商品是否开启规格1 开启 0 未开启',
  `params_json` longtext COMMENT '参数json',
  `img_json` longtext COMMENT '图片json',
  `create_time` bigint(20) DEFAULT NULL COMMENT '快照时间',
  `point` int(10) DEFAULT NULL COMMENT '商品使用积分',
  `promotion_json` longtext COMMENT '促销json值',
  `coupon_json` longtext COMMENT '优惠券json值',
  PRIMARY KEY (`snapshot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=484 DEFAULT CHARSET=utf8 COMMENT='交易快照(es_goods_snapshot)';

-- ----------------------------
--  Table structure for `es_groupbuy_active`
-- ----------------------------
DROP TABLE IF EXISTS `es_groupbuy_active`;
CREATE TABLE `es_groupbuy_active` (
  `act_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动Id',
  `act_name` varchar(255) DEFAULT NULL COMMENT '活动名称',
  `start_time` bigint(20) DEFAULT NULL COMMENT '活动开启时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '团购结束时间',
  `join_end_time` bigint(20) DEFAULT NULL COMMENT '团购报名截止时间',
  `add_time` bigint(20) DEFAULT NULL COMMENT '团购添加时间',
  `act_tag_id` int(10) DEFAULT NULL COMMENT '团购活动标签Id',
  `goods_num` int(10) DEFAULT NULL COMMENT '参与团购商品数量',
  PRIMARY KEY (`act_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='团购活动表(es_groupbuy_active)';

-- ----------------------------
--  Table structure for `es_groupbuy_area`
-- ----------------------------
DROP TABLE IF EXISTS `es_groupbuy_area`;
CREATE TABLE `es_groupbuy_area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地区ID',
  `area_name` varchar(255) DEFAULT NULL COMMENT '地区名称',
  `area_order` int(11) DEFAULT NULL COMMENT '地区排序',
  `area_path` varchar(255) DEFAULT NULL COMMENT '地区路径结构',
  `parent_id` int(11) DEFAULT NULL COMMENT '地区父节点',
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='团购地区(es_groupbuy_area)';

-- ----------------------------
--  Table structure for `es_groupbuy_cat`
-- ----------------------------
DROP TABLE IF EXISTS `es_groupbuy_cat`;
CREATE TABLE `es_groupbuy_cat` (
  `cat_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `parent_id` int(8) DEFAULT NULL COMMENT '父类id',
  `cat_name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `cat_path` varchar(255) DEFAULT NULL COMMENT '分类结构目录',
  `cat_order` int(8) DEFAULT NULL COMMENT '分类排序',
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='团购分类(es_groupbuy_cat)';

-- ----------------------------
--  Table structure for `es_groupbuy_goods`
-- ----------------------------
DROP TABLE IF EXISTS `es_groupbuy_goods`;
CREATE TABLE `es_groupbuy_goods` (
  `gb_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '团购商品Id',
  `sku_id` int(11) DEFAULT NULL COMMENT '货品Id',
  `act_id` int(11) DEFAULT NULL COMMENT '活动Id',
  `cat_id` int(11) DEFAULT NULL COMMENT 'cat_id',
  `area_id` int(11) DEFAULT NULL COMMENT '地区Id',
  `gb_name` varchar(1000) DEFAULT NULL COMMENT '团购名称',
  `gb_title` varchar(1000) DEFAULT NULL COMMENT '副标题',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品Id',
  `original_price` decimal(20,2) DEFAULT NULL COMMENT '原始价格',
  `price` decimal(20,2) DEFAULT NULL COMMENT '团购价格',
  `img_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `goods_num` int(11) DEFAULT NULL COMMENT '商品总数',
  `visual_num` int(11) DEFAULT NULL COMMENT '虚拟数量',
  `limit_num` int(11) DEFAULT NULL COMMENT '限购数量',
  `buy_num` int(11) DEFAULT NULL COMMENT '已团购数量',
  `view_num` int(11) DEFAULT NULL COMMENT '浏览数量',
  `remark` longtext COMMENT '介绍',
  `gb_status` int(1) DEFAULT NULL COMMENT '状态',
  `add_time` bigint(20) DEFAULT NULL COMMENT '添加时间',
  `wap_thumbnail` varchar(255) DEFAULT NULL COMMENT 'wap缩略图',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT 'pc缩略图',
  PRIMARY KEY (`gb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='团购商品(es_groupbuy_goods)';

-- ----------------------------
--  Table structure for `es_groupbuy_quantity_log`
-- ----------------------------
DROP TABLE IF EXISTS `es_groupbuy_quantity_log`;
CREATE TABLE `es_groupbuy_quantity_log` (
  `log_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `order_sn` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品ID',
  `quantity` int(10) DEFAULT NULL COMMENT '团购售空数量',
  `op_time` bigint(20) DEFAULT NULL COMMENT '操作时间',
  `log_type` varchar(20) DEFAULT NULL COMMENT '日志类型',
  `reason` varchar(200) DEFAULT NULL COMMENT '操作原因',
  `gb_id` int(10) DEFAULT NULL COMMENT '团购商品id',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='团购商品库存日志表(es_groupbuy_quantity_log)';

-- ----------------------------
--  Table structure for `es_half_price`
-- ----------------------------
DROP TABLE IF EXISTS `es_half_price`;
CREATE TABLE `es_half_price` (
  `hp_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '第二件半价活动ID',
  `start_time` bigint(20) DEFAULT NULL COMMENT '活动开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '活动结束时间',
  `title` varchar(50) DEFAULT NULL COMMENT '活动标题',
  `range_type` int(1) DEFAULT NULL COMMENT '是否全部商品参与',
  `disabled` int(1) DEFAULT NULL COMMENT '是否停用',
  `description` longtext COMMENT '活动说明',
  PRIMARY KEY (`hp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='第二件半价(es_half_price)';

-- ----------------------------
--  Table structure for `es_minus`
-- ----------------------------
DROP TABLE IF EXISTS `es_minus`;
CREATE TABLE `es_minus` (
  `minus_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '单品立减活动id',
  `single_reduction_value` decimal(20,0) DEFAULT NULL COMMENT '单品立减金额',
  `start_time` bigint(20) DEFAULT NULL COMMENT '起始时间',
  `start_time_str` varchar(50) DEFAULT NULL COMMENT '起始时间字符串',
  `end_time` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `end_time_str` varchar(50) DEFAULT NULL COMMENT '结束时间字符串',
  `title` varchar(50) DEFAULT NULL COMMENT '单品立减活动标题',
  `range_type` int(1) DEFAULT NULL COMMENT '是否选择全部商品',
  `disabled` int(1) DEFAULT NULL COMMENT '是否停用',
  `description` longtext COMMENT '描述',
  PRIMARY KEY (`minus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='单品立减(es_minus)';

-- ----------------------------
--  Table structure for `es_order`
-- ----------------------------
DROP TABLE IF EXISTS `es_order`;
CREATE TABLE `es_order` (
  `order_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `trade_sn` varchar(20) DEFAULT NULL COMMENT '交易编号',
  `seller_id` int(10) DEFAULT NULL COMMENT '店铺ID',
  `seller_name` varchar(255) DEFAULT NULL COMMENT '店铺名称',
  `member_id` int(10) DEFAULT NULL COMMENT '会员ID',
  `member_name` varchar(255) DEFAULT NULL COMMENT '买家账号',
  `order_status` varchar(50) DEFAULT NULL COMMENT '订单状态',
  `pay_status` varchar(50) DEFAULT NULL COMMENT '付款状态',
  `ship_status` varchar(50) DEFAULT NULL COMMENT '货运状态',
  `shipping_id` int(10) DEFAULT NULL COMMENT '配送方式ID',
  `comment_status` varchar(50) DEFAULT NULL COMMENT '评论是否完成',
  `shipping_type` varchar(255) DEFAULT NULL COMMENT '配送方式',
  `payment_method_id` varchar(50) DEFAULT NULL COMMENT '支付方式id',
  `payment_plugin_id` varchar(50) DEFAULT NULL COMMENT '支付插件id',
  `payment_method_name` varchar(50) DEFAULT NULL COMMENT '支付方式名称',
  `payment_type` varchar(50) DEFAULT NULL COMMENT '支付方式类型',
  `payment_time` bigint(20) DEFAULT NULL COMMENT '支付时间',
  `pay_money` decimal(20,2) DEFAULT NULL COMMENT '已支付金额',
  `ship_name` varchar(255) DEFAULT NULL COMMENT '收货人姓名',
  `ship_addr` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `ship_zip` varchar(20) DEFAULT NULL COMMENT '收货人邮编',
  `ship_mobile` varchar(50) DEFAULT NULL COMMENT '收货人手机',
  `ship_tel` varchar(50) DEFAULT NULL COMMENT '收货人电话',
  `receive_time` varchar(50) DEFAULT NULL COMMENT '收货时间',
  `ship_province_id` int(10) DEFAULT NULL COMMENT '配送地区-省份ID',
  `ship_city_id` int(10) DEFAULT NULL COMMENT '配送地区-城市ID',
  `ship_county_id` int(10) DEFAULT NULL COMMENT '配送地区-区(县)ID',
  `ship_town_id` int(10) DEFAULT NULL COMMENT '配送街道id',
  `ship_province` varchar(50) DEFAULT NULL COMMENT '配送地区-省份',
  `ship_city` varchar(50) DEFAULT NULL COMMENT '配送地区-城市',
  `ship_county` varchar(50) DEFAULT NULL COMMENT '配送地区-区(县)',
  `ship_town` varchar(50) DEFAULT NULL COMMENT '配送街道',
  `order_price` decimal(20,2) DEFAULT NULL COMMENT '订单总额',
  `goods_price` decimal(20,2) DEFAULT NULL COMMENT '商品总额',
  `shipping_price` decimal(20,2) DEFAULT NULL COMMENT '配送费用',
  `discount_price` decimal(20,2) DEFAULT NULL COMMENT '优惠金额',
  `disabled` int(1) DEFAULT NULL COMMENT '是否被删除',
  `weight` decimal(20,2) DEFAULT NULL COMMENT '订单商品总重量',
  `goods_num` int(10) DEFAULT NULL COMMENT '商品数量',
  `remark` varchar(1000) DEFAULT NULL COMMENT '订单备注',
  `cancel_reason` varchar(255) DEFAULT NULL COMMENT '订单取消原因',
  `the_sign` varchar(255) DEFAULT NULL COMMENT '签收人',
  `items_json` longtext COMMENT '货物列表json',
  `warehouse_id` int(10) DEFAULT NULL COMMENT '发货仓库ID',
  `need_pay_money` decimal(20,2) DEFAULT NULL COMMENT '应付金额',
  `ship_no` varchar(255) DEFAULT NULL COMMENT '发货单号',
  `address_id` int(10) DEFAULT NULL COMMENT '收货地址ID',
  `admin_remark` int(50) DEFAULT NULL COMMENT '管理员备注',
  `logi_id` int(10) DEFAULT NULL COMMENT '物流公司ID',
  `logi_name` varchar(255) DEFAULT NULL COMMENT '物流公司名称',
  `complete_time` bigint(20) DEFAULT NULL COMMENT '完成时间',
  `create_time` bigint(20) DEFAULT NULL COMMENT '订单创建时间',
  `signing_time` bigint(20) DEFAULT NULL COMMENT '签收时间',
  `ship_time` bigint(20) DEFAULT NULL COMMENT '送货时间',
  `pay_order_no` varchar(50) DEFAULT NULL COMMENT '支付方式返回的交易号',
  `service_status` varchar(50) DEFAULT NULL COMMENT '售后状态',
  `bill_status` int(10) DEFAULT NULL COMMENT '结算状态',
  `bill_sn` varchar(50) DEFAULT NULL COMMENT '结算单号',
  `client_type` varchar(50) DEFAULT NULL COMMENT '订单来源',
  `sn` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `need_receipt` int(1) DEFAULT NULL COMMENT '是否需要发票',
  `order_type` varchar(50) DEFAULT 'normal' COMMENT '订单类型',
  `order_data` text COMMENT '订单数据',
  PRIMARY KEY (`order_id`),
  KEY `ind_order_sn` (`sn`),
  KEY `ind_order_state` (`order_status`,`pay_status`,`ship_status`),
  KEY `ind_order_memberid` (`member_id`),
  KEY `ind_order_term` (`disabled`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='订单表(es_order)';

-- ----------------------------
--  Table structure for `es_order_items`
-- ----------------------------
DROP TABLE IF EXISTS `es_order_items`;
CREATE TABLE `es_order_items` (
  `item_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品ID',
  `product_id` int(10) DEFAULT NULL COMMENT '货品ID',
  `num` int(10) DEFAULT NULL COMMENT '销售量',
  `ship_num` int(10) DEFAULT NULL COMMENT '发货量',
  `trade_sn` varchar(50) DEFAULT NULL COMMENT '交易编号',
  `order_sn` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `price` decimal(20,2) DEFAULT NULL COMMENT '销售金额',
  `cat_id` int(10) DEFAULT NULL COMMENT '分类ID',
  `state` int(1) DEFAULT NULL COMMENT '状态',
  `snapshot_id` int(10) DEFAULT NULL COMMENT '快照id',
  `spec_json` varchar(1000) DEFAULT NULL COMMENT '规格json',
  `promotion_type` varchar(255) DEFAULT NULL COMMENT '促销类型',
  `promotion_id` int(10) DEFAULT NULL COMMENT '促销id',
  `refund_price` decimal(20,2) DEFAULT NULL COMMENT '订单项可退款金额',
  PRIMARY KEY (`item_id`),
  KEY `es_order_item` (`order_sn`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='订单货物表(es_order_items)';

-- ----------------------------
--  Table structure for `es_order_log`
-- ----------------------------
DROP TABLE IF EXISTS `es_order_log`;
CREATE TABLE `es_order_log` (
  `log_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_sn` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `op_id` int(10) DEFAULT NULL COMMENT '操作者id',
  `op_name` varchar(50) DEFAULT NULL COMMENT '操作者名称',
  `message` varchar(100) DEFAULT NULL COMMENT '日志信息',
  `op_time` bigint(20) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`log_id`),
  KEY `ind_order_log` (`order_sn`)
) ENGINE=InnoDB AUTO_INCREMENT=363 DEFAULT CHARSET=utf8 COMMENT='订单日志表(es_order_log)';

-- ----------------------------
--  Table structure for `es_order_meta`
-- ----------------------------
DROP TABLE IF EXISTS `es_order_meta`;
CREATE TABLE `es_order_meta` (
  `meta_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_sn` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `meta_key` varchar(255) DEFAULT NULL COMMENT '扩展-键',
  `meta_value` longtext COMMENT '扩展-值',
  `status` varchar(50) DEFAULT NULL COMMENT '售后状态',
  PRIMARY KEY (`meta_id`),
  KEY `es_ind_orderex_metaid` (`meta_id`)
) ENGINE=InnoDB AUTO_INCREMENT=355 DEFAULT CHARSET=utf8 COMMENT='订单扩展信息表(es_order_meta)';

-- ----------------------------
--  Table structure for `es_order_out_status`
-- ----------------------------
DROP TABLE IF EXISTS `es_order_out_status`;
CREATE TABLE `es_order_out_status` (
  `out_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_sn` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `out_type` varchar(20) DEFAULT NULL COMMENT '出库类型',
  `out_status` varchar(20) DEFAULT NULL COMMENT '出库状态',
  PRIMARY KEY (`out_id`)
) ENGINE=InnoDB AUTO_INCREMENT=949 DEFAULT CHARSET=utf8 COMMENT='订单出库状态(es_order_out_status)';

-- ----------------------------
--  Table structure for `es_pay_log`
-- ----------------------------
DROP TABLE IF EXISTS `es_pay_log`;
CREATE TABLE `es_pay_log` (
  `pay_log_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '收款单ID',
  `order_sn` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `pay_way` varchar(20) DEFAULT NULL COMMENT '支付方式',
  `pay_type` varchar(20) DEFAULT NULL COMMENT '支付类型',
  `pay_time` bigint(20) DEFAULT NULL COMMENT '付款时间',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '付款金额',
  `pay_member_name` varchar(255) DEFAULT NULL COMMENT '付款会员名',
  `pay_status` varchar(20) DEFAULT NULL COMMENT '付款状态',
  `pay_log_sn` varchar(20) DEFAULT NULL COMMENT '流水号',
  `pay_order_no` varchar(255) DEFAULT NULL COMMENT '支付方式返回流水号',
  PRIMARY KEY (`pay_log_id`),
  KEY `ind_pay_log` (`order_sn`,`pay_status`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='收款单(es_pay_log)';

-- ----------------------------
--  Table structure for `es_payment_bill`
-- ----------------------------
DROP TABLE IF EXISTS `es_payment_bill`;
CREATE TABLE `es_payment_bill` (
  `bill_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sn` varchar(50) DEFAULT NULL COMMENT '单号',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '提交给第三方平台单号',
  `return_trade_no` varchar(100) DEFAULT NULL COMMENT '第三方平台返回交易号',
  `is_pay` int(1) DEFAULT NULL COMMENT '是否已支付',
  `trade_type` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `payment_name` varchar(50) DEFAULT NULL COMMENT '支付方式名称',
  `pay_config` longtext COMMENT '支付参数',
  `trade_price` decimal(20,2) DEFAULT NULL COMMENT '交易金额',
  `payment_plugin_id` varchar(50) DEFAULT NULL COMMENT '支付插件',
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='支付帐单(es_payment_bill)';

-- ----------------------------
--  Table structure for `es_payment_method`
-- ----------------------------
DROP TABLE IF EXISTS `es_payment_method`;
CREATE TABLE `es_payment_method` (
  `method_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '支付方式id',
  `method_name` varchar(255) DEFAULT NULL COMMENT '支付方式名称',
  `plugin_id` varchar(255) DEFAULT NULL COMMENT '支付插件名称',
  `pc_config` longtext COMMENT 'pc是否可用',
  `wap_config` longtext COMMENT 'wap是否可用',
  `app_native_config` longtext COMMENT 'app 原生是否可用',
  `image` varchar(255) DEFAULT NULL COMMENT '支付方式图片',
  `is_retrace` int(2) DEFAULT NULL COMMENT '是否支持原路退回',
  `app_react_config` text COMMENT 'app RN是否可用',
  `mini_config` longtext COMMENT '小程序配置',
  PRIMARY KEY (`method_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1704 DEFAULT CHARSET=utf8 COMMENT='支付方式(es_payment_method)';

-- ----------------------------
--  Records of `es_payment_method`
-- ----------------------------
BEGIN;
INSERT INTO `es_payment_method` VALUES ('1699', '支付宝', 'alipayDirectPlugin', '{\"key\":\"pc_config&wap_config&app_native_config&app_react_config\",\"name\":\"是否开启\",\"config_list\":[{\"name\":\"alipay_public_key\",\"text\":\"支付宝公钥\",\"value\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqgzGUcz3Npp5Zss1JmSOjkjZvVi1pyV71A/R2K8HkaqHSEMTuAVk4mr+xojqQGIAihpzHrE3nFq9Cq612ncY9Uf2OabfpLqsilB+q97fBbd8YVNZq3thoFaYqYtmcv8tzDMLDUq1/6sHV3DJKrmyAMc7QfHuGZXRW5hGWDU2IDklump5/iWeDGDzPKZ+laHaiJbZ24ALmMiHKZlIHqYHkNvQlTNKgqBIbkjViQJf0PNQefgs6CcSsr5OpuMSiZLbgsOwh9o826JO2TMb18pkPnwNEW0cOcCyhngA1WvQxsrWQGSI/VRcUr3Ho0D1ZGmp09zmpON38N1eBmi3488TqwIDAQAB\"},{\"name\":\"app_id\",\"text\":\"应用ID\",\"value\":\"2017101909390405\"},{\"name\":\"merchant_private_key\",\"text\":\"商户私钥\",\"value\":\"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDhO9dJxtwzOd7vNgOMAfuZalWwnH97HOMzxI34dby7ZepVMQ+xFwPhPVa3Eu72kHVVHnkpXXzrxA3Ri0hk5vUudofSqqdIARuELLoUSaHWmupHQdJiFN1lNb+Rnc4M/+DybYCEJbZWfIRIc/48kg414zZDu7/8waLa1wlWbRKhvWFojHrK86SJcjAagf03aLWAMMqvGNfyCAqJa0jq0AvHLrrPDeigapG0SSUe9P5psCUhwjSx+qOL2yhWPfivlTgdLQryh4t9h0MuQ7AyQtVfn/B+F0js6iB6Cn16QJHA7FJvy3stEEA+XN7shn2l//q0ktQkXoVtgLvJKjvPv+bhAgMBAAECggEAF29liCyjzsSvuI4TMmcmaaERx22RKhsoXMOPzhFaENma10BilJeDdNUwB551tLv1KWUhUDeE/vcIRQxL/Qu26R22JipRe4F5/ne06NV8fFrkE4P6wu/O1XARPbKAtQOOwUq5e4Z9AWS1UuCWzHCZ/+tjQwru//qZ2lFTjGWO8bsTxSVpX86jo+se62HExhWyv/Bdmch1A+w1S13o2EdJ6k6mNDqtXAa4rgzsz1QuQsU1ShBx7sPkD3kTu7pH8uihXnGfqpzOZJ50rRu+zgDhX6DTucHR+LwIQY28kvPlGJORSCzXO4fUnpsiKJfFGUOhvY0hsDXUSNXEdbSemAv4kQKBgQDzXl/TrTzhNrllGFr4zMpPK+dDhC94yOSvPHtIoRqMe4N1GnXKohtv17cZuUJwcP/d4bkb1lDkuS6RmQCWP3Az/PqfGy1Dpgc2au0VVo9dWzsF+/qc/TLiZ/rpUi5IxTArtodc2UVB/z0hRkr8Q12Dd6HMJg1RMM9Zl8MazQCYXQKBgQDs7IJhKrP30ty6GHbTd42WSMHny6o7voKOL6WfRZyjFqNoItwotDxCdO5w5tfGNiCjyAXxlMCnFSf7b0Og8EqysaxRBvnVqP263Mar6WpvZqJvMqb8tM/e13UzWNIy5A75fru2ZYxKx1YSuuu9afcpJLtMpSw02bE46phpGq2QVQKBgDZce00+Ih4waiMh2JhArmQW63aSXUQ+o45dFTC5A8Qjhs6ulWco5LYL2lN9pVCWfSDj3cRREAq3LqbJJW9qRjbmqFjH6kl1GaFXKxQcHLP+v+VrTqSojigyj8J0X/BTU5pAEGZ2BdljGRWIrRFCPu4x18tRKKJdI8PgeW1QI3TlAoGAZM+mEo5HEZZJkW3DceuW9XF0AJtqjg1cJAAmKwVFxydk1HSw1SkL0wNKSgQPoCl77fS4grjW2MFpx9TqI9rbDfoH5lpfiAhmHlK+vnuNbGVxjHszDqgpIxrupbCPkFP2AzdnzA6diVwURdf/YxNptboJcG1/x+UxQZSg0WgmnhkCgYB/MY3JtgmvWR0hkarzwI3G2IT428ObfkrmWL+g7PdpfwNOt7aUQZVhVgH8v8+0A9W++lfeUNHyX8xNVrwriC09aTuIr5J6LC0tbWr9H/GHae2pxfnzGpIZEpu8WCXoMhao1JzjKti3fuNVzrAj2k/JFdNChSp3lKjfJ+B5N+0Kvw\\\\u003d\\\\u003d\"}],\"is_open\":1}', '{\"key\":\"pc_config&wap_config&app_native_config&app_react_config\",\"name\":\"是否开启\",\"config_list\":[{\"name\":\"alipay_public_key\",\"text\":\"支付宝公钥\",\"value\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqgzGUcz3Npp5Zss1JmSOjkjZvVi1pyV71A/R2K8HkaqHSEMTuAVk4mr+xojqQGIAihpzHrE3nFq9Cq612ncY9Uf2OabfpLqsilB+q97fBbd8YVNZq3thoFaYqYtmcv8tzDMLDUq1/6sHV3DJKrmyAMc7QfHuGZXRW5hGWDU2IDklump5/iWeDGDzPKZ+laHaiJbZ24ALmMiHKZlIHqYHkNvQlTNKgqBIbkjViQJf0PNQefgs6CcSsr5OpuMSiZLbgsOwh9o826JO2TMb18pkPnwNEW0cOcCyhngA1WvQxsrWQGSI/VRcUr3Ho0D1ZGmp09zmpON38N1eBmi3488TqwIDAQAB\"},{\"name\":\"app_id\",\"text\":\"应用ID\",\"value\":\"2017101909390405\"},{\"name\":\"merchant_private_key\",\"text\":\"商户私钥\",\"value\":\"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDhO9dJxtwzOd7vNgOMAfuZalWwnH97HOMzxI34dby7ZepVMQ+xFwPhPVa3Eu72kHVVHnkpXXzrxA3Ri0hk5vUudofSqqdIARuELLoUSaHWmupHQdJiFN1lNb+Rnc4M/+DybYCEJbZWfIRIc/48kg414zZDu7/8waLa1wlWbRKhvWFojHrK86SJcjAagf03aLWAMMqvGNfyCAqJa0jq0AvHLrrPDeigapG0SSUe9P5psCUhwjSx+qOL2yhWPfivlTgdLQryh4t9h0MuQ7AyQtVfn/B+F0js6iB6Cn16QJHA7FJvy3stEEA+XN7shn2l//q0ktQkXoVtgLvJKjvPv+bhAgMBAAECggEAF29liCyjzsSvuI4TMmcmaaERx22RKhsoXMOPzhFaENma10BilJeDdNUwB551tLv1KWUhUDeE/vcIRQxL/Qu26R22JipRe4F5/ne06NV8fFrkE4P6wu/O1XARPbKAtQOOwUq5e4Z9AWS1UuCWzHCZ/+tjQwru//qZ2lFTjGWO8bsTxSVpX86jo+se62HExhWyv/Bdmch1A+w1S13o2EdJ6k6mNDqtXAa4rgzsz1QuQsU1ShBx7sPkD3kTu7pH8uihXnGfqpzOZJ50rRu+zgDhX6DTucHR+LwIQY28kvPlGJORSCzXO4fUnpsiKJfFGUOhvY0hsDXUSNXEdbSemAv4kQKBgQDzXl/TrTzhNrllGFr4zMpPK+dDhC94yOSvPHtIoRqMe4N1GnXKohtv17cZuUJwcP/d4bkb1lDkuS6RmQCWP3Az/PqfGy1Dpgc2au0VVo9dWzsF+/qc/TLiZ/rpUi5IxTArtodc2UVB/z0hRkr8Q12Dd6HMJg1RMM9Zl8MazQCYXQKBgQDs7IJhKrP30ty6GHbTd42WSMHny6o7voKOL6WfRZyjFqNoItwotDxCdO5w5tfGNiCjyAXxlMCnFSf7b0Og8EqysaxRBvnVqP263Mar6WpvZqJvMqb8tM/e13UzWNIy5A75fru2ZYxKx1YSuuu9afcpJLtMpSw02bE46phpGq2QVQKBgDZce00+Ih4waiMh2JhArmQW63aSXUQ+o45dFTC5A8Qjhs6ulWco5LYL2lN9pVCWfSDj3cRREAq3LqbJJW9qRjbmqFjH6kl1GaFXKxQcHLP+v+VrTqSojigyj8J0X/BTU5pAEGZ2BdljGRWIrRFCPu4x18tRKKJdI8PgeW1QI3TlAoGAZM+mEo5HEZZJkW3DceuW9XF0AJtqjg1cJAAmKwVFxydk1HSw1SkL0wNKSgQPoCl77fS4grjW2MFpx9TqI9rbDfoH5lpfiAhmHlK+vnuNbGVxjHszDqgpIxrupbCPkFP2AzdnzA6diVwURdf/YxNptboJcG1/x+UxQZSg0WgmnhkCgYB/MY3JtgmvWR0hkarzwI3G2IT428ObfkrmWL+g7PdpfwNOt7aUQZVhVgH8v8+0A9W++lfeUNHyX8xNVrwriC09aTuIr5J6LC0tbWr9H/GHae2pxfnzGpIZEpu8WCXoMhao1JzjKti3fuNVzrAj2k/JFdNChSp3lKjfJ+B5N+0Kvw\\\\u003d\\\\u003d\"}],\"is_open\":1}', '{\"key\":\"pc_config&wap_config&app_native_config&app_react_config\",\"name\":\"是否开启\",\"config_list\":[{\"name\":\"alipay_public_key\",\"text\":\"支付宝公钥\",\"value\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqgzGUcz3Npp5Zss1JmSOjkjZvVi1pyV71A/R2K8HkaqHSEMTuAVk4mr+xojqQGIAihpzHrE3nFq9Cq612ncY9Uf2OabfpLqsilB+q97fBbd8YVNZq3thoFaYqYtmcv8tzDMLDUq1/6sHV3DJKrmyAMc7QfHuGZXRW5hGWDU2IDklump5/iWeDGDzPKZ+laHaiJbZ24ALmMiHKZlIHqYHkNvQlTNKgqBIbkjViQJf0PNQefgs6CcSsr5OpuMSiZLbgsOwh9o826JO2TMb18pkPnwNEW0cOcCyhngA1WvQxsrWQGSI/VRcUr3Ho0D1ZGmp09zmpON38N1eBmi3488TqwIDAQAB\"},{\"name\":\"app_id\",\"text\":\"应用ID\",\"value\":\"2017101909390405\"},{\"name\":\"merchant_private_key\",\"text\":\"商户私钥\",\"value\":\"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDhO9dJxtwzOd7vNgOMAfuZalWwnH97HOMzxI34dby7ZepVMQ+xFwPhPVa3Eu72kHVVHnkpXXzrxA3Ri0hk5vUudofSqqdIARuELLoUSaHWmupHQdJiFN1lNb+Rnc4M/+DybYCEJbZWfIRIc/48kg414zZDu7/8waLa1wlWbRKhvWFojHrK86SJcjAagf03aLWAMMqvGNfyCAqJa0jq0AvHLrrPDeigapG0SSUe9P5psCUhwjSx+qOL2yhWPfivlTgdLQryh4t9h0MuQ7AyQtVfn/B+F0js6iB6Cn16QJHA7FJvy3stEEA+XN7shn2l//q0ktQkXoVtgLvJKjvPv+bhAgMBAAECggEAF29liCyjzsSvuI4TMmcmaaERx22RKhsoXMOPzhFaENma10BilJeDdNUwB551tLv1KWUhUDeE/vcIRQxL/Qu26R22JipRe4F5/ne06NV8fFrkE4P6wu/O1XARPbKAtQOOwUq5e4Z9AWS1UuCWzHCZ/+tjQwru//qZ2lFTjGWO8bsTxSVpX86jo+se62HExhWyv/Bdmch1A+w1S13o2EdJ6k6mNDqtXAa4rgzsz1QuQsU1ShBx7sPkD3kTu7pH8uihXnGfqpzOZJ50rRu+zgDhX6DTucHR+LwIQY28kvPlGJORSCzXO4fUnpsiKJfFGUOhvY0hsDXUSNXEdbSemAv4kQKBgQDzXl/TrTzhNrllGFr4zMpPK+dDhC94yOSvPHtIoRqMe4N1GnXKohtv17cZuUJwcP/d4bkb1lDkuS6RmQCWP3Az/PqfGy1Dpgc2au0VVo9dWzsF+/qc/TLiZ/rpUi5IxTArtodc2UVB/z0hRkr8Q12Dd6HMJg1RMM9Zl8MazQCYXQKBgQDs7IJhKrP30ty6GHbTd42WSMHny6o7voKOL6WfRZyjFqNoItwotDxCdO5w5tfGNiCjyAXxlMCnFSf7b0Og8EqysaxRBvnVqP263Mar6WpvZqJvMqb8tM/e13UzWNIy5A75fru2ZYxKx1YSuuu9afcpJLtMpSw02bE46phpGq2QVQKBgDZce00+Ih4waiMh2JhArmQW63aSXUQ+o45dFTC5A8Qjhs6ulWco5LYL2lN9pVCWfSDj3cRREAq3LqbJJW9qRjbmqFjH6kl1GaFXKxQcHLP+v+VrTqSojigyj8J0X/BTU5pAEGZ2BdljGRWIrRFCPu4x18tRKKJdI8PgeW1QI3TlAoGAZM+mEo5HEZZJkW3DceuW9XF0AJtqjg1cJAAmKwVFxydk1HSw1SkL0wNKSgQPoCl77fS4grjW2MFpx9TqI9rbDfoH5lpfiAhmHlK+vnuNbGVxjHszDqgpIxrupbCPkFP2AzdnzA6diVwURdf/YxNptboJcG1/x+UxQZSg0WgmnhkCgYB/MY3JtgmvWR0hkarzwI3G2IT428ObfkrmWL+g7PdpfwNOt7aUQZVhVgH8v8+0A9W++lfeUNHyX8xNVrwriC09aTuIr5J6LC0tbWr9H/GHae2pxfnzGpIZEpu8WCXoMhao1JzjKti3fuNVzrAj2k/JFdNChSp3lKjfJ+B5N+0Kvw\\\\u003d\\\\u003d\"}],\"is_open\":1}', 'http://javashop-statics.oss-cn-beijing.aliyuncs.com/v70/null/044162472C1F4BF3A4BFB7A110BD47F3.png', '1', '{\"key\":\"pc_config&wap_config&app_native_config&app_react_config\",\"name\":\"是否开启\",\"config_list\":[{\"name\":\"alipay_public_key\",\"text\":\"支付宝公钥\",\"value\":\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqgzGUcz3Npp5Zss1JmSOjkjZvVi1pyV71A/R2K8HkaqHSEMTuAVk4mr+xojqQGIAihpzHrE3nFq9Cq612ncY9Uf2OabfpLqsilB+q97fBbd8YVNZq3thoFaYqYtmcv8tzDMLDUq1/6sHV3DJKrmyAMc7QfHuGZXRW5hGWDU2IDklump5/iWeDGDzPKZ+laHaiJbZ24ALmMiHKZlIHqYHkNvQlTNKgqBIbkjViQJf0PNQefgs6CcSsr5OpuMSiZLbgsOwh9o826JO2TMb18pkPnwNEW0cOcCyhngA1WvQxsrWQGSI/VRcUr3Ho0D1ZGmp09zmpON38N1eBmi3488TqwIDAQAB\"},{\"name\":\"app_id\",\"text\":\"应用ID\",\"value\":\"2017101909390405\"},{\"name\":\"merchant_private_key\",\"text\":\"商户私钥\",\"value\":\"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDhO9dJxtwzOd7vNgOMAfuZalWwnH97HOMzxI34dby7ZepVMQ+xFwPhPVa3Eu72kHVVHnkpXXzrxA3Ri0hk5vUudofSqqdIARuELLoUSaHWmupHQdJiFN1lNb+Rnc4M/+DybYCEJbZWfIRIc/48kg414zZDu7/8waLa1wlWbRKhvWFojHrK86SJcjAagf03aLWAMMqvGNfyCAqJa0jq0AvHLrrPDeigapG0SSUe9P5psCUhwjSx+qOL2yhWPfivlTgdLQryh4t9h0MuQ7AyQtVfn/B+F0js6iB6Cn16QJHA7FJvy3stEEA+XN7shn2l//q0ktQkXoVtgLvJKjvPv+bhAgMBAAECggEAF29liCyjzsSvuI4TMmcmaaERx22RKhsoXMOPzhFaENma10BilJeDdNUwB551tLv1KWUhUDeE/vcIRQxL/Qu26R22JipRe4F5/ne06NV8fFrkE4P6wu/O1XARPbKAtQOOwUq5e4Z9AWS1UuCWzHCZ/+tjQwru//qZ2lFTjGWO8bsTxSVpX86jo+se62HExhWyv/Bdmch1A+w1S13o2EdJ6k6mNDqtXAa4rgzsz1QuQsU1ShBx7sPkD3kTu7pH8uihXnGfqpzOZJ50rRu+zgDhX6DTucHR+LwIQY28kvPlGJORSCzXO4fUnpsiKJfFGUOhvY0hsDXUSNXEdbSemAv4kQKBgQDzXl/TrTzhNrllGFr4zMpPK+dDhC94yOSvPHtIoRqMe4N1GnXKohtv17cZuUJwcP/d4bkb1lDkuS6RmQCWP3Az/PqfGy1Dpgc2au0VVo9dWzsF+/qc/TLiZ/rpUi5IxTArtodc2UVB/z0hRkr8Q12Dd6HMJg1RMM9Zl8MazQCYXQKBgQDs7IJhKrP30ty6GHbTd42WSMHny6o7voKOL6WfRZyjFqNoItwotDxCdO5w5tfGNiCjyAXxlMCnFSf7b0Og8EqysaxRBvnVqP263Mar6WpvZqJvMqb8tM/e13UzWNIy5A75fru2ZYxKx1YSuuu9afcpJLtMpSw02bE46phpGq2QVQKBgDZce00+Ih4waiMh2JhArmQW63aSXUQ+o45dFTC5A8Qjhs6ulWco5LYL2lN9pVCWfSDj3cRREAq3LqbJJW9qRjbmqFjH6kl1GaFXKxQcHLP+v+VrTqSojigyj8J0X/BTU5pAEGZ2BdljGRWIrRFCPu4x18tRKKJdI8PgeW1QI3TlAoGAZM+mEo5HEZZJkW3DceuW9XF0AJtqjg1cJAAmKwVFxydk1HSw1SkL0wNKSgQPoCl77fS4grjW2MFpx9TqI9rbDfoH5lpfiAhmHlK+vnuNbGVxjHszDqgpIxrupbCPkFP2AzdnzA6diVwURdf/YxNptboJcG1/x+UxQZSg0WgmnhkCgYB/MY3JtgmvWR0hkarzwI3G2IT428ObfkrmWL+g7PdpfwNOt7aUQZVhVgH8v8+0A9W++lfeUNHyX8xNVrwriC09aTuIr5J6LC0tbWr9H/GHae2pxfnzGpIZEpu8WCXoMhao1JzjKti3fuNVzrAj2k/JFdNChSp3lKjfJ+B5N+0Kvw\\\\u003d\\\\u003d\"}],\"is_open\":1}', null), ('1703', '微信', 'weixinPayPlugin', '{\"key\":\"pc_config&wap_config\",\"name\":\"是否开启PC和WAP\",\"config_list\":[{\"name\":\"mchid\",\"text\":\"商户号MCHID\",\"value\":\"1272875901\"},{\"name\":\"appid\",\"text\":\"APPID\",\"value\":\"wxde2fcd7d020b05e5\"},{\"name\":\"key\",\"text\":\"API密钥(key)\",\"value\":\"21445a61830e5a640535f86f2ad49ca5\"},{\"name\":\"app_secret\",\"text\":\"应用密钥(AppScret)\",\"value\":\"21445a61830e5a640535f86f2ad49ca5\"},{\"name\":\"p12_path\",\"text\":\"证书路径\",\"value\":\"/opt/weixin/apiclient_cert.p12\"}],\"is_open\":1}', '{\"key\":\"pc_config&wap_config\",\"name\":\"是否开启PC和WAP\",\"config_list\":[{\"name\":\"mchid\",\"text\":\"商户号MCHID\",\"value\":\"1272875901\"},{\"name\":\"appid\",\"text\":\"APPID\",\"value\":\"wxde2fcd7d020b05e5\"},{\"name\":\"key\",\"text\":\"API密钥(key)\",\"value\":\"21445a61830e5a640535f86f2ad49ca5\"},{\"name\":\"app_secret\",\"text\":\"应用密钥(AppScret)\",\"value\":\"21445a61830e5a640535f86f2ad49ca5\"},{\"name\":\"p12_path\",\"text\":\"证书路径\",\"value\":\"/opt/weixin/apiclient_cert.p12\"}],\"is_open\":1}', '{\"key\":\"app_native_config\",\"name\":\"是否开启APP原生\",\"config_list\":[{\"name\":\"mchid\",\"text\":\"商户号MCHID\",\"value\":null},{\"name\":\"appid\",\"text\":\"APPID\",\"value\":null},{\"name\":\"key\",\"text\":\"API密钥(key)\",\"value\":null},{\"name\":\"app_secret\",\"text\":\"应用密钥(AppScret)\",\"value\":null},{\"name\":\"p12_path\",\"text\":\"证书路径\",\"value\":null}],\"is_open\":0}', 'https://javashop-statics.oss-cn-beijing.aliyuncs.com/demo/weixinpay.png', '1', '{\"key\":\"app_react_config\",\"name\":\"是否开启APP-RN\",\"config_list\":[{\"name\":\"mchid\",\"text\":\"商户号MCHID\",\"value\":null},{\"name\":\"appid\",\"text\":\"APPID\",\"value\":null},{\"name\":\"key\",\"text\":\"API密钥(key)\",\"value\":null},{\"name\":\"app_secret\",\"text\":\"应用密钥(AppScret)\",\"value\":null},{\"name\":\"p12_path\",\"text\":\"证书路径\",\"value\":null}],\"is_open\":0}', '{\"key\":\"mini_config\",\"name\":\"是否开启小程序\",\"config_list\":[{\"name\":\"mchid\",\"text\":\"商户号MCHID\",\"value\":null},{\"name\":\"appid\",\"text\":\"APPID\",\"value\":null},{\"name\":\"key\",\"text\":\"API密钥(key)\",\"value\":null},{\"name\":\"app_secret\",\"text\":\"应用密钥(AppScret)\",\"value\":null},{\"name\":\"p12_path\",\"text\":\"证书路径\",\"value\":null}],\"is_open\":0}');
COMMIT;

-- ----------------------------
--  Table structure for `es_promotion_goods`
-- ----------------------------
DROP TABLE IF EXISTS `es_promotion_goods`;
CREATE TABLE `es_promotion_goods` (
  `pg_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '商品对照ID',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品id',
  `product_id` int(10) DEFAULT NULL COMMENT '货品id',
  `start_time` bigint(20) DEFAULT NULL COMMENT '活动开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '活动结束时间',
  `activity_id` int(10) DEFAULT NULL COMMENT '活动id',
  `promotion_type` varchar(50) DEFAULT NULL COMMENT '促销工具类型',
  `title` varchar(50) DEFAULT NULL COMMENT '活动标题',
  `num` int(10) DEFAULT NULL COMMENT '参与活动的商品数量',
  `price` decimal(20,2) DEFAULT NULL COMMENT '活动时商品的价格',
  PRIMARY KEY (`pg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8 COMMENT='有效活动商品对照表(es_promotion_goods)';

-- ----------------------------
--  Table structure for `es_refund`
-- ----------------------------
DROP TABLE IF EXISTS `es_refund`;
CREATE TABLE `es_refund` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '退(货)款单id',
  `sn` varchar(255) DEFAULT NULL COMMENT '退货(款)单编号',
  `member_id` int(10) DEFAULT NULL COMMENT '会员id',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `order_sn` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `refund_status` varchar(255) DEFAULT NULL COMMENT '退(货)款状态',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `refund_point` int(10) DEFAULT NULL COMMENT '退还积分',
  `refund_price` decimal(20,2) DEFAULT NULL COMMENT '退款金额',
  `refund_way` varchar(50) DEFAULT NULL COMMENT '退款方式(原路退回，在线支付，线下支付)',
  `account_type` varchar(50) DEFAULT NULL COMMENT '退款账户类型',
  `return_account` varchar(50) DEFAULT NULL COMMENT '退款账户',
  `customer_remark` varchar(255) DEFAULT NULL COMMENT '客户备注',
  `seller_remark` varchar(255) DEFAULT NULL COMMENT '客服备注',
  `warehouse_remark` varchar(255) DEFAULT NULL COMMENT '库管备注',
  `finance_remark` varchar(255) DEFAULT NULL COMMENT '财务备注',
  `refund_reason` varchar(255) DEFAULT NULL COMMENT '退款原因',
  `refuse_reason` varchar(255) DEFAULT NULL COMMENT '拒绝原因',
  `bank_name` varchar(255) DEFAULT NULL COMMENT '银行名称',
  `bank_account_number` varchar(255) DEFAULT NULL COMMENT '银行账号',
  `bank_account_name` varchar(255) DEFAULT NULL COMMENT '银行开户名',
  `bank_deposit_name` varchar(255) DEFAULT NULL COMMENT '银行开户行',
  `trade_sn` varchar(50) DEFAULT NULL COMMENT '交易编号',
  `refund_type` varchar(50) DEFAULT NULL COMMENT '售后类型(取消订单,申请售后)',
  `pay_order_no` varchar(255) DEFAULT NULL COMMENT '支付结果交易号',
  `refuse_type` varchar(50) DEFAULT NULL COMMENT '退(货)款类型(退货，退款)',
  `payment_type` varchar(50) DEFAULT NULL COMMENT '订单类型(在线支付,货到付款)',
  `refund_fail_reason` varchar(255) DEFAULT NULL COMMENT '退款失败原因',
  `refund_time` bigint(20) DEFAULT NULL COMMENT '退款时间',
  `refund_gift` longtext COMMENT '赠品信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='退(货)款单(es_refund)';

-- ----------------------------
--  Table structure for `es_refund_goods`
-- ----------------------------
DROP TABLE IF EXISTS `es_refund_goods`;
CREATE TABLE `es_refund_goods` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '退货表id',
  `refund_sn` varchar(255) DEFAULT NULL COMMENT '退货(款)编号',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品id',
  `sku_id` int(10) DEFAULT NULL COMMENT '产品id',
  `ship_num` int(10) DEFAULT NULL COMMENT '发货数量',
  `price` decimal(20,2) DEFAULT NULL COMMENT '商品价格',
  `return_num` int(10) DEFAULT NULL COMMENT '退货数量',
  `storage_num` int(10) DEFAULT NULL COMMENT '入库数量',
  `goods_sn` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_image` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `spec_json` longtext COMMENT '规格数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='退货商品表(es_refund_goods)';

-- ----------------------------
--  Table structure for `es_refund_log`
-- ----------------------------
DROP TABLE IF EXISTS `es_refund_log`;
CREATE TABLE `es_refund_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `refund_sn` varchar(255) DEFAULT NULL COMMENT '退款sn',
  `logtime` bigint(20) DEFAULT NULL COMMENT '日志记录时间',
  `logdetail` varchar(255) DEFAULT NULL COMMENT '日志详细',
  `operator` varchar(255) DEFAULT NULL COMMENT '操作者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='退货（款）日志(es_refund_log)';

-- ----------------------------
--  Table structure for `es_seckill`
-- ----------------------------
DROP TABLE IF EXISTS `es_seckill`;
CREATE TABLE `es_seckill` (
  `seckill_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `seckill_name` varchar(50) DEFAULT NULL COMMENT '活动名称',
  `start_day` bigint(10) DEFAULT NULL COMMENT '活动日期',
  `apply_end_time` bigint(10) DEFAULT NULL COMMENT '报名截至时间',
  `seckill_rule` varchar(1000) DEFAULT NULL COMMENT '申请规则',
  `seckill_status` varchar(50) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`seckill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='限时抢购入库(es_seckill)';

-- ----------------------------
--  Table structure for `es_seckill_apply`
-- ----------------------------
DROP TABLE IF EXISTS `es_seckill_apply`;
CREATE TABLE `es_seckill_apply` (
  `apply_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `seckill_id` int(10) DEFAULT NULL COMMENT '活动id',
  `time_line` int(10) DEFAULT NULL COMMENT '时刻',
  `start_day` bigint(11) DEFAULT NULL COMMENT '活动开始日期',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品ID',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `sku_id` int(10) DEFAULT NULL COMMENT '商品规格ID',
  `price` decimal(20,2) DEFAULT NULL COMMENT '价格',
  `sold_quantity` int(10) DEFAULT NULL COMMENT '售空数量',
  `status` varchar(50) DEFAULT NULL COMMENT '申请状态',
  `sales_num` int(10) DEFAULT NULL COMMENT '已售数量',
  `original_price` decimal(20,2) DEFAULT NULL COMMENT '商品原始价格',
  PRIMARY KEY (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COMMENT='限时抢购申请(es_seckill_apply)';

-- ----------------------------
--  Table structure for `es_seckill_range`
-- ----------------------------
DROP TABLE IF EXISTS `es_seckill_range`;
CREATE TABLE `es_seckill_range` (
  `range_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seckill_id` int(10) DEFAULT NULL COMMENT '限时抢购活动id',
  `range_time` int(10) DEFAULT NULL COMMENT '整点时刻',
  PRIMARY KEY (`range_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='限时抢购时刻(es_seckill_range)';

-- ----------------------------
--  Table structure for `es_trade`
-- ----------------------------
DROP TABLE IF EXISTS `es_trade`;
CREATE TABLE `es_trade` (
  `trade_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'trade_id',
  `trade_sn` varchar(50) DEFAULT NULL COMMENT '交易编号',
  `member_id` int(10) DEFAULT NULL COMMENT '买家id',
  `member_name` varchar(100) DEFAULT NULL COMMENT '买家用户名',
  `payment_method_id` varchar(50) DEFAULT NULL COMMENT '支付方式id',
  `payment_plugin_id` varchar(50) DEFAULT NULL COMMENT '支付插件id',
  `payment_method_name` varchar(50) DEFAULT NULL COMMENT '支付方式名称',
  `payment_type` varchar(20) DEFAULT NULL COMMENT '支付方式类型',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '总价格',
  `goods_price` decimal(20,2) DEFAULT NULL COMMENT '商品价格',
  `freight_price` decimal(20,2) DEFAULT NULL COMMENT '运费',
  `discount_price` decimal(20,2) DEFAULT NULL COMMENT '优惠的金额',
  `consignee_id` int(10) DEFAULT NULL COMMENT '收货人id',
  `consignee_name` varchar(50) DEFAULT NULL COMMENT '收货人姓名',
  `consignee_country` varchar(50) DEFAULT NULL COMMENT '收货国家',
  `consignee_country_id` int(10) DEFAULT NULL COMMENT '收货国家id',
  `consignee_province` varchar(50) DEFAULT NULL COMMENT '收货省',
  `consignee_province_id` int(10) DEFAULT NULL COMMENT '收货省id',
  `consignee_city` varchar(50) DEFAULT NULL COMMENT '收货市',
  `consignee_city_id` int(10) DEFAULT NULL COMMENT '收货市id',
  `consignee_county` varchar(50) DEFAULT NULL COMMENT '收货区',
  `consignee_county_id` int(10) DEFAULT NULL COMMENT '收货区id',
  `consignee_town` varchar(50) DEFAULT NULL COMMENT '收货镇',
  `consignee_town_id` int(10) DEFAULT NULL COMMENT '收货镇id',
  `consignee_address` varchar(255) DEFAULT NULL COMMENT '收货详细地址',
  `consignee_mobile` varchar(50) DEFAULT NULL COMMENT '收货人手机号',
  `consignee_telephone` varchar(50) DEFAULT NULL COMMENT '收货人电话',
  `create_time` bigint(20) DEFAULT NULL COMMENT '交易创建时间',
  `order_json` longtext COMMENT '订单json(预留，7.0可能废弃)',
  `trade_status` varchar(50) DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`trade_id`) USING BTREE,
  INDEX `ind_trade_sn` (`trade_sn`) USING BTREE,
  INDEX `index_trade`(`trade_id`, `member_id`, `payment_method_id`, `payment_plugin_id`, `trade_status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='交易表(es_trade)';

-- ----------------------------
--  Table structure for `es_transaction_record`
-- ----------------------------
DROP TABLE IF EXISTS `es_transaction_record`;
CREATE TABLE `es_transaction_record` (
  `record_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_sn` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品ID',
  `goods_num` int(10) DEFAULT NULL COMMENT '商品数量',
  `rog_time` bigint(20) DEFAULT NULL COMMENT '确认收货时间',
  `uname` varchar(50) DEFAULT NULL COMMENT '用户名',
  `price` decimal(20,2) DEFAULT NULL COMMENT '交易价格',
  `member_id` int(10) DEFAULT NULL COMMENT '会员ID',
  PRIMARY KEY (`record_id`),
  KEY `index_goods_id` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='交易记录表(es_transaction_record)';

SET FOREIGN_KEY_CHECKS = 1;
