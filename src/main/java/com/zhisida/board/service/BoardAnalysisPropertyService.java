package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.BoardAnalysisProperty;
import com.zhisida.board.param.BoardAnalysisPropertyParam;
import com.zhisida.core.pojo.page.PageResult;

import java.util.List;

/**
 * 实时分析属性表service接口
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:05
 */
public interface BoardAnalysisPropertyService extends IService<BoardAnalysisProperty> {

    /**
     * 查询实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    PageResult<BoardAnalysisProperty> page(BoardAnalysisPropertyParam boardAnalysisPropertyParam);

    /**
     * 实时分析属性表列表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    List<BoardAnalysisProperty> list(BoardAnalysisPropertyParam boardAnalysisPropertyParam);

    /**
     * 添加实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    void add(BoardAnalysisPropertyParam boardAnalysisPropertyParam);

    /**
     * 删除实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    void delete(List<BoardAnalysisPropertyParam> boardAnalysisPropertyParamList);

    /**
     * 编辑实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    void edit(BoardAnalysisPropertyParam boardAnalysisPropertyParam);

    /**
     * 查看实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
     BoardAnalysisProperty detail(BoardAnalysisPropertyParam boardAnalysisPropertyParam);

    /**
     * 导出实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
     void export(BoardAnalysisPropertyParam boardAnalysisPropertyParam);

}
