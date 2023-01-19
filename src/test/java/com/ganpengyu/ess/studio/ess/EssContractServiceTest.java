package com.ganpengyu.ess.studio.ess;

import com.alibaba.fastjson.JSON;
import com.ganpengyu.ess.studio.EssStudioApplication;
import com.ganpengyu.ess.studio.basic.PV;
import com.ganpengyu.ess.studio.ess.model.contract.ContractDownloadResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 电子签合同服务
 *
 * @author Pengyu Gan
 * CreateDate 2022/11/29
 */
@SpringBootTest(classes = EssStudioApplication.class)
@RunWith(SpringRunner.class)
public class EssContractServiceTest {

    @Resource
    private EssContractService essContractService;

    @Test
    public void testDownloadContracts() {
        List<String> flowIds = new ArrayList<>();
        flowIds.add("xxx");
        flowIds.add("xxx");
        List<ContractDownloadResponse> responses
                = essContractService.downloadContracts(PV.companyId, PV.operatorId, PV.proxyAppId, flowIds);
        System.out.println(JSON.toJSON(responses));

    }

}
