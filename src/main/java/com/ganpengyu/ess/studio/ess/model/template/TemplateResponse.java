package com.ganpengyu.ess.studio.ess.model.template;

import lombok.Data;

import java.util.List;

/**
 * 合同签署模板
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@Data
public class TemplateResponse {

    /**
     * 子客模板 ID
     */
    private String templateId;

    /**
     * 渠道模板 ID
     */
    private String channelTemplateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 签署人列表，仅在查询单个模板详情时有值
     */
    private List<TemplateRecipient> templateRecipients;

    /**
     * 模板控件列表，仅在查询单个模板详情时有值
     */
    private List<TemplateComponent> templateComponents;

}
