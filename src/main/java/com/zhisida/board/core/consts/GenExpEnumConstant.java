
package com.zhisida.board.core.consts;

/**
 * 代码生产 异常枚举编码构成常量
 * <p>
 * 异常枚举编码由3部分组成，如下：
 * <p>
 * 模块编码（2位） + 分类编码（4位） + 具体项编码（至少1位）
 * <p>
 * 模块编码和分类编码在ExpEnumCodeConstant类中声明
 *
 * @author young-pastor
 */
public interface GenExpEnumConstant {

    /**
     * 模块分类编码（2位）
     * <p>
     * gen模块异常枚举编码
     */
    int BOARD_GEN_MODULE_EXP_CODE = 60;

    /* 分类编码（4位） */
    /**
     * 代码生成表相关异常枚举
     */
    int GEN_CODE_EXCEPTION_ENUM = 1100;

    /**
     * 代码生成详细配置相关异常枚举
     */
    int GEN_CONFIG_EXCEPTION_ENUM = 1200;

}
