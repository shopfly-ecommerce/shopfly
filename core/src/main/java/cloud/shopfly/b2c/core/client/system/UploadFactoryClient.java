/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.system;

import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @version v7.0
 * @Description: 存储方案Client
 * @Author: zjp
 * @Date: 2018/7/27 16:26
 */
public interface UploadFactoryClient {

    /**
     * 获取拼接后的url
     * @param url
     * @param width
     * @param height
     * @return
     */
     String getUrl(String url, Integer width, Integer height);

    /**
     * 文件上传
     * @param file 文件
     * @param scene	业务类型 goods,shop,member,other
     * @return
     */
    FileVO upload(MultipartFile file, String scene);
}
