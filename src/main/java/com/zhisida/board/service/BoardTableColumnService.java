
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.param.BoardTableColumnParam;
import com.zhisida.core.pojo.node.AntdBaseTreeNode;
import com.zhisida.core.pojo.page.PageResult;

import java.util.List;

/**
 * 数据字段配置service接口
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:27:41
 */
public interface BoardTableColumnService extends IService<BoardTableColumn> {

    /**
     * 查询数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    PageResult<BoardTableColumn> page(BoardTableColumnParam boardTableColumnParam);

    /**
     * 数据字段配置列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    List<BoardTableColumn> list(BoardTableColumnParam boardTableColumnParam);

    /**
     * 添加数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    void add(BoardTableColumnParam boardTableColumnParam);

    /**
     * 删除数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    void delete(List<BoardTableColumnParam> boardTableColumnParamList);

    /**
     * 编辑数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    void edit(BoardTableColumnParam boardTableColumnParam);

    /**
     * 查看数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
     BoardTableColumn detail(BoardTableColumnParam boardTableColumnParam);

    /**
     * 导出数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
     void export(BoardTableColumnParam boardTableColumnParam);

    void deleteByBoardTableIds(List<Long> oldTableIds);

    void sync(BoardTableColumnParam boardTableColumnParam);

    List<AntdBaseTreeNode> tree(BoardTableColumnParam boardTableColumnParam);
}
