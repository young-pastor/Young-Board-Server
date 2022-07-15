package com.zhisida.board.controller;

import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.board.param.BoardAnalysisEventParam;
import com.zhisida.board.service.BoardAnalysisEventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实时分析事件控制器
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:27:10
 */
@RestController
public class BoardAnalysisEventController {

    @Resource
    private BoardAnalysisEventService boardAnalysisEventService;

    /**
     * 查询实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    @Permission
    @GetMapping("/boardAnalysisEvent/page")
    @BusinessLog(title = "实时分析事件_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardAnalysisEventParam boardAnalysisEventParam) {
        return ResponseData.success(boardAnalysisEventService.page(boardAnalysisEventParam));
    }

    /**
     * 添加实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    @Permission
    @PostMapping("/boardAnalysisEvent/add")
    @BusinessLog(title = "实时分析事件_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardAnalysisEventParam.add.class) BoardAnalysisEventParam boardAnalysisEventParam) {
            boardAnalysisEventService.add(boardAnalysisEventParam);
        return ResponseData.success();
    }

    /**
     * 删除实时分析事件，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    @Permission
    @PostMapping("/boardAnalysisEvent/delete")
    @BusinessLog(title = "实时分析事件_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardAnalysisEventParam.delete.class) List<BoardAnalysisEventParam> boardAnalysisEventParamList) {
            boardAnalysisEventService.delete(boardAnalysisEventParamList);
        return ResponseData.success();
    }

    /**
     * 编辑实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    @Permission
    @PostMapping("/boardAnalysisEvent/edit")
    @BusinessLog(title = "实时分析事件_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardAnalysisEventParam.edit.class) BoardAnalysisEventParam boardAnalysisEventParam) {
            boardAnalysisEventService.edit(boardAnalysisEventParam);
        return ResponseData.success();
    }

    /**
     * 查看实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    @Permission
    @GetMapping("/boardAnalysisEvent/detail")
    @BusinessLog(title = "实时分析事件_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardAnalysisEventParam.detail.class) BoardAnalysisEventParam boardAnalysisEventParam) {
        return ResponseData.success(boardAnalysisEventService.detail(boardAnalysisEventParam));
    }

    /**
     * 实时分析事件列表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    @Permission
    @GetMapping("/boardAnalysisEvent/list")
    @BusinessLog(title = "实时分析事件_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardAnalysisEventParam boardAnalysisEventParam) {
        return ResponseData.success(boardAnalysisEventService.list(boardAnalysisEventParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    @Permission
    @GetMapping("/boardAnalysisEvent/export")
    @BusinessLog(title = "实时分析事件_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardAnalysisEventParam boardAnalysisEventParam) {
        boardAnalysisEventService.export(boardAnalysisEventParam);
    }

}
