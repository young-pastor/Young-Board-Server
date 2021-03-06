
package com.zhisida.system.controller;

import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.core.pojo.response.SuccessResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.system.service.SysMachineService;

import javax.annotation.Resource;

/**
 * 系统属性监控控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysMachineController {

    @Resource
    private SysMachineService sysMachineService;

    /**
     * 系统属性监控
     *
     * @author Young-Pastor
     */
    @GetMapping("/sysMachine/query")
    @BusinessLog(title = "系统属性监控_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData query() {
        return new SuccessResponseData(sysMachineService.query());
    }
}
