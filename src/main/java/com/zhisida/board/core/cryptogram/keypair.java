
package com.zhisida.board.core.cryptogram;

/**
 * 基于SM2的秘钥对
 * （本项目中配置的，自己使用可根据自己的需求进行更换）
 *
 * @author young-pastor
 */
public class keypair {

    /**
     * 公钥
     */
    public static String PUBLIC_KEY = "04298364ec840088475eae92a591e01284d1abefcda348b47eb324bb521bb03b0b2a5bc393f6b71dabb8f15c99a0050818b56b23f31743b93df9cf8948f15ddb54";

    /**
     * 私钥
     */
    public static String PRIVATE_KEY = "3037723d47292171677ec8bd7dc9af696c7472bc5f251b2cec07e65fdef22e25";

    /**
     * SM4的对称秘钥（生产环境需要改成自己使用的）
     * 16 进制字符串，要求为 128 比特
     */
    public static String KEY = "0123456789abcdeffedcba9876543210";

}
