package com.ganpengyu.ess.studio.ess;

import com.alibaba.fastjson.JSON;
import com.ganpengyu.ess.studio.EssStudioApplication;
import com.ganpengyu.ess.studio.basic.PV;
import com.ganpengyu.ess.studio.ess.model.seal.SealResponse;
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
public class EssSealServiceTest {

    @Resource
    private EssSealService sealService;

    @Test
    public void testGetSeals() {
        List<SealResponse> seals = sealService.getSeals(PV.companyId, PV.operatorId, PV.proxyAppId);
        System.out.println(JSON.toJSON(seals));
    }

}
