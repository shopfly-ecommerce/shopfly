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
 * Ali cloudossFile upload plug-in
 *
 * @author mengyuanming
 * @version v1.0
 * @since v6.4.0
 * @date 2017years8month14On the afternoon8:03:16
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
		endPoint.setText("The domain name");

		ConfigItem buketName = new ConfigItem();
		buketName.setType("text");
		buketName.setName("bucketName");
		buketName.setText("Storage space");

		ConfigItem picLocation = new ConfigItem();
		picLocation.setType("text");
		picLocation.setName("picLocation");
		picLocation.setText("The secondary path");

		ConfigItem accessKeyId = new ConfigItem();
		accessKeyId.setType("text");
		accessKeyId.setName("accessKeyId");
		accessKeyId.setText("The keyid");

		ConfigItem accessKeySecret = new ConfigItem();
		accessKeySecret.setType("text");
		accessKeySecret.setName("accessKeySecret");
		accessKeySecret.setText("The key");

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
		// Get file suffixes
		String ext = input.getExt();
		// The file name
		String picName = UUID.randomUUID().toString().toUpperCase().replace("-", "") + "." + ext;
		// File name, based on UUID
		String fileName = picLocation + scene + "/" + picName;
		FileVO file = new FileVO();
		file.setName(picName);

		file.setUrl(this.putObject(input.getStream(), ext, fileName, endpoint, bucketName, picLocation, accessKeyId, accessKeySecret,scene));
		file.setExt(ext);
		return file;

	}

	@Override
	public void deleteFile(String filePath,Map config) {
		// Obtain oss storage configuration information
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
		// Thumbnail full path
		String thumbnailPah = url + "_" + width + "x" + height;
		// Returns the thumbnail full path
		return thumbnailPah;
	}

	@Override
	public Integer getIsOpen() {
		return 0;
	}

	@Override
	public String getPluginName() {
		return "Ali cloudossstorage";
	}

	/**
	 * To upload pictures
	 *
	 * @param input
	 *            Input stream for uploading image files
	 * @param fileType
	 *            File type, that is, suffix
	 * @param fileName
	 *            The file name
	 * @param endpoint
	 *            The domain name
	 * @param bucketName
	 *            Storage space name
	 * @param picLocation
	 *            Secondary path Name
	 * @param accessKeyId
	 *            The keyid
	 * @param accessKeySecret
	 *            The key
	 * @return Access pictureurlThe path
	 */
	private String putObject(InputStream input, String fileType, String fileName, String endpoint, String bucketName,
			String picLocation, String accessKeyId, String accessKeySecret,String scene) {
		// The default null
		String urls = null;
		// The final return path
		String saveUrl = null;
		OSSClient ossClient = null;
		try {
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			// Create Metadata to upload Object
			ObjectMetadata meta = new ObjectMetadata();
			// Set the content type to be uploaded
			meta.setContentType(FileUtil.contentType(fileType));
			// Caching behavior of a web page when it is downloaded
			meta.setCacheControl("no-cache");
			PutObjectRequest request = new PutObjectRequest(bucketName, fileName, input, meta);
			// Creating an upload request
			ossClient.putObject(request);
			// Setting Object Permission
			boolean found = ossClient.doesObjectExist(bucketName, fileName);
			if (found = true) {
				ossClient.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
			}
			Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
			URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
			// Process the returned signature URL to get the final display URL
			urls = url.toString();
			String[] strs = urls.split("\\?");
			for (int i = 0, len = strs.length; i < len; i++) {
				saveUrl = strs[0].toString();
			}
			LOGER.info("OSSThe address that was uploaded successfully" + saveUrl);
		} catch (OSSException oe) {
			LOGER.error("OSSExceptionabnormal");
			oe.printStackTrace();
			throw new ServiceException(SystemErrorCode.E902.code(), "Uploading files failed. Procedure！");
		} catch (ClientException ce) {
			LOGER.error("ClientExceptionabnormal");
			ce.printStackTrace();
			throw new ServiceException(SystemErrorCode.E902.code(), "Uploading files failed. Procedure！");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(SystemErrorCode.E902.code(), "Uploading files failed. Procedure！");
		} finally {
			ossClient.shutdown();
		}
		return saveUrl;
	}
	/**
	 * According to theurlTo obtainfileName
	 *
	 * @param fileUrl
	 *            fileurl
	 * @return String fileName The file name
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
	 * According to theurlTo obtainbucketName
	 *
	 * @param fileUrl
	 *            fileurl
	 * @return String bucketName The domain name
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
	 * Deleting uploaded Files
	 *
	 * @param filePath
	 *            Example Delete a full file path
	 * @param endpoint
	 *            Storage space name
	 * @param accessKeyId
	 *            The keyid
	 * @param accessKeySecret
	 *            The key
	 * @return
	 */
	public boolean delete(String filePath, String endpoint, String accessKeyId, String accessKeySecret) {
		// Get bucketName based on the URL
		String bucketNames = OSSPlugin.getBucketName(filePath);
		// Get fileName from the URL
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
			throw new ServiceException(SystemErrorCode.E903.code(), "Failed to delete a file！");
		} finally {
			ossClient.shutdown();
		}
		return true;
	}

}
