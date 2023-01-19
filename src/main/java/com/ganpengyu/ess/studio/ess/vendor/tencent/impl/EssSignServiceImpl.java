package com.ganpengyu.ess.studio.ess.vendor.tencent.impl;

import com.ganpengyu.ess.studio.ess.EssSignService;
import com.ganpengyu.ess.studio.ess.EssTemplateService;
import com.ganpengyu.ess.studio.ess.common.EssKernelException;
import com.ganpengyu.ess.studio.ess.common.EssUtils;
import com.ganpengyu.ess.studio.ess.model.enums.ApproveTypeEnum;
import com.ganpengyu.ess.studio.ess.model.enums.SignURLTypeEnum;
import com.ganpengyu.ess.studio.ess.model.sign.ApproverResponse;
import com.ganpengyu.ess.studio.ess.model.sign.SignApprover;
import com.ganpengyu.ess.studio.ess.model.sign.SignCancelResponse;
import com.ganpengyu.ess.studio.ess.model.sign.SignReleaseResponse;
import com.ganpengyu.ess.studio.ess.model.sign.SignResponse;
import com.ganpengyu.ess.studio.ess.model.template.TemplateRecipient;
import com.ganpengyu.ess.studio.ess.model.template.TemplateResponse;
import com.ganpengyu.ess.studio.ess.vendor.tencent.EssClient;
import com.ganpengyu.ess.studio.ess.vendor.tencent.TencentEssBootstrap;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelBatchCancelFlowsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelBatchCancelFlowsResponse;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateReleaseFlowRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateReleaseFlowResponse;
import com.tencentcloudapi.essbasic.v20210526.models.CreateFlowsByTemplatesRequest;
import com.tencentcloudapi.essbasic.v20210526.models.CreateFlowsByTemplatesResponse;
import com.tencentcloudapi.essbasic.v20210526.models.CreateSignUrlsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.CreateSignUrlsResponse;
import com.tencentcloudapi.essbasic.v20210526.models.FlowApproverInfo;
import com.tencentcloudapi.essbasic.v20210526.models.FlowInfo;
import com.tencentcloudapi.essbasic.v20210526.models.FormField;
import com.tencentcloudapi.essbasic.v20210526.models.RelieveInfo;
import com.tencentcloudapi.essbasic.v20210526.models.SignUrlInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 电子签签署服务实现
 *
 * @author Pengyu Gan
 * CreateDate 2022/11/29
 */
@Slf4j
@Service
public class EssSignServiceImpl implements EssSignService {

    @Resource
    private TencentEssBootstrap essBootstrap;

    @Resource
    private EssTemplateService templateService;

