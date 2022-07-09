
package com.zhisida.board.controller;

import com.zhisida.board.param.BoardAnalysisFilterParam;
import com.zhisida.board.service.BoardAnalysisFilterService;
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
 * 实时分析条件控制器
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:20:35
 */
@RestController
public class BoardAnalysisFilterController {

    @Resource
    private BoardAnalysisFilterService boardAnalysisFilterService;

    /**
     * 查询实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    @Permission
    @GetMapping("/boardAnalysisFilter/page")
    @BusinessLog(title = "实时分析条件_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        return ResponseData.success(boardAnalysisFilterService.page(boardAnalysisFilterParam));
    }

    /**
     * 添加实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    @Permission
    @PostMapping("/boardAnalysisFilter/add")
    @BusinessLog(title = "实时分析条件_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BoardAnalysisFilterParam.add.class) BoardAnalysisFilterParam boardAnalysisFilterParam) {
            boardAnalysisFilterService.add(boardAnalysisFilterParam);
        return ResponseData.success();
    }

    /**
     * 删除实时分析条件，可批量删除
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    @Permission
    @PostMapping("/boardAnalysisFilter/delete")
    @BusinessLog(title = "实时分析条件_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BoardAnalysisFilterParam.delete.class) List<BoardAnalysisFilterParam> boardAnalysisFilterParamList) {
            boardAnalysisFilterService.delete(boardAnalysisFilterParamList);
        return ResponseData.success();
    }

    /**
     * 编辑实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    @Permission
    @PostMapping("/boardAnalysisFilter/edit")
    @BusinessLog(title = "实时分析条件_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BoardAnalysisFilterParam.edit.class) BoardAnalysisFilterParam boardAnalysisFilterParam) {
            boardAnalysisFilterService.edit(boardAnalysisFilterParam);
        return ResponseData.success();
    }

    /**
     * 查看实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    @Permission
    @GetMapping("/boardAnalysisFilter/detail")
    @BusinessLog(title = "实时分析条件_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BoardAnalysisFilterParam.detail.class) BoardAnalysisFilterParam boardAnalysisFilterParam) {
        return ResponseData.success(boardAnalysisFilterService.detail(boardAnalysisFilterParam));
    }

    /**
     * 实时分析条件列表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    @Permission
    @GetMapping("/boardAnalysisFilter/list")
    @BusinessLog(title = "实时分析条件_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        return ResponseData.success(boardAnalysisFilterService.list(boardAnalysisFilterParam));
    }

    /**
     * 导出系统用户
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    @Permission
    @GetMapping("/boardAnalysisFilter/export")
    @BusinessLog(title = "实时分析条件_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        boardAnalysisFilterService.export(boardAnalysisFilterParam);
    }

}
