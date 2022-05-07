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
package cloud.shopfly.b2c.api.base;

import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import cloud.shopfly.b2c.core.base.service.FileManager;
import cloud.shopfly.b2c.framework.util.FileUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * baiduUeditorConfiguration and file upload support
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/6/6
 */
@RestController()
@RequestMapping("/ueditor")
public class UeditorBaseController {


    /**
     * File upload interface
     */
    @Autowired
    private FileManager fileManager;

    /**
     * The configuration content constant is used to cache configuration information so that it is not read by the hard disk every time
     */
    private static String config;

    @GetMapping(value = "/",produces = "application/javascript")
    @ApiOperation(value = "To obtainueditorconfiguration")
    @ApiImplicitParam(name = "callback", value = "jsonpthecallback", required = true, dataType = "String")
    public String config(String callback) throws JSONException {

       return "/**/"+callback+"("+ getConfig() +");";

    }

    /**
     * Access to the configuration<br>
     * ifconfigOtherwise, the disk will read it<br>
     * Read the file as/resource/ueditor_config.json<br><br>
     * @return
     */
    private String getConfig() {

        if (config == null) {
            config = FileUtil.readFile("/ueditor_config.json");
        }

        return config;
    }

    /**
     * File upload<br>
     * acceptPOSTrequest<br>
     * You can upload multiple files and screenshots
     * @param upfile File stream
     * @return
     * @throws JSONException
     * @throws IOException
     */
    @PostMapping(value = "/")
    @ApiOperation(value = "ueditorfile/Image upload")
    public Map upload( MultipartFile upfile) throws JSONException, IOException {
        Map result = new HashMap(16);
        if (upfile != null && upfile.getOriginalFilename() != null) {

            // The file type
            String contentType= upfile.getContentType();
            // Gets the file name suffix
            String ext = contentType.substring(contentType.lastIndexOf("/") + 1, contentType.length());

            if(!FileUtil.isAllowUpImg(ext)){

                result.put("state","File format not allowed to upload, please uploadgif,jpg,png,jpeg,mp4Format file.");
                return  result;

            }

            FileDTO input  = new FileDTO();
            input.setName(upfile.getOriginalFilename());
            input.setStream(upfile.getInputStream());
            input.setExt(ext);
            FileVO file  = this.fileManager.upload(input, "ueditor");
            String url  = file.getUrl();
            String title = file.getName();
            String original = file.getName();
            result.put("state","SUCCESS");
            result.put("url", url);
            result.put("title", title);
            result.put("name", title);
            result.put("original", original);
            result.put("type","."+file.getExt());
            return  result;

        }else{

            result.put("state","The file to upload was not read");
            return  result;
        }


    }
}
