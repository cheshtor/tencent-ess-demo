package com.ganpengyu.ess.studio.ess.vendor.tencent.impl;

import com.ganpengyu.ess.studio.ess.EssCertificateService;
import com.ganpengyu.ess.studio.ess.common.EssKernelException;
import com.ganpengyu.ess.studio.ess.model.certificate.ConsoleURLResponse;
import com.ganpengyu.ess.studio.ess.model.enums.ConsoleURLTypeEnum;
import com.ganpengyu.ess.studio.ess.vendor.tencent.TencentEssBootstrap;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.CreateConsoleLoginUrlRequest;
import com.tencentcloudapi.essbasic.v20210526.models.CreateConsoleLoginUrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 电子签认证服务实现
 *
 * @author Pengyu Gan
 * CreateDate 2022/11/29
 */
@Slf4j
@Service
public class EssCertificateServiceImpl implements EssCertificateService {

    @Resource
    private TencentEssBootstrap essBootstrap;

    @Override
    public ConsoleURLResponse getConsoleURL(Long companyId, String companyName, Long companyOperatorId, ConsoleURLTypeEnum consoleURLType, String moduleId) {
        Agent agent = essBootstrap.getAgent("", String.valueOf(companyId), String.valueOf(companyOperatorId));
        CreateConsoleLoginUrlRequest request = new CreateConsoleLoginUrlRequest();
        request.setAgent(agent);
        request.setProxyOrganizationName(companyName);
        request.setModule(consoleURLType.getType());
        request.setModuleId(moduleId);
        request.setMenuStatus("DISABLE");
        EssbasicClient client = essBootstrap.getClient();
        try {
            CreateConsoleLoginUrlResponse response = client.CreateConsoleLoginUrl(request);
            log.info("[腾讯电子签请求追踪]获取控制台链接。请求 ID：{}", response.getRequestId());
            ConsoleURLResponse consoleUrlResponse = new ConsoleURLResponse();
            consoleUrlResponse.setConsoleUrl(response.getConsoleUrl());
            consoleUrlResponse.setActivated(response.getIsActivated());
            consoleUrlResponse.setProxyOperatorIsVerified(response.getProxyOperatorIsVerified());
            consoleUrlResponse.setRequestId(response.getRequestId());
            return consoleUrlResponse;
        } catch (TencentCloudSDKException e) {
            log.error("获取腾讯电子签控制台链接地址异常。请求 ID：{}, companyId:{}，companyName:{}，companyOperatorId:{}, consoleURLType:{}, moduleId:{}",
                    e.getRequestId(), companyId, companyName, companyOperatorId, consoleURLType.getType(), moduleId, e);
            throw new EssKernelException("获取腾讯电子签控制台链接地址异常。" + e.getRequestId());
        }
    }
}
