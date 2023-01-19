package com.ganpengyu.ess.studio.ess.model.template;

import lombok.Data;

/**
 * 合同模板签署人
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@Data
public class TemplateRecipient {

    /**
     * 签署人 ID
     */
    private String recipientId;

    /**
     * 签署人类型
     * 企业 - ENTERPRISE
     * 个人 - INDIVIDUAL
     */
    private String recipientType;

    /**
     * 签署人备注信息
     */
    private String roleName;

    /**
     * 是否为签署发起方
     */
    private boolean promoter;

}
