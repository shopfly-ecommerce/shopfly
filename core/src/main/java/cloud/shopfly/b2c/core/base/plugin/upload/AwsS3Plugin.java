package cloud.shopfly.b2c.core.base.plugin.upload;

import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * aws S3 uploader
 *
 * @author fengkun
 * @version v1.0
 * @date 2022-04-26 15:13:41
 * @since v7.2.0
 */
@Component
public class AwsS3Plugin implements Uploader {
    @Override
    public List<ConfigItem> definitionConfigItem() {

        List<ConfigItem> list = new ArrayList();

        ConfigItem picLocation = new ConfigItem();
        picLocation.setType("text");
        picLocation.setName("region");
        picLocation.setText("region");

        ConfigItem bucketName = new ConfigItem();
        bucketName.setType("text");
        bucketName.setName("bucketName");
        bucketName.setText("bucketName");

        ConfigItem accessKeyId = new ConfigItem();
        accessKeyId.setType("text");
        accessKeyId.setName("accessKeyId");
        accessKeyId.setText("Access key ID");

        ConfigItem accessKeySecret = new ConfigItem();
        accessKeySecret.setType("text");
        accessKeySecret.setName("accessKeySecret");
        accessKeySecret.setText("Secret access key");

        list.add(picLocation);
        list.add(bucketName);
        list.add(accessKeyId);
        list.add(accessKeySecret);

        return list;
    }

    @Override
    public FileVO upload(FileDTO input, String scene, Map config) {

        String bucketName = StringUtil.toString(config.get("bucketName"));
        String region = StringUtil.toString(config.get("region"));
        String accessKeyId = StringUtil.toString(config.get("accessKeyId"));
        String accessKeySecret = StringUtil.toString(config.get("accessKeySecret"));

        // Get file suffix
        String ext = input.getExt();
        // random file name
        String picName = UUID.randomUUID().toString().toUpperCase().replace("-", "") + "." + ext;
        // file name
        String fileName = scene + "/" + picName;
        FileVO file = new FileVO();
        file.setName(picName);
        boolean flag = this.putObject(input.getStream(), input.getSize(), fileName, bucketName, region, accessKeyId, accessKeySecret);
        if (flag) {
            file.setUrl("https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName);
            file.setExt(ext);
        }

        return file;
    }

    private boolean putObject(InputStream stream, Long size, String fileName,
                              String bucketName, String regionStr,
                              String accessKeyId, String accessKeySecret) {

        Region region = Region.of(regionStr);
        S3Client s3 = S3Client.builder()
                .credentialsProvider(getAwsCredentialsProviderChain(accessKeyId, accessKeySecret))
                .region(region)
                .build();

        try {

            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .metadata(metadata)
                    .build();

            PutObjectResponse response = s3.putObject(putOb,
                    RequestBody.fromInputStream(stream, size));


            return true;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    private AwsCredentialsProviderChain getAwsCredentialsProviderChain(String accessKeyId, String accessKeySecret) {

        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(new AwsCredentialsProvider() {
                    @Override
                    public AwsCredentials resolveCredentials() {
                        return AwsBasicCredentials.create(accessKeyId, accessKeySecret);
                    }
                }).build();
    }


    @Override
    public void deleteFile(String filePath, Map config) {

        String bucketName = StringUtil.toString(config.get("bucketName"));
        String regionStr = StringUtil.toString(config.get("region"));
        String accessKeyId = StringUtil.toString(config.get("accessKeyId"));
        String accessKeySecret = StringUtil.toString(config.get("accessKeySecret"));

        ArrayList<ObjectIdentifier> toDelete = new ArrayList<ObjectIdentifier>();
        toDelete.add(ObjectIdentifier.builder().key(filePath).build());

        Region region = Region.of(regionStr);
        S3Client s3 = S3Client.builder()
                .credentialsProvider(getAwsCredentialsProviderChain(accessKeyId, accessKeySecret))
                .region(region)
                .build();

        try {
            DeleteObjectsRequest dor = DeleteObjectsRequest.builder()
                    .bucket(bucketName)
                    .delete(Delete.builder().objects(toDelete).build())
                    .build();
            s3.deleteObjects(dor);
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }

    @Override
    public String getPluginId() {
        return "awsS3Plugin";
    }

    @Override
    public String getThumbnailUrl(String url, Integer width, Integer height) {
        //url is https://javashop.s3.us-west-1.amazonaws.com/goods/84458C0B9F9E4C03A37FEA2AEBD40015.jpeg
        //hope : https://javashop-thumbnail.s3.us-west-1.amazonaws.com/goods/84458C0B9F9E4C03A37FEA2AEBD40015.jpeg_300x300
        String reg = "\\/\\/([^.]*)\\.";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {

            String bucketName = matcher.group(1);
            url = url.replaceFirst(bucketName, bucketName + "-thumbnail");
            return url + "_" + width + "x" + height;
        }

        return url;
    }

    @Override
    public Integer getIsOpen() {
        return 0;
    }

    @Override
    public String getPluginName() {
        return "aws S3";
    }

}
