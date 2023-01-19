package com.ganpengyu.ess.studio.ess.model.enums;

/**
 * 合同签署人状态枚举
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
public enum ApproverStatusEnum {

    PENDING("PENDING", "待签署"),
    ACCEPT("ACCEPT", "已签署"),
    REJECT("REJECT", "拒绝"),
    DEADLINE("DEADLINE", "过期没人处理"),
    CANCEL("CANCEL", "流程已撤回"),
    STOP("STOP", "流程已终止"),
    WAITPICKUP("WAITPICKUP", "待领取"),
    FILLPENDING("FILLPENDING", "待填写"),
    FILLACCEPT("FILLACCEPT", "填写完成"),
    FILLREJECT("FILLREJECT", "拒绝填写"),
    EXCEPTION("EXCEPTION", "异常"),
    ;
    private final String type;
    private final String description;

    ApproverStatusEnum(String type, String description) {
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
