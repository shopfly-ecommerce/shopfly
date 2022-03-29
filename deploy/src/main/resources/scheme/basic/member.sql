/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Version : 80015
 Source Host           : localhost
 Source Database       : test

 Target Server Version : 80015
 File Encoding         : utf-8

 Date: 08/08/2019 14:30:42 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `es_comment_gallery`
-- ----------------------------
DROP TABLE IF EXISTS `es_comment_gallery`;
CREATE TABLE `es_comment_gallery` (
  `img_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `comment_id` int(10) DEFAULT NULL COMMENT '主键',
  `original` varchar(255) DEFAULT NULL COMMENT '图片路径',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `img_belong` int(10) DEFAULT '0' COMMENT '图片所属 0：初评，1：追评',
  PRIMARY KEY (`img_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论图片(es_comment_gallery)';

-- ----------------------------
--  Table structure for `es_comment_reply`
-- ----------------------------
DROP TABLE IF EXISTS `es_comment_reply`;
CREATE TABLE `es_comment_reply` (
  `reply_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `comment_id` int(10) DEFAULT NULL COMMENT '评论id',
  `parent_id` int(10) DEFAULT NULL COMMENT '回复父id',
  `content` longtext COMMENT '回复内容',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `role` varchar(50) DEFAULT NULL COMMENT '商家或者买家',
  `path` varchar(100) DEFAULT NULL COMMENT '父子路径0|10|',
  PRIMARY KEY (`reply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论回复(es_comment_reply)';

-- ----------------------------
--  Table structure for `es_connect`
-- ----------------------------
DROP TABLE IF EXISTS `es_connect`;
CREATE TABLE `es_connect` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(50) DEFAULT NULL COMMENT '会员id',
  `union_id` varchar(50) DEFAULT NULL COMMENT '第三方唯一标示',
  `union_type` varchar(50) DEFAULT NULL COMMENT '信任登录类型',
  `unbound_time` bigint(50) DEFAULT NULL COMMENT '解绑时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信任登录(es_connect)';

-- ----------------------------
--  Table structure for `es_connect_setting`
-- ----------------------------
DROP TABLE IF EXISTS `es_connect_setting`;
CREATE TABLE `es_connect_setting` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(50) DEFAULT NULL COMMENT '授权类型',
  `config` longtext COMMENT '参数组',
  `name` varchar(50) DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='信任登录参数(es_connect_setting)';

-- ----------------------------
--  Table structure for `es_member`
-- ----------------------------
DROP TABLE IF EXISTS `es_member`;
CREATE TABLE `es_member` (
  `member_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `uname` varchar(255) DEFAULT NULL COMMENT '会员登陆用户名',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) DEFAULT NULL COMMENT '会员登陆密码',
  `create_time` bigint(20) DEFAULT NULL COMMENT '会员注册时间',
  `sex` int(1) DEFAULT NULL COMMENT '会员性别',
  `birthday` bigint(20) DEFAULT NULL COMMENT '会员生日',
  `province_id` int(10) DEFAULT NULL COMMENT '所属省份ID',
  `city_id` int(10) DEFAULT NULL COMMENT '所属城市ID',
  `county_id` int(10) DEFAULT NULL COMMENT '所属县(区)ID',
  `town_id` int(10) DEFAULT NULL COMMENT '所属城镇ID',
  `province` varchar(255) DEFAULT NULL COMMENT '所属省份名称',
  `city` varchar(255) DEFAULT NULL COMMENT '所属城市名称',
  `county` varchar(255) DEFAULT NULL COMMENT '所属县(区)名称',
  `town` varchar(255) DEFAULT NULL COMMENT '所属城镇名称',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `tel` varchar(255) DEFAULT NULL COMMENT '座机号码',
  `grade_point` bigint(20) DEFAULT NULL COMMENT '等级积分',
  `msn` varchar(255) DEFAULT NULL COMMENT '会员MSN',
  `remark` varchar(255) DEFAULT NULL COMMENT '会员备注',
  `last_login` bigint(20) DEFAULT NULL COMMENT '上次登陆时间',
  `login_count` int(10) DEFAULT NULL COMMENT '登陆次数',
  `is_cheked` int(1) DEFAULT NULL COMMENT '邮件是否已验证',
  `register_ip` varchar(255) DEFAULT NULL COMMENT '注册IP地址',
  `recommend_point_state` int(10) DEFAULT NULL COMMENT '是否已经完成了推荐积分',
  `info_full` int(1) DEFAULT NULL COMMENT '会员信息是否完善',
  `find_code` varchar(255) DEFAULT NULL COMMENT 'find_code',
  `face` varchar(255) DEFAULT NULL COMMENT '会员头像',
  `midentity` int(10) DEFAULT NULL COMMENT '身份证号',
  `disabled` int(8) DEFAULT NULL COMMENT '会员状态',
  `consum_point` bigint(20) DEFAULT NULL COMMENT '消费积分',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`member_id`) USING BTREE,
  INDEX `ind_member_uname`(`uname`) USING BTREE,
  INDEX `ind_member_mobile`(`mobile`) USING BTREE,
  INDEX `ind_member_email`(`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='会员表(es_member)';

-- ----------------------------
--  Table structure for `es_member_address`
-- ----------------------------
DROP TABLE IF EXISTS `es_member_address`;
CREATE TABLE `es_member_address` (
  `addr_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(10) DEFAULT NULL COMMENT '会员ID',
  `name` varchar(255) DEFAULT NULL COMMENT '收货人姓名',
  `country` varchar(255) DEFAULT NULL COMMENT '收货人国籍',
  `province_id` int(10) DEFAULT NULL COMMENT '所属省份ID',
  `city_id` int(10) DEFAULT NULL COMMENT '所属城市ID',
  `county_id` int(10) DEFAULT NULL COMMENT '所属县(区)ID',
  `town_id` int(10) DEFAULT NULL COMMENT '所属城镇ID',
  `county` varchar(255) DEFAULT NULL COMMENT '所属县(区)名称',
  `city` varchar(255) DEFAULT NULL COMMENT '所属城市名称',
  `province` varchar(255) DEFAULT NULL COMMENT '所属省份名称',
  `town` varchar(255) DEFAULT NULL COMMENT '所属城镇名称',
  `addr` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系电话(一般指座机)',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `def_addr` int(1) DEFAULT NULL COMMENT '是否为默认收货地址',
  `ship_address_name` varchar(255) DEFAULT NULL COMMENT '地址别名',
  PRIMARY KEY (`addr_id`),
  KEY `ind_mem_addr` (`member_id`,`def_addr`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='会员收货地址表(es_member_address)';

-- ----------------------------
--  Table structure for `es_member_ask`
-- ----------------------------
DROP TABLE IF EXISTS `es_member_ask`;
CREATE TABLE `es_member_ask` (
  `ask_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品id',
  `member_id` int(10) DEFAULT NULL COMMENT '会员id',
  `content` longtext COMMENT '咨询内容',
  `create_time` bigint(20) DEFAULT NULL COMMENT '咨询时间',
  `reply` longtext COMMENT '回复内容',
  `reply_time` bigint(20) DEFAULT NULL COMMENT '回复时间',
  `reply_status` smallint(1) DEFAULT NULL COMMENT '回复状态 1 已回复 2 未回复',
  `status` smallint(1) DEFAULT NULL COMMENT '状态 ',
  `member_name` varchar(100) DEFAULT NULL COMMENT '卖家名称',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `member_face` varchar(255) DEFAULT NULL COMMENT '会员头像',
  `auth_status` varchar(255) DEFAULT NULL COMMENT '审核状态:PASS_AUDIT(审核通过),REFUSE_AUDIT(审核拒绝)',
  PRIMARY KEY (`ask_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='咨询(es_member_ask)';

-- ----------------------------
--  Table structure for `es_member_collection_goods`
-- ----------------------------
DROP TABLE IF EXISTS `es_member_collection_goods`;
CREATE TABLE `es_member_collection_goods` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(10) DEFAULT NULL COMMENT '会员ID',
  `goods_id` int(10) DEFAULT NULL COMMENT '收藏商品ID',
  `create_time` bigint(20) DEFAULT NULL COMMENT '收藏商品时间',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_price` decimal(20,2) DEFAULT NULL COMMENT '商品价格',
  `goods_sn` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `goods_img` varchar(255) DEFAULT NULL COMMENT '商品图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员商品收藏表(es_member_collection_goods)';

-- ----------------------------
--  Table structure for `es_member_comment`
-- ----------------------------
DROP TABLE IF EXISTS `es_member_comment`;
CREATE TABLE `es_member_comment` (
  `comment_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '评论主键',
  `goods_id` int(10) DEFAULT NULL COMMENT '商品id',
  `sku_id` int(10) DEFAULT NULL COMMENT 'skuid',
  `member_id` int(10) DEFAULT NULL COMMENT '会员id',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `member_face` varchar(255) DEFAULT NULL COMMENT '会员头像',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `content` longtext COMMENT '评论内容',
  `create_time` bigint(20) DEFAULT NULL COMMENT '评论时间',
  `have_image` smallint(1) DEFAULT NULL COMMENT '是否有图片 1 有 0 没有',
  `status` smallint(1) DEFAULT NULL COMMENT '状态  1 正常 0 删除 ',
  `grade` varchar(50) DEFAULT NULL COMMENT '好中差评',
  `order_sn` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `reply_status` smallint(1) DEFAULT NULL COMMENT '是否回复 1 是 0 否',
  `additional_status` int(10) DEFAULT '0' COMMENT '追加评论状态 0：未追加，1：已追加',
  `additional_content` longtext COMMENT '追加的评论内容',
  `additional_time` bigint(20) DEFAULT NULL COMMENT '追加评论时间',
  `additional_have_image` int(10) DEFAULT NULL COMMENT '追加的评论是否上传了图片 0：未上传，1：已上传',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='评论(es_member_comment)';

-- ----------------------------
--  Table structure for `es_member_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `es_member_coupon`;
CREATE TABLE `es_member_coupon` (
  `mc_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `coupon_id` int(10) DEFAULT NULL COMMENT '优惠券表主键',
  `member_id` int(10) DEFAULT NULL COMMENT '会员主键id',
  `used_time` bigint(11) DEFAULT NULL COMMENT '使用时间',
  `create_time` bigint(11) DEFAULT NULL COMMENT '领取时间',
  `order_id` int(10) DEFAULT NULL COMMENT '订单主键',
  `order_sn` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `member_name` varchar(30) DEFAULT NULL COMMENT '会员昵称',
  `title` varchar(20) DEFAULT NULL COMMENT '优惠券名称',
  `coupon_price` decimal(10,2) DEFAULT NULL COMMENT '优惠券面额',
  `coupon_threshold_price` decimal(10,2) DEFAULT NULL COMMENT '优惠券门槛金额',
  `start_time` bigint(11) DEFAULT NULL COMMENT '使用起始时间',
  `end_time` bigint(11) DEFAULT NULL COMMENT '使用截止时间',
  `used_status` smallint(1) DEFAULT NULL COMMENT '使用状态',
  PRIMARY KEY (`mc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='会员优惠券(es_member_coupon)';

-- ----------------------------
--  Table structure for `es_member_notice_log`
-- ----------------------------
DROP TABLE IF EXISTS `es_member_notice_log`;
CREATE TABLE `es_member_notice_log` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '会员历史消息id',
  `member_id` int(8) DEFAULT NULL COMMENT '会员id',
  `content` longtext COMMENT '站内信内容',
  `send_time` bigint(20) DEFAULT NULL COMMENT '发送时间',
  `is_del` int(2) DEFAULT NULL COMMENT '是否删除，0删除，1没有删除',
  `is_read` int(2) DEFAULT NULL COMMENT '是否已读，0未读，1已读',
  `receive_time` bigint(20) DEFAULT NULL COMMENT '接收时间',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=323 DEFAULT CHARSET=utf8 COMMENT='会员站内消息历史(es_member_notice_log)';

-- ----------------------------
--  Table structure for `es_member_point_history`
-- ----------------------------
DROP TABLE IF EXISTS `es_member_point_history`;
CREATE TABLE `es_member_point_history` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(10) DEFAULT NULL COMMENT '会员ID',
  `grade_point` bigint(20) DEFAULT NULL COMMENT '等级积分',
  `time` bigint(20) DEFAULT NULL COMMENT '操作时间',
  `reason` varchar(255) DEFAULT NULL COMMENT '操作理由',
  `grade_point_type` int(2) DEFAULT NULL COMMENT '等级积分类型',
  `operator` varchar(255) DEFAULT NULL COMMENT '操作者',
  `consum_point` bigint(20) DEFAULT NULL COMMENT '消费积分',
  `consum_point_type` int(2) DEFAULT NULL COMMENT '消费积分类型',
  PRIMARY KEY (`id`),
  KEY `ind_ponit_history` (`member_id`,`grade_point_type`,`consum_point_type`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8 COMMENT='会员积分表(es_member_point_history)';

-- ----------------------------
--  Table structure for `es_member_receipt`
-- ----------------------------
DROP TABLE IF EXISTS `es_member_receipt`;
CREATE TABLE `es_member_receipt` (
  `receipt_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '会员发票id',
  `member_id` int(8) DEFAULT NULL COMMENT '会员id',
  `receipt_type` varchar(50) DEFAULT NULL COMMENT '发票类型',
  `receipt_title` varchar(255) DEFAULT NULL COMMENT '发票抬头',
  `receipt_content` varchar(255) DEFAULT NULL COMMENT '发票内容',
  `tax_no` varchar(255) DEFAULT NULL COMMENT '发票税号',
  `reg_addr` varchar(255) DEFAULT NULL COMMENT '注册地址',
  `reg_tel` varchar(255) DEFAULT NULL COMMENT '注册电话',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(255) DEFAULT NULL COMMENT '银行账户',
  `is_default` int(2) NOT NULL COMMENT '是否为默认',
  PRIMARY KEY (`receipt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='会员发票(es_member_receipt)';

-- ----------------------------
--  Table structure for `es_notice_log`
-- ----------------------------
DROP TABLE IF EXISTS `es_notice_log`;
CREATE TABLE `es_notice_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `notice_content` varchar(255) DEFAULT NULL COMMENT '站内信内容',
  `send_time` bigint(50) DEFAULT NULL COMMENT '发送时间',
  `is_delete` int(10) DEFAULT NULL COMMENT '是否删除 ：1 删除   0  未删除',
  `is_read` int(10) DEFAULT NULL COMMENT '是否已读 ：1已读   0 未读',
  `type` varchar(10) DEFAULT NULL COMMENT '消息类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8 COMMENT='店铺站内消息(es_notice_log)';

-- ----------------------------
--  Table structure for `es_receipt_history`
-- ----------------------------
DROP TABLE IF EXISTS `es_receipt_history`;
CREATE TABLE `es_receipt_history` (
  `history_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '发票历史id',
  `order_sn` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `member_id` int(8) DEFAULT NULL COMMENT '会员id',
  `receipt_type` varchar(50) DEFAULT NULL COMMENT '发票类型',
  `receipt_title` varchar(100) DEFAULT NULL COMMENT '发票抬头',
  `receipt_amount` decimal(20,2) DEFAULT NULL COMMENT '发票金额',
  `receipt_content` varchar(255) DEFAULT NULL COMMENT '发票内容',
  `tax_no` varchar(100) DEFAULT NULL COMMENT '税号',
  `reg_addr` varchar(255) DEFAULT NULL COMMENT '注册地址',
  `reg_tel` varchar(50) DEFAULT NULL COMMENT '注册电话',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(200) DEFAULT NULL COMMENT '银行账户',
  `add_time` bigint(20) DEFAULT NULL COMMENT '开票时间',
  `member_name` varchar(100) DEFAULT NULL COMMENT '会员名称',
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='发票历史(es_receipt_history)';

-- ----------------------------
--  Table structure for `es_ship_template`
-- ----------------------------
DROP TABLE IF EXISTS `es_ship_template`;
CREATE TABLE `es_ship_template` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '模板名称',
  `type` smallint(1) DEFAULT NULL COMMENT '1 重量算运费 2 计件算运费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费模版(es_ship_template)';

-- ----------------------------
--  Table structure for `es_ship_template_child`
-- ----------------------------
DROP TABLE IF EXISTS `es_ship_template_child`;
CREATE TABLE `es_ship_template_child` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `template_id` int(10) DEFAULT NULL COMMENT '外键  模板id',
  `first_company` int(10) DEFAULT NULL COMMENT '首个（件）',
  `first_price` decimal(20,2) DEFAULT NULL COMMENT '首个（件）价格',
  `continued_company` int(10) DEFAULT NULL COMMENT '续个（件）',
  `continued_price` decimal(20,2) DEFAULT NULL COMMENT '续个（件）价格',
  `area` longtext COMMENT '地区json',
  `area_id` longtext COMMENT '地区id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费模板子模板(es_ship_template_child)';

SET FOREIGN_KEY_CHECKS = 1;
