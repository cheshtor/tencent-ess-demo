package com.ganpengyu.ess.studio.ess.model.sign;

import lombok.Data;

/**
 * 发起合同签署流程响应
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@Data
public class SignResponse {

    /**
     * 是否发起成功
     */
    private boolean success;

    /**
     * 发起失败错误信息
     */
    private String errorMessage;

    /**
     * 业务方分配的合同 ID
     */
    private String contractId;

    /**
     * 合同 ID
     */
    private String flowId;

    /**
     * 甲方
     */
    private ApproverResponse firstParty;

    /**
     * 乙方
     */
    private ApproverResponse secondParty;

}
