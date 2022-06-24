
package com.zhisida.board.boardTableConnect.controller;

import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.BoardTableConnectParam;
import com.zhisida.board.service.BoardTableConnectService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 字段关联配置控制器
 *
 * @author young-pastor
 * @date 2022-06-20 11:45:42
 */
@RestController
public class BoardTableConnectController {

    @Resource
    private BoardTableConnectService boardTableConnectService;

    /**
     * 查询字段关联配置
     *
     * @author young-pastor
     * @date 2022-06-20 11:45:42
     */
    @Permission
    @GetMapping("/boardTableConnect/page")
    @BusinessLog(title = "字段关联配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardTableConnectParam boardTableConnectParam) {
        return new SuccessResponseData(boardTableConnectService.page(boardTableConnectParam));
    }

    /**
     * 添加字段关联配置
     *
     * @author young-pastor
     * @date 2022-06-20 11:45:42
     */
    @Permission
    @PostMapping("/boardTableConnect/add")
    @BusinessLog(title = "字段关联配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardTableConnectParam.add.class) BoardTableConnectParam boardTableConnectParam) {
            boardTableConnectService.add(boardTableConnectParam);
        return new SuccessResponseData();
    }

    /**
     * 删除字段关联配置，可批量删除
     *
     * @author young-pastor
     * @date 2022-06-20 11:45:42
     */
    @Permission
    @PostMapping("/boardTableConnect/delete")
    @BusinessLog(title = "字段关联配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardTableConnectParam.delete.class) List<BoardTableConnectParam> boardTableConnectParamList) {
            boardTableConnectService.delete(boardTableConnectParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑字段关联配置
     *
     * @author young-pastor
     * @date 2022-06-20 11:45:42
     */
    @Permission
    @PostMapping("/boardTableConnect/edit")
    @BusinessLog(title = "字段关联配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardTableConnectParam.edit.class) BoardTableConnectParam boardTableConnectParam) {
            boardTableConnectService.edit(boardTableConnectParam);
        return new SuccessResponseData();
    }

    /**
     * 查看字段关联配置
     *
     * @author young-pastor
     * @date 2022-06-20 11:45:42
     */
    @Permission
    @GetMapping("/boardTableConnect/detail")
    @BusinessLog(title = "字段关联配置_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardTableConnectParam.detail.class) BoardTableConnectParam boardTableConnectParam) {
        return new SuccessResponseData(boardTableConnectService.detail(boardTableConnectParam));
    }

    /**
     * 字段关联配置列表
     *
     * @author young-pastor
     * @date 2022-06-20 11:45:42
     */
    @Permission
    @GetMapping("/boardTableConnect/list")
    @BusinessLog(title = "字段关联配置_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardTableConnectParam boardTableConnectParam) {
        return new SuccessResponseData(boardTableConnectService.list(boardTableConnectParam));
    }

    /**
     * 导出系统用户
     *
     * @author young-pastor
     * @date 2022-06-20 11:45:42
     */
    @Permission
    @GetMapping("/boardTableConnect/export")
    @BusinessLog(title = "字段关联配置_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardTableConnectParam boardTableConnectParam) {
        boardTableConnectService.export(boardTableConnectParam);
    }

}