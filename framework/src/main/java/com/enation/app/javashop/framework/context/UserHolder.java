/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.context;

import com.enation.app.javashop.framework.security.model.Buyer;
import com.enation.app.javashop.framework.security.model.Seller;

/**
 * 用户信息hold接口
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-05-28
 */
public interface UserHolder {

    /**
     * 获取seller
     * @return
     */
    Seller getSeller();

    /**
     * 获取buyer
     * @return
     */
    Buyer getBuyer();


}
