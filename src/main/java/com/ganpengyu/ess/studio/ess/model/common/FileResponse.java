package com.ganpengyu.ess.studio.ess.model.common;

import lombok.Data;

/**
 * 电子签文件上传响应
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
@Data
public class FileResponse {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件 ID
     */
    private String fileId;

    /**
     * 文件地址
     */
    private String fileURL;

}