    @Override
    public List<SignResponse> findFlowDetails(Long companyId, Long companyOperatorId, String proxyAppId, SignURLTypeEnum signURLTypeEnum, String jumpUrl, List<String> flowIds) {
        if (CollectionUtils.isEmpty(flowIds)) {
            throw new EssKernelException("没有需要查询详情的合同");
        }
        if (flowIds.size() > 100) {
            throw new EssKernelException("批量查询合同详情不能超过 100 分合同");
        }
        if (null == signURLTypeEnum) {
            signURLTypeEnum = SignURLTypeEnum.WEIXINAPP;
        }
        Agent agent = essBootstrap.getAgent(proxyAppId, String.valueOf(companyId), String.valueOf(companyOperatorId));
        EssClient client = essBootstrap.getClient();

        CreateSignUrlsRequest request = new CreateSignUrlsRequest();
        request.setAgent(agent);
        request.setEndpoint(signURLTypeEnum.getType());
        request.setGenerateType("ALL"); // 同时查询甲乙双方签署人
        request.setFlowIds(flowIds.toArray(new String[]{}));
        // 当生成 H5 页面签署链接时，设置签署完成后的跳转地址
        if (signURLTypeEnum.equals(SignURLTypeEnum.CHANNEL) && EssUtils.isStringNotEmpty(jumpUrl)) {
            request.setJumpUrl(jumpUrl);
        }
        try {
            CreateSignUrlsResponse response = client.CreateSignUrls(request);
            log.info("[腾讯电子签请求追踪]查询合同详情。请求 ID：{}", response.getRequestId());
            // 每个 flowId 对应一个错误信息，没有错误则是空字符串
            String[] errorMessages = response.getErrorMessages();
            SignUrlInfo[] signUrlInfos = response.getSignUrlInfos();
            List<SignResponse> responses = new ArrayList<>();
            // 腾讯会按照传入的 flowId 的顺序依次返回这份合同的所有签署方信息，所以这个自增变量不能放在循环内部
            int signUrlInfoIndex = 0;
            for (int i = 0; i < errorMessages.length; i++) {
                String errorMessage = errorMessages[i];
                SignResponse signResponse = new SignResponse();
                // 返回的错误信息和传入请求的合同 ID 按顺序对应
                signResponse.setFlowId(flowIds.get(i));
                // 根据错误信息是否为空字符串来判断是否有错误
                signResponse.setSuccess(EssUtils.isStringEmpty(errorMessage));
                signResponse.setErrorMessage(errorMessage);
                if (signResponse.isSuccess()) {
                    // 单方签署的情况
                    if (signUrlInfos.length == flowIds.size()) {
                        SignUrlInfo signUrlInfo = signUrlInfos[0];
                        ApproverResponse approver = mapSignUrlInfoToApproverResponse(signUrlInfo);
                        signResponse.setSecondParty(approver);
                    } else if (signUrlInfos.length / 2 == flowIds.size()) { // 双方签署的情况
                        int signUrlInfoIndex1 = signUrlInfoIndex++;
                        int signUrlInfoIndex2 = signUrlInfoIndex++;
                        // 一个合同 ID 会返回甲乙两方签署人的详情，所以当获取成功时
                        // 需要按顺序取两个结果作为甲方和乙方进行解析
                        SignUrlInfo signUrlInfo1 = signUrlInfos[signUrlInfoIndex1];
                        SignUrlInfo signUrlInfo2 = signUrlInfos[signUrlInfoIndex2];
                        ApproverResponse approver1 = mapSignUrlInfoToApproverResponse(signUrlInfo1);
                        ApproverResponse approver2 = mapSignUrlInfoToApproverResponse(signUrlInfo2);
                        // 设置第一个签署人的身份
                        if (approver1.getApproveType().equals(ApproveTypeEnum.ORGANIZATION)) {
                            signResponse.setFirstParty(approver1);
                        } else if (approver1.getApproveType().equals(ApproveTypeEnum.PERSON)) {
                            signResponse.setSecondParty(approver1);
                        }
                        // 设置第二个签署人的身份
                        if (approver2.getApproveType().equals(ApproveTypeEnum.ORGANIZATION)) {
                            signResponse.setFirstParty(approver2);
                        } else if (approver2.getApproveType().equals(ApproveTypeEnum.PERSON)) {
                            signResponse.setSecondParty(approver2);
                        }
                    } else {
                        throw new EssKernelException("不允许同时查询合同签署方数量不一致的合同的签署链接。");
                    }
                }
                responses.add(signResponse);
            }
            return responses;
        } catch (TencentCloudSDKException e) {
            log.error("为合同生成签署链接异常。请求 ID：{}, companyId:{}, companyOperatorId:{}, proxyAppId:{}, signURLTypeEnum:{}, flowIds: {}",
                    e.getRequestId(), companyId, companyOperatorId, proxyAppId, signURLTypeEnum.getType(), flowIds);
            throw new EssKernelException("为合同生成签署链接异常。" + e.getRequestId());
        }
    }

