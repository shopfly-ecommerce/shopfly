/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.service.impl;


import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.base.model.dto.FileDTO;
import dev.shopflix.core.base.model.vo.ConfigItem;
import dev.shopflix.core.base.model.vo.FileVO;
import dev.shopflix.core.base.plugin.upload.Uploader;
import dev.shopflix.core.base.service.FileManager;
import dev.shopflix.core.system.model.vo.UploaderVO;
import dev.shopflix.core.system.service.UploadFactory;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.logs.Logger;
import dev.shopflix.framework.logs.LoggerFactory;
import dev.shopflix.framework.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传接口实现
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月19日 下午4:38:42
 */
@Service
public class FileManagerImpl implements FileManager {

    @Autowired
    private UploadFactory uploadFactory;
    @Autowired
    private Cache cache;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public FileVO upload(FileDTO input, String scene) {
        if (StringUtil.isEmpty(scene)) {
            scene = "normal";
        }
        Uploader uploader = uploadFactory.getUploader();
        return uploader.upload(input, scene, this.getconfig());
    }

    @Override
    public void deleteFile(String filePath) {
        Uploader uploader = uploadFactory.getUploader();
        uploader.deleteFile(filePath, this.getconfig());
    }

    @Override
    public FileVO uploadFile(MultipartFile file, String scene) {
        //使用此方法，验证后缀名由调用者判断，这里主要目前主要是给中台使用 2020年12月17日16:22:08 by fk
        try {
            if (file != null && file.getOriginalFilename() != null) {
                //文件类型
                String contentType = file.getContentType();
                logger.debug("++++++++++++++++++文件类型为：++++++++++++" + contentType);
                FileDTO input = new FileDTO();
                input.setName(file.getOriginalFilename());
                input.setStream(file.getInputStream());
                input.setExt("jpeg");
                if (scene == null) {
                    scene = "other";
                }
                return this.upload(input, scene);
            } else {
                throw new ResourceNotFoundException("没有文件");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取存储方案配置
     *
     * @return
     */
    private Map getconfig() {
        UploaderVO upload = (UploaderVO) cache.get(CachePrefix.UPLOADER.getPrefix());
        if (StringUtil.isEmpty(upload.getConfig())) {
            return new HashMap<>(16);
        }
        Gson gson = new Gson();
        List<ConfigItem> list = gson.fromJson(upload.getConfig(), new TypeToken<List<ConfigItem>>() {
        }.getType());
        Map<String, String> result = new HashMap<>(16);
        if (list != null) {
            for (ConfigItem item : list) {
                result.put(item.getName(), StringUtil.toString(item.getValue()));
            }
        }
        return result;
    }


}
