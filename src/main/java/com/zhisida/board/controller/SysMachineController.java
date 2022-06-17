
package com.zhisida.board.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.service.SysMachineService;

import javax.annotation.Resource;

/**
 * 系统属性监控控制器
 *
 * @author young-pastor
 */
@RestController
public class SysMachineController {

    @Resource
    private SysMachineService sysMachineService;

    /**
     * 系统属性监控
     *
     * @author young-pastor
     */
    @GetMapping("/sysMachine/query")
    @BusinessLog(title = "系统属性监控_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData query() {
        return new SuccessResponseData(sysMachineService.query());
    }
}
