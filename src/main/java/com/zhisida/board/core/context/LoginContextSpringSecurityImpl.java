
package com.zhisida.board.core.context;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zhisida.board.core.consts.CommonConstant;
import com.zhisida.board.core.consts.SymbolConstant;
import com.zhisida.board.core.context.login.LoginContext;
import com.zhisida.board.core.exception.AuthException;
import com.zhisida.board.core.exception.enums.AuthExceptionEnum;
import com.zhisida.board.core.pojo.login.LoginEmpInfo;
import com.zhisida.board.core.pojo.login.SysLoginUser;
import com.zhisida.board.core.enums.AdminTypeEnum;
import com.zhisida.board.service.SysAuthService;
import com.zhisida.board.entity.SysUser;
import com.zhisida.board.service.SysUserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录用户上下文实现类
 *
 * @author young-pastor
 */
@Component
public class LoginContextSpringSecurityImpl implements LoginContext {

    @Resource
    private SysAuthService sysAuthService;

    @Resource
    private SysUserService sysUserService;

    private LoginContextSpringSecurityImpl() {

    }

    /**
     * 获取当前登录用户
     *
     * @author young-pastor
     */
    @Override
    public SysLoginUser getSysLoginUser() {
        Authentication authentication = sysAuthService.getAuthentication();
        if (ObjectUtil.isEmpty(authentication) || authentication.getPrincipal() instanceof String) {
            throw new AuthException(AuthExceptionEnum.NO_LOGIN_USER);
        } else {
            return (SysLoginUser) authentication.getPrincipal();
        }
    }

    /**
     * 获取当前登录用户，如未登录，则返回null，不抛异常
     *
     * @author young-pastor
     */
    @Override
    public SysLoginUser getSysLoginUserWithoutException() {
        Authentication authentication = sysAuthService.getAuthentication();
        if (ObjectUtil.isEmpty(authentication) || authentication.getPrincipal() instanceof String) {
            return null;
        } else {
            return (SysLoginUser) authentication.getPrincipal();
        }
    }

    /**
     * 获取当前登录用户的id
     *
     * @author young-pastor
     */
    @Override
    public Long getSysLoginUserId() {
        return this.getSysLoginUser().getId();
    }

    /**
     * 判断用户是否登录
     *
     * @author young-pastor
     */
    @Override
    public boolean hasLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtil.isEmpty(authentication) || authentication.getPrincipal() instanceof String) {
            return false;
        } else {
            return !(authentication instanceof AnonymousAuthenticationToken);
        }
    }

    /**
     * 获取当前登录的用户账号
     *
     * @author young-pastor
     */
    @Override
    public String getSysLoginUserAccount() {
        return this.getSysLoginUser().getAccount();
    }

    /**
     * 判断当前登录用户是否有某资源的访问权限
     *
     * @author young-pastor
     */
    @Override
    public boolean hasPermission(String requestUri) {
        String removePrefix = StrUtil.removePrefix(requestUri, SymbolConstant.LEFT_DIVIDE);
        String requestPermission = removePrefix.replaceAll(SymbolConstant.LEFT_DIVIDE, SymbolConstant.COLON);
        return this.getSysLoginUser().getPermissions().contains(requestPermission);
    }

    /**
     * 判断当前登录用户是否包含某个角色
     *
     * @author young-pastor
     */
    @Override
    public boolean hasRole(String roleCode) {
        List<String> roleCodeList = this.getLoginUserRoleCodeList();
        return roleCodeList.contains(roleCode);
    }

    /**
     * 判断当前登录用户是否包含任意一个角色
     *
     * @author young-pastor
     */
    @Override
    public boolean hasAnyRole(String roleCodes) {
        boolean flag = false;
        List<String> loginUserRoleCodeList = this.getLoginUserRoleCodeList();
        String[] roleCodeArr = StrUtil.split(roleCodes, SymbolConstant.COMMA);
        for (String roleCode : roleCodeArr) {
            if (loginUserRoleCodeList.contains(roleCode)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 管理员类型（0超级管理员 1非管理员）
     * 判断当前登录用户是否是超级管理员
     *
     * @author young-pastor
     */
    @Override
    public boolean isSuperAdmin() {
        return this.isAdmin(AdminTypeEnum.SUPER_ADMIN.getCode());
    }

    /**
     * 判断当前登录用户是否包含所有角色
     *
     * @author young-pastor
     */
    @Override
    public boolean hasAllRole(String roleCodes) {
        boolean flag = true;
        List<String> loginUserRoleCodeList = this.getLoginUserRoleCodeList();
        String[] roleCodeArr = StrUtil.split(roleCodes, SymbolConstant.COMMA);
        for (String roleCode : roleCodeArr) {
            if (!loginUserRoleCodeList.contains(roleCode)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断当前登录用户是否是指定类型的管理员
     *
     * @author young-pastor
     */
    private boolean isAdmin(Integer adminTypeCode) {
        Integer adminType = this.getSysLoginUser().getAdminType();
        boolean flag = false;
        if (adminType.equals(adminTypeCode)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 当前登录用户的数据范围（组织机构id集合）
     *
     * @author young-pastor
     */
    @Override
    public List<Long> getLoginUserDataScopeIdList() {
        return this.getSysLoginUser().getDataScopes();
    }

    /**
     * 获取当前登录用户的组织机构id集合
     *
     * @author young-pastor
     */
    @Override
    public Long getSysLoginUserOrgId() {
        LoginEmpInfo loginEmpInfo = this.getSysLoginUser().getLoginEmpInfo();
        if (ObjectUtil.isNotNull(loginEmpInfo)) {
            if (ObjectUtil.isNotEmpty(loginEmpInfo.getOrgId())) {
                return loginEmpInfo.getOrgId();
            }
        }
        return null;
    }

    /**
     * 获取当前登录用户的角色id集合
     *
     * @author young-pastor
     */
    @Override
    public List<String> getLoginUserRoleIds() {
        List<String> resultList = CollectionUtil.newArrayList();
        this.getSysLoginUser().getRoles().forEach(dict -> resultList.add(dict.getStr(CommonConstant.ID)));
        return resultList;
    }

    @Override
    public SysLoginUser getSysLoginUserUpToDate() {
        SysLoginUser sysLoginUser = this.getSysLoginUser();
        Long loginUserId = sysLoginUser.getId();
        SysUser sysUser = sysUserService.getById(loginUserId);
        //构造SysLoginUser
        return sysAuthService.genSysLoginUser(sysUser);
    }

    /**
     * 获取当前用户的角色编码集合
     *
     * @author young-pastor
     */
    private List<String> getLoginUserRoleCodeList() {
        List<String> roleCodeList = CollectionUtil.newArrayList();
        this.getSysLoginUser().getRoles().forEach(dict -> roleCodeList.add(dict.getStr(CommonConstant.CODE)));
        return roleCodeList;
    }
}
