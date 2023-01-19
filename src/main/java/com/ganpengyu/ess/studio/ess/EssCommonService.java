package com.ganpengyu.ess.studio.ess;

import com.ganpengyu.ess.studio.ess.model.common.FileDescription;
import com.ganpengyu.ess.studio.ess.model.common.FileResponse;
import com.ganpengyu.ess.studio.ess.model.enums.FileBusinessTypeEnum;

import java.util.List;

/**
 * 电子签通用服务
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
public interface EssCommonService {

    /**
     * 文件上传。在签署合同时，如果合同模板里存在需要上传文件的控件，则需要先调用本接口将文件
     * 上传到腾讯云电子签，并使用返回的文件 ID 来填充控件。
     *
     * @param proxyAppId   子客在腾讯电子签的 ID
     * @param businessType {@link FileBusinessTypeEnum} 文件业务类型
     * @param files        {@link FileDescription} 文件信息
     * @return {@link FileResponse} 文件上传结果
     */
    List<FileResponse> uploadFiles(String proxyAppId, FileBusinessTypeEnum businessType, List<FileDescription> files);

}
