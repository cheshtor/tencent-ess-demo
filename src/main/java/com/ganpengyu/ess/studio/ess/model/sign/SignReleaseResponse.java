package com.ganpengyu.ess.studio.ess.model.sign;

import lombok.Data;

/**
 * 发起解除协议响应
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
@Data
public class SignReleaseResponse {

    /**
     * 待解除的合同流程 ID
     */
    private String flowId;

    /**
     * 解除协议的合同流程 ID
     */
    private String releaseFlowId;

    /**
     * 甲方解除协议的签署链接
     */
    private String firstPartyReleaseSignUrl;

    /**
     * 乙方解除协议的签署链接
     */
    private String secondPartyReleaseSignUrl;

    /**
     * 发起成功标志
     */
    private boolean success;

    /**
     * 发起失败错误原因
     */
    private String errorMessage;

}
