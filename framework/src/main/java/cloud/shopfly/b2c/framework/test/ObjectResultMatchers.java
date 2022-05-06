/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;

/**
 * 对象比较 Matchers
 * Created by kingapex on 2018/3/30.
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/30
 */
public class ObjectResultMatchers {


    /**
     * 对象比较 Matcher
     * @param expect 要比较的对象
     * @return  对象比较 Matcher
     */
    public ResultMatcher objectEquals(Object expect) {
        return (result) -> {

            String content = result.getResponse().getContentAsString();
            Object actual = toObject(content,expect.getClass());
            AssertionErrors.assertEquals("对象不一致",expect,actual);


        };
    }

    /**
     * 对象比较 Matcher
     * @param expect 要比较的对象
     * @return  对象比较 Matcher
     */
    public ResultMatcher stringEquals(String expect) {
        return (result) -> {
            MockHttpServletResponse s =result.getResponse();
            String content = result.getResponse().getContentAsString();
            AssertionErrors.assertEquals("对象不一致",expect,content);


        };
    }

    /**
     * 将字串转为 对象
     * @param content json字串
     * @param clzz 要转换的类型
     * @return 转换后的对象
     * @throws IOException
     */
    private  Object toObject(String content,Class clzz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, clzz);
    }

}
