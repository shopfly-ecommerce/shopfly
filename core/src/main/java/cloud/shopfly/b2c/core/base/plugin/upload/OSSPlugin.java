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

import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.FileUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.util.*;


/**
 * 阿里云oss文件上传插件
 *
 * @author mengyuanming
 * @version v1.0
 * @since v6.4.0
 * @date 2017年8月14日下午8:03:16
 *
 */
@SuppressWarnings("unchecked")
@Component
public class OSSPlugin implements Uploader {

	protected static final Log LOGER = LogFactory.getLog(OSSPlugin.class);

	@Override
	public List<ConfigItem> definitionConfigItem() {
		List<ConfigItem> list = new ArrayList();

		ConfigItem endPoint = new ConfigItem();
		endPoint.setType("text");
		endPoint.setName("endpoint");
		endPoint.setText("域名");

		ConfigItem buketName = new ConfigItem();
		buketName.setType("text");
		buketName.setName("bucketName");
		buketName.setText("储存空间");

		ConfigItem picLocation = new ConfigItem();
		picLocation.setType("text");
		picLocation.setName("picLocation");
		picLocation.setText("二级路径");

		ConfigItem accessKeyId = new ConfigItem();
		accessKeyId.setType("text");
		accessKeyId.setName("accessKeyId");
		accessKeyId.setText("密钥id");

		ConfigItem accessKeySecret = new ConfigItem();
		accessKeySecret.setType("text");
		accessKeySecret.setName("accessKeySecret");
		accessKeySecret.setText("密钥");

		list.add(endPoint);
		list.add(buketName);
		list.add(picLocation);
		list.add(accessKeyId);
		list.add(accessKeySecret);

		return list;
	}

	@Override
	public FileVO upload(FileDTO input, String scene, Map config) {
		String endpoint = StringUtil.toString(config.get("endpoint"));
		String bucketName = StringUtil.toString(config.get("bucketName"));
		String picLocation = StringUtil.toString(config.get("picLocation"));
		String accessKeyId = StringUtil.toString(config.get("accessKeyId"));
		String accessKeySecret = StringUtil.toString(config.get("accessKeySecret"));
		// 获取文件后缀
		String ext = input.getExt();
		//文件名称
		String picName = UUID.randomUUID().toString().toUpperCase().replace("-", "") + "." + ext;
		// 文件名，根据UUID来
		String fileName = picLocation + scene + "/" + picName;
		FileVO file = new FileVO();
		file.setName(picName);

		file.setUrl(this.putObject(input.getStream(), ext, fileName, endpoint, bucketName, picLocation, accessKeyId, accessKeySecret,scene));
		file.setExt(ext);
		return file;

	}

	@Override
	public void deleteFile(String filePath,Map config) {
		//获取oss存储配置信息
		String endpoint = StringUtil.toString(config.get("endpoint"));
		String accessKeyId = StringUtil.toString(config.get("accessKeyId"));
		String accessKeySecret = StringUtil.toString(config.get("accessKeySecret"));

		this.delete(filePath, endpoint, accessKeyId, accessKeySecret);
	}

	@Override
	public String getPluginId() {
		return "ossPlugin";
	}

	@Override
	public String getThumbnailUrl(String url, Integer width, Integer height) {
		// 缩略图全路径
		String thumbnailPah = url + "_" + width + "x" + height;
		// 返回缩略图全路径
		return thumbnailPah;
	}

	@Override
	public Integer getIsOpen() {
		return 0;
	}

	@Override
	public String getPluginName() {
		return "阿里云oss存储";
	}