    @Override
    public List<SignReleaseResponse> release(Long companyId, Long companyOperatorId, String proxyAppId, String reason, List<String> flowIds) {
        if (CollectionUtils.isEmpty(flowIds)) {
            throw new EssKernelException("没有需要发起解除协议的合同");
        }
        List<SignReleaseResponse> responses = new ArrayList<>();
        Agent agent = essBootstrap.getAgent(proxyAppId, String.valueOf(companyId), String.valueOf(companyOperatorId));
        EssClient client = essBootstrap.getClient();
        // 为每一个合同创建一份解除协议
        for (String flowId : flowIds) {
            SignReleaseResponse releaseResponse = new SignReleaseResponse();
            releaseResponse.setFlowId(flowId);

            ChannelCreateReleaseFlowRequest request = new ChannelCreateReleaseFlowRequest();
            request.setAgent(agent);
            request.setNeedRelievedFlowId(flowId);
            RelieveInfo relieveInfo = new RelieveInfo();
            relieveInfo.setReason(reason);
            relieveInfo.setRemainInForceItem("无");
            relieveInfo.setOriginalExpenseSettlement("无");
            relieveInfo.setOriginalOtherSettlement("无");
            relieveInfo.setOtherDeals("无");
            request.setReliveInfo(relieveInfo);
            try {
                ChannelCreateReleaseFlowResponse response = client.ChannelCreateReleaseFlow(request);
                log.info("[腾讯电子签请求追踪]发起合同解除。请求 ID：{}", response.getRequestId());
                String releaseFlowId = response.getFlowId();
                CreateSignUrlsResponse signUrlsResponse = createSignUrls(agent, client, new String[]{releaseFlowId});
                if (!EssUtils.isArrayOnlyContainsEmptyString(signUrlsResponse.getErrorMessages())) {
                    continue;
                }
                SignUrlInfo[] signUrlInfos = signUrlsResponse.getSignUrlInfos();
                for (SignUrlInfo signUrlInfo : signUrlInfos) {
                    if (signUrlInfo.getApproverType().equals(ApproveTypeEnum.ORGANIZATION.getType())) {
                        releaseResponse.setFirstPartyReleaseSignUrl(signUrlInfo.getSignUrl());
                    } else if (signUrlInfo.getApproverType().equals(ApproveTypeEnum.PERSON.getType())) {
                        releaseResponse.setSecondPartyReleaseSignUrl(signUrlInfo.getSignUrl());
                    }
                }
                releaseResponse.setReleaseFlowId(releaseFlowId);
                releaseResponse.setSuccess(true);
            } catch (TencentCloudSDKException e) {
                releaseResponse.setErrorMessage(e.getMessage());
                releaseResponse.setSuccess(false);
            } finally {
                responses.add(releaseResponse);
            }
        }
        return responses;
    }

    @Override
    public List<SignCancelResponse> cancel(Long companyId, Long companyOperatorId, String proxyAppId, String reason, List<String> flowIds) {
        if (CollectionUtils.isEmpty(flowIds)) {
            throw new EssKernelException("没有需要撤销的合同");
        }
        List<SignCancelResponse> cancelResponses = new ArrayList<>();
        Agent agent = essBootstrap.getAgent(proxyAppId, String.valueOf(companyId), String.valueOf(companyOperatorId));
        ChannelBatchCancelFlowsRequest request = new ChannelBatchCancelFlowsRequest();
        request.setAgent(agent);
        request.setFlowIds(flowIds.toArray(new String[]{}));
        request.setCancelMessage(reason);
        EssClient client = essBootstrap.getClient();
        try {
            ChannelBatchCancelFlowsResponse response = client.ChannelBatchCancelFlows(request);
            log.info("[腾讯电子签请求追踪]发起合同撤销。请求 ID：{}", response.getRequestId());
            String[] failMessages = response.getFailMessages();
            boolean batchSuccess = EssUtils.isArrayEmpty(failMessages);
            for (int i = 0; i < flowIds.size(); i++) {
                SignCancelResponse cancelResponse = new SignCancelResponse();
                cancelResponse.setFlowId(flowIds.get(i));
                if (batchSuccess) {
                    cancelResponse.setSuccess(true);
                } else {
                    cancelResponse.setSuccess(EssUtils.isStringEmpty(failMessages[i]));
                    cancelResponse.setErrorMessage(failMessages[i]);
                }
                cancelResponses.add(cancelResponse);
            }
            return cancelResponses;
        } catch (TencentCloudSDKException e) {
            log.error("合同撤销失败。请求 ID：{}, companyId:{}, companyOperatorId:{}, proxyAppId:{}, 合同 ID 列表：{}",
                    e.getRequestId(), companyId, companyOperatorId, proxyAppId, flowIds);
            throw new EssKernelException("合同撤销异常。" + e.getRequestId());
        }
    }

