
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.BoardEventGroup;
import com.zhisida.board.param.BoardEventGroupParam;

import java.util.List;

/**
 * 元事件分组service接口
 *
 * @author young-pastor
 * @date 2022-06-20 11:52:21
 */
public interface BoardEventGroupService extends IService<BoardEventGroup> {

    /**
     * 查询元事件分组
     *
     * @author young-pastor
     * @date 2022-06-20 11:52:21
     */
    PageResult<BoardEventGroup> page(BoardEventGroupParam boardEventGroupParam);

    /**
     * 元事件分组列表
     *
     * @author young-pastor
     * @date 2022-06-20 11:52:21
     */
    List<BoardEventGroup> list(BoardEventGroupParam boardEventGroupParam);

    /**
     * 添加元事件分组
     *
     * @author young-pastor
     * @date 2022-06-20 11:52:21
     */
    void add(BoardEventGroupParam boardEventGroupParam);

    /**
     * 删除元事件分组
     *
     * @author young-pastor
     * @date 2022-06-20 11:52:21
     */
    void delete(List<BoardEventGroupParam> boardEventGroupParamList);

    /**
     * 编辑元事件分组
     *
     * @author young-pastor
     * @date 2022-06-20 11:52:21
     */
    void edit(BoardEventGroupParam boardEventGroupParam);

    /**
     * 查看元事件分组
     *
     * @author young-pastor
     * @date 2022-06-20 11:52:21
     */
     BoardEventGroup detail(BoardEventGroupParam boardEventGroupParam);

    /**
     * 导出元事件分组
     *
     * @author young-pastor
     * @date 2022-06-20 11:52:21
     */
     void export(BoardEventGroupParam boardEventGroupParam);

}
