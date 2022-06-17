
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.SysAreaParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.service.SysAreaService;

import javax.annotation.Resource;

/**
 * 系统区域控制器
 *
 * @author young-pastor
 */
@RestController
public class SysAreaController {

    @Resource
    private SysAreaService sysAreaService;

    /**
     * 系统区域列表（树）
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysArea/list")
    @BusinessLog(title = "系统区域_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysAreaParam sysAreaParam) {
        return new SuccessResponseData(sysAreaService.list(sysAreaParam));
    }
}
