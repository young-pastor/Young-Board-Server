
package com.zhisida.system.controller;

import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.core.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.system.param.SysPosParam;
import com.zhisida.system.service.SysPosService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统职位控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysPosController {

    @Resource
    private SysPosService sysPosService;

    /**
     * 查询系统职位
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysPos/page")
    @BusinessLog(title = "系统职位_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysPosParam sysPosParam) {
        return new SuccessResponseData(sysPosService.page(sysPosParam));
    }

    /**
     * 系统职位列表
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysPos/list")
    @BusinessLog(title = "系统职位_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysPosParam sysPosParam) {
        return new SuccessResponseData(sysPosService.list(sysPosParam));
    }

    /**
     * 添加系统职位
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysPos/add")
    @BusinessLog(title = "系统职位_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysPosParam.add.class) SysPosParam sysPosParam) {
        sysPosService.add(sysPosParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统职位
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysPos/delete")
    @BusinessLog(title = "系统职位_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysPosParam.delete.class) List<SysPosParam> sysPosParamList) {
        sysPosService.delete(sysPosParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统职位
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysPos/edit")
    @BusinessLog(title = "系统职位_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysPosParam.edit.class) SysPosParam sysPosParam) {
        sysPosService.edit(sysPosParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统职位
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysPos/detail")
    @BusinessLog(title = "系统职位_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysPosParam.detail.class) SysPosParam sysPosParam) {
        return new SuccessResponseData(sysPosService.detail(sysPosParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysPos/export")
    @BusinessLog(title = "系统职位_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(SysPosParam sysPosParam) {
        sysPosService.export(sysPosParam);
    }
}