    @Override
    public List<SignResponse> sign(Long companyId, Long companyOperatorId, String proxyAppId, String companyName, String contractType, Long deadline, String templateId, List<SignApprover> signApproverList) {
        if (CollectionUtils.isEmpty(signApproverList)) {
            throw new EssKernelException("没有合同签署人（乙方）");
        }
        if (signApproverList.size() > 20) {
            throw new EssKernelException("不能同时发起超过 20 份合同签署流程");
        }
        Agent agent = essBootstrap.getAgent(proxyAppId, String.valueOf(companyId), String.valueOf(companyOperatorId));
        // 获取合同模板
        List<TemplateResponse> templates = templateService.getCompanyTemplates(companyId, companyName, companyOperatorId, proxyAppId, templateId);
        TemplateResponse template = templates.get(0);
        // 获取合同模板的签署人列表
        List<TemplateRecipient> recipients = template.getTemplateRecipients();
        if (null == recipients || recipients.isEmpty()) {
            throw new EssKernelException("合同签署模板没有签署人信息");
        }
        // 签署人列表中的企业方
        TemplateRecipient companyRecipient = recipients.stream()
                .filter(recipient -> "ENTERPRISE".equals(recipient.getRecipientType()))
                .findAny()
                .orElse(null);
        FlowApproverInfo companyFlowApprover = null;
        if (null != companyRecipient) {
            // 为企业方创建流程签署人信息
            companyFlowApprover = new FlowApproverInfo();
            companyFlowApprover.setApproverType("ORGANIZATION");
            companyFlowApprover.setOrganizationName(companyName);
            companyFlowApprover.setOrganizationOpenId(String.valueOf(companyId));
            companyFlowApprover.setOpenId(String.valueOf(companyOperatorId));
            companyFlowApprover.setRecipientId(companyRecipient.getRecipientId());
        }

        // 签署人列表中的个人方
        TemplateRecipient personalRecipient = recipients.stream()
                .filter(recipient -> "INDIVIDUAL".equals(recipient.getRecipientType()))
                .findAny()
                .orElse(null);

        List<FlowInfo> flowInfoList = new ArrayList<>();
        // 为每个乙方创建一份合同
        for (SignApprover signApprover : signApproverList) {
            if (EssUtils.isStringEmpty(signApprover.getName())) {
                throw new EssKernelException("存在未指定名称的乙方");
            }
            if (EssUtils.isStringEmpty(signApprover.getMobile()) && EssUtils.isStringEmpty(signApprover.getIdCardNumber())) {
                throw new EssKernelException("乙方" + signApprover.getName() + "未指定手机号或身份证号");
            }
            // 为个人方创建流程签署人信息
            FlowApproverInfo personalFlowApprover = new FlowApproverInfo();
            personalFlowApprover.setApproverType("PERSON");
            personalFlowApprover.setName(signApprover.getName());
            if (EssUtils.isStringNotEmpty(signApprover.getMobile())) {
                personalFlowApprover.setMobile(signApprover.getMobile());
            } else if (EssUtils.isStringNotEmpty(signApprover.getIdCardNumber())) {
                personalFlowApprover.setIdCardType("ID_CARD");
                personalFlowApprover.setIdCardNumber(signApprover.getIdCardNumber());
            }
            personalFlowApprover.setRecipientId(personalRecipient.getRecipientId());

            // 组合企业方（甲方）和个人方（乙方），一定是先企业后个人，之后的逻辑处理需要依赖此顺序
            List<FlowApproverInfo> approverInfoList = new ArrayList<>();
            if (null != companyFlowApprover) {
                approverInfoList.add(companyFlowApprover);
            }
            approverInfoList.add(personalFlowApprover);
            // 构建签署信息
            FlowInfo flowInfo = buildFlowInfo(contractType, signApprover.getContractName(), deadline, templateId, signApprover, approverInfoList);
            if (EssUtils.isStringNotEmpty(signApprover.getExtraData())) {
                // 设置回调时需要携带的业务信息
                flowInfo.setCustomerData(signApprover.getExtraData());
            }
            flowInfoList.add(flowInfo);
        }
        return doSign(agent, flowInfoList);
    }

