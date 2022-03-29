/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.plugin.upload;

import dev.shopflix.core.base.DomainHelper;
import dev.shopflix.core.base.model.dto.FileDTO;
import dev.shopflix.core.base.model.vo.ConfigItem;
import dev.shopflix.core.base.model.vo.FileVO;
import dev.shopflix.core.base.model.vo.RadioOption;
import dev.shopflix.framework.context.ThreadContextHolder;
import dev.shopflix.framework.util.AbstractRequestUtil;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.FileUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.*;


/**
 * 本地上传插件
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018年3月22日 下午7:40:27
 */
@SuppressWarnings("unchecked")
@Component
public class LocalPlugin implements Uploader {

    @Autowired
    ServletContext context;


    @Autowired
    private DomainHelper domainHelper;

    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList<>();
        ConfigItem nginxOpen = new ConfigItem();
        nginxOpen.setType("radio");
        nginxOpen.setName("nginx_open");
        nginxOpen.setText("nginx支持");
        List<RadioOption> options = new ArrayList<>();
        RadioOption radioOption = new RadioOption();
        radioOption.setLabel("不支持");
        radioOption.setValue(0);
        options.add(radioOption);

        radioOption = new RadioOption();
        radioOption.setLabel("支持");
        radioOption.setValue(1);
        options.add(radioOption);
        nginxOpen.setOptions(options);


        ConfigItem resourceUrl = new ConfigItem();
        resourceUrl.setType("text");
        resourceUrl.setName("static_server_domain");
        resourceUrl.setText("域名");

        ConfigItem serviceUrl = new ConfigItem();
        serviceUrl.setType("text");
        serviceUrl.setName("static_server_path");
        serviceUrl.setText("路径");

        list.add(nginxOpen);
        list.add(resourceUrl);
        list.add(serviceUrl);
        return list;
    }

    @Override
    public String getPluginId() {
        return "localPlugin";
    }

    /**
     * 删除本地图片
     *
     * @param filePath 文件全路径
     */
    @Override
    public void deleteFile(String filePath, Map config) {
        String serverName = domainHelper.getBuyerDomain();
        if (AbstractRequestUtil.isMobile()) {
            serverName = domainHelper.getMobileDomain();
        }
        filePath = filePath.replaceAll(serverName, ThreadContextHolder.getHttpRequest().getServletContext().getRealPath(""));
        FileUtil.delete(filePath);
    }

    /**
     * 获取时间
     */
    private String getTimePath() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DAY_OF_MONTH);
        int minute = now.get(Calendar.HOUR_OF_DAY);
        String filePath = "";
        if (year != 0) {
            filePath += year + "/";
        }
        if (month != 0) {
            filePath += month + "/";
        }
        if (date != 0) {
            filePath += date + "/";
        }
        if (minute != 0) {
            filePath += minute + "/";
        }
        return filePath;
    }

    @Override
    public FileVO upload(FileDTO input, String scene, Map config) {
        String serverName = domainHelper.getBuyerDomain();
        if (AbstractRequestUtil.isMobile()) {
            serverName = domainHelper.getMobileDomain();
        }
        //参数校验
        if (input.getStream() == null) {
            throw new IllegalArgumentException("file or filename object is null");
        }
        //获取文件名词
        String fileName = input.getName();
        //获取文件后缀
        String ext = input.getExt();
        //  拼接文件名
        fileName = DateUtil.toString(new Date(), "mmss") + StringUtil.getRandStr(4) + "." + ext;
        //  返回浏览器路径
        String path = null;
        // 入库路径
        String filePath = null;
        // 拼接路径
        path = serverName + "/statics/attachment/" + scene + "/";
        filePath = context.getRealPath("/") + "statics/attachment/" + scene + "/";
        // 获取当前时间
        String timePath = this.getTimePath();
        // 拼接返回浏览器路径
        path += timePath + fileName;
        // 拼接入库路径及文件名 */
        filePath += timePath;
        filePath += fileName;
        //写入文件
        FileUtil.write(input.getStream(), filePath);
        //  返回浏览器
        FileVO file = new FileVO();
        file.setName(fileName);
        file.setUrl(path);
        file.setExt(ext);
        return file;
    }

    /**
     * 生成缩略图全路径 本地存储缩略图格式为 原图片名称_宽x高.原图片名称后缀
     * 如原图路径为/User/2017/03/04/original.jpg，需要生成100x100的图片 则缩略图全路径为
     * /User/2017/03/04/original.jpg_100x100.jpg
     */
    @Override
    public String getThumbnailUrl(String url, Integer width, Integer height) {
        // 截图原图后缀
        String suffix = url.substring(url.lastIndexOf("."), url.length());
        // 缩略图全路径
        String thumbnailPah = url + "_" + width + "x" + height + suffix;
        // 返回缩略图全路径
        return thumbnailPah;

    }

    @Override
    public String getPluginName() {
        return "本地存储";
    }

    @Override
    public Integer getIsOpen() {
        return 1;
    }

}
