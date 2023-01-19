package com.ganpengyu.ess.studio.ess.model.seal;

import com.ganpengyu.ess.studio.ess.model.enums.SealStatusEnum;
import com.ganpengyu.ess.studio.ess.model.enums.SealTypeEnum;
import lombok.Data;

/**
 * 印章
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@Data
public class SealResponse {

    /**
     * 印章 ID
     */
    private String sealId;

    /**
     * 印章名称
     */
    private String sealName;

    /**
     * 印章类型
     *
     * @see SealTypeEnum
     */
    private SealTypeEnum sealType;

    /**
     * 印章状态
     *
     * @see SealStatusEnum
     */
    private SealStatusEnum sealStatus;

    /**
     * 印章图片链接地址，5 分钟内有效
     */
    private String url;

    /**
     * 印章授权人数量
     */
    private Integer authorizedUserCount;

}
