/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.util;


/**
 * TokenKeyGenerate
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-12-18 下午3:39
 */
public class TokenKeyGenerate {


    /**
     * 生成token KEY
     *
     * @param uuid
     * @param memberId
     * @return
     */
    public static String generateBuyerAccessToken(String uuid, Integer memberId) {
        return UserTokenPrefix.ACCESS_TOKEN.getPrefix() + memberId + "_" + uuid;
    }

    /**
     * 生成模糊token 提供删除使用
     *
     * @param uuid
     * @param memberId
     * @return
     */
    public static String generateBuyerRefreshToken(String uuid, Integer memberId) {
        return UserTokenPrefix.REFRESH_TOKEN.getPrefix() + memberId + "_" + uuid;
    }

    /**
     * 生成 模糊删除 key
     *
     * @param memberId
     * @return
     */
    public static String generateVagueBuyerAccessToken(Integer memberId) {
        return UserTokenPrefix.ACCESS_TOKEN.getPrefix() + memberId + "_";
    }

    /**
     * 生成 模糊删除 key
     *
     * @param memberId
     * @return
     */
    public static String generateVagueBuyerRefreshToken(Integer memberId) {
        return UserTokenPrefix.REFRESH_TOKEN.getPrefix() + memberId + "_";
    }


    /**
     * 生成token KEY
     *
     * @param uuid
     * @param memberId
     * @return
     */
    public static String generateAdminAccessToken(String uuid, Integer memberId) {
        return UserTokenPrefix.ACCESS_TOKEN.getPrefix() + "ADMIN_" + memberId + "_" + uuid;
    }

    /**
     * 生成刷新token KEY
     *
     * @param uuid
     * @param memberId
     * @return
     */
    public static String generateAdminRefreshToken(String uuid, Integer memberId) {
        return UserTokenPrefix.REFRESH_TOKEN.getPrefix() + "ADMIN_" + memberId + "_" + uuid;
    }


    /**
     * 生成 模糊删除 key
     *
     * @param memberId
     * @return
     */
    public static String generateVagueAdminAccessToken(Integer memberId) {
        return UserTokenPrefix.ACCESS_TOKEN.getPrefix() + "ADMIN_" + memberId + "_";
    }

    /**
     * 生成 模糊删除 key
     *
     * @param memberId
     * @return
     */
    public static String generateVagueAdminRefreshToken(Integer memberId) {
        return UserTokenPrefix.REFRESH_TOKEN.getPrefix() + "ADMIN_" + memberId + "_";
    }

}
