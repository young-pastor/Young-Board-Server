
package com.zhisida.core.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.zhisida.core.pojo.response.ErrorResponseData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应工具类
 *
 * @author Young-Pastor
 */
public class ResponseUtil {

    /**
     * 响应异常，直接向前端写response，用于异常处理器捕获不到时手动抛出
     *
     * @author Young-Pastor
     */
    public static void responseExceptionError(HttpServletResponse response,
                                              Integer code,
                                              String message,
                                              String exceptionClazz) throws IOException {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        ErrorResponseData errorResponseData = new ErrorResponseData(code, message);
        errorResponseData.setExceptionClazz(exceptionClazz);
        String errorResponseJsonData = JSON.toJSONString(errorResponseData);
        response.getWriter().write(errorResponseJsonData);
    }

    /**
     * 响应异常，向前端返回ErrorResponseData的json数据，用于全局异常处理器
     *
     * @author Young-Pastor
     */
    public static ErrorResponseData responseDataError(Integer code, String message, String exceptionClazz) {
        ErrorResponseData errorResponseData = new ErrorResponseData(code, message);
        errorResponseData.setExceptionClazz(exceptionClazz);
        return errorResponseData;
    }
}
