/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.parameter;

import dev.shopflix.framework.util.EmojiCharacterUtil;
import org.apache.http.entity.ContentType;
import org.apache.poi.util.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 入参转换器
 *
 * @author fk
 * @version v2.0
 * @since v7.2.0
 * 2020.5.8
 */
public class EmojiEncodeRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 保存处理后的参数
     */
    private Map<String, String[]> params = new HashMap<String, String[]>();

    private byte[] content;

    public EmojiEncodeRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.params.putAll(request.getParameterMap());
        this.modifyParameterValues();
        //自定义方法，用于参数去重
        if (ContentType.APPLICATION_JSON.getMimeType().equals(request.getContentType())) {
            //对application/json数据格式的数据进行处理
            //获取文本数据;
            this.content = IOUtils.toByteArray(request.getInputStream());
        }

    }

    /**
     * 修改query传入的参数值
     */
    public void modifyParameterValues() {
        //将parameter的值表情转码后重写回去
        Set<Entry<String, String[]>> entrys = params.entrySet();
        for (Entry<String, String[]> entry : entrys) {
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = EmojiCharacterUtil.encode(values[i]);
            }
            this.params.put(entry.getKey(), values);
        }
    }

    @Override
    public Enumeration<String> getParameterNames() {//重写getParameterNames()
        return new Vector<String>(params.keySet()).elements();
    }


    @Override
    public String getParameter(String name) {//重写getParameter()
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {//重写getParameterValues()
        return params.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() { //重写getParameterMap()
        return this.params;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        //  这种获取的参数的方式针对于内容类型为文本类型，比如Content-Type:text/plain,application/json,text/html等
        //在springmvc中可以使用@RequestBody 来获取 json数据类型
        //其他文本类型不做处理，重点处理json数据格式
        String contentType = super.getHeader("Content-Type");
        if (contentType == null || !contentType.equalsIgnoreCase("application/json")) {
            return super.getInputStream();
        } else {
            //根据自己的需要重新指定方法
            String s = new String(this.content);
            ByteArrayInputStream in = new ByteArrayInputStream(EmojiCharacterUtil.encode(s).getBytes());
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return in.read();
                }

                @Override
                public int read(byte[] b, int off, int len) throws IOException {
                    return in.read(b, off, len);
                }

                @Override
                public int read(byte[] b) throws IOException {
                    return in.read(b);
                }

                @Override
                public void setReadListener(ReadListener listener) {
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public long skip(long n) throws IOException {
                    return in.skip(n);
                }

                @Override
                public void close() throws IOException {
                    in.close();
                }

                @Override
                public synchronized void mark(int readlimit) {
                    in.mark(readlimit);
                }

                @Override
                public synchronized void reset() throws IOException {
                    in.reset();
                }
            };
        }
    }
}
