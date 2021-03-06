
package com.zhisida.system.controller;

import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.core.pojo.response.SuccessResponseData;
import com.zhisida.system.param.SysTimersLogParam;
import com.zhisida.system.service.SysTimersLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 任务日志控制器
 *
 * @author Young-Pastor
 * @date 2022-06-24 17:02:32
 */
@RestController
public class SysTimersLogController {

    @Resource
    private SysTimersLogService sysTimersLogService;

    /**
     * 查询任务日志
     *
     * @author Young-Pastor
     * @date 2022-06-24 17:02:32
     */
    @Permission
    @GetMapping("/sysTimersLog/page")
    @BusinessLog(title = "任务日志_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysTimersLogParam sysTimersLogParam) {
        return new SuccessResponseData(sysTimersLogService.page(sysTimersLogParam));
    }

    /**
     * 删除任务日志，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-06-24 17:02:32
     */
    @Permission
    @PostMapping("/sysTimersLog/delete")
    @BusinessLog(title = "任务日志_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody SysTimersLogParam sysTimersLogParam) {
        sysTimersLogService.delete(sysTimersLogParam);
        return new SuccessResponseData();
    }

}
