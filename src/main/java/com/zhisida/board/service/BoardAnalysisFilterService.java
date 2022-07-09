
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.BoardAnalysisFilter;
import com.zhisida.board.param.BoardAnalysisFilterParam;
import com.zhisida.core.pojo.page.PageResult;

import java.util.List;

/**
 * 实时分析条件service接口
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:20:35
 */
public interface BoardAnalysisFilterService extends IService<BoardAnalysisFilter> {

    /**
     * 查询实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    PageResult<BoardAnalysisFilter> page(BoardAnalysisFilterParam boardAnalysisFilterParam);

    /**
     * 实时分析条件列表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    List<BoardAnalysisFilter> list(BoardAnalysisFilterParam boardAnalysisFilterParam);

    /**
     * 添加实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    void add(BoardAnalysisFilterParam boardAnalysisFilterParam);

    /**
     * 删除实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    void delete(List<BoardAnalysisFilterParam> boardAnalysisFilterParamList);

    /**
     * 编辑实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    void edit(BoardAnalysisFilterParam boardAnalysisFilterParam);

    /**
     * 查看实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
     BoardAnalysisFilter detail(BoardAnalysisFilterParam boardAnalysisFilterParam);

    /**
     * 导出实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
     void export(BoardAnalysisFilterParam boardAnalysisFilterParam);

}
