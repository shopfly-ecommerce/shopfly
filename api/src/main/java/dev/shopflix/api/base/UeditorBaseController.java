/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.base;

import dev.shopflix.core.base.model.dto.FileDTO;
import dev.shopflix.core.base.model.vo.FileVO;
import dev.shopflix.core.base.service.FileManager;
import dev.shopflix.framework.util.FileUtil;
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
 * 百度Ueditor配置及文件上传支持
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/6/6
 */
@RestController()
@RequestMapping("/ueditor")
public class UeditorBaseController {


    /**
     * 文件上传接口
     */
    @Autowired
    private FileManager fileManager;

    /**
     * 配置内容常量，用于缓存配置信息，避免每次由硬盘读取
     */
    private static String config;

    @GetMapping(value = "/",produces = "application/javascript")
    @ApiOperation(value = "获取ueditor配置")
    @ApiImplicitParam(name = "callback", value = "jsonp的callback", required = true, dataType = "String")
    public String config(String callback) throws JSONException {

       return "/**/"+callback+"("+ getConfig() +");";

    }

    /**
     * 获取配置<br>
     * 如果config中已经存在，则直接返回，否则由硬盘读取<br>
     * 读取文件为/resource/ueditor_config.json<br><br>
     * @return
     */
    private String getConfig() {

        if (config == null) {
            config = FileUtil.readFile("/ueditor_config.json");
        }

        return config;
    }

    /**
     * 文件上传<br>
     * 接受POST请求<br>
     * 同时支持多择文件上传和截图上传
     * @param upfile 文件流
     * @return
     * @throws JSONException
     * @throws IOException
     */
    @PostMapping(value = "/")
    @ApiOperation(value = "ueditor文件/图片上传")
    public Map upload( MultipartFile upfile) throws JSONException, IOException {
        Map result = new HashMap(16);
        if (upfile != null && upfile.getOriginalFilename() != null) {

            //文件类型
            String contentType= upfile.getContentType();
            //获取文件名称后缀
            String ext = contentType.substring(contentType.lastIndexOf("/") + 1, contentType.length());

            if(!FileUtil.isAllowUpImg(ext)){

                result.put("state","不允许上传的文件格式，请上传gif,jpg,png,jpeg,mp4格式文件。");
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

            result.put("state","没有读取要上传的文件");
            return  result;
        }


    }
}
