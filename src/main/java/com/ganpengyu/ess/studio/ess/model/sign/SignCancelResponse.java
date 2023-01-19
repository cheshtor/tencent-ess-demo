package com.ganpengyu.ess.studio.ess.model.sign;

import lombok.Data;

/**
 * 合同撤销结果
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
@Data
public class SignCancelResponse {

    /**
     * 合同 ID
     */
    private String flowId;

    /**
     * 是否撤销成功
     */
    private boolean success;

    /**
     * 撤销失败原因
     */
    private String errorMessage;

}
