
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.SysTimersLogParam;
import com.zhisida.board.service.SysTimersLogService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

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
    public ResponseData delete(@RequestBody @Validated(SysTimersLogParam.delete.class) List<SysTimersLogParam> sysTimersLogParamList) {
            sysTimersLogService.delete(sysTimersLogParamList);
        return new SuccessResponseData();
    }

}
