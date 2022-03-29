/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system.impl;

import dev.shopflix.core.base.model.vo.FileVO;
import dev.shopflix.core.base.plugin.upload.Uploader;
import dev.shopflix.core.base.service.FileManager;
import dev.shopflix.core.client.system.UploadFactoryClient;
import dev.shopflix.core.system.service.UploadFactory;
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
