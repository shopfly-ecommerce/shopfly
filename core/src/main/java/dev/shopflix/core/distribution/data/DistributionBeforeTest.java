/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.data;

import dev.shopflix.framework.database.DaoSupport;

/**
 * 分销数据准备
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-06-11 上午11:11
 */
public class DistributionBeforeTest {


    public static void before(DaoSupport daoSupport) {
        //清空已有数据
        daoSupport.execute("Truncate es_bill_member");
        daoSupport.execute("Truncate es_bill_total");
        daoSupport.execute("Truncate es_commission_tpl");
        daoSupport.execute("Truncate es_distribution");
        daoSupport.execute("Truncate es_distribution_order");
        daoSupport.execute("Truncate es_short_url");
        daoSupport.execute("Truncate es_upgrade_log");
        daoSupport.execute("Truncate es_withdraw_apply");
        daoSupport.execute("Truncate es_withdraw_setting");

        daoSupport.execute("INSERT INTO `es_bill_member` VALUES ('1', '1', '1', '1527782400', '1530374399', '60.00', '120.00', '1', '10000.00', '5000.00', '1', '60.00', 'test1', 'MEMBER111'), ('2', '1', '2', '1527782400', '1530374399', '40.00', '180.00', '1', '20000.00', '10000.00', '1', '140.00', 'test2', 'MEMBER222')");
        daoSupport.execute("INSERT INTO `es_bill_total` VALUES('1', '1527782400', '1530374399', '1', '100.00', '300.00', '10000.00', '200.00', '1', '1.00', 'TOTAL123')");
        daoSupport.execute("INSERT INTO `es_commission_tpl`VALUES('1', '模版1', '', 'MANUAL', '999.00', '1', '111', '1.00', '5.00', '1'), ('2', '模版2', '', 'MANUAL', '9999.00', '2', '222', '2.00', '4.00', '0'), ('3', '模版3', '', 'MANUAL', '99991.00', '3', '555', '3.00', '3.00', '0'),('4', '模版4', '', 'AUTOMATIC', '99992.00', '4', '333', '4.00', '2.00', '0'),('5', '模版5', '', 'AUTOMATIC', '99993.00', '5', '444', '5.00', '1.00', '0')");
        daoSupport.execute("INSERT INTO `es_distribution` VALUES ('1', '1', 'test1', '|0|1|', null, null, '1', '0', '0.00', '0.00', '867.00', '0.00', '1', '2', null,'1509009923'), ('2', '2', 'test2', '|0|1|2|', '1', null, '1', '0', '0.00', '0.00', '1000.00', '0.00', '默认模版', '1', null,'1509009923'), ('3', '3', 'test3', '|0|1|2|3|', '2', '1', '0', '0', '0.00', '0.00', '0.00', '0.00', '默认模版', '1', null,'1509009923')");
        daoSupport.execute("INSERT INTO `es_distribution_order` VALUES ('2', '2', 'sn_2222', '1', 'test1', '1', '2', '2', '1912321312', '30000.00', '1530254399', '50.00', '100.00', '30.00', '20.00', '1', '3000.00', '1', '2', 'test2',1,1,''), ('3', '3', 'sn_333', '3', 'test3', '2', null, '3', '1912321312', '20000.00', '1530154399', '100.00', '0.00', '0.00', '0.00', '0', '0.00', '1', '1', 'test',1,1,'')");
        daoSupport.execute("INSERT INTO `es_short_url` VALUES('1', 'r6RRNz', '/api/distribution/su/visit?redirect_url=/goods-1.html&member_id=1'), ('2', 'J3Uzea', '/goods-1.html?member_id=1')");
        daoSupport.execute("INSERT INTO `es_upgrade_log`VALUES('1', '1', 'liushuai', '手动', '1', '1', '2', '1', '1528133650')");
        daoSupport.execute("INSERT INTO `es_withdraw_apply` VALUES ('1', '10.00', 'TRANSFER_ACCOUNTS', '1', 'test1', '1', '转账备注', '转账备注', '1528159424', '1528724535', '1528724845'), ('2', '10.00', 'FAIL_AUDITING', '2', 'test2', '1', '审核备注', null, '1528159424', null, null), ('3', '10.00', 'VIA_AUDITING', '1', 'test1', '1', '审核备注', null, '1528159432',null,null), ('4', '10.00', 'APPLY', '2', 'test2', '1', null, null, '1528159642',null,null), ('5', '123.00', 'APPLY', '1', 'test1', '13123', null, null, '1528333714',null,null)");
        daoSupport.execute("INSERT INTO `es_withdraw_setting` VALUES('1', '1', '{\"memberName\":\"1\",\"bankName\":\"2\",\"openingNum\":\"3\",\"bankCard\":\"4\"}')");


    }


}
