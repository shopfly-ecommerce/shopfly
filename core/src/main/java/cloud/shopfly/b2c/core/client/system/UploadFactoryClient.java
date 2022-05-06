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
package cloud.shopfly.b2c.core.client.system;

import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @version v7.0
 * @Description: 存储方案Client
 * @Author: zjp
 * @Date: 2018/7/27 16:26
 */
public interface UploadFactoryClient {

    /**
     * 获取拼接后的url
     * @param url
     * @param width
     * @param height
     * @return
     */
     String getUrl(String url, Integer width, Integer height);

    /**
     * 文件上传
     * @param file 文件
     * @param scene	业务类型 goods,shop,member,other
     * @return
     */
    FileVO upload(MultipartFile file, String scene);
}
