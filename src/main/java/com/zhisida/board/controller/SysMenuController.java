
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.context.login.LoginContextHolder;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.node.LoginMenuTreeNode;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.service.SysRoleMenuService;
import com.zhisida.board.service.SysUserRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.entity.SysMenu;
import com.zhisida.board.param.SysMenuParam;
import com.zhisida.board.service.SysMenuService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统菜单控制器
 *
 * @author young-pastor
 */
@RestController
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 系统菜单列表（树）
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysMenu/list")
    @BusinessLog(title = "系统菜单_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysMenuParam sysMenuParam) {
        return new SuccessResponseData(sysMenuService.list(sysMenuParam));
    }

    /**
     * 添加系统菜单
     *
     * @author young-pastor
     */
    @Permission
    @PostMapping("/sysMenu/add")
    @BusinessLog(title = "系统菜单_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysMenuParam.add.class) SysMenuParam sysMenuParam) {
        sysMenuService.add(sysMenuParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统菜单
     *
     * @author young-pastor
     */
    @Permission
    @PostMapping("/sysMenu/delete")
    @BusinessLog(title = "系统菜单_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysMenuParam.delete.class) SysMenuParam sysMenuParam) {
        sysMenuService.delete(sysMenuParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统菜单
     *
     * @author young-pastor
     */
    @Permission
    @PostMapping("/sysMenu/edit")
    @BusinessLog(title = "系统菜单_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysMenuParam.edit.class) SysMenuParam sysMenuParam) {
        sysMenuService.edit(sysMenuParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统菜单
     *
     * @author young-pastor
     */
    @Permission
    @PostMapping("/sysMenu/detail")
    @BusinessLog(title = "系统菜单_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysMenuParam.detail.class) SysMenuParam sysMenuParam) {
        return new SuccessResponseData(sysMenuService.detail(sysMenuParam));
    }

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysMenu/tree")
    @BusinessLog(title = "系统菜单_树", opType = LogAnnotionOpTypeEnum.TREE)
    public ResponseData tree(SysMenuParam sysMenuParam) {
        return new SuccessResponseData(sysMenuService.tree(sysMenuParam));
    }

    /**
     * 获取系统菜单树，用于给角色授权时选择
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysMenu/treeForGrant")
    @BusinessLog(title = "系统菜单_授权树", opType = LogAnnotionOpTypeEnum.TREE)
    public ResponseData treeForGrant(SysMenuParam sysMenuParam) {
        return new SuccessResponseData(sysMenuService.treeForGrant(sysMenuParam));
    }

    /**
     * 根据系统切换菜单
     *
     * @author young-pastor
     */
    @PostMapping("/sysMenu/change")
    @BusinessLog(title = "系统菜单_切换", opType = LogAnnotionOpTypeEnum.TREE)
    public ResponseData change(@RequestBody @Validated(SysMenuParam.change.class) SysMenuParam sysMenuParam) {
        Long sysLoginUserId = LoginContextHolder.me().getSysLoginUserId();
        List<Long> userRoleIdList = sysUserRoleService.getUserRoleIdList(sysLoginUserId);
        List<Long> menuIdList = sysRoleMenuService.getRoleMenuIdList(userRoleIdList);
        //转换成登录菜单
        List<SysMenu> sysMenuList = sysMenuService.getLoginMenus(sysLoginUserId, sysMenuParam.getApplication(), menuIdList);
        List<LoginMenuTreeNode> menuTreeNodeList = sysMenuService.convertSysMenuToLoginMenu(sysMenuList);
        return new SuccessResponseData(menuTreeNodeList);
    }
}
