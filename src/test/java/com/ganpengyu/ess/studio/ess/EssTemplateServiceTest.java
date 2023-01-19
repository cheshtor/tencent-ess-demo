package com.ganpengyu.ess.studio.ess;

import com.alibaba.fastjson.JSON;
import com.ganpengyu.ess.studio.EssStudioApplication;
import com.ganpengyu.ess.studio.basic.PV;
import com.ganpengyu.ess.studio.ess.model.template.TemplateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@SpringBootTest(classes = EssStudioApplication.class)
@RunWith(SpringRunner.class)
public class EssTemplateServiceTest {

    @Resource
    private EssTemplateService templateService;

    @Test
    public void testGetCompanyTemplates() {
        List<TemplateResponse> template = templateService.getCompanyTemplates(PV.companyId, PV.companyName, PV.operatorId, PV.proxyAppId, null);
        System.out.println(JSON.toJSON(template));
    }

    @Test
    public void testGetChannelTemplates() {
        List<TemplateResponse> channelTemplate = templateService.getChannelTemplates();
        System.out.println(JSON.toJSON(channelTemplate));
    }

    @Test
    public void testGetCompanyTemplateIdByChannelTemplateId() {
        String templateId = templateService.getCompanyTemplateIdByChannelTemplateId(PV.companyId, PV.companyName, PV.operatorId, PV.proxyAppId, "xxx");
        System.out.println(templateId);
    }

}
