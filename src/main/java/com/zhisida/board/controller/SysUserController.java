
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.DataScope;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.annotion.Wrapper;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.param.SysUserParam;
import com.zhisida.board.service.SysUserService;
import com.zhisida.board.core.context.wrapper.SysUserWrapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * 系统用户控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 查询系统用户
     *
     * @author Young-Pastor
     */
    @Permission
    @DataScope
    @GetMapping("/sysUser/page")
    @BusinessLog(title = "系统用户_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    @Wrapper(SysUserWrapper.class)
    public ResponseData page(SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.page(sysUserParam));
    }

    /**
     * 增加系统用户
     *
     * @author Young-Pastor
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/add")
    @BusinessLog(title = "系统用户_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysUserParam.add.class) SysUserParam sysUserParam) {
        sysUserService.add(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统用户
     *
     * @author Young-Pastor
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/delete")
    @BusinessLog(title = "系统用户_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysUserParam.delete.class) List<SysUserParam> sysUserParamList) {
        sysUserService.delete(sysUserParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统用户
     *
     * @author Young-Pastor
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/edit")
    @BusinessLog(title = "系统用户_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysUserParam.edit.class) SysUserParam sysUserParam) {
        sysUserService.edit(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统用户
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysUser/detail")
    @BusinessLog(title = "系统用户_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    @Wrapper(SysUserWrapper.class)
    public ResponseData detail(@Validated(SysUserParam.detail.class) SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.detail(sysUserParam));
    }

    /**
     * 修改状态
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysUser/changeStatus")
    @BusinessLog(title = "系统用户_修改状态", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData changeStatus(@RequestBody @Validated(SysUserParam.changeStatus.class) SysUserParam sysUserParam) {
        sysUserService.changeStatus(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 授权角色
     *
     * @author Young-Pastor
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/grantRole")
    @BusinessLog(title = "系统用户_授权角色", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantRole(@RequestBody @Validated(SysUserParam.grantRole.class) SysUserParam sysUserParam) {
        sysUserService.grantRole(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 授权数据
     *
     * @author Young-Pastor
     */
    @Permission
    @DataScope
    @PostMapping("/sysUser/grantData")
    @BusinessLog(title = "系统用户_授权数据", opType = LogAnnotionOpTypeEnum.GRANT)
    public ResponseData grantData(@RequestBody @Validated(SysUserParam.grantData.class) SysUserParam sysUserParam) {
        sysUserService.grantData(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 更新信息
     *
     * @author Young-Pastor
     */
    @PostMapping("/sysUser/updateInfo")
    @BusinessLog(title = "系统用户_更新信息", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateInfo(@RequestBody @Validated(SysUserParam.updateInfo.class) SysUserParam sysUserParam) {
        sysUserService.updateInfo(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 修改密码
     *
     * @author Young-Pastor
     */
    @PostMapping("/sysUser/updatePwd")
    @BusinessLog(title = "系统用户_修改密码", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updatePwd(@RequestBody @Validated(SysUserParam.updatePwd.class) SysUserParam sysUserParam) {
        sysUserService.updatePwd(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 拥有角色
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysUser/ownRole")
    @BusinessLog(title = "系统用户_拥有角色", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownRole(@Validated(SysUserParam.detail.class) SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.ownRole(sysUserParam));
    }

    /**
     * 拥有数据
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysUser/ownData")
    @BusinessLog(title = "系统用户_拥有数据", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData ownData(@Validated(SysUserParam.detail.class) SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.ownData(sysUserParam));
    }

    /**
     * 重置密码
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysUser/resetPwd")
    @BusinessLog(title = "系统用户_重置密码", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData resetPwd(@RequestBody @Validated(SysUserParam.resetPwd.class) SysUserParam sysUserParam) {
        sysUserService.resetPwd(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 修改头像
     *
     * @author Young-Pastor
     */
    @PostMapping("/sysUser/updateAvatar")
    @BusinessLog(title = "系统用户_修改头像", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateAvatar(@RequestBody @Validated(SysUserParam.updateAvatar.class) SysUserParam sysUserParam) {
        sysUserService.updateAvatar(sysUserParam);
        return new SuccessResponseData();
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysUser/export")
    @BusinessLog(title = "系统用户_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(SysUserParam sysUserParam) {
        sysUserService.export(sysUserParam);
    }


    /**
     * 用户选择器
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysUser/selector")
    @BusinessLog(title = "系统用户_选择器", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData selector(SysUserParam sysUserParam) {
        return new SuccessResponseData(sysUserService.selector(sysUserParam));
    }

}
