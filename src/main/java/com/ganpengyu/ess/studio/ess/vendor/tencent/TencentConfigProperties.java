package com.ganpengyu.ess.studio.ess.vendor.tencent;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pengyu Gan
 * CreateDate 2022/11/30
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ess.tencent")
public class TencentConfigProperties {

    private String secretId;

    private String secretKey;

    private String appId;

    /**
     * 渠道方管理员 ID，用于查询渠道模板列表
     */
    private String channelUserId;

    /**
     * 渠道版接口调用地址
     */
    private String endPoint;

    /**
     * SAAS 版本接口调用地址
     */
    private String saasEndPoint;

    private String fileServiceEndPoint;

}
