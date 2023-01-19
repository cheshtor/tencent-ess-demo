package com.ganpengyu.ess.studio.ess.model.enums;

import java.util.Arrays;

/**
 * 电子签附件业务类型
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
public enum FileBusinessTypeEnum {

    TEMPLATE("TEMPLATE", "模板； 文件类型：.pdf/.doc/.docx/.html"),
    DOCUMENT("DOCUMENT", "签署过程及签署后的合同文档/图片控件 文件类型：.pdf/.doc/.docx/.jpg/.png/.xls.xlsx/.html"),
    ;

    private final String type;
    private final String description;

    FileBusinessTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static FileBusinessTypeEnum fromValue(String type) {
        return Arrays.stream(FileBusinessTypeEnum.values())
                .filter(x -> x.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
