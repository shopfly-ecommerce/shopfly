/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.service;

import dev.shopflix.core.member.model.dos.MemberAddress;
import dev.shopflix.framework.database.Page;

import java.util.List;

/**
 * User delivery address business interface
 *
 * @author dmy
 * @version v2.0
 * @since v7.0.0
 * 2018-03-18 15:37:00
 */
public interface MemberAddressManager {

    /**
     * Query the collection of user shipping address information
     *
     * @return User delivery address collection
     */
    List<MemberAddress> list();

    /**
     * Query the current user's shipping address paginated list
     *
     * @param page     Number of paginated pages
     * @param pageSize Number of paginated display
     * @param memberId User ID
     * @return User delivery address collection
     */
    Page list(int page, int pageSize, Integer memberId);

    /**
     * Add user shipping address
     *
     * @param memberAddress User shipping address parameter information
     * @return Add the user's shipping address information after successful addition
     */
    MemberAddress add(MemberAddress memberAddress);

    /**
     * Update user shipping address
     *
     * @param memberAddress User shipping address parameter information
     * @param id            primary key ID
     * @return The user's delivery address information after the modification is successful
     */
    MemberAddress edit(MemberAddress memberAddress, Integer id);

    /**
     * Delete user shipping address
     *
     * @param id primary key ID
     */
    void delete(Integer id);

    /**
     * Query the details of a user's shipping address
     *
     * @param id primary key ID
     * @return User shipping address details
     */
    MemberAddress getModel(Integer id);

    /**
     * Get the details of the user's default shipping address
     *
     * @param memberId User ID
     * @return User default shipping address details
     */
    MemberAddress getDefaultAddress(Integer memberId);

    /**
     * Set as default shipping address
     *
     * @param id primary key ID
     */
    void editDefault(Integer id);


}