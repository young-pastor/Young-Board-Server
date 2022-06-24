
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.SysOpLogParam;
import com.zhisida.board.param.SysVisLogParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.service.SysOpLogService;
import com.zhisida.board.service.SysVisLogService;

import javax.annotation.Resource;

/**
 * 系统日志控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysLogController {

    @Resource
    private SysVisLogService sysVisLogService;

    @Resource
    private SysOpLogService sysOpLogService;

    /**
     * 查询访问日志
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysVisLog/page")
    public ResponseData visLogPage(SysVisLogParam visLogParam) {
        return new SuccessResponseData(sysVisLogService.page(visLogParam));
    }

    /**
     * 查询操作日志
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysOpLog/page")
    public ResponseData opLogPage(SysOpLogParam sysOpLogParam) {
        return new SuccessResponseData(sysOpLogService.page(sysOpLogParam));
    }

    /**
     * 清空访问日志
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysVisLog/delete")
    @BusinessLog(title = "访问日志_清空", opType = LogAnnotionOpTypeEnum.CLEAN)
    public ResponseData visLogDelete() {
        sysVisLogService.delete();
        return new SuccessResponseData();
    }

    /**
     * 清空操作日志
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sysOpLog/delete")
    @BusinessLog(title = "操作日志_清空", opType = LogAnnotionOpTypeEnum.CLEAN)
    public ResponseData opLogDelete() {
        sysOpLogService.delete();
        return new SuccessResponseData();
    }

    /**
     * 导出系统操作日志
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysOpLog/export")
    @BusinessLog(title = "操作日志_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(SysOpLogParam sysOpLogParam) {
        sysOpLogService.export(sysOpLogParam);
    }

    /**
     * 导出系统访问日志
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sysVisLog/export")
    @BusinessLog(title = "访问日志_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(SysVisLogParam sysVisLogParam) {
        sysVisLogService.export(sysVisLogParam);
    }

}
