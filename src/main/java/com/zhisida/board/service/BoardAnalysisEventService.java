
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.board.entity.BoardAnalysisEvent;
import com.zhisida.board.param.BoardAnalysisEventParam;
import java.util.List;

/**
 * 实时分析事件service接口
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:27:10
 */
public interface BoardAnalysisEventService extends IService<BoardAnalysisEvent> {

    /**
     * 查询实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    PageResult<BoardAnalysisEvent> page(BoardAnalysisEventParam boardAnalysisEventParam);

    /**
     * 实时分析事件列表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    List<BoardAnalysisEvent> list(BoardAnalysisEventParam boardAnalysisEventParam);

    /**
     * 添加实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    void add(BoardAnalysisEventParam boardAnalysisEventParam);

    /**
     * 删除实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    void delete(List<BoardAnalysisEventParam> boardAnalysisEventParamList);

    /**
     * 编辑实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    void edit(BoardAnalysisEventParam boardAnalysisEventParam);

    /**
     * 查看实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
     BoardAnalysisEvent detail(BoardAnalysisEventParam boardAnalysisEventParam);

    /**
     * 导出实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
     void export(BoardAnalysisEventParam boardAnalysisEventParam);

}
