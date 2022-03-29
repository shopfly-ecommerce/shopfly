/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Version : 80015
 Source Host           : localhost
 Source Database       : test

 Target Server Version : 80015
 File Encoding         : utf-8

 Date: 08/08/2019 14:18:31 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `es_bill_member`
-- ----------------------------
DROP TABLE IF EXISTS `es_bill_member`;
CREATE TABLE `es_bill_member` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `total_id` int(10) DEFAULT NULL COMMENT '总结算单id',
  `member_id` int(10) DEFAULT NULL COMMENT '会员id',
  `start_time` int(10) DEFAULT NULL COMMENT '开始时间',
  `end_time` int(10) DEFAULT NULL COMMENT '结束时间',
  `final_money` decimal(10,2) DEFAULT NULL COMMENT '最终结算金额',
  `push_money` decimal(10,2) DEFAULT NULL COMMENT '提成金额',
  `order_money` decimal(10,2) DEFAULT NULL COMMENT '订单金额',
  `return_order_money` decimal(10,2) DEFAULT NULL COMMENT '订单返还金额',
  `return_push_money` decimal(10,2) DEFAULT NULL COMMENT '返还提成金额',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `sn` varchar(255) DEFAULT NULL COMMENT '编号',
  `order_count` int(10) DEFAULT NULL COMMENT '订单数',
  `return_order_count` int(10) DEFAULT NULL COMMENT '返还订单数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='个人业绩单(es_bill_member)';

-- ----------------------------
--  Table structure for `es_bill_total`
-- ----------------------------
DROP TABLE IF EXISTS `es_bill_total`;
CREATE TABLE `es_bill_total` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `start_time` bigint(12) DEFAULT NULL COMMENT '开始时间',
  `end_time` bigint(12) DEFAULT NULL COMMENT '结束时间',
  `order_count` bigint(12) DEFAULT NULL COMMENT '订单数量',
  `return_order_count` int(10) DEFAULT NULL COMMENT '退还订单数量',
  `final_money` decimal(20,2) DEFAULT NULL COMMENT '结算金额',
  `push_money` decimal(20,2) DEFAULT NULL COMMENT '提成金额',
  `return_push_money` decimal(20,2) DEFAULT NULL COMMENT '退还提成金额',
  `order_money` decimal(20,2) DEFAULT NULL COMMENT '订单金额',
  `return_order_money` decimal(20,2) DEFAULT NULL COMMENT '退还订单金额',
  `sn` varchar(255) DEFAULT NULL COMMENT '编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='总业绩单(es_bill_total)';

-- ----------------------------
--  Table structure for `es_commission_tpl`
-- ----------------------------
DROP TABLE IF EXISTS `es_commission_tpl`;
CREATE TABLE `es_commission_tpl` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tpl_name` varchar(20) DEFAULT NULL COMMENT '名称',
  `tpl_describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `switch_model` varchar(255) DEFAULT NULL COMMENT '切换模式',
  `profit` bigint(10) DEFAULT NULL COMMENT '利润率要求',
  `num` bigint(10) DEFAULT NULL COMMENT '下限人数',
  `cycle` int(10) DEFAULT NULL COMMENT '周期',
  `grade1` decimal(10,2) DEFAULT NULL COMMENT '1级返利',
  `grade2` decimal(10,2) DEFAULT NULL COMMENT '2级返利',
  `is_default` smallint(1) DEFAULT NULL COMMENT '是否默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='模版(es_commission_tpl)';

-- ----------------------------
--  Records of `es_commission_tpl`
-- ----------------------------
BEGIN;
INSERT INTO `es_commission_tpl` VALUES ('1', '小伙伴', '提成模板', '0', '1000', '1', '30', '1.00', '2.00', '1'), ('2', '合伙人', '提成模板', '0', '5000', '2', '200', '2.00', '3.00', '0');
COMMIT;

-- ----------------------------
--  Table structure for `es_distribution`
-- ----------------------------
DROP TABLE IF EXISTS `es_distribution`;
CREATE TABLE `es_distribution` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(11) DEFAULT NULL COMMENT '会员id',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `path` varchar(255) DEFAULT NULL COMMENT '关系路径',
  `member_id_lv2` bigint(10) DEFAULT NULL COMMENT '上上级',
  `member_id_lv1` bigint(10) DEFAULT NULL COMMENT '上级',
  `downline` bigint(10) DEFAULT NULL COMMENT '下线人数',
  `order_num` bigint(10) DEFAULT NULL COMMENT '订单数',
  `rebate_total` decimal(20,2) DEFAULT NULL COMMENT '返利总额',
  `turnover_price` decimal(20,2) DEFAULT NULL COMMENT '营业额总额',
  `can_rebate` decimal(20,2) DEFAULT NULL COMMENT '可提现金额',
  `commission_frozen` decimal(20,2) DEFAULT NULL COMMENT '返利金额冻结',
  `current_tpl_name` varchar(255) DEFAULT NULL COMMENT '使用模版名称',
  `current_tpl_id` int(11) DEFAULT NULL COMMENT '使用模版',
  `withdraw_frozen_price` decimal(20,2) DEFAULT NULL COMMENT '提现冻结',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='分销商(es_distribution)';

