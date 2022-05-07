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
package cloud.shopfly.b2c.framework.test;


import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.security.model.Role;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by kingapex on 2018/3/28.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/28
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest()
@Transactional(rollbackFor = Exception.class)
@Rollback()
@ContextConfiguration(classes = {TestConfig.class})

public abstract class BaseTest {


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private Cache cache;

    protected final String uuid = "8ac6bc40-a5f9-11e8-afe8-657ead33359b";

    /**
     * 10 days
     */
    static final long EXPIRATIONTIME = 864_000_000;
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    /**
     * The seller1 sellerId = 3
     */
    protected String seller1 = "eyJhbGciOiJIUzUxMiJ9.eyJzZWxmT3BlcmF0ZWQiOjAsInVpZCI6MSwic3ViIjoiQ0xFUksiLCJzZWxsZXJJZCI6Mywicm9sZSI6IlNVUEVSX1NFTExFUiIsImZvdW5kZXIiOm51bGwsInJvbGVzIjpbIkJVWUVSIiwiU0VMTEVSIiwiQ0xFUksiXSwic2VsbGVyTmFtZSI6IuWwj-W6lyIsImNsZXJrSWQiOjEsImNsZXJrTmFtZSI6Iua1i-ivleW6l-WRmCIsInVzZXJuYW1lIjoi5rWL6K-V6LSm5oi3In0.CEYCkpz0TW4VNFqVIH5nGlbwbDXme9-aUo3k05P0VrKKvlibof1IUqfPDN_jvt1o8PtLRZchkzkup2TSvZOivw";
    /**
     * The seller2 sellerId = 4
     */
    protected String seller2 = "eyJhbGciOiJIUzUxMiJ9.eyJzZWxmT3BlcmF0ZWQiOjAsInVpZCI6MSwic3ViIjoiQ0xFUksiLCJzZWxsZXJJZCI6NCwicm9sZSI6IlNVUEVSX1NFTExFUiIsImZvdW5kZXIiOm51bGwsInJvbGVzIjpbIkJVWUVSIiwiU0VMTEVSIiwiQ0xFUksiXSwic2VsbGVyTmFtZSI6IuWwj-W6lyIsImNsZXJrSWQiOjEsImNsZXJrTmFtZSI6Iua1i-ivleW6l-WRmCIsInVzZXJuYW1lIjoi5rWL6K-V6LSm5oi3In0.a97FcMUqhzzR3dD1OUsYc4YBMsCSaM30_uKM952F3hTiw1VIuzaYibT9dUvnMYSE6rrsRR2HF4V6XGrCL0Fgww";

    /**
     * buyers1 uid = 1
     */
    protected String buyer1 = "eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOjEsInN1YiI6IkJVWUVSIiwicm9sZXMiOlsiQlVZRVIiXSwiZXhwIjoxNTU2NTA2NDU0LCJ1c2VybmFtZSI6IndhbmdmZW5nIn0.wdJxhrq0coEp4KpoXTdWZ0V59hmOpnFlyTUCIVkwyDVsq2ZjTIDf8l0EYmwRDRi-dgDUhC6Nb9C-_WOl51UNlw";

    /**
     * buyers2 uid = 2
     */
    protected String buyer2 = "eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOjIsInN1YiI6IkJVWUVSIiwicm9sZXMiOlsiQlVZRVIiXSwiZXhwIjoxNTU2NTA2OTAxLCJ1c2VybmFtZSI6IndhbmdmZW5nMSJ9.zUIknjpdLPOfr5N2_c4OTZw_a5aqsipGTUoz2AmH_CsvjnMGnZwrS4FfPo6IAdtJgXcz3HVYDVXbiNszffxwLQ";

    /**
     * Super administratorstoken uid =1
     */
    protected String superAdmin = "eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOjEsInN1YiI6IkFETUlOIiwicm9sZSI6IlNVUEVSX0FETUlOIiwiZm91bmRlciI6bnVsbCwidXVpZCI6bnVsbCwidXNlcm5hbWUiOiLmtYvor5XotKbmiLcifQ.Ozpotzn7rK21q1JupTob-qDAP_qda6iNT2Sqk9sCrHjmDL3d8EM2C-yg4AvaRKLmWsC-guSdMZfikxs4SHUhFQ";

    /**
     * Encapsulating transfer parameter
     *
     * @param names  Parameter names
     * @param values Parameter value array
     * @return Assembled parametersmap The list of
     */
    protected static List<MultiValueMap<String, String>> toMultiValueMaps(String[] names, String[]... values) {

        List<MultiValueMap<String, String>> stringMultiValueMapList = new ArrayList<>();
        // Values. The array length group

        for (String[] value : values) {

            MultiValueMap<String, String> stringMultiValueMap = new LinkedMultiValueMap<>();

            for (int i = 0; i < value.length; i++) {

                stringMultiValueMap.add(names[i], value[i]);


            }
            stringMultiValueMapList.add(stringMultiValueMap);

        }

        return stringMultiValueMapList;
    }


