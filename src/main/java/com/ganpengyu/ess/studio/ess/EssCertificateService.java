package com.ganpengyu.ess.studio.ess;


import com.ganpengyu.ess.studio.ess.model.certificate.ConsoleURLResponse;
import com.ganpengyu.ess.studio.ess.model.enums.ConsoleURLTypeEnum;

/**
 * 电子签认证服务
 *
 * @author Pengyu Gan
 * CreateDate 2022/11/29
 */
public interface EssCertificateService {

    /**
     * 获取腾讯电子签控制台链接地址。此接口能够获取多种类型的控制台链接，具体请参考
     * 枚举 {@link ConsoleURLTypeEnum} 里的说明。除了 {@link ConsoleURLTypeEnum#ACCOUNT} 外，
     * 其他枚举值在使用时如果指定了 moduleId, 就表示进入这个 moduleId 所代表的
     * 资源的详情页面，否则进入列表页面。
     * https://cloud.tencent.com/document/product/1420/61524
     *
     * @param companyId         业务系统分配的企业 ID
     * @param companyName       业务系统分配的企业名称
     * @param companyOperatorId 业务系统分配的企业经办人 ID
     * @param consoleURLType    {@link ConsoleURLTypeEnum} 控制台链接类型
     * @param moduleId          控制台链接类型所代表的那一类资源里某一条具体资源的 ID
     * @return {@link ConsoleURLResponse} 控制台链接地址详情
     */
    ConsoleURLResponse getConsoleURL(Long companyId, String companyName, Long companyOperatorId, ConsoleURLTypeEnum consoleURLType, String moduleId);

}