	/**
	 * 上传图片
	 *
	 * @param input
	 *            上传图片文件的输入流
	 * @param fileType
	 *            文件类型，也就是后缀
	 * @param fileName
	 *            文件名称
	 * @param endpoint
	 *            域名
	 * @param bucketName
	 *            储存空间名称
	 * @param picLocation
	 *            二级路径名称
	 * @param accessKeyId
	 *            密钥id
	 * @param accessKeySecret
	 *            密钥
	 * @return 访问图片的url路径
	 */
	private String putObject(InputStream input, String fileType, String fileName, String endpoint, String bucketName,
			String picLocation, String accessKeyId, String accessKeySecret,String scene) {
		// 默认null
		String urls = null;
		// 最终返回的路径
		String saveUrl = null;
		OSSClient ossClient = null;
		try {
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 设置上传内容类型
			meta.setContentType(FileUtil.contentType(fileType));
			// 被下载时网页的缓存行为
			meta.setCacheControl("no-cache");
			PutObjectRequest request = new PutObjectRequest(bucketName, fileName, input, meta);
			// 创建上传请求
			ossClient.putObject(request);
			// 设置Object权限
			boolean found = ossClient.doesObjectExist(bucketName, fileName);
			if (found = true) {
				ossClient.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
			}
			Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
			URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
			// 对返回的签名url处理获取最终展示用的url
			urls = url.toString();
			String[] strs = urls.split("\\?");
			for (int i = 0, len = strs.length; i < len; i++) {
				saveUrl = strs[0].toString();
			}
			LOGER.info("OSS上传成功的地址" + saveUrl);
		} catch (OSSException oe) {
			LOGER.error("OSSException异常");
			oe.printStackTrace();
			throw new ServiceException(SystemErrorCode.E902.code(), "上传文件失败！");
		} catch (ClientException ce) {
			LOGER.error("ClientException异常");
			ce.printStackTrace();
			throw new ServiceException(SystemErrorCode.E902.code(), "上传文件失败！");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(SystemErrorCode.E902.code(), "上传文件失败！");
		} finally {
			ossClient.shutdown();
		}
		return saveUrl;
	}
	/**
	 * 根据url获取fileName
	 *
	 * @param fileUrl
	 *            文件url
	 * @return String fileName 文件名称
	 */
	private static String getFileName(String fileUrl) {
		String str = "aliyuncs.com/";
		int beginIndex = fileUrl.indexOf(str);
		if (beginIndex == -1) {
			return null;
		}

		return fileUrl.substring(beginIndex + str.length());
	}

	/**
	 * 根据url获取bucketName
	 *
	 * @param fileUrl
	 *            文件url
	 * @return String bucketName 域名
	 */
	private static String getBucketName(String fileUrl) {
		String http = "http://";
		String https = "https://";
		int httpIndex = fileUrl.indexOf(http);
		int httpsIndex = fileUrl.indexOf(https);
		int startIndex = 0;
		if (httpIndex == -1) {
			if (httpsIndex == -1) {
				return null;
			} else {
				startIndex = httpsIndex + https.length();
			}
		} else {
			startIndex = httpIndex + http.length();
		}
		int endIndex = fileUrl.indexOf(".oss-");
		return fileUrl.substring(startIndex, endIndex);
	}

	/**
	 * 删除上传文件
	 *
	 * @param filePath
	 *            删除文件全路径
	 * @param endpoint
	 *            储存空间名称
	 * @param accessKeyId
	 *            密钥id
	 * @param accessKeySecret
	 *            密钥
	 * @return
	 */
	public boolean delete(String filePath, String endpoint, String accessKeyId, String accessKeySecret) {
		// 根据url获取bucketName
		String bucketNames = OSSPlugin.getBucketName(filePath);
		// 根据url获取fileName
		String fileName = OSSPlugin.getFileName(filePath);
		if (bucketNames == null || fileName == null) {
			return false;
		}
		OSSClient ossClient = null;
		try {
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			GenericRequest request = new DeleteObjectsRequest(bucketNames).withKey(fileName);
			ossClient.deleteObject(request);
		} catch (Exception oe) {
			oe.printStackTrace();
			throw new ServiceException(SystemErrorCode.E903.code(), "删除文件失败！");
		} finally {
			ossClient.shutdown();
		}
		return true;
	}

}
