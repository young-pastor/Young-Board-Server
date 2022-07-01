
package com.zhisida.system.controller;

import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.DataScope;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.core.pojo.response.SuccessResponseData;
import com.zhisida.system.param.SysRoleParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.system.service.SysRoleService;

import javax.annotation.Resource;

/**
 * 系统角色控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 查询系统角色
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysRole/page")
    @BusinessLog(title = "系统角色_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.page(sysRoleParam));
    }

    /**
     * 系统角色下拉（用于授权角色时选择）
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysRole/dropDown")
    @BusinessLog(title = "系统角色_下拉", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData dropDown() {
        return new SuccessResponseData(sysRoleService.dropDown());
    }

    /**
     * 添加系统角色
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysRole/add")
    @BusinessLog(title = "系统角色_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysRoleParam.add.class) SysRoleParam sysRoleParam) {
        sysRoleService.add(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统角色
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysRole/delete")
    @BusinessLog(title = "系统角色_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysRoleParam.delete.class) SysRoleParam sysRoleParam) {
        sysRoleService.delete(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统角色
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysRole/edit")
    @BusinessLog(title = "系统角色_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysRoleParam.edit.class) SysRoleParam sysRoleParam) {
        sysRoleService.edit(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统角色
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysRole/detail")
    @BusinessLog(title = "系统角色_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysRoleParam.detail.class) SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.detail(sysRoleParam));
    }

    /**
     * 授权菜单
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysRole/grantMenu")
    @BusinessLog(title = "系统角色_授权菜单", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantMenu(@RequestBody @Validated(SysRoleParam.grantMenu.class) SysRoleParam sysRoleParam) {
        sysRoleService.grantMenu(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 授权数据
     *
     * @author Young-Pastor
     */
    @Permission
    @DataScope
    @PostMapping("/sysRole/grantData")
    @BusinessLog(title = "系统角色_授权数据", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantData(@RequestBody @Validated(SysRoleParam.grantData.class) SysRoleParam sysRoleParam) {
        sysRoleService.grantData(sysRoleParam);
        return new SuccessResponseData();
    }

    /**
     * 拥有菜单
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysRole/ownMenu")
    @BusinessLog(title = "系统角色_拥有菜单", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownMenu(@Validated(SysRoleParam.detail.class) SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.ownMenu(sysRoleParam));
    }

    /**
     * 拥有数据
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysRole/ownData")
    @BusinessLog(title = "系统角色_拥有数据", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownData(@Validated(SysRoleParam.detail.class) SysRoleParam sysRoleParam) {
        return new SuccessResponseData(sysRoleService.ownData(sysRoleParam));
    }

}
