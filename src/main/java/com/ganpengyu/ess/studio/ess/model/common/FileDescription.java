package com.ganpengyu.ess.studio.ess.model.common;

import lombok.Data;

/**
 * 电子签附件描述
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
@Data
public class FileDescription {

    /**
     * 文件名
     */
    private String filename;

    /**
     * Base64 编码的文件内容
     */
    private String fileBody;

}
