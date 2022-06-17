
package com.zhisida.board.enums;

import com.zhisida.board.core.annotion.ExpEnumType;
import com.zhisida.board.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.board.core.factory.ExpEnumCodeFactory;
import com.zhisida.board.core.consts.SysExpEnumConstant;

/**
 * 文件信息表相关枚举
 *
 * @author young-pastor
 */
@ExpEnumType(module = SysExpEnumConstant.BOARD_SYS_MODULE_EXP_CODE, kind = SysExpEnumConstant.SYS_FILE_INFO_EXCEPTION_ENUM)
public enum SysFileInfoExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 该条记录不存在
     */
    NOT_EXISTED(1, "您查询的该条记录不存在"),

    /**
     * 获取文件流错误
     */
    FILE_STREAM_ERROR(2, "获取文件流错误"),

    /**
     * 文件不存在
     */
    NOT_EXISTED_FILE(3, "文件不存在"),

    /**
     * 获取上传文件异常
     */
    ERROR_FILE(4, "获取上传文件异常"),

    /**
     * 下载文件错误
     */
    DOWNLOAD_FILE_ERROR(5, "下载文件错误"),

    /**
     * 预览文件异常
     */
    PREVIEW_ERROR_NOT_SUPPORT(6, "预览文件异常，您预览的文件类型不支持或文件出现错误"),

    /**
     * 预览文件异常
     */
    PREVIEW_ERROR_LIBREOFFICE(7, "预览文件异常，请检查LibreOffice是否启动"),

    /**
     * 文件操作客户端初始化异常
     */
    CLIENT_INIT_ERROR(8, "文件操作客户端初始化异常"),

    /**
     * 在线文档暂时只支持本地文件
     */
    ONLINE_EDIT_SUPPORT_LOCAL_ONLY(9, "在线文档暂时只支持本地文件"),

    /**
     * 在线文档参数错误
     */
    ONLINE_EDIT_PARAM_ERROR(10, "在线文档参数错误");

    private final Integer code;

    private final String message;

    SysFileInfoExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeFactory.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
