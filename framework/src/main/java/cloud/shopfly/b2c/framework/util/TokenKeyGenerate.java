/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.util;


/**
 * TokenKeyGenerate
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-12-18 In the afternoon3:39
 */
public class TokenKeyGenerate {


    /**
     * generatetoken KEY
     *
     * @param uuid
     * @param memberId
     * @return
     */
    public static String generateBuyerAccessToken(String uuid, Integer memberId) {
        return UserTokenPrefix.ACCESS_TOKEN.getPrefix() + memberId + "_" + uuid;
    }

    /**
     * To generate the fuzzytoken Provide delete use
     *
     * @param uuid
     * @param memberId
     * @return
     */
    public static String generateBuyerRefreshToken(String uuid, Integer memberId) {
        return UserTokenPrefix.REFRESH_TOKEN.getPrefix() + memberId + "_" + uuid;
    }

    /**
     * Generate fuzzy deletekey
     *
     * @param memberId
     * @return
     */
    public static String generateVagueBuyerAccessToken(Integer memberId) {
        return UserTokenPrefix.ACCESS_TOKEN.getPrefix() + memberId + "_";
    }

    /**
     * Generate fuzzy deletekey
     *
     * @param memberId
     * @return
     */
    public static String generateVagueBuyerRefreshToken(Integer memberId) {
        return UserTokenPrefix.REFRESH_TOKEN.getPrefix() + memberId + "_";
    }


    /**
     * generatetoken KEY
     *
     * @param uuid
     * @param memberId
     * @return
     */
    public static String generateAdminAccessToken(String uuid, Integer memberId) {
        return UserTokenPrefix.ACCESS_TOKEN.getPrefix() + "ADMIN_" + memberId + "_" + uuid;
    }

    /**
     * Generate the refreshtoken KEY
     *
     * @param uuid
     * @param memberId
     * @return
     */
    public static String generateAdminRefreshToken(String uuid, Integer memberId) {
        return UserTokenPrefix.REFRESH_TOKEN.getPrefix() + "ADMIN_" + memberId + "_" + uuid;
    }


    /**
     * Generate fuzzy deletekey
     *
     * @param memberId
     * @return
     */
    public static String generateVagueAdminAccessToken(Integer memberId) {
        return UserTokenPrefix.ACCESS_TOKEN.getPrefix() + "ADMIN_" + memberId + "_";
    }

    /**
     * Generate fuzzy deletekey
     *
     * @param memberId
     * @return
     */
    public static String generateVagueAdminRefreshToken(Integer memberId) {
        return UserTokenPrefix.REFRESH_TOKEN.getPrefix() + "ADMIN_" + memberId + "_";
    }

}
