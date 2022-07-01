
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.node.AntdBaseTreeNode;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.board.entity.BoardEvent;
import com.zhisida.board.param.BoardEventParam;

import java.util.List;

/**
 * 元事件配置service接口
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:17
 */
public interface BoardEventService extends IService<BoardEvent> {

    /**
     * 查询元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    PageResult<BoardEvent> page(BoardEventParam boardEventParam);

    /**
     * 元事件配置列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    List<BoardEvent> list(BoardEventParam boardEventParam);

    /**
     * 添加元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    void add(BoardEventParam boardEventParam);

    /**
     * 删除元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    void delete(List<BoardEventParam> boardEventParamList);

    /**
     * 编辑元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    void edit(BoardEventParam boardEventParam);

    /**
     * 查看元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
     BoardEvent detail(BoardEventParam boardEventParam);

    /**
     * 导出元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
     void export(BoardEventParam boardEventParam);

    List<AntdBaseTreeNode> tree(BoardEventParam boardEventParam);
}
