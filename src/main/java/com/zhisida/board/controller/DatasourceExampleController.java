
package com.zhisida.board.controller;

import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.service.DatasourceExampleService;

import javax.annotation.Resource;

/**
 * 一个示例接口
 *
 * @author young-pastor
 */
@RestController
@RequestMapping("/example")
public class DatasourceExampleController {

    @Resource
    private DatasourceExampleService datasourceService;

    @GetMapping("/niceDay")
    public ResponseData niceDay() {
        return new SuccessResponseData("nice day");
    }

    @GetMapping("/masterDatasource")
    public ResponseData masterDatasource() {
        return new SuccessResponseData(datasourceService.masterDatasource());
    }

    @GetMapping("/backupDatasource")
    public ResponseData backupDatasource() {
        return new SuccessResponseData(datasourceService.backupDatasource());
    }

    @GetMapping("/datasourceTransactionNone")
    public ResponseData datasourceTransactionNone() {
        datasourceService.datasourceTransactionNone();
        return new SuccessResponseData();
    }

    @GetMapping("/datasourceTransaction")
    public ResponseData datasourceTransaction() {
        datasourceService.datasourceTransaction();
        return new SuccessResponseData();
    }

}
