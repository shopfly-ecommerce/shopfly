/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.service;

import com.enation.app.javashop.core.member.model.dos.MemberAddress;
import com.enation.app.javashop.framework.database.Page;

import java.util.List;

/**
 * 会员地址业务层
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-18 15:37:00
 */
public interface MemberAddressManager {

    /**
     * 查询会员地址列表
     *
     * @return 地址集合
     */
    List<MemberAddress> list();

    /**
     * 查询会员地址列表
     *
     * @param page     页数
     * @param pageSize 每页显示数
     * @param memberId 会员id
     * @return
     */
    Page list(int page, int pageSize, Integer memberId);

    /**
     * 添加会员地址
     *
     * @param memberAddress 会员地址
     * @return MemberAddress 会员地址
     */
    MemberAddress add(MemberAddress memberAddress);

    /**
     * 修改会员地址
     *
     * @param memberAddress 会员地址
     * @param id            会员地址主键
     * @return MemberAddress 会员地址
     */
    MemberAddress edit(MemberAddress memberAddress, Integer id);

    /**
     * 删除会员地址
     *
     * @param id 会员地址主键
     */
    void delete(Integer id);

    /**
     * 获取会员地址
     *
     * @param id 会员地址主键
     * @return MemberAddress  会员地址
     */
    MemberAddress getModel(Integer id);

    /**
     * 获取会员默认地址
     *
     * @param memberId 会员id
     * @return 会员默认地址
     */
    MemberAddress getDefaultAddress(Integer memberId);


    /**
     * 修改地址为默认
     *
     * @param id 地址的id
     */
    void editDefault(Integer id);


}