
package com.zhisida.board.core.scanner;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zhisida.board.core.consts.SymbolConstant;
import com.zhisida.board.core.context.resources.ApiResourceContext;
import com.zhisida.board.core.util.AopTargetUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 资源扫描器
 *
 * @author young-pastor
 */
public class ApiResourceScanner implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        //如果controller是代理对象,则需要获取原始类的信息
        Object aopTarget = AopTargetUtil.getTarget(bean);

        if (aopTarget == null) {
            aopTarget = bean;
        }
        Class<?> clazz = aopTarget.getClass();

        //判断是不是控制器,不是控制器就略过
        boolean controllerFlag = getControllerFlag(clazz);
        if (!controllerFlag) {
            return bean;
        }

        //扫描控制器的所有带ApiResource注解的方法
        Set<String> apiUrls = doScan(clazz);

        //将扫描到的注解存储到缓存
        persistApiResources(apiUrls);

        return bean;
    }


    /**
     * 判断一个类是否是控制器
     *
     * @author young-pastor
     */
    private boolean getControllerFlag(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();

        for (Annotation annotation : annotations) {
            if (RestController.class.equals(annotation.annotationType()) || Controller.class.equals(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 扫描整个类中包含的所有控制器
     *
     * @author young-pastor
     */
    private Set<String> doScan(Class<?> clazz) {

        // 获取类头的@RequestMapping内容
        String primaryMappingUrl = getRequestMappingUrl(clazz);

        // 获取所有方法的RequestMapping的url
        Set<String> apiResources = CollectionUtil.newHashSet();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        if (declaredMethods.length > 0) {
            for (Method declaredMethod : declaredMethods) {
                String requestMappingUrl = getRequestMappingUrl(declaredMethod);
                apiResources.add(primaryMappingUrl + requestMappingUrl);
            }
        }
        return apiResources;
    }

    /**
     * 存储扫描到的api资源
     *
     * @author young-pastor
     */
    private void persistApiResources(Set<String> apiResources) {
        ApiResourceContext.addBatchUrls(apiResources);
    }

    /**
     * 获取@RequestMapping注解的url信息
     *
     * @author young-pastor
     */
    private String getRequestMappingUrl(AnnotatedElement annotatedElement) {

        RequestMapping requestMapping = annotatedElement.getAnnotation(RequestMapping.class);
        GetMapping getMapping = annotatedElement.getAnnotation(GetMapping.class);
        PostMapping postMapping = annotatedElement.getAnnotation(PostMapping.class);

        // 分别判断三个注解中有没有value和path的值，优先级是 RequestMapping > GetMapping > PostMapping
        if (requestMapping != null) {
            String[] value = requestMapping.value();
            String[] path = requestMapping.path();
            if (value.length > 0) {
                return getRequestMappingPath(value);
            } else if (path.length > 0) {
                return getRequestMappingPath(path);
            }
        } else if (getMapping != null) {
            String[] value = getMapping.value();
            String[] path = getMapping.path();
            if (value.length > 0) {
                return getRequestMappingPath(value);
            } else if (path.length > 0) {
                return getRequestMappingPath(path);
            }
        } else if (postMapping != null) {
            String[] value = postMapping.value();
            String[] path = postMapping.path();
            if (value.length > 0) {
                return getRequestMappingPath(value);
            } else if (path.length > 0) {
                return getRequestMappingPath(path);
            }
        }

        return "";
    }

    /**
     * 获取数组第一个字符串
     *
     * @author young-pastor
     */
    private String getRequestMappingPath(String[] strings) {
        if (strings.length == 0) {
            return "";
        }
        String result = strings[0];

        // 如果RequestMapping的值是“/”直接返回
        if (SymbolConstant.LEFT_DIVIDE.equals(result)) {
            return result;
        }

        // 添加路径首部的"/"
        if (!result.startsWith(SymbolConstant.LEFT_DIVIDE)) {
            result = SymbolConstant.LEFT_DIVIDE + result;
        }

        // 则去掉尾部的"/"
        if (result.endsWith(SymbolConstant.LEFT_DIVIDE)) {
            result = StrUtil.removeSuffix(result, SymbolConstant.LEFT_DIVIDE);
        }

        return result;
    }

}
