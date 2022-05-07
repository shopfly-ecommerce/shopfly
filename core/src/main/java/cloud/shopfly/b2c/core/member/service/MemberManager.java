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
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dto.MemberQueryParam;
import cloud.shopfly.b2c.core.member.model.dto.MemberStatisticsDTO;
import cloud.shopfly.b2c.core.member.model.vo.BackendMemberVO;
import cloud.shopfly.b2c.core.member.model.vo.MemberPointVO;
import cloud.shopfly.b2c.core.member.model.vo.MemberVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Member business Layer
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 11:33:56
 */
public interface MemberManager {

    /**
     * Access to members
     *
     * @param id Primary key of member table
     * @return Member  members
     */
    Member getModel(Integer id);

    /**
     * Query members by username
     *
     * @param uname Username
     * @return The member information
     */
    Member getMemberByName(String uname);

    /**
     * Query members according to the users mobile phone number
     *
     * @param mobile Mobile phone number
     * @return The member information
     */
    Member getMemberByMobile(String mobile);

    /**
     * Obtain users by mailbox
     *
     * @param email email
     * @return The member information
     */
    Member getMemberByEmail(String email);

    /**
     * Modify the member
     *
     * @param member members
     * @param id     Member of the primary key
     * @return Member members
     */
    Member edit(Member member, Integer id);

    /**
     * Members exit
     *
     * @param uid membersid
     */
    void logout(Integer uid);

    /**
     * Get some ancillary statistics for the current membership
     * Such as：Membership order number、Member merchandise collection number
     *
     * @return Membership Statistics
     */
    MemberStatisticsDTO getMemberStatistics();

    /**
     * The number of logins is zero
     */
    void loginNumToZero();

    /**
     * Search for new Members
     *
     * @param length How many queries
     * @return Collection of New members
     */
    List<BackendMemberVO> newMember(Integer length);

    /**
     * Modify the login times of members
     *
     * @param memberId membersid
     * @param now      The time stamp
     */
    void updateLoginNum(Integer memberId, Long now);

    /**
     * Query current members points
     *
     * @return membersvo
     */
    MemberPointVO getMemberPoint();

    /**
     * Joint login post-processing
     *
     * @param member The member information
     * @param uuid   uuid
     * @return The member information
     */
    MemberVO connectLoginHandle(Member member, String uuid);

    /**
     * Member login with username and password
     *
     * @param username Username
     * @param password Password
     * @return Log in successfully.
     */
    MemberVO login(String username, String password);

    /**
     * Members mobile phone number login
     *
     * @param mobile Mobile phone no.
     * @return Log in successfully.
     */
    MemberVO login(String mobile);

    /**
     * Processing after login member
     *
     * @param member
     * @return
     */
    MemberVO loginHandle(Member member);

    /**
     * Verify the validity of member account password(Authentication only, not login)
     *
     * @param username Username/Mobile phone no./email
     * @param password Password
     * @return correcttrue errorfalse
     */
    Member validation(String username, String password);

    /**
     * Generating user names
     *
     * @param uname User Specifies the user name
     * @return Array of usernames
     */
    String[] generateMemberUname(String uname);

    /**
     * Registered members
     *
     * @param member members
     * @return The member information
     */
    Member register(Member member);

    /**
     * Obtain some information of current member according to account number for retrieving password
     *
     * @param account Username/Mobile phone no./email
     * @return The member information
     */
    Member getMemberByAccount(String account);

    /**
     * Query membership list
     *
     * @param memberQueryParam Query conditions
     * @return
     */
    Page list(MemberQueryParam memberQueryParam);

    /**
     * Clear member login information
     *
     * @param memberId membersid
     */
    void memberLoginout(Integer memberId);

    /**
     * According to the membershipidsGets a collection of members
     *
     * @param memberIds membersidAn array of
     * @return The member information
     */
    List<Member> getMemberByIds(Integer[] memberIds);

    /**
     * APPMembers mobile phone number login
     *
     * @param mobile Mobile phone no.
     * @return Log in successfully.
     */
    MemberVO appLogin(String mobile);
}
