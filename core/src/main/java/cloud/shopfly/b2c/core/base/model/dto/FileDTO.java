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
package cloud.shopfly.b2c.core.base.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.InputStream;

/**
 * 文件上传入参
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月19日 下午4:42:51
 */
@ApiModel
public class FileDTO {
    /**
     * 文件流
     */
    @ApiModelProperty(name = "stream", value = "文件流", required = true)
    private InputStream stream;
    /**
     * 文件名称
     */
    @NotEmpty(message = "文件名称不能为空")
    @ApiModelProperty(name = "name", value = "文件名称", required = true)
    private String name;
    /**
     * 文件后缀
     */
    private String ext;

    /**
     * file size
     */
    private Long size;

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileDTO fileDTO = (FileDTO) o;

        if (stream != null ? !stream.equals(fileDTO.stream) : fileDTO.stream != null) {
            return false;
        }
        if (name != null ? !name.equals(fileDTO.name) : fileDTO.name != null) {
            return false;
        }
        return ext != null ? ext.equals(fileDTO.ext) : fileDTO.ext == null;
    }

    @Override
    public int hashCode() {
        int result = stream != null ? stream.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ext != null ? ext.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "stream=" + stream +
                ", name='" + name + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
