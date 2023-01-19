package com.ganpengyu.ess.studio.ess.model.sign;

import lombok.Data;

/**
 * 合同签署链接详情
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/6
 */
@Data
public class SignURLResponse extends ApproverResponse {

    /**
     * 链接是否获取成功
     */
    private boolean success;

    /**
     * 链接获取失败错误信息
     */
    private String errorMessage;

}
