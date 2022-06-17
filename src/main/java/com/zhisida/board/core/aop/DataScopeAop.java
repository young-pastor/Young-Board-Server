
package com.zhisida.board.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import com.zhisida.board.core.consts.AopSortConstant;
import com.zhisida.board.core.context.login.LoginContextHolder;
import com.zhisida.board.core.pojo.base.param.BaseParam;

import java.util.List;

/**
 * 数据权限切面
 *
 * @author young-pastor
 */
@Aspect
@Order(AopSortConstant.DATA_SCOPE_AOP)
public class DataScopeAop {

    /**
     * 数据范围切入点
     *
     * @author young-pastor
     */
    @Pointcut("@annotation(com.zhisida.board.core.annotion.DataScope)")
    private void getDataScopePointCut() {
    }

    /**
     * 执行数据范围过滤
     *
     * @author young-pastor
     */
    @Before("getDataScopePointCut()")
    public void doDataScope(JoinPoint joinPoint) {

        //不是超级管理员时进行数据权限过滤
        if (!LoginContextHolder.me().isSuperAdmin()) {
            Object[] args = joinPoint.getArgs();

            //数据范围就是组织机构id集合
            List<Long> loginUserDataScopeIdList = LoginContextHolder.me().getLoginUserDataScopeIdList();
            BaseParam baseParam;
            for (Object object : args) {
                if (object instanceof BaseParam) {
                    baseParam = (BaseParam) object;
                    baseParam.setDataScope(loginUserDataScopeIdList);
                }
            }
        }
    }
}