    /**
     * 发起合同签署
     *
     * @param agent        {@link Agent} 电子签应用信息
     * @param flowInfoList {@link FlowInfo} 合同签署流程信息
     * @return {@link SignResponse} 合同签署发起结果
     */
    private List<SignResponse> doSign(Agent agent, List<FlowInfo> flowInfoList) {
        List<SignResponse> signResponseList = new ArrayList<>();
        EssClient client = essBootstrap.getClient();
        // 单独发起每份合同，不采用批量发起
        for (FlowInfo flowInfo : flowInfoList) {
            CreateFlowsByTemplatesRequest request = new CreateFlowsByTemplatesRequest();
            request.setAgent(agent);
            request.setFlowInfos(new FlowInfo[]{flowInfo});
            SignResponse signResponse = new SignResponse();
            try {
                // 发起单份合同签署
                CreateFlowsByTemplatesResponse response = client.CreateFlowsByTemplates(request);
                log.info("[腾讯电子签请求追踪]发起合同签署。请求 ID：{}", response.getRequestId());
                // 判断合同发起是否成功
                String[] signErrorMessages = response.getErrorMessages();
                if (EssUtils.isArrayNotContainsEmptyString(signErrorMessages)) {
                    log.warn("发起合同签署异常。请求 ID：{}, 合同名称：{}，模板 ID：{}", response.getRequestId(), flowInfo.getFlowName(), flowInfo.getTemplateId());
                    signResponse.setSuccess(false);
                    signResponse.setErrorMessage(signErrorMessages[0]);
                    signResponse.setContractId(flowInfo.getCustomerData());
                    continue;
                }
                // 将业务分配的合同 ID 回写
                signResponse.setContractId(flowInfo.getCustomerData());
                // 获取签署流程 ID
                String[] flowIds = response.getFlowIds();
                // 使用签署流程 ID 获取跳转小程序的签署链接地址及签署流程详情
                CreateSignUrlsResponse signUrlsResponse = createSignUrls(agent, client, flowIds);
                // 读取创建合同签署链接请求的错误信息，如果存在错误信息表示创建失败，流程终止
                if (!EssUtils.isArrayOnlyContainsEmptyString(signUrlsResponse.getErrorMessages())) {
                    continue;
                }
                // 读取所有签署方的流程详情
                SignUrlInfo[] signUrlInfos = signUrlsResponse.getSignUrlInfos();
                for (SignUrlInfo signUrlInfo : signUrlInfos) {
                    // 封装签署人流程详情
                    ApproverResponse approverResponse = mapSignUrlInfoToApproverResponse(signUrlInfo);
                    if (signUrlInfo.getApproverType().equals(ApproveTypeEnum.ORGANIZATION.getType())) {
                        signResponse.setFirstParty(approverResponse);
                    } else if (signUrlInfo.getApproverType().equals(ApproveTypeEnum.PERSON.getType())) {
                        signResponse.setSecondParty(approverResponse);
                    }
                    signResponse.setFlowId(approverResponse.getFlowId());
                }
                signResponse.setSuccess(true);
            } catch (TencentCloudSDKException e) {
                signResponse.setSuccess(false);
                signResponse.setErrorMessage(e.getMessage());
                log.error("发起合同签署异常。请求 ID：{}, 合同名称：{}，模板 ID：{}", e.getRequestId(), flowInfo.getFlowName(), flowInfo.getTemplateId(), e);
            } finally {
                signResponseList.add(signResponse);
            }
        }
        return signResponseList;
    }

