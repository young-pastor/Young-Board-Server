
package com.zhisida.board.core.exception;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.Log;
import com.zhisida.board.core.exception.enums.*;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.zhisida.board.core.consts.AopSortConstant;
import com.zhisida.board.core.consts.CommonConstant;
import com.zhisida.board.core.consts.SymbolConstant;
import com.zhisida.board.core.context.requestno.RequestNoContext;
import com.zhisida.board.core.exception.AuthException;
import com.zhisida.board.core.exception.DemoException;
import com.zhisida.board.core.exception.PermissionException;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.board.core.pojo.response.ErrorResponseData;
import com.zhisida.board.core.sms.modular.aliyun.exp.AliyunSmsException;
import com.zhisida.board.core.sms.modular.tencent.exp.TencentSmsException;
import com.zhisida.board.core.util.HttpServletUtil;
import com.zhisida.board.core.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常处理器
 *
 * @author young-pastor
 */
@Order(AopSortConstant.GLOBAL_EXP_HANDLER_AOP)
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Log log = Log.get();

    /**
     * 腾讯云短信发送异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(TencentSmsException.class)
    @ResponseBody
    public ErrorResponseData aliyunSmsException(TencentSmsException e) {
        log.error(">>> 发送短信异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getErrorMessage());
        return renderJson(500, e.getErrorMessage());
    }

    /**
     * 阿里云短信发送异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(AliyunSmsException.class)
    @ResponseBody
    public ErrorResponseData aliyunSmsException(AliyunSmsException e) {
        log.error(">>> 发送短信异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getErrorMessage());
        return renderJson(500, e.getErrorMessage());
    }

    /**
     * 请求参数缺失异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ErrorResponseData missParamException(MissingServletRequestParameterException e) {
        log.error(">>> 请求参数异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        String parameterType = e.getParameterType();
        String parameterName = e.getParameterName();
        String message = StrUtil.format(">>> 缺少请求的参数{}，类型为{}", parameterName, parameterType);
        return renderJson(500, message);
    }

    /**
     * 拦截参数格式传递异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ErrorResponseData httpMessageNotReadable(HttpMessageNotReadableException e) {
        log.error(">>> 参数格式传递异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return renderJson(RequestTypeExceptionEnum.REQUEST_JSON_ERROR);
    }

    /**
     * 拦截不支持媒体类型异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ErrorResponseData httpMediaTypeNotSupport(HttpMediaTypeNotSupportedException e) {
        log.error(">>> 参数格式传递异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return renderJson(RequestTypeExceptionEnum.REQUEST_TYPE_IS_JSON);
    }

    /**
     * 拦截请求方法的异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ErrorResponseData methodNotSupport(HttpServletRequest request) {
        if (ServletUtil.isPostMethod(request)) {
            log.error(">>> 请求方法异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), RequestMethodExceptionEnum.REQUEST_METHOD_IS_GET.getMessage());
            return renderJson(RequestMethodExceptionEnum.REQUEST_METHOD_IS_GET);
        }
        if (ServletUtil.isGetMethod(request)) {
            log.error(">>> 请求方法异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), RequestMethodExceptionEnum.REQUEST_METHOD_IS_POST.getMessage());
            return renderJson(RequestMethodExceptionEnum.REQUEST_METHOD_IS_POST);
        }
        return null;
    }

    /**
     * 拦截资源找不到的运行时异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseData notFound(NoHandlerFoundException e) {
        log.error(">>> 资源不存在异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage() +"，请求地址为:" + HttpServletUtil.getRequest().getRequestURI());
        return renderJson(PermissionExceptionEnum.URL_NOT_EXIST.getCode(), PermissionExceptionEnum.URL_NOT_EXIST.getMessage() +"，请求地址为:" + HttpServletUtil.getRequest().getRequestURI());
    }

    /**
     * 拦截参数校验错误异常,JSON传参
     *
     * @author young-pastor
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponseData methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String argNotValidMessage = getArgNotValidMessage(e.getBindingResult());
        log.error(">>> 参数校验错误异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), argNotValidMessage);
        return renderJson(ParamExceptionEnum.PARAM_ERROR.getCode(), argNotValidMessage);
    }

    /**
     * 拦截参数校验错误异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ErrorResponseData paramError(BindException e) {
        String argNotValidMessage = getArgNotValidMessage(e.getBindingResult());
        log.error(">>> 参数校验错误异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), argNotValidMessage);
        return renderJson(ParamExceptionEnum.PARAM_ERROR.getCode(), argNotValidMessage);
    }

    /**
     * 拦截认证失败异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public ErrorResponseData authFail(AuthException e) {
        log.error(">>> 认证异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return renderJson(e.getCode(), e.getErrorMessage());
    }

    /**
     * 拦截权限异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(PermissionException.class)
    @ResponseBody
    public ErrorResponseData noPermission(PermissionException e) {
        log.error(">>> 权限异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage() +"，请求地址为:" + HttpServletUtil.getRequest().getRequestURI());
        return renderJson(e.getCode(), e.getErrorMessage() + "，请求地址为:" + HttpServletUtil.getRequest().getRequestURI());
    }

    /**
     * 拦截业务异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ErrorResponseData businessError(ServiceException e) {
        log.error(">>> 业务异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return renderJson(e.getCode(), e.getErrorMessage(), e);
    }

    /**
     * 拦截mybatis数据库操作的异常
     * <p>
     * 用在demo模式，拦截DemoException
     *
     * @author young-pastor
     */
    @ExceptionHandler(MyBatisSystemException.class)
    @ResponseBody
    public ErrorResponseData persistenceException(MyBatisSystemException e) {
        log.error(">>> mybatis操作出现异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        Throwable cause = e.getCause();
        if (cause instanceof PersistenceException) {
            Throwable secondCause = cause.getCause();
            if (secondCause instanceof DemoException) {
                DemoException demoException = (DemoException) secondCause;
                return ResponseUtil.responseDataError(demoException.getCode(), demoException.getErrorMessage(), e.getStackTrace()[0].toString());
            }
        }
        return ResponseUtil.responseDataError(ServerExceptionEnum.SERVER_ERROR.getCode(), ServerExceptionEnum.SERVER_ERROR.getMessage(), e.getStackTrace()[0].toString());
    }

    /**
     * 拦截未知的运行时异常
     *
     * @author young-pastor
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData serverError(Throwable e) {
        log.error(">>> 服务器运行异常，请求号为：{}", RequestNoContext.get());
        e.printStackTrace();
        return renderJson(e);
    }

    /**
     * 渲染异常json
     *
     * @author young-pastor
     */
    private ErrorResponseData renderJson(Integer code, String message) {
        return renderJson(code, message, null);
    }

    /**
     * 渲染异常json
     *
     * @author young-pastor
     */
    private ErrorResponseData renderJson(AbstractBaseExceptionEnum baseExceptionEnum) {
        return renderJson(baseExceptionEnum.getCode(), baseExceptionEnum.getMessage(), null);
    }

    /**
     * 渲染异常json
     *
     * @author young-pastor
     */
    private ErrorResponseData renderJson(Throwable throwable) {
        return renderJson(((AbstractBaseExceptionEnum) ServerExceptionEnum.SERVER_ERROR).getCode(),
                ((AbstractBaseExceptionEnum) ServerExceptionEnum.SERVER_ERROR).getMessage(), throwable);
    }

    /**
     * 渲染异常json
     * <p>
     * 根据异常枚举和Throwable异常响应，异常信息响应堆栈第一行
     *
     * @author young-pastor
     */
    private ErrorResponseData renderJson(Integer code, String message, Throwable e) {
        if (ObjectUtil.isNotNull(e)) {

            //获取所有堆栈信息
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            //默认的异常类全路径为第一条异常堆栈信息的
            String exceptionClassTotalName = stackTraceElements[0].toString();

            //遍历所有堆栈信息，找到开头的第一条异常信息
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                if (stackTraceElement.toString().contains(CommonConstant.DEFAULT_PACKAGE_NAME)) {
                    exceptionClassTotalName = stackTraceElement.toString();
                    break;
                }
            }
            return ResponseUtil.responseDataError(code, message, exceptionClassTotalName);
        } else {
            return ResponseUtil.responseDataError(code, message, null);
        }
    }

    /**
     * 获取请求参数不正确的提示信息
     * <p>
     * 多个信息，拼接成用逗号分隔的形式
     *
     * @author young-pastor
     */
    private String getArgNotValidMessage(BindingResult bindingResult) {
        if (bindingResult == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        //多个错误用逗号分隔
        List<ObjectError> allErrorInfos = bindingResult.getAllErrors();
        for (ObjectError error : allErrorInfos) {
            stringBuilder.append(SymbolConstant.COMMA).append(error.getDefaultMessage());
        }

        //最终把首部的逗号去掉
        return StrUtil.removePrefix(stringBuilder.toString(), SymbolConstant.COMMA);
    }

}
