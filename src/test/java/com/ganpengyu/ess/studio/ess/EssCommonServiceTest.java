package com.ganpengyu.ess.studio.ess;

import com.alibaba.fastjson.JSON;
import com.ganpengyu.ess.studio.EssStudioApplication;
import com.ganpengyu.ess.studio.basic.PV;
import com.ganpengyu.ess.studio.ess.model.common.FileDescription;
import com.ganpengyu.ess.studio.ess.model.common.FileResponse;
import com.ganpengyu.ess.studio.ess.model.enums.FileBusinessTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pengyu Gan
 * CreateDate 2022/12/19
 */
@SpringBootTest(classes = EssStudioApplication.class)
@RunWith(SpringRunner.class)
public class EssCommonServiceTest {

    @Resource
    private EssCommonService essCommonService;

    @Test
    public void testUploadFiles() {
        FileDescription fileDescription = new FileDescription();
        fileDescription.setFilename("身份证正面");
        fileDescription.setFileBody("xxx");

        FileDescription fileDescription2 = new FileDescription();
        fileDescription2.setFilename("身份证反面");
        fileDescription2.setFileBody("xxx");


        List<FileDescription> fileDescriptions = Arrays.asList(fileDescription, fileDescription2);

        List<FileResponse> fileResponses = essCommonService.uploadFiles(PV.proxyAppId, FileBusinessTypeEnum.DOCUMENT, fileDescriptions);
        System.out.println(JSON.toJSON(fileResponses));
    }

}
