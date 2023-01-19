package com.ganpengyu.ess.studio.ess.model.contract;

import lombok.Data;

import java.util.List;

/**
 * 合同文件下载响应
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/3
 */
@Data
public class ContractDownloadResponse {

    /**
     * 合同文件是否获取成功
     */
    private boolean success;

    /**
     * 合同文件获取失败错误信息
     */
    private String errorMessage;

    /**
     * 签署流程 ID
     */
    private String flowId;

    /**
     * 合同文件列表。一个 flowId 表示一次合同签署，一次合同签署可能有多份合同
     * 文件，所以这里是合同文件信息的列表。
     */
    private List<ContractFileResponse> contractFiles;

}