    /**
     * Object to comparematcher
     *
     * @param expect The object to compare
     * @return Like to compareResultMatcher
     */
    protected ResultMatcher objectEquals(Object expect) {
        ObjectResultMatchers matchers = new ObjectResultMatchers();
        return matchers.objectEquals(expect);
    }

    /**
     * Converts an object to one that can be passed as a parametermap
     *
     * @param obj object
     * @return
     * @throws IllegalAccessException
     */
    public static MultiValueMap<String, String> objectToMap(Object obj) throws IllegalAccessException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            fieldName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
            String value = StringUtil.toString(field.get(obj));
            map.add(fieldName, value);
        }
        return map;

    }

    /**
     * Object to comparematcher
     *
     * @param expect The object to compare
     * @return Like to compareResultMatcher
     */
    protected ResultMatcher stringEquals(String expect) {
        ObjectResultMatchers matchers = new ObjectResultMatchers();
        return matchers.stringEquals(expect);
    }

    /**
     * Generate the buyerstoken
     *
     * @param uid      membersid
     * @param username Member name
     * @return
     */

    protected String createBuyerToken(Integer uid, String username) {
        Buyer buyer = new Buyer();
        buyer.setUid(uid);
        buyer.setUsername(username);
        ObjectMapper oMapper = new ObjectMapper();
        Map buyerMap = oMapper.convertValue(buyer, HashMap.class);
        String jsonWebToken = Jwts.builder()

                .setClaims(buyerMap)
                .setSubject(Role.BUYER.name())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return jsonWebToken;

    }

    /**
     * Generate the sellerstoken
     *
     * @param uid        Member name
     * @param sellerId   The storeID
     * @param userName   Member name
     * @param sellerName Shop name
     * @return
     */
    protected String createSellerToken(Integer uid, Integer sellerId, String userName, String sellerName) {
//        Clerk clerk = new Clerk();
//        clerk.setUid(uid);
//        clerk.setSellerId(sellerId);
//        clerk.setSellerName(sellerName);
//        clerk.setClerkId(1);
//        clerk.setClerkName(userName);
//        clerk.setUsername(userName);
//        clerk.setRole("SUPER_SELLER");
//        ObjectMapper oMapper = new ObjectMapper();
//        Map clerkMap = oMapper.convertValue(clerk, HashMap.class);
//        String jsonWebToken = Jwts.builder()
//                .setClaims(clerkMap)
//                .setSubject("CLERK")
//                .signWith(SignatureAlgorithm.HS512, JWTConstant.SECRET)
//                .compact();
        return null;
    }

    /**
     * willMultiValueMap Converted toMap
     *
     * @return
     */
    protected Map formatMap(MultiValueMap<String, String> map) {
        Map stringMap = new HashMap<>(16);
        Set<String> strings = map.keySet();
        for (String str : strings) {
            stringMap.put(str, map.get(str).get(0));
        }
        return stringMap;
    }

    /**
     * willMapConverted toMap<String,String>
     *
     * @param map
     * @return
     */
    protected Map formatMap(Map<String, Object> map) {
        Map actual = new HashMap(16);
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            actual.put(entry.getKey(), StringUtil.toString(entry.getValue()));
        }
        return actual;
    }

    /**
     * Delete the files in the folder
     *
     * @param path The file path
     * @return
     */
    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // Delete the files in the folder first
                delAllFile(path + "/" + tempList[i]);
                // Then delete the empty folder
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Delete folders
     *
     * @param folderPath Folder path
     */
    public void delFolder(String folderPath) {
        try {
            // Delete everything on it
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //Delete empty folders
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sign in
     *
     * @param username
     * @param password
     * @return
     */
    protected Map login(String username, String password) throws Exception {
        cache.put("CAPTCHA_" + "123456789" + "_" + "LOGIN", "1111");
        String member = mockMvc.perform(get("/passport/login")
                .param("username", username)
                .param("password", password)
                .param("captcha", "1111")
                .param("uuid", "123456789").header("uuid", "123456789"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
        Map map = JsonUtil.toMap(member);
        return map;
    }

    /**
     * Sign in
     *
     * @param username Username
     * @param passpord Password
     * @return
     * @throws Exception
     */
    protected Map loginForMap(String username, String passpord) throws Exception {
        cache.put("CAPTCHA_" + uuid + "_LOGIN", "1111", 1000);
        String json = mockMvc.perform(get("/passport/login")
                .param("username", username)
                .param("password", passpord)
                .param("captcha", "1111")
                .param("uuid", uuid))
                .andExpect(status().is(200)).andReturn().getResponse().getContentAsString();
        Map map = JsonUtil.toMap(json);
        return map;
    }

    protected Map register(String name,String password,String mobile) throws Exception{
        cache.put("SMS_CODE_" + "REGISTER" + "_"+mobile, "111111");
        String json = mockMvc.perform(post("/passport/register/pc")
                .param("username", name)
                .param("password", password)
                .param("mobile",mobile)
                .param("sms_code","111111")
                .param("uuid", uuid).header("uuid",uuid))
                .andExpect(status().is(200)).andReturn().getResponse().getContentAsString();
        Map map = JsonUtil.toMap(json);
        return map;
    }

}
