
package com.zhisida.board.core.error;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.exception.enums.PermissionExceptionEnum;
import com.zhisida.board.core.exception.enums.ServerExceptionEnum;
import com.zhisida.board.core.pojo.response.ErrorResponseData;

import java.util.Map;

/**
 * 将系统管理未知错误异常，输出格式重写为我们熟悉的响应格式
 *
 * @author young-pastor
 */
public class BoardErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions attributeOptions) {

        // 1.先获取spring默认的返回内容
        Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, attributeOptions);

        // 2.如果返回的异常是ServiceException，则按ServiceException响应的内容进行返回
        Throwable throwable = this.getError(webRequest);
        if (throwable instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) throwable;
            return BeanUtil.beanToMap(new ErrorResponseData(serviceException.getCode(), serviceException.getErrorMessage()));
        }

        // 3.如果返回的是404 http状态码
        Integer status = (Integer) defaultErrorAttributes.get("status");
        if (status.equals(HttpStatus.HTTP_NOT_FOUND)) {
            return BeanUtil.beanToMap(new ErrorResponseData(PermissionExceptionEnum.URL_NOT_EXIST.getCode(), PermissionExceptionEnum.URL_NOT_EXIST.getMessage()));
        }

        // 4.无法确定的返回服务器异常
        return BeanUtil.beanToMap(new ErrorResponseData(ServerExceptionEnum.SERVER_ERROR.getCode(), ServerExceptionEnum.SERVER_ERROR.getMessage()));
    }

}
