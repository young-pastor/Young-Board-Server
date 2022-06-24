
package com.zhisida.board.core.context.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.board.core.consts.CommonConstant;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.exception.enums.ServerExceptionEnum;
import com.zhisida.board.core.pojo.login.LoginEmpInfo;
import com.zhisida.board.core.pojo.login.SysLoginUser;
import com.zhisida.board.core.pojo.node.LoginMenuTreeNode;
import com.zhisida.board.core.tenant.consts.TenantConstants;
import com.zhisida.board.core.tenant.context.TenantCodeHolder;
import com.zhisida.board.core.tenant.context.TenantDbNameHolder;
import com.zhisida.board.core.util.HttpServletUtil;
import com.zhisida.board.core.util.IpAddressUtil;
import com.zhisida.board.core.util.UaUtil;
import com.zhisida.board.entity.SysMenu;
import com.zhisida.board.service.SysMenuService;
import com.zhisida.board.service.SysRoleMenuService;
import com.zhisida.board.service.SysRoleService;
import com.zhisida.board.service.SysUserService;
import com.zhisida.board.service.SysAppService;
import com.zhisida.board.service.SysEmpService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 登录用户工厂类
 *
 * @author Young-Pastor
 */
public class LoginUserFactory {

    private static final SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);

    private static final SysEmpService sysEmpService = SpringUtil.getBean(SysEmpService.class);

    private static final SysAppService sysAppService = SpringUtil.getBean(SysAppService.class);

    private static final SysMenuService sysMenuService = SpringUtil.getBean(SysMenuService.class);

    private static final SysRoleService sysRoleService = SpringUtil.getBean(SysRoleService.class);

    private static final SysRoleMenuService sysRoleMenuService = SpringUtil.getBean(SysRoleMenuService.class);

    /**
     * 填充登录用户相关信息
     *
     * @author Young-Pastor
     */
    public static void fillLoginUserInfo(SysLoginUser sysLoginUser) {
        HttpServletRequest request = HttpServletUtil.getRequest();
        if (ObjectUtil.isNotNull(request)) {
            sysLoginUser.setLastLoginIp(IpAddressUtil.getIp(request));
            sysLoginUser.setLastLoginTime(DateTime.now().toString());
            sysLoginUser.setLastLoginAddress(IpAddressUtil.getAddress(request));
            sysLoginUser.setLastLoginBrowser(UaUtil.getBrowser(request));
            sysLoginUser.setLastLoginOs(UaUtil.getOs(request));
            Long userId = sysLoginUser.getId();

            // 员工信息
            LoginEmpInfo loginEmpInfo = sysEmpService.getLoginEmpInfo(userId);
            sysLoginUser.setLoginEmpInfo(loginEmpInfo);

            // 角色信息
            List<Dict> roles = sysRoleService.getLoginRoles(userId);
            sysLoginUser.setRoles(roles);

            // 获取角色id集合
            List<Long> roleIdList = roles.stream().map(dict -> Convert.toLong(dict.get(CommonConstant.ID))).collect(Collectors.toList());

            // 获取菜单id集合
            List<Long> menuIdList = sysRoleMenuService.getRoleMenuIdList(roleIdList);

            // 权限信息
            List<String> permissions = sysMenuService.getLoginPermissions(userId, menuIdList);
            sysLoginUser.setPermissions(permissions);

            // 数据范围信息
            List<Long> dataScopes = sysUserService.getUserDataScopeIdList(userId, loginEmpInfo.getOrgId());
            sysLoginUser.setDataScopes(dataScopes);

            // 具备应用信息（多系统，默认激活一个，可根据系统切换菜单）,返回的结果中第一个为激活的系统
            List<Dict> apps = sysAppService.getLoginApps(userId, roleIdList);
            sysLoginUser.setApps(apps);

            // 如果根本没有应用信息，则没有菜单信息
            if (ObjectUtil.isEmpty(apps)) {
                sysLoginUser.setMenus(CollectionUtil.newArrayList());
            } else {
                //AntDesign菜单信息，根据人获取，用于登录后展示菜单树，默认获取默认激活的系统的菜单
                String defaultActiveAppCode = apps.get(0).getStr(CommonConstant.CODE);
                List<SysMenu> loginMenus = sysMenuService.getLoginMenus(userId, defaultActiveAppCode, menuIdList);
                Map<String, List<SysMenu>> collect = loginMenus.stream().collect(Collectors.groupingBy(SysMenu::getApplication));
                List<SysMenu> tempList = collect.get(defaultActiveAppCode);
                List<LoginMenuTreeNode> loginMenuTreeNodes = sysMenuService.convertSysMenuToLoginMenu(tempList);
                sysLoginUser.setMenus(loginMenuTreeNodes);
            }

            SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
            //如果开启了多租户功能，则设置当前登录用户的租户标识
            if (sysConfigCache.getTenantOpenFlag()) {
                String tenantCode = TenantCodeHolder.get();
                String dataBaseName = TenantDbNameHolder.get();
                if (StrUtil.isNotBlank(tenantCode) && StrUtil.isNotBlank(dataBaseName)) {
                    Dict tenantInfo = Dict.create();
                    tenantInfo.set(TenantConstants.TENANT_CODE, tenantCode);
                    tenantInfo.set(TenantConstants.TENANT_DB_NAME, dataBaseName);
                    sysLoginUser.setTenants(tenantInfo);
                }
                //注意，这里remove不代表所有情况，在aop remove
                TenantCodeHolder.remove();
                TenantDbNameHolder.remove();
            }

        } else {
            throw new ServiceException(ServerExceptionEnum.REQUEST_EMPTY);
        }
    }
}
