package com.zhisida.board.controller;

import com.zhisida.board.param.BoardAnalysisDimensionParam;
import com.zhisida.board.service.BoardAnalysisDimensionService;
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
 * 实时分析分组属性控制器
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:03
 */
@RestController
public class BoardAnalysisDimensionController {

    @Resource
    private BoardAnalysisDimensionService boardAnalysisDimensionService;

    /**
     * 查询实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    @Permission
    @GetMapping("/boardAnalysisDimension/page")
    @BusinessLog(title = "实时分析分组属性_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        return ResponseData.success(boardAnalysisDimensionService.page(boardAnalysisDimensionParam));
    }

    /**
     * 添加实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    @Permission
    @PostMapping("/boardAnalysisDimension/add")
    @BusinessLog(title = "实时分析分组属性_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardAnalysisDimensionParam.add.class) BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
            boardAnalysisDimensionService.add(boardAnalysisDimensionParam);
        return ResponseData.success();
    }

    /**
     * 删除实时分析分组属性，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    @Permission
    @PostMapping("/boardAnalysisDimension/delete")
    @BusinessLog(title = "实时分析分组属性_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardAnalysisDimensionParam.delete.class) List<BoardAnalysisDimensionParam> boardAnalysisDimensionParamList) {
            boardAnalysisDimensionService.delete(boardAnalysisDimensionParamList);
        return ResponseData.success();
    }

    /**
     * 编辑实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    @Permission
    @PostMapping("/boardAnalysisDimension/edit")
    @BusinessLog(title = "实时分析分组属性_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardAnalysisDimensionParam.edit.class) BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
            boardAnalysisDimensionService.edit(boardAnalysisDimensionParam);
        return ResponseData.success();
    }

    /**
     * 查看实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    @Permission
    @GetMapping("/boardAnalysisDimension/detail")
    @BusinessLog(title = "实时分析分组属性_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardAnalysisDimensionParam.detail.class) BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        return ResponseData.success(boardAnalysisDimensionService.detail(boardAnalysisDimensionParam));
    }

    /**
     * 实时分析分组属性列表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    @Permission
    @GetMapping("/boardAnalysisDimension/list")
    @BusinessLog(title = "实时分析分组属性_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        return ResponseData.success(boardAnalysisDimensionService.list(boardAnalysisDimensionParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    @Permission
    @GetMapping("/boardAnalysisDimension/export")
    @BusinessLog(title = "实时分析分组属性_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        boardAnalysisDimensionService.export(boardAnalysisDimensionParam);
    }

}
