package com.ganpengyu.ess.studio.ess;

import com.alibaba.fastjson.JSON;
import com.ganpengyu.ess.studio.EssStudioApplication;
import com.ganpengyu.ess.studio.basic.PV;
import com.ganpengyu.ess.studio.ess.model.enums.SignURLTypeEnum;
import com.ganpengyu.ess.studio.ess.model.sign.SignApprover;
import com.ganpengyu.ess.studio.ess.model.sign.SignCancelResponse;
import com.ganpengyu.ess.studio.ess.model.sign.SignReleaseResponse;
import com.ganpengyu.ess.studio.ess.model.sign.SignResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pengyu Gan
 * CreateDate 2022/12/2
 */
@SpringBootTest(classes = EssStudioApplication.class)
@RunWith(SpringRunner.class)
public class EssSignServiceTest {

    @Resource
    private EssSignService essSignService;

    @Test
    public void testSign() {
        SignApprover signApprover = new SignApprover();
        signApprover.setName("张三");
        signApprover.setMobile("13800000000");
        Map<String, String> componentValues = new HashMap<>();
        componentValues.put("contractno", "HT-LABOR-20221219-0001");
        componentValues.put("company", PV.companyName);
        componentValues.put("legal", "龙傲天");
        componentValues.put("delegate", "刘波儿");
        componentValues.put("delegatephone", "13800000000");
        componentValues.put("companyaddr", "天安门");
        componentValues.put("worker", "张三");
        componentValues.put("workerid", "5101841993000000000");
        componentValues.put("workergender", "男");
        componentValues.put("workeraddr", "星辰大海");
        componentValues.put("workerphone", "13800000000");
        componentValues.put("workercreditno", "6217001178652345");
        componentValues.put("beginyear", "2022");
        componentValues.put("beginmonth", "12");
        componentValues.put("beginday", "25");
        componentValues.put("project", "西沙群岛海水淡化工程");
        componentValues.put("projectcity", "西沙市");
        componentValues.put("projectdistrict", "永暑岛");
        componentValues.put("jobtype", "摸鱼工");
        componentValues.put("pieceprice", "80");
        componentValues.put("additions", "无");
        componentValues.put("workeridcardpos", "xxx");
        componentValues.put("workeridcardneg", "xxx");
        signApprover.setComponentValues(componentValues);

        List<SignApprover> signApproverList = Arrays.asList(signApprover);

        List<SignResponse> signResponses = essSignService.sign(PV.companyId, PV.operatorId, PV.proxyAppId, PV.companyName, "劳务合同", null, "xxx", signApproverList);
        System.out.println(JSON.toJSON(signResponses));
    }

    @Test
    public void testSign2() {
        SignApprover signApprover = new SignApprover();
        signApprover.setName("张三");
        signApprover.setMobile("13800000000");
        Map<String, String> componentValues = new HashMap<>();
        componentValues.put("company", PV.companyName);
        componentValues.put("worker", "张三");
        componentValues.put("workerid", "510184000000000000");
        componentValues.put("project", "西沙群岛海水淡化工程");
        componentValues.put("jobtype", "摸鱼");
        signApprover.setComponentValues(componentValues);
        List<SignApprover> signApproverList = Arrays.asList(signApprover);
//        List<SignResponse> signResponses = essSignService.sign(PV.companyId, PV.operatorId, PV.proxyAppId, PV.companyName, "进场确认书", "西沙群岛海水淡化工程进场确认书", null, "yDRT5UUgyg386z4kUyPLr8RBGCS5jfCp", signApproverList);
//        System.out.println(JSON.toJSON(signResponses));

    }

    @Test
    public void testRelease() {
        List<String> flowIds = new ArrayList<>();
        flowIds.add("xxx");
        List<SignReleaseResponse> responses = essSignService.release(PV.companyId, PV.operatorId, PV.proxyAppId, "我想解除", flowIds);
        System.out.println(JSON.toJSON(responses));
    }

    @Test
    public void testCancel() {
        List<String> flowIds = new ArrayList<>();
        flowIds.add("xxx");
        List<SignCancelResponse> response = essSignService.cancel(PV.companyId, PV.operatorId, PV.proxyAppId, "我想撤回", flowIds);
        System.out.println(JSON.toJSON(response));
    }

    @Test
    public void testGenerateSignURL() {
        List<String> flowIds = new ArrayList<>();
        flowIds.add("xxx");
        flowIds.add("xxx");
        List<SignResponse> response = essSignService.findFlowDetails(PV.companyId, PV.operatorId, PV.proxyAppId, SignURLTypeEnum.APP, null, flowIds);
        System.out.println(JSON.toJSON(response));
    }

}
