package com.ganpengyu.ess.studio.ess.vendor.tencent.compatibly;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;

/**
 * 兼容 SAAS 版本的接口调用客户端。由于 SAAS 版的客户端和渠道版的同名但不同包，所以这里包装一次，避免在
 * 同一个类中同时使用两个客户端时会出现全包名引用的现象。
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/8
 */
public class SaasEssClient extends com.tencentcloudapi.ess.v20201111.EssClient {

    public SaasEssClient(Credential credential, String region, ClientProfile profile) {
        super(credential, region, profile);
    }

}
