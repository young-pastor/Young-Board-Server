
package com.zhisida.system.controller;

import com.zhisida.core.pojo.base.param.BaseParam;
import com.zhisida.system.param.SysCodeGenerateConfigParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.core.pojo.response.SuccessResponseData;
import com.zhisida.system.service.SysCodeGenerateConfigService;

import javax.annotation.Resource;

/**
 * 代码生成详细配置控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysCodeGenerateConfigController {

    @Resource
    private SysCodeGenerateConfigService sysCodeGenerateConfigService;

    /**
     * 编辑代码生成详细配置
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysCodeGenerateConfig/edit")
    @BusinessLog(title = "代码生成详细配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BaseParam.edit.class) SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
            sysCodeGenerateConfigService.edit(sysCodeGenerateConfigParam.getSysCodeGenerateConfigParamList());
        return new SuccessResponseData();
    }

    /**
     * 查看代码生成详细配置
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysCodeGenerateConfig/detail")
    @BusinessLog(title = "代码生成详细配置_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysCodeGenerateConfigParam.detail.class) SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        return new SuccessResponseData(sysCodeGenerateConfigService.detail(sysCodeGenerateConfigParam));
    }

    /**
     * 代码生成详细配置列表
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysCodeGenerateConfig/list")
    @BusinessLog(title = "代码生成详细配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        return new SuccessResponseData(sysCodeGenerateConfigService.list(sysCodeGenerateConfigParam));
    }

}
