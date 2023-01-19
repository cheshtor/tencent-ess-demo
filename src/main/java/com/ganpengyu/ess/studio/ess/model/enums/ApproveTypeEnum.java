package com.ganpengyu.ess.studio.ess.model.enums;

/**
 * 合同签署人类型枚举
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
public enum ApproveTypeEnum {

    ORGANIZATION("ORGANIZATION", "企业经办人"),
    PERSON("PERSON", "自然人"),
    ;

    private final String type;
    private final String description;

    ApproveTypeEnum(String type, String description) {
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
