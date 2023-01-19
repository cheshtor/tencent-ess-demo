package com.ganpengyu.ess.studio.ess;

import com.ganpengyu.ess.studio.EssStudioApplication;
import com.ganpengyu.ess.studio.basic.PV;
import com.ganpengyu.ess.studio.ess.model.certificate.ConsoleURLResponse;
import com.ganpengyu.ess.studio.ess.model.enums.ConsoleURLTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@SpringBootTest(classes = EssStudioApplication.class)
@RunWith(SpringRunner.class)
public class EssCertificateServiceTest {

    @Resource
    private EssCertificateService certificateService;

    @Test
    public void testGetConsoleURL() {
        String moduleId = null;
        ConsoleURLResponse consoleURL = certificateService.getConsoleURL(PV.companyId, PV.companyName, PV.operatorId, ConsoleURLTypeEnum.ACCOUNT, moduleId);
        System.out.println(consoleURL.getConsoleUrl());
    }

}
