package com.ganpengyu.ess.studio.ess;

import com.ganpengyu.ess.studio.ess.model.seal.SealResponse;

import java.util.List;

/**
 * 电子签印章服务
 *
 * @author Pengyu Gan
 * CreateDate 2022/11/29
 */
public interface EssSealService {

    /**
     * 获取子客印章列表（默认查询前 100 枚印章）。此方法返回的印章链接地址 5 分钟有效，超时后无法在访问，需要再次获取。
     *
     * @param companyId         业务系统分配的企业 ID
     * @param companyOperatorId 业务系统分配的企业经办人 ID
     * @param proxyAppId        子客在腾讯电子签的 ID
     * @return {@link SealResponse} 子客印章列表
     */
    List<SealResponse> getSeals(Long companyId, Long companyOperatorId, String proxyAppId);

}
