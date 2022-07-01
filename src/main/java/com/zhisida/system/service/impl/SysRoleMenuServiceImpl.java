
package com.zhisida.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.system.param.SysRoleParam;
import org.springframework.stereotype.Service;
import com.zhisida.system.entity.SysRoleMenu;
import com.zhisida.system.mapper.SysRoleMenuMapper;
import com.zhisida.system.service.SysRoleMenuService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色菜单service接口实现类
 *
 * @author Young-Pastor
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public List<Long> getRoleMenuIdList(List<Long> roleIdList) {
        if(ObjectUtil.isNotEmpty(roleIdList)) {
            LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysRoleMenu::getRoleId, roleIdList);
            return this.list(queryWrapper).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        }
        return CollectionUtil.newArrayList();
    }

    @Override
    public void grantMenu(SysRoleParam sysRoleParam) {
        Long roleId = sysRoleParam.getId();
        //删除所拥有菜单
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        this.remove(queryWrapper);
        //授权菜单
        sysRoleParam.getGrantMenuIdList().forEach(menuId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            this.save(sysRoleMenu);
        });
    }

    @Override
    public void deleteRoleMenuListByMenuIdList(List<Long> menuIdList) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleMenu::getMenuId, menuIdList);
        this.remove(queryWrapper);
    }

    @Override
    public void deleteRoleMenuListByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        this.remove(queryWrapper);
    }
}
