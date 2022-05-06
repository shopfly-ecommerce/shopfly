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
package cloud.shopfly.b2c.core.pagecreate.exception;

/**
 * 系统异常
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-10 上午7:14
 */
public enum PageCreateErrorCode {


    //ERRORCODE
    E901("服务器异常"),
    E902("progressid 不能为空"),
    E903("静态页面地址 不能为空"),
    E904("生成静态页参数有误"),
    E905("有静态页生成任务正在进行中，需等待本次任务完成后才能再次生成。");
    private String describe;

    PageCreateErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取统计的异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }


    /**
     * 获取统计的错误消息
     *
     * @return
     */
    public String des() {
        return this.describe;
    }


}
