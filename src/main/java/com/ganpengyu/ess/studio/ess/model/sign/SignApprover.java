package com.ganpengyu.ess.studio.ess.model.sign;

import lombok.Data;

import java.util.Map;

/**
 * 合同签署人
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@Data
public class SignApprover {

    /**
     * 签署人姓名
     */
    private String name;

    /**
     * 签署人手机号
     */
    private String mobile;

    /**
     * 签署人身份证号
     */
    private String idCardNumber;

    /**
     * 附件信息，在电子签回调时携带
     */
    private String extraData;

    /**
     * 当前合同签署人的合同名称。为了便于阅读和查询，每一个乙方
     * 的合同名称都不一样。格式为：标准合同名称_乙方_甲方
     */
    private String contractName;

    /**
     * 合同模板控件的值
     */
    private Map<String, String> componentValues;

}
