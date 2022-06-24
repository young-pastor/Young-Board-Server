
package com.zhisida.board.core.sms.modular.aliyun.msign;

/**
 * 多个签名的缓存管理，为了打破一个签名发送次数的限制
 *
 * @author Young-Pastor
 */
public interface MultiSignManager {

    /**
     * 获取签名
     *
     * @param phone    电话
     * @param signName 发送短信用的签名，是一个以逗号隔开的字符串
     * @return 签名
     * @author Young-Pastor
     */
    String getSign(String phone, String signName);

}