-- ----------------------------
--  Table structure for `es_distribution_goods`
-- ----------------------------
DROP TABLE IF EXISTS `es_distribution_goods`;
CREATE TABLE `es_distribution_goods` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `goods_id` bigint(10) DEFAULT NULL COMMENT '商品id',
  `grade1_rebate` decimal(20,2) DEFAULT NULL COMMENT '一级返现',
  `grade2_rebate` decimal(20,2) DEFAULT NULL COMMENT '二级返现',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='分销商品返现(es_distribution_goods)';

-- ----------------------------
--  Table structure for `es_distribution_order`
-- ----------------------------
DROP TABLE IF EXISTS `es_distribution_order`;
CREATE TABLE `es_distribution_order` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(10) DEFAULT NULL COMMENT '订单id',
  `order_sn` varchar(255) DEFAULT NULL COMMENT '订单sn',
  `buyer_member_id` bigint(20) DEFAULT NULL COMMENT '购买会员id',
  `buyer_member_name` varchar(255) DEFAULT NULL COMMENT '购买会员名称',
  `member_id_lv1` bigint(20) DEFAULT NULL COMMENT '一级分销商id',
  `member_id_lv2` bigint(20) DEFAULT NULL COMMENT '二级分销商id',
  `bill_id` bigint(20) DEFAULT NULL COMMENT '结算单id',
  `settle_cycle` bigint(20) DEFAULT NULL COMMENT '解冻日期',
  `create_time` bigint(20) DEFAULT NULL COMMENT '订单创建时间',
  `order_price` decimal(20,2) DEFAULT NULL COMMENT '订单金额',
  `grade1_rebate` decimal(20,2) DEFAULT NULL COMMENT '1级提成金额',
  `grade2_rebate` decimal(20,2) DEFAULT NULL COMMENT '2级提成金额',
  `grade1_sellback_price` decimal(20,2) DEFAULT NULL COMMENT '1级退款金额',
  `grade2_sellback_price` decimal(20,2) DEFAULT NULL COMMENT '2级退款金额',
  `is_return` smallint(1) DEFAULT NULL COMMENT '是否退货',
  `return_money` decimal(20,2) DEFAULT NULL COMMENT '退款金额',
  `is_withdraw` smallint(1) DEFAULT '0' COMMENT '是否已经提现',
  `lv1_point` decimal(10,2) DEFAULT NULL COMMENT '一级饭点比例',
  `lv2_point` decimal(10,2) DEFAULT NULL COMMENT '二级返点比例',
  `goods_rebate` longtext COMMENT '商品返现描述详细',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='分销订单(es_distribution_order)';

-- ----------------------------
--  Table structure for `es_seller_bill`
-- ----------------------------
DROP TABLE IF EXISTS `es_seller_bill`;
CREATE TABLE `es_seller_bill` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `order_sn` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `expenditure` decimal(20,2) DEFAULT NULL COMMENT '商品反现支出',
  `return_expenditure` decimal(20,2) DEFAULT NULL COMMENT '返还商品反现支出',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='店铺结算(es_seller_bill)';

-- ----------------------------
--  Table structure for `es_short_url`
-- ----------------------------
DROP TABLE IF EXISTS `es_short_url`;
CREATE TABLE `es_short_url` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `url` varchar(255) DEFAULT NULL COMMENT '跳转地址',
  `su` varchar(255) DEFAULT NULL COMMENT '短链接key',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='短链接(es_short_url)';

-- ----------------------------
--  Table structure for `es_upgrade_log`
-- ----------------------------
DROP TABLE IF EXISTS `es_upgrade_log`;
CREATE TABLE `es_upgrade_log` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(10) DEFAULT NULL COMMENT '会员id',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `type` varchar(255) DEFAULT NULL COMMENT '切换类型',
  `old_tpl_id` bigint(20) DEFAULT NULL COMMENT '旧的模板id',
  `new_tpl_id` bigint(20) DEFAULT NULL COMMENT '新的模板id',
  `new_tpl_name` varchar(255) DEFAULT NULL COMMENT '新的模板名称',
  `old_tpl_name` varchar(255) DEFAULT NULL COMMENT '旧的模板名称',
  `create_time` int(20) DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='升级日志(es_upgrade_log)';

-- ----------------------------
--  Table structure for `es_withdraw_apply`
-- ----------------------------
DROP TABLE IF EXISTS `es_withdraw_apply`;
CREATE TABLE `es_withdraw_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `apply_money` decimal(20,2) DEFAULT NULL COMMENT '提现金额',
  `member_id` int(11) DEFAULT NULL COMMENT '会员id',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `apply_remark` varchar(255) DEFAULT NULL COMMENT '申请备注',
  `inspect_remark` varchar(255) DEFAULT NULL COMMENT '审核备注',
  `transfer_remark` varchar(255) DEFAULT NULL COMMENT '转账备注',
  `apply_time` int(11) DEFAULT NULL COMMENT '申请时间',
  `inspect_time` int(11) DEFAULT NULL COMMENT '审核时间',
  `transfer_time` int(11) DEFAULT NULL COMMENT '转账时间',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='提现申请(es_withdraw_apply)';

-- ----------------------------
--  Table structure for `es_withdraw_setting`
-- ----------------------------
DROP TABLE IF EXISTS `es_withdraw_setting`;
CREATE TABLE `es_withdraw_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(11) DEFAULT NULL COMMENT '用户id',
  `param` varchar(255) DEFAULT NULL COMMENT '参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='分销提现设置(es_withdraw_setting)';

SET FOREIGN_KEY_CHECKS = 1;