    /**
     * 获取指定合同的签署链接
     *
     * @param agent   {@link Agent} 配置信息
     * @param client  {@link EssClient} 腾讯电子签接口请求客户端
     * @param flowIds 要获取签署链接的腾讯电子签合同 ID
     * @return {@link CreateSignUrlsResponse} 签署链接信息
     * @throws TencentCloudSDKException 网络异常
     */
    private CreateSignUrlsResponse createSignUrls(Agent agent, EssClient client, String[] flowIds) throws TencentCloudSDKException {
        CreateSignUrlsRequest signUrlsRequest = new CreateSignUrlsRequest();
        signUrlsRequest.setAgent(agent);
        signUrlsRequest.setFlowIds(flowIds);
        return client.CreateSignUrls(signUrlsRequest);
    }

    /**
     * 构建合同签署流程信息
     *
     * @param contractType     业务系统自定义的合同类型
     * @param contractName     合同名称
     * @param deadline         合同签署截止日期
     * @param templateId       合同模板 ID
     * @param signApprover     合同签署人（乙方）
     * @param approverInfoList {@link FlowApproverInfo} 合同签署人信息
     * @return {@link FlowInfo} 合同签署流程信息
     */
    private FlowInfo buildFlowInfo(String contractType, String contractName, Long deadline, String templateId, SignApprover signApprover, List<FlowApproverInfo> approverInfoList) {
        FlowInfo flowInfo = new FlowInfo();
        flowInfo.setTemplateId(templateId);
        flowInfo.setFlowName(contractName);
        // 如果没有指定截止日期，则默认为 7 天
        if (null == deadline || deadline == 0) {
            deadline = (System.currentTimeMillis() / 1000) + (30 * 24 * 60 * 60L);
        }
        flowInfo.setDeadline(deadline);

        flowInfo.setFlowApprovers(approverInfoList.toArray(new FlowApproverInfo[]{}));
        flowInfo.setFlowType(EssUtils.isStringEmpty(contractType) ? "合同" : contractType);
        // 设置模板控件的值
        Map<String, String> componentValues = signApprover.getComponentValues();
        if (null != componentValues && !componentValues.isEmpty()) {
            List<FormField> flowFormFields = new ArrayList<>();
            componentValues.forEach((componentName, componentValue) -> {
                FormField formField = new FormField();
                formField.setComponentName(componentName);
                formField.setComponentValue(componentValue);
                flowFormFields.add(formField);
            });
            flowInfo.setFormFields(flowFormFields.toArray(new FormField[]{}));
        }
        return flowInfo;
    }

    /**
     * 将 {@link SignUrlInfo} 映射为 {@link ApproverResponse}
     *
     * @param signUrlInfo {@link SignUrlInfo} 数据源
     * @return {@link ApproverResponse} 签署详情
     */
    private ApproverResponse mapSignUrlInfoToApproverResponse(SignUrlInfo signUrlInfo) {
        ApproverResponse approverResponse = new ApproverResponse();
        approverResponse.setFlowId(signUrlInfo.getFlowId());
        approverResponse.setName(signUrlInfo.getName());
        approverResponse.setMobile(signUrlInfo.getMobile());
        approverResponse.setSignId(signUrlInfo.getSignId());
        approverResponse.setSignOrder(signUrlInfo.getSignOrder());
        approverResponse.setDeadline(signUrlInfo.getDeadline());
        approverResponse.setApproveType(ApproveTypeEnum.valueOf(signUrlInfo.getApproverType().toUpperCase()));
        approverResponse.setSignURL(signUrlInfo.getSignUrl());
        return approverResponse;
    }
}
