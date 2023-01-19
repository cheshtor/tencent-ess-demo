package com.ganpengyu.ess.studio.ess;

import com.ganpengyu.ess.studio.ess.model.enums.FlowStatusEnum;
import com.ganpengyu.ess.studio.ess.model.enums.SignURLTypeEnum;
import com.ganpengyu.ess.studio.ess.model.sign.SignApprover;
import com.ganpengyu.ess.studio.ess.model.sign.SignCancelResponse;
import com.ganpengyu.ess.studio.ess.model.sign.SignReleaseResponse;
import com.ganpengyu.ess.studio.ess.model.sign.SignResponse;

import java.util.List;

/**
 * 电子签签署服务
 *
 * @author Pengyu Gan
 * CreateDate 2022/11/29
 */
public interface EssSignService {

    /**
     * 发起合同签署。传入方法的每一个乙方都代表一份独立的合同，每次批量发起合同签署的上线为 20 份，超过
     * 限制的调用会抛出异常。
     *
     * @param companyId         业务系统分配的企业 ID
     * @param companyOperatorId 业务系统分配的企业经办人 ID
     * @param proxyAppId        子客在腾讯电子签的 ID
     * @param companyName       业务系统分配的企业名称
     * @param contractType      业务系统自定义的合同类型
     * @param deadline          合同签署的截止时间戳(秒级)，如果未指定，则默认为 30 天
     * @param templateId        合同模板 ID
     * @param signApproverList  合同签署人（乙方）列表，一个乙方代表一份合同
     * @return {@link SignResponse} 签署流程涉及签署人信息
     */
    List<SignResponse> sign(Long companyId, Long companyOperatorId, String proxyAppId, String companyName, String contractType, Long deadline, String templateId, List<SignApprover> signApproverList);

    /**
     * 批量撤销合同。只能撤回以下状态的合同：
     * 1. 合同创建
     * 2. 合同签署中
     * 3. 合同即将过期
     * 4. 合同异常
     * 不能撤回的状态有：
     * 1. 合同签署完成
     * 2. 合同拒签
     * 3. 合同过期
     * 4. 合同撤回
     *
     * @param companyId         业务系统分配的企业 ID
     * @param companyOperatorId 业务系统分配的企业经办人 ID
     * @param proxyAppId        子客在腾讯电子签的 ID
     * @param reason            撤回理由
     * @param flowIds           合同 ID 列表
     * @return {@link SignCancelResponse} 合同撤销结果
     * @see FlowStatusEnum
     */
    List<SignCancelResponse> cancel(Long companyId, Long companyOperatorId, String proxyAppId, String reason, List<String> flowIds);

    /**
     * 发起解除协议。只有处于签署完成状态的合同才能够发起解除。
     *
     * @param companyId         业务系统分配的企业 ID
     * @param companyOperatorId 业务系统分配的企业经办人 ID
     * @param proxyAppId        子客在腾讯电子签的 ID
     * @param reason            撤回理由
     * @param flowIds           要发起解除协议的合同 ID 列表
     * @return {@link SignReleaseResponse} 解除协议发起结果
     */
    List<SignReleaseResponse> release(Long companyId, Long companyOperatorId, String proxyAppId, String reason, List<String> flowIds);

    /**
     * 查询合同详情。本接口也可用于查询合同签署链接。
     *
     * @param companyId         业务系统分配的企业 ID
     * @param companyOperatorId 业务系统分配的企业经办人 ID
     * @param proxyAppId        子客在腾讯电子签的 ID
     * @param signURLTypeEnum   {@link SignURLTypeEnum} 腾讯电子签签署链接类型枚举。如果不传，默认为 {@link SignURLTypeEnum#WEIXINAPP}
     * @param jumpUrl           如果生成的是 {@link SignURLTypeEnum#CHANNEL} 类型的签署链接，在签署完成后系统默认跳转的页面地址
     * @param flowIds           要生成跳转链接的合同 ID 列表
     * @return {@link SignResponse} 包含指定类型签署链接的签署详情
     */
    List<SignResponse> findFlowDetails(Long companyId, Long companyOperatorId, String proxyAppId, SignURLTypeEnum signURLTypeEnum, String jumpUrl, List<String> flowIds);
}
