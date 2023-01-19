package com.ganpengyu.ess.studio.ess.vendor.tencent;

import com.ganpengyu.ess.studio.ess.vendor.tencent.compatibly.SaasEssClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.UserInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 腾讯电子签客户端初始化
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/1
 */
@Component
public class TencentEssBootstrap {

    @Resource
    private TencentConfigProperties configProperties;

    private EssClient essClient;

    private EssClient essFileClient;

    private SaasEssClient saasEssClient;

    public EssClient getClient() {
        if (null == essClient) {
            Credential cred = new Credential(configProperties.getSecretId(), configProperties.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(configProperties.getEndPoint());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            essClient = new EssClient(cred, "", clientProfile);
        }
        return essClient;
    }

    public EssClient getFileClient() {
        if (null == essFileClient) {
            Credential cred = new Credential(configProperties.getSecretId(), configProperties.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(configProperties.getFileServiceEndPoint());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            essFileClient = new EssClient(cred, "", clientProfile);
        }
        return essFileClient;
    }

    public SaasEssClient getSaasClient() {
        if (null == saasEssClient) {
            Credential cred = new Credential(configProperties.getSecretId(), configProperties.getSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(configProperties.getSaasEndPoint());
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            saasEssClient = new SaasEssClient(cred, "", clientProfile);
        }
        return saasEssClient;
    }

    public Agent getAgent(String proxyAppId, String proxyOrganizationOpenId, String proxyOperatorOpenId) {
        Agent agent = new Agent();
        agent.setAppId(configProperties.getAppId());
        agent.setProxyAppId(proxyAppId);
        agent.setProxyOrganizationOpenId(proxyOrganizationOpenId);
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(proxyOperatorOpenId);
        agent.setProxyOperator(userInfo);
        return agent;
    }

}
