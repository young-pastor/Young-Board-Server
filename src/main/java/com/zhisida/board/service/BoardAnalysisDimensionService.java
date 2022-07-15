package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.board.entity.BoardAnalysisDimension;
import com.zhisida.board.param.BoardAnalysisDimensionParam;
import java.util.List;

/**
 * 实时分析分组属性service接口
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:03
 */
public interface BoardAnalysisDimensionService extends IService<BoardAnalysisDimension> {

    /**
     * 查询实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    PageResult<BoardAnalysisDimension> page(BoardAnalysisDimensionParam boardAnalysisDimensionParam);

    /**
     * 实时分析分组属性列表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    List<BoardAnalysisDimension> list(BoardAnalysisDimensionParam boardAnalysisDimensionParam);

    /**
     * 添加实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    void add(BoardAnalysisDimensionParam boardAnalysisDimensionParam);

    /**
     * 删除实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    void delete(List<BoardAnalysisDimensionParam> boardAnalysisDimensionParamList);

    /**
     * 编辑实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    void edit(BoardAnalysisDimensionParam boardAnalysisDimensionParam);

    /**
     * 查看实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
     BoardAnalysisDimension detail(BoardAnalysisDimensionParam boardAnalysisDimensionParam);

    /**
     * 导出实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
     void export(BoardAnalysisDimensionParam boardAnalysisDimensionParam);

}
