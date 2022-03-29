/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Version : 80015
 Source Host           : localhost
 Source Database       : test

 Target Server Version : 80015
 File Encoding         : utf-8

 Date: 08/08/2019 14:35:48 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `es_sss_goods_data`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_goods_data`;
CREATE TABLE `es_sss_goods_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `brand_id` int(10) DEFAULT NULL COMMENT '品牌id',
  `category_id` int(10) DEFAULT NULL COMMENT '分类id',
  `category_path` varchar(255) DEFAULT NULL COMMENT '分类路径',
  `price` decimal(20,2) DEFAULT NULL COMMENT '商品价格',
  `favorite_num` int(10) DEFAULT NULL COMMENT '收藏数量',
  `market_enable` smallint(1) DEFAULT NULL COMMENT '是否上架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8 COMMENT='统计库商品数据(es_sss_goods_data)';

-- ----------------------------
--  Table structure for `es_sss_goods_pv`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_goods_pv`;
CREATE TABLE `es_sss_goods_pv` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `vs_year` int(10) DEFAULT NULL COMMENT '年份',
  `vs_num` int(20) DEFAULT NULL COMMENT '访问量',
  `vs_month` int(10) DEFAULT NULL COMMENT '月份',
  `vs_day` int(10) DEFAULT NULL COMMENT '天',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='商品访问量统计表(es_sss_goods_pv)';

-- ----------------------------
--  Table structure for `es_sss_goods_pv_2018`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_goods_pv_2018`;
CREATE TABLE `es_sss_goods_pv_2018` (
  `id` int(10) NOT NULL DEFAULT '0',
  `goods_id` int(10) DEFAULT NULL,
  `goods_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `vs_year` int(10) DEFAULT NULL,
  `vs_month` int(10) DEFAULT NULL,
  `vs_day` int(10) DEFAULT NULL,
  `vs_num` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `es_sss_goods_pv_2019`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_goods_pv_2019`;
CREATE TABLE `es_sss_goods_pv_2019` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `goods_id` int(10) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `vs_year` int(10) DEFAULT NULL,
  `vs_month` int(10) DEFAULT NULL,
  `vs_day` int(10) DEFAULT NULL,
  `vs_num` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1298 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `es_sss_member_register_data`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_member_register_data`;
CREATE TABLE `es_sss_member_register_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(10) DEFAULT NULL COMMENT '会员id',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名字',
  `create_time` int(12) DEFAULT NULL COMMENT '注册日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='注册会员信息(es_sss_member_register_data)';

-- ----------------------------
--  Table structure for `es_sss_order_data`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_order_data`;
CREATE TABLE `es_sss_order_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sn` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `buyer_id` int(10) DEFAULT NULL COMMENT '会员id',
  `buyer_name` varchar(255) DEFAULT NULL COMMENT '商家名称',
  `order_status` varchar(255) DEFAULT NULL COMMENT '订单状态',
  `pay_status` varchar(255) DEFAULT NULL COMMENT '付款状态',
  `order_price` decimal(20,2) DEFAULT NULL COMMENT '订单金额',
  `goods_num` int(10) DEFAULT NULL COMMENT '订单商品数量',
  `ship_province_id` int(10) DEFAULT NULL COMMENT '省id',
  `ship_city_id` int(10) DEFAULT NULL COMMENT '市id',
  `create_time` int(12) DEFAULT NULL COMMENT '订单创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='订单数据(es_sss_order_data)';

-- ----------------------------
--  Table structure for `es_sss_order_data_2018`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_order_data_2018`;
CREATE TABLE `es_sss_order_data_2018` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sn` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `buyer_id` int(10) DEFAULT NULL COMMENT '会员id',
  `buyer_name` varchar(255) DEFAULT NULL COMMENT '商家名称',
  `order_status` varchar(255) DEFAULT NULL COMMENT '订单状态',
  `pay_status` varchar(255) DEFAULT NULL COMMENT '付款状态',
  `order_price` decimal(20,2) DEFAULT NULL COMMENT '订单金额',
  `goods_num` int(10) DEFAULT NULL COMMENT '订单商品数量',
  `ship_province_id` int(10) DEFAULT NULL COMMENT '省id',
  `ship_city_id` int(10) DEFAULT NULL COMMENT '市id',
  `create_time` int(12) DEFAULT NULL COMMENT '订单创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `es_sss_order_data_2019`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_order_data_2019`;
CREATE TABLE `es_sss_order_data_2019` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `sn` varchar(255) DEFAULT NULL,
  `buyer_id` int(10) DEFAULT NULL,
  `buyer_name` varchar(255) DEFAULT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `pay_status` varchar(255) DEFAULT NULL,
  `order_price` decimal(20,2) DEFAULT NULL,
  `goods_num` int(10) DEFAULT NULL,
  `ship_province_id` int(10) DEFAULT NULL,
  `ship_city_id` int(10) DEFAULT NULL,
  `create_time` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `es_sss_order_goods_data`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_order_goods_data`;
CREATE TABLE `es_sss_order_goods_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_num` int(10) DEFAULT NULL COMMENT '数量',
  `price` decimal(20,2) DEFAULT NULL COMMENT '商品单价',
  `sub_total` decimal(20,2) DEFAULT NULL COMMENT '小计',
  `category_path` varchar(255) DEFAULT NULL COMMENT '分类path',
  `category_id` int(10) DEFAULT NULL COMMENT '分类id',
  `create_time` int(12) DEFAULT NULL COMMENT '创建时间',
  `industry_id` bigint(10) DEFAULT NULL COMMENT '行业id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='订单商品表(es_sss_order_goods_data)';

-- ----------------------------
--  Table structure for `es_sss_order_goods_data_2018`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_order_goods_data_2018`;
CREATE TABLE `es_sss_order_goods_data_2018` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_num` int(10) DEFAULT NULL COMMENT '数量',
  `price` decimal(20,2) DEFAULT NULL COMMENT '商品单价',
  `sub_total` decimal(20,2) DEFAULT NULL COMMENT '小计',
  `category_path` varchar(255) DEFAULT NULL COMMENT '分类path',
  `category_id` int(10) DEFAULT NULL COMMENT '分类id',
  `create_time` int(12) DEFAULT NULL COMMENT '创建时间',
  `industry_id` bigint(10) DEFAULT NULL COMMENT '行业id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `es_sss_order_goods_data_2019`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_order_goods_data_2019`;
CREATE TABLE `es_sss_order_goods_data_2019` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(255) DEFAULT NULL,
  `goods_id` int(10) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_num` int(10) DEFAULT NULL,
  `price` decimal(20,2) DEFAULT NULL,
  `sub_total` decimal(20,2) DEFAULT NULL,
  `category_path` varchar(255) DEFAULT NULL,
  `category_id` int(10) DEFAULT '0',
  `create_time` int(12) DEFAULT '0',
  `industry_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `es_sss_refund_data`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_refund_data`;
CREATE TABLE `es_sss_refund_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` varchar(255) DEFAULT NULL COMMENT '订单sn',
  `refund_sn` varchar(255) DEFAULT NULL COMMENT '售后订单sn',
  `refund_price` decimal(20,2) DEFAULT NULL COMMENT '退还金额',
  `create_time` int(12) DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='退货数据(es_sss_refund_data)';

-- ----------------------------
--  Records of `es_sss_refund_data`
-- ----------------------------
BEGIN;
INSERT INTO `es_sss_refund_data` VALUES ('1', '180830025128', '20180830000003', '3199.00', '1535611888'), ('2', '180830024026', '20180830000002', '2998.00', '1535611226');
COMMIT;

-- ----------------------------
--  Table structure for `es_sss_refund_data_2018`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_refund_data_2018`;
CREATE TABLE `es_sss_refund_data_2018` (
  `id` int(10) NOT NULL DEFAULT '0',
  `refund_sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `order_sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `refund_price` decimal(20,2) DEFAULT NULL,
  `create_time` int(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `es_sss_refund_data_2019`
-- ----------------------------
DROP TABLE IF EXISTS `es_sss_refund_data_2019`;
CREATE TABLE `es_sss_refund_data_2019` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `refund_sn` varchar(255) DEFAULT NULL,
  `order_sn` varchar(255) DEFAULT NULL,
  `refund_price` decimal(20,2) DEFAULT NULL,
  `create_time` int(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
