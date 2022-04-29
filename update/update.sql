-- Delete useless fields in es_member_address table
alter table es_member_address drop column province_id,drop column city_id,drop column county_id,drop column town_id,drop column province,drop column county,drop column town,drop column tel;
-- es_member_address New field add by 2020-04-24
alter table es_member_address add country_code varchar(50) default null comment 'Country code';
alter table es_member_address add state_name varchar(255) default null comment 'State name';
alter table es_member_address add state_code varchar(50) default null comment 'State code';
alter table es_member_address add zip_code varchar(255) default null comment 'Zip code';

-- Delete useless fields in es_order table
alter table es_order drop column ship_province_id,drop column ship_city_id,drop column ship_county_id,drop column ship_town_id,drop column ship_province,drop column ship_county,drop column ship_town,drop column ship_tel;
-- es_member_address New field add by 2020-04-24
alter table es_order add ship_country varchar(255) default null comment 'Country name';
alter table es_order add ship_state varchar(255) default null comment 'State name';
alter table es_order add ship_country_code varchar(50) default null comment 'Country code';
alter table es_order add ship_state_code varchar(50) default null comment 'State code';

-- Delete useless fields in es_trade table
alter table es_trade drop column consignee_country_id,drop column consignee_province,drop column consignee_province_id,drop column consignee_city_id,drop column consignee_county,drop column consignee_county_id,drop column consignee_town,drop column consignee_town_id,drop column consignee_telephone;
-- es_trade New field add by 2020-04-24
alter table es_trade add consignee_country_code varchar(50) default null comment 'Country code';
alter table es_trade add consignee_state varchar(255) default null comment 'State name';
alter table es_trade add consignee_state_code varchar(50) default null comment 'State code';
alter table es_trade add consignee_zip_code varchar(255) default null comment 'Zip code';

-- Delete useless fields in es_sss_order_data table
alter table es_sss_order_data drop column ship_province_id,drop column ship_city_id;
-- es_sss_order_data New field add by 2020-04-24
alter table es_sss_order_data add ship_country_code varchar(50) default null comment 'Country code';
alter table es_sss_order_data add ship_state_code varchar(50) default null comment 'State code';

-- Delete useless fields in es_member table
alter table es_member drop column province_id,drop column city_id,drop column county_id,drop column town_id,drop column province,drop column county,drop column town;
-- es_member New field add by 2020-04-24
alter table es_member add country varchar(255) default null comment 'Country name';
alter table es_member add country_code varchar(50) default null comment 'Country code';
alter table es_member add state_name varchar(255) default null comment 'State name';
alter table es_member add state_code varchar(50) default null comment 'State code';

-- es_rate_area New table add by 2020-04-29
CREATE TABLE `es_rate_area` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'A primary key',
  `name` varchar(255) DEFAULT NULL COMMENT 'rate area name',
  `area` longtext COMMENT 'regionjson',
  `area_id` longtext COMMENT 'regionid',
  `create_time` bigint(20) DEFAULT NULL COMMENT 'create time',
  `area_json` longtext COMMENT 'area json data',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='rate area subtemplate(es_rate_area)';

-- es_ship_template_setting New table add by 2020-04-29
CREATE TABLE `es_ship_template_setting` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'A primary key',
  `template_id` int(10) DEFAULT NULL COMMENT 'A foreign key  The templateid',
  `rate_area_id` int(10) DEFAULT NULL COMMENT 'A foreign key  The rateAreaId',
  `rate_area_name` varchar(255) DEFAULT NULL COMMENT 'rate area name',
  `amt_type` varchar(255) DEFAULT NULL,
  `amt` double(20,2) DEFAULT NULL,
  `conditions_type` varchar(255) DEFAULT NULL,
  `region_start` double(20,2) DEFAULT NULL,
  `region_end` double(20,2) DEFAULT NULL,
  `sort` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='Freight template subtemplate(es_ship_template_setting)';