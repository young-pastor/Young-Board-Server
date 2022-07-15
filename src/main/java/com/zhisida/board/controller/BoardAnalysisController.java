
package com.zhisida.board.controller;

import com.zhisida.board.param.BoardAnalysisParam;
import com.zhisida.board.service.BoardAnalysisService;
import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实时分析控制器
 *
 * @author Young-Pastor
 * @date 2022-07-07 21:06:23
 */
@RestController
public class BoardAnalysisController {

    @Resource
    private BoardAnalysisService boardAnalysisService;

    /**
     * 查询实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    @Permission
    @GetMapping("/boardAnalysis/page")
    @BusinessLog(title = "实时分析_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardAnalysisParam boardAnalysisParam) {
        return ResponseData.success(boardAnalysisService.page(boardAnalysisParam));
    }

    /**
     * 添加实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    @Permission
    @PostMapping("/boardAnalysis/add")
    @BusinessLog(title = "实时分析_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardAnalysisParam.add.class) BoardAnalysisParam boardAnalysisParam) {
        boardAnalysisService.add(boardAnalysisParam);
        return ResponseData.success();
    }

    /**
     * 删除实时分析，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    @Permission
    @PostMapping("/boardAnalysis/delete")
    @BusinessLog(title = "实时分析_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardAnalysisParam.delete.class) List<BoardAnalysisParam> boardAnalysisParamList) {
        boardAnalysisService.delete(boardAnalysisParamList);
        return ResponseData.success();
    }

    /**
     * 编辑实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    @Permission
    @PostMapping("/boardAnalysis/edit")
    @BusinessLog(title = "实时分析_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardAnalysisParam.edit.class) BoardAnalysisParam boardAnalysisParam) {
        boardAnalysisService.edit(boardAnalysisParam);
        return ResponseData.success();
    }

    /**
     * 查看实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    @Permission
    @GetMapping("/boardAnalysis/detail")
    @BusinessLog(title = "实时分析_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardAnalysisParam.detail.class) BoardAnalysisParam boardAnalysisParam) {
        return ResponseData.success(boardAnalysisService.detail(boardAnalysisParam));
    }

    /**
     * 实时分析列表
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    @Permission
    @GetMapping("/boardAnalysis/list")
    @BusinessLog(title = "实时分析_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardAnalysisParam boardAnalysisParam) {
        return ResponseData.success(boardAnalysisService.list(boardAnalysisParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    @Permission
    @GetMapping("/boardAnalysis/export")
    @BusinessLog(title = "实时分析_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardAnalysisParam boardAnalysisParam) {
        boardAnalysisService.export(boardAnalysisParam);
    }

    @Permission
    @PostMapping("/boardAnalysis/analysis")
    @BusinessLog(title = "实时分析_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData analysis(@RequestBody BoardAnalysisParam boardAnalysisParam) {
        return ResponseData.success(boardAnalysisService.analysis(boardAnalysisParam));
    }
    @Permission
    @PostMapping("/boardAnalysis/analysisById")
    @BusinessLog(title = "实时分析_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData analysisById(@RequestBody BoardAnalysisParam boardAnalysisParam) {
        return ResponseData.success(boardAnalysisService.analysisById(boardAnalysisParam.getId()));
    }
}
