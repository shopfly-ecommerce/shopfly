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
package cloud.shopfly.b2c.core.member;

/**
 * Member exception code
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum MemberErrorCode {

    // Member of the error code
    E100("The number of member addresses reached the upper limit！"),
    E101("You cannot change the current default address to a non-default address！"),
    E102("Unable to collect their own shops！"),
    E103("This shop has been added to the collection！"),
    E104("Unable to collect goods from their own shops！"),
    E105("This item has been added to the collection！"),
    E106("Parameter incomplete！"),
    E107("Incorrect parameter！"),
    E108("The current user name is already in use"),
    E109("The currenttokenHave failed"),
    E110("Member has quit"),
    E111("The current mobile number has been bound to another user"),
    E112("Password length:4-20A character"),
    E113("The passwords are inconsistent"),
    E114("Current member does not bind mobile phone number"),
    E115("Please verify the phone first"),
    E116("The user name has been occupied"),
    E117("The mailbox is occupied"),
    E118("The current mobile phone number has been used"),
    E119("Please authenticate the user first"),
    E120("The tax code cannot be blank"),
    E121("The invoice amount has reached the upper limit"),
    E122("Please specify sending member"),
    E123("Current member does not exist"),
    E124("Current membership is disabled"),

    E130("The login mode is not supported"),
    E131("Joint login failure"),
    E132("The current member is bound to another account"),
    E133("Authorized overtime"),
    E134("Members are not bound to relevant accounts"),
    E135("30Do not repeatedly unbind within a day"),

    E200("No Comment permission"),
    E201("Comment error uploading a reference"),
    E202("Consultation parameter error"),
    E203("Coupon overclaim"),

    E136("Permission manipulation error"),
    E137("Current membership has expired"),
    E138("The super administrator cannot be deleted"),
    E139("The super administrator cannot be added"),
    E140("Login failed"),
    E141("District illegal"),
    E142("You are not yet a shopkeeper"),
    E143("The current account has been bound to another user"),
    E144("This account already has a store"),
    E145("This member has become a clerk in another store"),
    E146("This member is already a clerk in our store"),
    E147("Parameter error");

    private String describe;

    MemberErrorCode(String des) {
        this.describe = des;
    }

    /**
     * Get exception code
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }


}
