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
package cloud.shopfly.b2c.core.client.system.impl;

import cloud.shopfly.b2c.core.client.system.UploadFactoryClient;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import cloud.shopfly.b2c.core.base.plugin.upload.Uploader;
import cloud.shopfly.b2c.core.base.service.FileManager;
import cloud.shopfly.b2c.core.system.service.UploadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @version v7.0
 * @Description:
 * @Author: zjp
 * @Date: 2018/7/27 16:27
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class UploadFactoryClientDefaultImpl implements UploadFactoryClient {
    @Autowired
    private UploadFactory uploadFactory;
    @Autowired
    private FileManager fileManager;

    @Override
    public String getUrl(String url, Integer width, Integer height) {
        Uploader uploader = uploadFactory.getUploader();
        return uploader.getThumbnailUrl(url, width, height);
    }

    @Override
    public FileVO upload(MultipartFile file, String scene) {
        return fileManager.uploadFile(file, scene);
    }
}
