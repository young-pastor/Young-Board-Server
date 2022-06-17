
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.DataScope;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.base.param.BaseParam;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.SysOrgParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.service.SysOrgService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统组织机构控制器
 *
 * @author young-pastor
 */
@RestController
public class SysOrgController {

    @Resource
    private SysOrgService sysOrgService;

    /**
     * 查询系统机构
     *
     * @author young-pastor
     */
    @Permission
    @DataScope
    @GetMapping("/sysOrg/page")
    @BusinessLog(title = "系统机构_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysOrgParam sysOrgParam) {
        return new SuccessResponseData(sysOrgService.page(sysOrgParam));
    }

    /**
     * 系统组织机构列表
     *
     * @author young-pastor
     */
    @Permission
    @DataScope
    @GetMapping("/sysOrg/list")
    @BusinessLog(title = "系统组织机构_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysOrgParam sysOrgParam) {
        return new SuccessResponseData(sysOrgService.list(sysOrgParam));
    }

    /**
     * 添加系统组织机构
     *
     * @author young-pastor
     */
    @Permission
    @DataScope
    @PostMapping("/sysOrg/add")
    @BusinessLog(title = "系统组织机构_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BaseParam.add.class) SysOrgParam sysOrgParam) {
        sysOrgService.add(sysOrgParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统组织机构
     *
     * @author young-pastor
     */
    @Permission
    @DataScope
    @PostMapping("/sysOrg/delete")
    @BusinessLog(title = "系统组织机构_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BaseParam.delete.class) List<SysOrgParam> sysOrgParamList) {
        sysOrgService.delete(sysOrgParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统组织机构
     *
     * @author young-pastor
     */
    @Permission
    @DataScope
    @PostMapping("/sysOrg/edit")
    @BusinessLog(title = "系统组织机构_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BaseParam.edit.class) SysOrgParam sysOrgParam) {
        sysOrgService.edit(sysOrgParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统组织机构
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysOrg/detail")
    @BusinessLog(title = "系统组织机构_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BaseParam.detail.class) SysOrgParam sysOrgParam) {
        return new SuccessResponseData(sysOrgService.detail(sysOrgParam));
    }

    /**
     * 获取组织机构树
     *
     * @author young-pastor
     */
    @Permission
    @DataScope
    @GetMapping("/sysOrg/tree")
    @BusinessLog(title = "系统组织机构_树", opType = LogAnnotionOpTypeEnum.TREE)
    public ResponseData tree(SysOrgParam sysOrgParam) {
        return new SuccessResponseData(sysOrgService.tree(sysOrgParam));
    }

    /**
     * 导出系统组织机构
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysOrg/export")
    @BusinessLog(title = "系统组织机构_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(SysOrgParam sysOrgParam) {
        sysOrgService.export(sysOrgParam);
    }
}
