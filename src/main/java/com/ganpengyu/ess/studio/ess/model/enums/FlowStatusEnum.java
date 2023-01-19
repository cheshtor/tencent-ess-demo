package com.ganpengyu.ess.studio.ess.model.enums;

/**
 * 合同签署状态枚举
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
public enum FlowStatusEnum {

    INIT("INIT", "合同创建"),
    PART("PART", "合同签署中"),
    ALL("ALL", "合同签署完成"),
    REJECT("REJECT", "合同拒签"),
    CANCEL("CANCEL", "合同撤回"),
    WILLEXPIRE("WILLEXPIRE", "合同即将过期"),
    DEADLINE("DEADLINE", "合同流签(合同过期)"),
    EXCEPTION("EXCEPTION", "合同异常"),
    RELIEVED("RELIEVED", "解除协议（已解除）"),
    ;
    private final String type;
    private final String description;

    FlowStatusEnum(String type, String description) {
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
