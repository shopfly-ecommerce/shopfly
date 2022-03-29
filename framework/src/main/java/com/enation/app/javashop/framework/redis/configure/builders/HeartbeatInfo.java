/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.redis.configure.builders;

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
