/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.base.service;

import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月19日 下午4:37:44
 */
public interface FileManager {
    /**
     * 文件上传
     *
     * @param input 文件
     * @param scene 业务类型
     * @return
     */
    FileVO upload(FileDTO input, String scene);

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    void deleteFile(String filePath);

    /**
     * 跨服务调用的文件上传
     * @param file 文件
     * @param scene	业务类型 goods,shop,member,other
     * @return
     */
    FileVO uploadFile(MultipartFile file, String scene);


}
