
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.base.param.BaseParam;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.SysAppParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.service.SysAppService;

import javax.annotation.Resource;

/**
 * 系统应用控制器
 *
 * @author young-pastor
 */
@RestController
public class SysAppController {

    @Resource
    private SysAppService sysAppService;

    /**
     * 查询系统应用
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysApp/page")
    @BusinessLog(title = "系统应用_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysAppParam sysAppParam) {
        return new SuccessResponseData(sysAppService.page(sysAppParam));
    }

    /**
     * 添加系统应用
     *
     * @author young-pastor
     */
    @Permission
    @PostMapping("/sysApp/add")
    @BusinessLog(title = "系统应用_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BaseParam.add.class) SysAppParam sysAppParam) {
        sysAppService.add(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统应用
     *
     * @author young-pastor
     */
    @Permission
    @PostMapping("/sysApp/delete")
    @BusinessLog(title = "系统应用_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BaseParam.delete.class) SysAppParam sysAppParam) {
        sysAppService.delete(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统应用
     *
     * @author young-pastor
     */
    @Permission
    @PostMapping("/sysApp/edit")
    @BusinessLog(title = "系统应用_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BaseParam.edit.class) SysAppParam sysAppParam) {
        sysAppService.edit(sysAppParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统应用
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysApp/detail")
    @BusinessLog(title = "系统应用_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BaseParam.detail.class) SysAppParam sysAppParam) {
        return new SuccessResponseData(sysAppService.detail(sysAppParam));
    }

    /**
     * 系统应用列表
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysApp/list")
    @BusinessLog(title = "系统应用_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysAppParam sysAppParam) {
        return new SuccessResponseData(sysAppService.list(sysAppParam));
    }

    /**
     * 设为默认应用
     *
     * @author young-pastor
     */
    @Permission
    @PostMapping("/sysApp/setAsDefault")
    @BusinessLog(title = "系统应用_设为默认应用", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData setAsDefault(@RequestBody @Validated(BaseParam.detail.class) SysAppParam sysAppParam) {
        sysAppService.setAsDefault(sysAppParam);
        return new SuccessResponseData();
    }
}
