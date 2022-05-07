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
package cloud.shopfly.b2c.framework.parameter;

import cloud.shopfly.b2c.framework.util.EmojiCharacterUtil;
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
 * Input converter
 *
 * @author fk
 * @version v2.0
 * @since v7.2.0
 * 2020.5.8
 */
public class EmojiEncodeRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Save the processed parameters
     */
    private Map<String, String[]> params = new HashMap<String, String[]>();

    private byte[] content;

    public EmojiEncodeRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.params.putAll(request.getParameterMap());
        this.modifyParameterValues();
        // Custom method for parameter deduplication
        if (ContentType.APPLICATION_JSON.getMimeType().equals(request.getContentType())) {
            // Process data in application/ JSON data format
            // Get text data;
            this.content = IOUtils.toByteArray(request.getInputStream());
        }

    }

    /**
     * editqueryThe parameter value passed in
     */
    public void modifyParameterValues() {
        // Transcode the value of parameter and rewrite it back
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
    public Enumeration<String> getParameterNames() {//rewritegetParameterNames()
        return new Vector<String>(params.keySet()).elements();
    }


    @Override
    public String getParameter(String name) {//rewritegetParameter()
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {//rewritegetParameterValues()
        return params.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() { //rewritegetParameterMap()
        return this.params;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // This to obtain the parameters of the way for the Content Type to text types, such as the content-type: text/plain, application/json, text/HTML, etc
        // In SpringMVC you can use @requestBody to get json data types
        // Other text types are not processed, focusing on THE JSON data format
        String contentType = super.getHeader("Content-Type");
        if (contentType == null || !contentType.equalsIgnoreCase("application/json")) {
            return super.getInputStream();
        } else {
            // Respecify methods according to your needs
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
