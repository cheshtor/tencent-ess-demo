package com.ganpengyu.ess.studio.ess.model.certificate;

import lombok.Data;

/**
 * @author Pengyu Gan
 * CreateDate 2022/12/1
 */
@Data
public class ConsoleURLResponse {

    /**
     * 控制台 URL
     */
    private String consoleUrl;

    /**
     * 企业是否已实名
     */
    private boolean activated;

    /**
     * 企业管理员是否已实名
     */
    private boolean proxyOperatorIsVerified;

    /**
     * 请求流水号
     */
    private String requestId;

}
