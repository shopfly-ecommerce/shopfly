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
package cloud.shopfly.b2c.core.base.plugin.upload;

import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import cloud.shopfly.b2c.core.base.model.vo.RadioOption;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsSettingVO;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.context.WebInterceptorConfigurer;
import cloud.shopfly.b2c.framework.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Local upload plug-in
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018years3month22On the afternoon7:40:27
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
        nginxOpen.setText("nginxsupport");
        List<RadioOption> options = new ArrayList<>();
        RadioOption radioOption = new RadioOption();
        radioOption.setLabel("Does not support");
        radioOption.setValue(0);
        options.add(radioOption);

        radioOption = new RadioOption();
        radioOption.setLabel("support");
        radioOption.setValue(1);
        options.add(radioOption);
        nginxOpen.setOptions(options);


        ConfigItem resourceUrl = new ConfigItem();
        resourceUrl.setType("text");
        resourceUrl.setName("static_server_domain");
        resourceUrl.setText("The domain name");

        ConfigItem serviceUrl = new ConfigItem();
        serviceUrl.setType("text");
        serviceUrl.setName("static_server_path");
        serviceUrl.setText("The path");

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
     * Delete a local image
     *
     * @param filePath Full file path
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
     * To get the time
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
        // Parameter calibration
        if (input.getStream() == null) {
            throw new IllegalArgumentException("file or filename object is null");
        }

        /********************************
         *          Generate file name
         *********************************/
        // Get file nouns
        String fileName = input.getName();
        // Get file suffixes
        String ext = input.getExt();
        // Splicing file name
        fileName = DateUtil.toString(new Date(), "mmss") + StringUtil.getRandStr(4) + "." + ext;

        /********************************
         *    Generate the accessurlAnd disk path
         *********************************/
        String subPath=  "/images/" + scene + "/"+fileName;
        // Back to browser path
        String  url = serverName + subPath;

        // Project or FAT JAR root directory
        ApplicationHome home = new ApplicationHome(WebInterceptorConfigurer.class);
        File jarFile = home.getSource();
        String rootPath = jarFile.getParentFile().toString();
        // Disk path = root path + subPath
        String filePath = rootPath+ subPath;


        // Written to the file
        FileUtil.write(input.getStream(), filePath);
        try {
            if ("goods".equals(scene)) {
                // Generate thumbnails
                createThumbnail(filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Back to browser
        FileVO file = new FileVO();
        file.setName(fileName);
        file.setUrl(url);
        file.setExt(ext);
        return file;
    }

    @Autowired
    private SettingClient settingClient;

    /**
     * Generate thumbnails for the original image
     * @param origin
     */
    private void createThumbnail(String origin) throws IOException {

        // Gets the photo specification Settings for an album
        String photoSizeSettingJson = settingClient.get(SettingGroup.GOODS);
        GoodsSettingVO photoSizeSetting = JsonUtil.jsonToObject(photoSizeSettingJson, GoodsSettingVO.class);

        // The thumbnail
        String thumbnail = getThumbnailUrl(origin, photoSizeSetting.getThumbnailWidth(), photoSizeSetting.getThumbnailHeight());
        // insets
        String small = getThumbnailUrl(origin, photoSizeSetting.getSmallWidth(), photoSizeSetting.getSmallHeight());
        // A larger version
        String big = getThumbnailUrl(origin, photoSizeSetting.getBigWidth(), photoSizeSetting.getBigHeight());

        // Generate thumbnails of various specifications
        ImageUtil.createThumbnail(origin,thumbnail,photoSizeSetting.getThumbnailWidth(), photoSizeSetting.getThumbnailHeight());
        ImageUtil.createThumbnail(origin,small,photoSizeSetting.getSmallWidth(), photoSizeSetting.getSmallHeight());
        ImageUtil.createThumbnail(origin,big,photoSizeSetting.getBigWidth(), photoSizeSetting.getBigHeight());

    }

    /**
     * Generate thumbnail full path Local storage Thumbnail format is the original image name_widexhigh.The suffix of the original picture name
     * As shown in the original figure, path is/User/2017/03/04/original.jpg, need to generate100x100The thumbnail full path is
     * /User/2017/03/04/original.jpg_100x100.jpg
     */
    @Override
    public String getThumbnailUrl(String url, Integer width, Integer height) {
        // The suffix of the original screenshot
        String suffix = url.substring(url.lastIndexOf("."), url.length());
        // Thumbnail full path
        String thumbnailPah = url + "_" + width + "x" + height + suffix;
        // Returns the thumbnail full path
        return thumbnailPah;

    }

    @Override
    public String getPluginName() {
        return "The local store";
    }

    @Override
    public Integer getIsOpen() {
        return 1;
    }

}
