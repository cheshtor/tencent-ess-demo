package com.ganpengyu.ess.studio.ess;


import com.ganpengyu.ess.studio.ess.model.template.TemplateResponse;

import java.util.List;

/**
 * 电子签模板服务
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
public interface EssTemplateService {

    /**
     * 获取子客合同签署模板详情或子客合同签署模板列表
     * https://cloud.tencent.com/document/api/1420/61521
     *
     * @param companyId         业务系统分配的企业 ID
     * @param companyName       业务系统分配的企业名称
     * @param companyOperatorId 业务系统分配的企业经办人 ID
     * @param proxyAppId        子客在腾讯电子签的 ID
     * @param templateId        合同签署模板 ID，如果不传则表示查询所有的模板 (子客模板ID)
     * @return {@link TemplateResponse} 合同签署模板详情列表
     */
    List<TemplateResponse> getCompanyTemplates(Long companyId, String companyName, Long companyOperatorId, String proxyAppId, String templateId);

    /**
     * 获取渠道方的渠道模板列表。返回值仅包含渠道模板 ID 和渠道模板名称。
     *
     * @return {@link TemplateResponse} 渠道方的渠道模板列表
     */
    List<TemplateResponse> getChannelTemplates();

    /**
     * 获取渠道模板在子客模板库的 ID
     *
     * @param companyId         业务系统分配的企业 ID
     * @param companyName       业务系统分配的企业名称
     * @param companyOperatorId 业务系统分配的企业经办人 ID
     * @param proxyAppId        子客在腾讯电子签的 ID
     * @param channelTemplateId 渠道模板 ID
     * @return 渠道模板在子客模板库的 ID，如果子客没有此模板则返回 null
     */
    String getCompanyTemplateIdByChannelTemplateId(Long companyId, String companyName, Long companyOperatorId, String proxyAppId, String channelTemplateId);

}
