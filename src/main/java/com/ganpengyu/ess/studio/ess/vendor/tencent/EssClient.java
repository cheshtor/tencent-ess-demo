package com.ganpengyu.ess.studio.ess.vendor.tencent;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.JsonResponseModel;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeOrganizationSealsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeOrganizationSealsResponse;

import java.lang.reflect.Type;

/**
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
public class EssClient extends EssbasicClient {

    public EssClient(Credential credential, String region, ClientProfile profile) {
        super(credential, region, profile);
    }

    public ChannelDescribeOrganizationSealsResponse channelDescribeOrganizationSeals(ChannelDescribeOrganizationSealsRequest request) throws TencentCloudSDKException {
        JsonResponseModel<ChannelDescribeOrganizationSealsResponse> response = null;
        String responseStr = "";
        try {
            Type type = new TypeToken<JsonResponseModel<ChannelDescribeOrganizationSealsResponse>>() {
            }.getType();
            responseStr = this.internalRequest(request, "ChannelDescribeOrganizationSeals");
            response = gson.fromJson(responseStr, type);
        } catch (JsonSyntaxException e) {
            throw new TencentCloudSDKException("response message: " + responseStr + ".\n Error message: " + e.getMessage());
        }
        return response.response;
    }

}
