
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.analysis.BoardAnalysisData;
import com.zhisida.board.entity.BoardAnalysis;
import com.zhisida.board.param.BoardAnalysisParam;
import com.zhisida.core.pojo.page.PageResult;

import java.util.List;

/**
 * 实时分析service接口
 *
 * @author Young-Pastor
 * @date 2022-07-07 21:06:23
 */
public interface BoardAnalysisService extends IService<BoardAnalysis> {

    /**
     * 查询实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    PageResult<BoardAnalysis> page(BoardAnalysisParam boardAnalysisParam);

    /**
     * 实时分析列表
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    List<BoardAnalysis> list(BoardAnalysisParam boardAnalysisParam);

    /**
     * 添加实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    void add(BoardAnalysisParam boardAnalysisParam);

    /**
     * 删除实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    void delete(List<BoardAnalysisParam> boardAnalysisParamList);

    /**
     * 编辑实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    void edit(BoardAnalysisParam boardAnalysisParam);

    /**
     * 查看实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
     BoardAnalysis detail(BoardAnalysisParam boardAnalysisParam);

    /**
     * 导出实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
     void export(BoardAnalysisParam boardAnalysisParam);

    BoardAnalysisData analysis(BoardAnalysisParam boardAnalysisParam);

    BoardAnalysisData analysisById(Long id);
}
