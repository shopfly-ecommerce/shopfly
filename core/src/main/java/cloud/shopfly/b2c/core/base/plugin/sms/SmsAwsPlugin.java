package cloud.shopfly.b2c.core.base.plugin.sms;

import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cn.hutool.core.collection.CollUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Dawei
 * Datetime: 2022-05-28 10:09
 */
@Component
public class SmsAwsPlugin implements SmsPlatform {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private Debugger debugger;

    @Override
    public boolean onSend(String phone, String content, Map param) {
        if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(content) || CollUtil.isEmpty(param)) {
            logger.error("Parameters for sending SMS messages are abnormal,Please check thephone=" + phone + " ,content=" + content + " ,param= " + param);
            return false;
        }
        debugger.log("Tuned up SmsAwsPlugin", "Parameters for：", param.toString());
        String region = (String) param.get("region");
        if (StringUtil.isEmpty(region)) {
            logger.error("region does not exist,Please check the region");
            return false;
        }
        String accessKeyId = (String) param.get("accessKeyId");
        if (StringUtil.isEmpty(accessKeyId)) {
            logger.error("accessKeyId does not exist,Please check the accessKeyId");
            return false;
        }
        String secretAccessKey = (String) param.get("secretAccessKey");
        if (StringUtil.isEmpty(secretAccessKey)) {
            logger.error("secretAccessKey does not exist,Please check the secretAccessKey");
            return false;
        }
        AwsCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        try (SnsClient snsClient = SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build()) {
            PublishRequest request = PublishRequest.builder()
                    .message(content)
                    .phoneNumber(phone)
                    .build();

            PublishResponse result = snsClient.publish(request);
            if (logger.isDebugEnabled()) {
                logger.debug(result.messageId() + " Message sent. Status was " + result.sdkHttpResponse().statusCode());
            }
            return result.sdkHttpResponse().statusCode() == 200;
        } catch (SnsException e) {
            logger.error("SMS message sending failed,Please check the phone=" + phone + " ,content=" + content + " ,param= " + param, e);
            return false;
        }
    }

    @Override
    public String getPluginId() {
        return "smsAwsPlugin";
    }

    @Override
    public String getPluginName() {
        return "Short message of the assistant gateway";
    }

    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList();

        ConfigItem accessKeyId = new ConfigItem();
        accessKeyId.setType("text");
        accessKeyId.setName("accessKeyId");
        accessKeyId.setText("Access key ID");

        ConfigItem secretAccessKey = new ConfigItem();
        secretAccessKey.setType("text");
        secretAccessKey.setName("secretAccessKey");
        secretAccessKey.setText("Secret Access Key");

        ConfigItem id = new ConfigItem();
        id.setType("text");
        id.setName("region");
        id.setText("Region");

        list.add(accessKeyId);
        list.add(secretAccessKey);
        list.add(id);

        return list;
    }

    @Override
    public Integer getIsOpen() {
        return 0;
    }
}
