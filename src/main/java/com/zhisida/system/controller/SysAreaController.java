
package com.zhisida.system.controller;

import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.core.pojo.response.SuccessResponseData;
import com.zhisida.system.param.SysAreaParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.system.service.SysAreaService;

import javax.annotation.Resource;

/**
 * 系统区域控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysAreaController {

    @Resource
    private SysAreaService sysAreaService;

    /**
     * 系统区域列表（树）
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysArea/list")
    @BusinessLog(title = "系统区域_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysAreaParam sysAreaParam) {
        return new SuccessResponseData(sysAreaService.list(sysAreaParam));
    }
}
