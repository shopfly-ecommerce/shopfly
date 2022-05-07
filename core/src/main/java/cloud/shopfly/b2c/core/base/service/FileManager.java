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
 * File upload interface
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018years3month19On the afternoon4:37:44
 */
public interface FileManager {
    /**
     * File upload
     *
     * @param input file
     * @param scene Business types
     * @return
     */
    FileVO upload(FileDTO input, String scene);

    /**
     * Delete the file
     *
     * @param filePath The file path
     */
    void deleteFile(String filePath);

    /**
     * File upload across service invocations
     * @param file file
     * @param scene	Business typesgoods,shop,member,other
     * @return
     */
    FileVO uploadFile(MultipartFile file, String scene);


}
