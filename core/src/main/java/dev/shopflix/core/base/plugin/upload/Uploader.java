/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.plugin.upload;

import dev.shopflix.core.base.model.dto.FileDTO;
import dev.shopflix.core.base.model.vo.ConfigItem;
import dev.shopflix.core.base.model.vo.FileVO;

import java.util.List;
import java.util.Map;


/**
 * 存储方案参数接口
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月19日 下午4:54:28
 */
public interface Uploader {

    /**
     * 配置各个存储方案的参数
     *
     * @return 参数列表
     */
    List<ConfigItem> definitionConfigItem();

    /**
     * 上传文件
     *
     * @param input  上传对象
     * @param scene  业务场景
     * @param config 配置信息
     * @return
     */
    FileVO upload(FileDTO input, String scene, Map config);

    /**
     * 删除文件
     *
     * @param filePath 文件地址
     * @param config   配置信息
     */
    void deleteFile(String filePath, Map config);

    /**
     * 获取插件ID
     *
     * @return 插件beanId
     */
    String getPluginId();

    /**
     * 生成缩略图路径
     *
     * @param url    原图片全路径
     * @param width  需要生成图片尺寸的宽
     * @param height 需要生成图片尺寸的高
     * @return 生成的缩略图路径
     */
    String getThumbnailUrl(String url, Integer width, Integer height);

    /**
     * 存储方案是否开启
     *
     * @return 0 不开启  1 开启
     */
    Integer getIsOpen();

    /**
     * 获取插件名称
     *
     * @return 插件名称
     */
    String getPluginName();

}
