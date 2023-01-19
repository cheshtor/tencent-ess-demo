package com.ganpengyu.ess.studio.ess.model.template;

import lombok.Data;

/**
 * 模板控件
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@Data
public class TemplateComponent {

    /**
     * 控件 ID
     */
    private String componentId;

    /**
     * 控件名称
     */
    private String componentName;

    /**
     * 控件描述
     */
    private String componentDescription;

    /**
     * 控件类型
     */
    private String componentType;

    /**
     * 是否必填
     */
    private boolean required;

    /**
     * 填写此控件的签署人 ID
     */
    private String recipientId;

}
