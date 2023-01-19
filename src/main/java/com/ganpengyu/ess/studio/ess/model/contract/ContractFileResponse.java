package com.ganpengyu.ess.studio.ess.model.contract;

import lombok.Data;

/**
 * 合同文件信息
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/3
 */
@Data
public class ContractFileResponse {

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同文件类型
     */
    private String fileType;

    /**
     * 合同下载链接，有效时间 5 分钟
     */
    private String contractURL;

    /**
     * 合同下载链接有效截止时间
     */
    private long deadline;

    public ContractFileResponse() {
        this.deadline = (System.currentTimeMillis() / 1000) + (5 * 60);
    }

}
