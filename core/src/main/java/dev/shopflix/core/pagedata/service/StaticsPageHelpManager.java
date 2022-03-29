/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.pagedata.service;

import java.util.List;

/**
 * 静态页面帮助页面
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/7/17 下午3:27
 * @Description:
 *
 */
public interface StaticsPageHelpManager {

    /**
     * 获取帮助页面总数
     * @return
     */
    Integer count();


    /**
     * 分页获取帮助
     * @param page
     * @param pageSize
     * @return
     */
    List helpList(Integer page, Integer pageSize);


}
