/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.service;

import com.enation.app.javashop.core.system.model.dos.UploaderDO;
import com.enation.app.javashop.core.system.model.vo.UploaderVO;
import com.enation.app.javashop.framework.database.Page;

/**
 * 存储方案业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-22 09:31:56
 */
public interface UploaderManager {

    /**
     * 查询存储方案列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加存储方案
     *
     * @param uploader 存储方案
     * @return Uploader 存储方案
     */
    UploaderDO add(UploaderVO uploader);

    /**
     * 修改存储方案
     *
     * @param uploader 存储方案
     * @return Uploader 存储方案
     */
    UploaderVO edit(UploaderVO uploader);

    /**
     * 获取存储方案
     *
     * @param id 存储方案主键
     * @return Uploader  存储方案
     */
    UploaderDO getUploader(Integer id);

    /**
     * 获取存储方案
     *
     * @param bean 存储方案beanid
     * @return Uploader  存储方案
     */
    UploaderDO getUploader(String bean);

    /**
     * 开启某个存储方案
     *
     * @param bean 存储方案bean
     * @return Uploader  存储方案
     */
    void openUploader(String bean);

    /**
     * 根据存储方案的beanid 获取存储方案的配置项
     *
     * @param bean 存储方案beanid
     * @return 存储方案
     */
    UploaderVO getUploadConfig(String bean);

}