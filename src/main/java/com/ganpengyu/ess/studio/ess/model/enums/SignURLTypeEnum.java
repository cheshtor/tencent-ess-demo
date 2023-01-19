package com.ganpengyu.ess.studio.ess.model.enums;

/**
 * 腾讯电子签签署链接类型枚举。
 * 对于不同类型链接的说明，可参考官方文档：
 * https://cloud.tencent.com/document/api/1420/61522
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/6
 */
public enum SignURLTypeEnum {

    /**
     * 生成一个短连接，用户点击后会直接跳转到微信小程序进行签署
     */
    WEIXINAPP("WEIXINAPP", "短链直接跳小程序"),

    /**
     * 生成一个 H5 页面的链接，用户访问此页面唤起微信小程序进行签署。如果设置了 jumpUrl，
     * 则在签署完成后由系统自动引导页面跳转到这个指定地址
     */
    CHANNEL("CHANNEL", "跳转H5页面"),

    /**
     * 生成一个从移动端跳转到微信小程序的链接，这里会用到腾讯电子签微信小程序的 AppId 和 原始 ID。
     * 对于全屏拉起小程序和半屏拉起小程序的处理存在不同逻辑，具体参考官方文档。
     * 腾讯电子签（正式版）
     * AppID：wxa023b292fd19d41d
     * 原始 ID: gh_da88f6188665
     * 腾讯电子签 Demo（联调测试环境）
     * AppID: wx371151823f6f3edf
     * 原始 ID: gh_39a5d3de69fa
     */
    APP("APP", "第三方APP或小程序跳转电子签小程序"),

    /**
     * 生成一个长链接，用户点击后会直接跳转到微信小程序进行签署
     */
    LONGURL2WEIXINAPP("LONGURL2WEIXINAPP", "长链接跳转小程序"),
    ;

    private final String type;
    private final String description;

    SignURLTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
