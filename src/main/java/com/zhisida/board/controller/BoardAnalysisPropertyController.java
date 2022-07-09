package com.zhisida.board.controller;

import com.zhisida.board.param.BoardAnalysisPropertyParam;
import com.zhisida.board.service.BoardAnalysisPropertyService;
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
 * 实时分析属性表控制器
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:05
 */
@RestController
public class BoardAnalysisPropertyController {

    @Resource
    private BoardAnalysisPropertyService boardAnalysisPropertyService;

    /**
     * 查询实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    @Permission
    @GetMapping("/boardAnalysisProperty/page")
    @BusinessLog(title = "实时分析属性表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        return ResponseData.success(boardAnalysisPropertyService.page(boardAnalysisPropertyParam));
    }

    /**
     * 添加实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    @Permission
    @PostMapping("/boardAnalysisProperty/add")
    @BusinessLog(title = "实时分析属性表_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardAnalysisPropertyParam.add.class) BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
            boardAnalysisPropertyService.add(boardAnalysisPropertyParam);
        return ResponseData.success();
    }

    /**
     * 删除实时分析属性表，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    @Permission
    @PostMapping("/boardAnalysisProperty/delete")
    @BusinessLog(title = "实时分析属性表_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardAnalysisPropertyParam.delete.class) List<BoardAnalysisPropertyParam> boardAnalysisPropertyParamList) {
            boardAnalysisPropertyService.delete(boardAnalysisPropertyParamList);
        return ResponseData.success();
    }

    /**
     * 编辑实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    @Permission
    @PostMapping("/boardAnalysisProperty/edit")
    @BusinessLog(title = "实时分析属性表_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardAnalysisPropertyParam.edit.class) BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
            boardAnalysisPropertyService.edit(boardAnalysisPropertyParam);
        return ResponseData.success();
    }

    /**
     * 查看实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    @Permission
    @GetMapping("/boardAnalysisProperty/detail")
    @BusinessLog(title = "实时分析属性表_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardAnalysisPropertyParam.detail.class) BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        return ResponseData.success(boardAnalysisPropertyService.detail(boardAnalysisPropertyParam));
    }

    /**
     * 实时分析属性表列表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    @Permission
    @GetMapping("/boardAnalysisProperty/list")
    @BusinessLog(title = "实时分析属性表_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        return ResponseData.success(boardAnalysisPropertyService.list(boardAnalysisPropertyParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    @Permission
    @GetMapping("/boardAnalysisProperty/export")
    @BusinessLog(title = "实时分析属性表_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        boardAnalysisPropertyService.export(boardAnalysisPropertyParam);
    }

}
