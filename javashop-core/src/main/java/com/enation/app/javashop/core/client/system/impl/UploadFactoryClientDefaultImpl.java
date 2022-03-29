/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.system.impl;

import com.enation.app.javashop.core.base.model.vo.FileVO;
import com.enation.app.javashop.core.base.plugin.upload.Uploader;
import com.enation.app.javashop.core.base.service.FileManager;
import com.enation.app.javashop.core.client.system.UploadFactoryClient;
import com.enation.app.javashop.core.system.service.UploadFactory;
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
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
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
