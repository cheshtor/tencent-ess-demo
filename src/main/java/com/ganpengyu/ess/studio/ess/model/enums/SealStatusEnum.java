package com.ganpengyu.ess.studio.ess.model.enums;

/**
 * 印章状态枚举
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
public enum SealStatusEnum {

    CHECKING("CHECKING", "审核中"),
    SUCCESS("SUCCESS", "已启用"),
    FAIL("FAIL", "审核拒绝"),
    CHECKING_SADM("CHECKING-SADM", "待超管审核"),
    DISABLE("DISABLE", "已停用"),
    STOPPED("STOPPED", "已终止"),
    ;

    private final String type;
    private final String description;

    SealStatusEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
