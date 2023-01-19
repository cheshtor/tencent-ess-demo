package com.ganpengyu.ess.studio.ess.vendor.tencent.impl;

import com.ganpengyu.ess.studio.ess.EssContractService;
import com.ganpengyu.ess.studio.ess.common.EssKernelException;
import com.ganpengyu.ess.studio.ess.common.EssUtils;
import com.ganpengyu.ess.studio.ess.model.contract.ContractDownloadResponse;
import com.ganpengyu.ess.studio.ess.model.contract.ContractFileResponse;
import com.ganpengyu.ess.studio.ess.vendor.tencent.EssClient;
import com.ganpengyu.ess.studio.ess.vendor.tencent.TencentEssBootstrap;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeResourceUrlsByFlowsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeResourceUrlsByFlowsResponse;
import com.tencentcloudapi.essbasic.v20210526.models.FlowResourceUrlInfo;
import com.tencentcloudapi.essbasic.v20210526.models.ResourceUrlInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 电子签合同服务实现
 *
 * @author Pengyu Gan
 * CreateDate 2022/11/29
 */
@Slf4j
@Service
public class EssContractServiceImpl implements EssContractService {

    @Resource
    private TencentEssBootstrap essBootstrap;

    @Override
    public List<ContractDownloadResponse> downloadContracts(Long companyId, Long companyOperatorId, String proxyAppId, List<String> flowIds) {
        if (flowIds.size() > 50) {
            throw new EssKernelException("批量获取合同文件下载链接一次不能超过 50 份合同");
        }
        List<ContractDownloadResponse> responses = new ArrayList<>();
        Agent agent = essBootstrap.getAgent(proxyAppId, String.valueOf(companyId), String.valueOf(companyOperatorId));
        EssClient client = essBootstrap.getClient();
        // 使用签署流程 ID 获取合同文件信息
        DescribeResourceUrlsByFlowsRequest request = new DescribeResourceUrlsByFlowsRequest();
        request.setAgent(agent);
        request.setFlowIds(flowIds.toArray(new String[]{}));
        try {
            DescribeResourceUrlsByFlowsResponse response = client.DescribeResourceUrlsByFlows(request);
            log.info("[腾讯电子签请求追踪]获取合同下载链接。请求 ID：{}", response.getRequestId());
            // 请求错误信息
            String[] errorMessages = response.getErrorMessages();
            boolean batchSuccess = EssUtils.isArrayOnlyContainsEmptyString(errorMessages);
            // 获取每个流程对应的合同文件信息列表
            FlowResourceUrlInfo[] flowResourceUrlInfos = response.getFlowResourceUrlInfos();
            for (int i = 0; i < flowResourceUrlInfos.length; i++) {
                // 签署流程的合同文件信息
                FlowResourceUrlInfo flowUrlInfo = flowResourceUrlInfos[i];
                ContractDownloadResponse downloadResponse = new ContractDownloadResponse();
                // 设置请求成功标志
                if (batchSuccess) {
                    downloadResponse.setSuccess(true);
                } else {
                    downloadResponse.setSuccess(false);
                    downloadResponse.setErrorMessage(errorMessages[i]);
                }
                downloadResponse.setFlowId(flowUrlInfo.getFlowId());
                List<ContractFileResponse> contractFileResponses = new ArrayList<>();
                downloadResponse.setContractFiles(contractFileResponses);
                // 读取每个签署流程对应的合同下载链接列表（因为可能一个签署流程涉及多份合同）
                ResourceUrlInfo[] resourceUrlInfos = flowUrlInfo.getResourceUrlInfos();
                for (ResourceUrlInfo urlInfo : resourceUrlInfos) {
                    ContractFileResponse contractFileResponse = new ContractFileResponse();
                    contractFileResponse.setContractName(urlInfo.getName());
                    contractFileResponse.setFileType(urlInfo.getType());
                    contractFileResponse.setContractURL(urlInfo.getUrl());
                    contractFileResponses.add(contractFileResponse);
                }
                responses.add(downloadResponse);
            }
        } catch (TencentCloudSDKException e) {
            log.error("批量获取合同文件下载链接失败。请求 ID：{}, companyId:{}, companyOperatorId:{}, proxyAppId:{}, flowIds:{}",
                    e.getRequestId(), companyId, companyOperatorId, proxyAppId, flowIds, e);
            throw new EssKernelException("批量获取合同文件下载链接失败。" + e.getRequestId());
        }
        return responses;
    }
}
