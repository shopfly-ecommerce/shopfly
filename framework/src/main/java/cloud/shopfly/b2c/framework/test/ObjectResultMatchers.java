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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;

/**
 * Object to compareMatchers
 * Created by kingapex on 2018/3/30.
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/30
 */
public class ObjectResultMatchers {


    /**
     * Object to compareMatcher
     * @param expect The object to compare
     * @return  Object to compareMatcher
     */
    public ResultMatcher objectEquals(Object expect) {
        return (result) -> {

            String content = result.getResponse().getContentAsString();
            Object actual = toObject(content,expect.getClass());
            AssertionErrors.assertEquals("Object inconsistency",expect,actual);


        };
    }

    /**
     * Object to compareMatcher
     * @param expect The object to compare
     * @return  Object to compareMatcher
     */
    public ResultMatcher stringEquals(String expect) {
        return (result) -> {
            MockHttpServletResponse s =result.getResponse();
            String content = result.getResponse().getContentAsString();
            AssertionErrors.assertEquals("Object inconsistency",expect,content);


        };
    }

    /**
     * Turns a string into an object
     * @param content jsonstring
     * @param clzz The type to be converted
     * @return The transformed object
     * @throws IOException
     */
    private  Object toObject(String content,Class clzz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, clzz);
    }

}
