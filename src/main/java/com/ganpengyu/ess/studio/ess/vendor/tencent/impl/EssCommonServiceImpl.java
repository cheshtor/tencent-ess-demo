package com.ganpengyu.ess.studio.ess.vendor.tencent.impl;

import com.ganpengyu.ess.studio.ess.EssCommonService;
import com.ganpengyu.ess.studio.ess.common.EssKernelException;
import com.ganpengyu.ess.studio.ess.model.common.FileDescription;
import com.ganpengyu.ess.studio.ess.model.common.FileResponse;
import com.ganpengyu.ess.studio.ess.model.enums.FileBusinessTypeEnum;
import com.ganpengyu.ess.studio.ess.vendor.tencent.EssClient;
import com.ganpengyu.ess.studio.ess.vendor.tencent.TencentConfigProperties;
import com.ganpengyu.ess.studio.ess.vendor.tencent.TencentEssBootstrap;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.UploadFile;
import com.tencentcloudapi.essbasic.v20210526.models.UploadFilesRequest;
import com.tencentcloudapi.essbasic.v20210526.models.UploadFilesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 电子签通用服务实现
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
@Slf4j
@Service
public class EssCommonServiceImpl implements EssCommonService {

    @Resource
    private TencentEssBootstrap essBootstrap;

    @Resource
    private TencentConfigProperties configProperties;

    @Override
    public List<FileResponse> uploadFiles(String proxyAppId, FileBusinessTypeEnum businessType, List<FileDescription> files) {
        if (CollectionUtils.isEmpty(files)) {
            throw new EssKernelException("没有需要上传的文件");
        }
        if (files.size() > 20) {
            throw new EssKernelException("不能上传超过 20 份文件");
        }
        List<FileResponse> responses = new ArrayList<>();
        Agent agent = essBootstrap.getAgent(proxyAppId, null, null);
        UploadFilesRequest request = new UploadFilesRequest();
        request.setAgent(agent);
        request.setBusinessType(businessType.getType());
        // 入参转换
        List<UploadFile> uploadFiles = files.stream().map(file -> {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setFileName(file.getFilename());
            uploadFile.setFileBody(file.getFileBody());
            return uploadFile;
        }).collect(Collectors.toList());
        request.setFileInfos(uploadFiles.toArray(new UploadFile[]{}));

        EssClient client = essBootstrap.getFileClient();
        try {
            UploadFilesResponse response = client.UploadFiles(request);
            log.info("[腾讯电子签请求追踪]上传图片。请求 ID：{}", response.getRequestId());
            String[] fileIds = response.getFileIds();
            String[] fileUrls = response.getFileUrls();
            for (int i = 0; i < fileIds.length; i++) {
                FileResponse fileResponse = new FileResponse();
                fileResponse.setFilename(files.get(i).getFilename());
                fileResponse.setFileId(fileIds[i]);
                fileResponse.setFileURL(fileUrls[i]);
                responses.add(fileResponse);
            }
            return responses;
        } catch (TencentCloudSDKException e) {
            log.error("上传文件到电子签异常。请求 ID：{}, proxyAppId:{}, files:{}", e.getRequestId(), proxyAppId, files, e);
            throw new EssKernelException("上传文件到电子签异常。" + e.getRequestId());
        }
    }
}
