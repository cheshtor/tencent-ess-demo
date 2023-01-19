package com.ganpengyu.ess.studio.ess.model.enums;

/**
 * 控制台链接类型枚举。除了 {@link ConsoleURLTypeEnum#ACCOUNT} 外，其他枚举值在使用时
 * 如果指定了一个 ID,就表示进入这个 ID 所代表的资源的详情页面，否则进入列表页面。
 * <p>
 * https://cloud.tencent.com/document/product/1420/61524#2.-.E8.BE.93.E5.85.A5.E5.8F.82.E6.95.B0
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
public enum ConsoleURLTypeEnum {

    /**
     * 用于获取合同列表页面的链接
     */
    DOCUMENT("DOCUMENT", "文件/合同管理"),

    /**
     * 用于获取模板列表页面的链接
     */
    TEMPLATE("TEMPLATE", "模板管理"),

    /**
     * 用于获取印章列表页面的链接
     */
    SEAL("SEAL", "印章管理"),

    /**
     * 用于获取组织架构列表页面的链接
     */
    OPERATOR("OPERATOR", "组织架构/人员"),

    /**
     * 用于获取进入子客腾讯电子签控制台的链接，或者用于判断子客企业和管理员是否已激活
     */
    ACCOUNT("", "账号信息"),
    ;

    private final String type;
    private final String description;

    ConsoleURLTypeEnum(String type, String description) {
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
