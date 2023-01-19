package com.ganpengyu.ess.studio.ess.model.sign;

import com.ganpengyu.ess.studio.ess.model.enums.ApproveTypeEnum;
import lombok.Data;

/**
 * 发起签署后的合同签署人相关信息
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/3
 */
@Data
public class ApproverResponse {

    /**
     * 流程 ID
     */
    private String flowId;

    /**
     * 签署方名称，如果是企业方则为经办人名称
     */
    private String name;

    /**
     * 签署人手机号
     */
    private String mobile;

    /**
     * 签署人编号
     */
    private String signId;

    /**
     * 签署顺序，数字越小，优先级越高
     */
    private Long SignOrder;

    /**
     * 合同签署过期时间（秒级）
     */
    private Long deadline;

    /**
     * 签署人类型
     *
     * @see ApproveTypeEnum
     */
    private ApproveTypeEnum approveType;

    /**
     * 跳转小程序签署的链接地址
     */
    private String signURL;

}
