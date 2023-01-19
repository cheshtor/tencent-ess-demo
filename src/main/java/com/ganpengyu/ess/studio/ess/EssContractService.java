package com.ganpengyu.ess.studio.ess;


import com.ganpengyu.ess.studio.ess.model.contract.ContractDownloadResponse;

import java.util.List;

/**
 * 电子签合同服务
 *
 * @author Pengyu Gan
 * CreateDate 2022/11/29
 */
public interface EssContractService {

    /**
     * 获取指定签署流程对应的合同文本下载信息。一个签署流程可能对应多份
     * 合同，所以本接口会返回下载信息的列表。
     *
     * @param companyId         业务系统分配的企业 ID
     * @param companyOperatorId 业务系统分配的企业经办人 ID
     * @param proxyAppId        子客在腾讯电子签的 ID
     * @param flowIds           签署流程 ID 列表
     * @return {@link ContractDownloadResponse} 合同文本下载信息
     */
    List<ContractDownloadResponse> downloadContracts(Long companyId, Long companyOperatorId, String proxyAppId, List<String> flowIds);

}
