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
package cloud.shopfly.b2c.core.base.service;

import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月19日 下午4:37:44
 */
public interface FileManager {
    /**
     * 文件上传
     *
     * @param input 文件
     * @param scene 业务类型
     * @return
     */
    FileVO upload(FileDTO input, String scene);

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    void deleteFile(String filePath);

    /**
     * 跨服务调用的文件上传
     * @param file 文件
     * @param scene	业务类型 goods,shop,member,other
     * @return
     */
    FileVO uploadFile(MultipartFile file, String scene);


}
