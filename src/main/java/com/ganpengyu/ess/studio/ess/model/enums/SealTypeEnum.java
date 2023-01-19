package com.ganpengyu.ess.studio.ess.model.enums;

/**
 * 印章类型枚举
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
public enum SealTypeEnum {

    OFFICIAL("OFFICIAL", "公章"),
    CONTRACT("CONTRACT", "合同章"),
    ;

    private final String type;
    private final String description;

    SealTypeEnum(String type, String description) {
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
