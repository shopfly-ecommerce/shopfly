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
