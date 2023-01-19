package com.ganpengyu.ess.studio.ess.vendor.tencent.impl;

import com.ganpengyu.ess.studio.ess.EssSealService;
import com.ganpengyu.ess.studio.ess.common.EssKernelException;
import com.ganpengyu.ess.studio.ess.model.enums.SealStatusEnum;
import com.ganpengyu.ess.studio.ess.model.enums.SealTypeEnum;
import com.ganpengyu.ess.studio.ess.model.seal.SealResponse;
import com.ganpengyu.ess.studio.ess.vendor.tencent.EssClient;
import com.ganpengyu.ess.studio.ess.vendor.tencent.TencentEssBootstrap;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeOrganizationSealsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeOrganizationSealsResponse;
import com.tencentcloudapi.essbasic.v20210526.models.OccupiedSeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 电子签印章服务实现
 *
 * @author Pengyu Gan
 * CreateDate 2022/11/29
 */
@Slf4j
@Service
public class EssSealServiceImpl implements EssSealService {

    @Resource
    private TencentEssBootstrap essBootstrap;

    @Override
    public List<SealResponse> getSeals(Long companyId, Long companyOperatorId, String proxyAppId) {
        Agent agent = essBootstrap.getAgent(proxyAppId, String.valueOf(companyId), String.valueOf(companyOperatorId));
        ChannelDescribeOrganizationSealsRequest request = new ChannelDescribeOrganizationSealsRequest();
        request.setAgent(agent);
        request.setLimit(100L);
        EssClient client = essBootstrap.getClient();
        try {
            ChannelDescribeOrganizationSealsResponse response = client.channelDescribeOrganizationSeals(request);
            log.info("[腾讯电子签请求追踪]获取印章。请求 ID：{}", response.getRequestId());
            OccupiedSeal[] occupiedSeals = response.getSeals();
            return Arrays.stream(occupiedSeals).map(seal -> {
                SealResponse sealResponse = new SealResponse();
                sealResponse.setSealId(seal.getSealId());
                sealResponse.setSealName(seal.getSealName());
                sealResponse.setSealType(SealTypeEnum.valueOf(seal.getSealType().toUpperCase()));
                sealResponse.setSealStatus(SealStatusEnum.valueOf(seal.getSealStatus().replace("-", "_").toUpperCase()));
                sealResponse.setUrl(seal.getUrl());
                sealResponse.setAuthorizedUserCount(seal.getAuthorizedUsers().length);
                return sealResponse;
            }).collect(Collectors.toList());
        } catch (TencentCloudSDKException e) {
            log.error("获取子客企业印章失败。请求 ID：{}, companyId: {}, companyOperatorId: {}, proxyAppId: {}",
                    e.getRequestId(), companyId, companyOperatorId, proxyAppId, e);
            throw new EssKernelException("获取子客企业印章失败。" + e.getRequestId());
        }
    }
}
