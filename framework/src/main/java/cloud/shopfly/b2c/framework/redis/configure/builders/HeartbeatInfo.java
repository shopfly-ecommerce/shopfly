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
package cloud.shopfly.b2c.framework.redis.configure.builders;

/**
 * 由sohu tv cache cloud copy过来的
 * 心跳类
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public class HeartbeatInfo {

    /**
     * 应用id
     */
    private long appId;

    /**
     * 实例个数
     */
    private int shardNum;

    /**
     * 分配信息
     */
    private String shardInfo;

    /**
     * 应用状态
     */
    private int status;

    /**
     * 消息
     */
    private String message;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public int getShardNum() {
        return shardNum;
    }

    public void setShardNum(int shardNum) {
        this.shardNum = shardNum;
    }

    public String getShardInfo() {
        return shardInfo;
    }

    public void setShardInfo(String shardInfo) {
        this.shardInfo = shardInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HeartbeatInfo{" + "appId=" + appId + ", shardNum=" + shardNum
                + ", shardInfo='" + shardInfo + '\'' + ", status=" + status
                + ", message='" + message + '\'' + '}';
    }
}
