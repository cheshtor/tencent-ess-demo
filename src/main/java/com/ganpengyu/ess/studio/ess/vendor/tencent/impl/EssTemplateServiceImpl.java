package com.ganpengyu.ess.studio.ess.vendor.tencent.impl;

import com.ganpengyu.ess.studio.ess.EssTemplateService;
import com.ganpengyu.ess.studio.ess.common.EssKernelException;
import com.ganpengyu.ess.studio.ess.common.EssUtils;
import com.ganpengyu.ess.studio.ess.model.template.TemplateComponent;
import com.ganpengyu.ess.studio.ess.model.template.TemplateRecipient;
import com.ganpengyu.ess.studio.ess.model.template.TemplateResponse;
import com.ganpengyu.ess.studio.ess.vendor.tencent.TencentConfigProperties;
import com.ganpengyu.ess.studio.ess.vendor.tencent.TencentEssBootstrap;
import com.ganpengyu.ess.studio.ess.vendor.tencent.compatibly.SaasEssClient;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowTemplatesRequest;
import com.tencentcloudapi.ess.v20201111.models.DescribeFlowTemplatesResponse;
import com.tencentcloudapi.ess.v20201111.models.UserInfo;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.Component;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeTemplatesRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeTemplatesResponse;
import com.tencentcloudapi.essbasic.v20210526.models.Recipient;
import com.tencentcloudapi.essbasic.v20210526.models.TemplateInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 电子签模板服务实现
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@Slf4j
@Service
public class EssTemplateServiceImpl implements EssTemplateService {

    @Resource
    private TencentEssBootstrap essBootstrap;

    @Resource
    private TencentConfigProperties configProperties;

    @Override
    public List<TemplateResponse> getCompanyTemplates(Long companyId, String companyName, Long companyOperatorId, String proxyAppId, String templateId) {
        List<TemplateResponse> responses = new ArrayList<>();
        Agent agent = essBootstrap.getAgent(proxyAppId, String.valueOf(companyId), String.valueOf(companyOperatorId));
        DescribeTemplatesRequest request = new DescribeTemplatesRequest();
        request.setAgent(agent);
        if (EssUtils.isStringNotEmpty(templateId)) {
            request.setTemplateId(templateId);
        } else {
            request.setContentType(1L);
            request.setLimit(100L);
        }
        EssbasicClient client = essBootstrap.getClient();
        try {
            DescribeTemplatesResponse response = client.DescribeTemplates(request);
            log.info("[腾讯电子签请求追踪]获取企业合同模板。请求 ID：{}", response.getRequestId());
            TemplateInfo[] templates = response.getTemplates();
            if (null == templates || templates.length == 0) {
                throw new EssKernelException("合同模板不存在");
            }
            for (TemplateInfo template : templates) {
                // 模板基本信息
                TemplateResponse templateResponse = new TemplateResponse();
                templateResponse.setTemplateId(template.getTemplateId());
                templateResponse.setChannelTemplateId(template.getChannelTemplateId());
                templateResponse.setTemplateName(template.getTemplateName());
                // 解析模板控件
                Component[] components = template.getComponents();
                if (null != components && components.length > 0) {
                    List<TemplateComponent> templateComponents = Arrays.stream(components).map(component -> {
                        TemplateComponent templateComponent = new TemplateComponent();
                        templateComponent.setComponentId(component.getComponentId());
                        templateComponent.setComponentName(component.getComponentName());
                        templateComponent.setComponentDescription(component.getComponentDescription());
                        templateComponent.setComponentType(component.getComponentType());
                        templateComponent.setRequired(component.getComponentRequired());
                        templateComponent.setRecipientId(component.getComponentRecipientId());
                        return templateComponent;
                    }).collect(Collectors.toList());
                    templateResponse.setTemplateComponents(templateComponents);
                }
                // 解析模板签署人
                Recipient[] recipients = template.getRecipients();
                if (null != recipients && recipients.length > 0) {
                    List<TemplateRecipient> templateRecipients = Arrays.stream(recipients).map(recipient -> {
                        TemplateRecipient templateRecipient = new TemplateRecipient();
                        templateRecipient.setRecipientId(recipient.getRecipientId());
                        templateRecipient.setRecipientType(recipient.getRecipientType());
                        templateRecipient.setRoleName(recipient.getRoleName());
                        templateRecipient.setPromoter(recipient.getIsPromoter());
                        return templateRecipient;
                    }).collect(Collectors.toList());
                    templateResponse.setTemplateRecipients(templateRecipients);
                }
                responses.add(templateResponse);
            }
            return responses;
        } catch (TencentCloudSDKException e) {
            log.error("获取模板详情/列表失败。请求 ID：{}, companyId:{}, companyName:{}, companyOperatorId: {}, templateId: {}",
                    e.getRequestId(), companyId, companyName, companyOperatorId, templateId, e);
            throw new EssKernelException("获取模板详情/列表失败。" + e.getRequestId());
        }
    }

    @Override
    public List<TemplateResponse> getChannelTemplates() {
        DescribeFlowTemplatesRequest request = new DescribeFlowTemplatesRequest();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(configProperties.getChannelUserId());
        request.setOperator(userInfo);
        request.setLimit(200L);
        request.setIsChannel(true);

        SaasEssClient saasClient = essBootstrap.getSaasClient();
        try {
            DescribeFlowTemplatesResponse response = saasClient.DescribeFlowTemplates(request);
            log.info("[腾讯电子签请求追踪]获取渠道合同模板。请求 ID：{}", response.getRequestId());
            com.tencentcloudapi.ess.v20201111.models.TemplateInfo[] templates = response.getTemplates();
            return Arrays.stream(templates).map(template -> {
                TemplateResponse templateResponse = new TemplateResponse();
                templateResponse.setChannelTemplateId(template.getTemplateId());
                templateResponse.setTemplateName(template.getTemplateName());
                return templateResponse;
            }).collect(Collectors.toList());
        } catch (TencentCloudSDKException e) {
            log.error("获取渠道侧模板信息列表异常。请求 ID：{}", e.getRequestId(), e);
            throw new EssKernelException("获取渠道侧模板信息列表失败。" + e.getRequestId());
        }
    }

    @Override
    public String getCompanyTemplateIdByChannelTemplateId(Long companyId, String companyName, Long companyOperatorId, String proxyAppId, String channelTemplateId) {
        List<TemplateResponse> companyTemplates = getCompanyTemplates(companyId, companyName, companyOperatorId, proxyAppId, null);
        Optional<TemplateResponse> optional = companyTemplates.stream()
                .filter(companyTemplate -> companyTemplate.getChannelTemplateId().equals(channelTemplateId))
                .findAny();
        return optional.map(TemplateResponse::getTemplateId).orElse(null);
    }
}
