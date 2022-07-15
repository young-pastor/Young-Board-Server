
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.board.entity.BoardPropertyGroup;
import com.zhisida.board.param.BoardEventGroupParam;
import com.zhisida.board.param.BoardPropertyGroupParam;

import java.util.List;

/**
 * 属性分组service接口
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:37
 */
public interface BoardPropertyGroupService extends IService<BoardPropertyGroup> {

    /**
     * 查询属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    PageResult<BoardPropertyGroup> page(BoardPropertyGroupParam boardPropertyGroupParam);

    /**
     * 属性分组列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    List<BoardPropertyGroup> list(BoardPropertyGroupParam boardPropertyGroupParam);

    /**
     * 添加属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    void add(BoardPropertyGroupParam boardPropertyGroupParam);

    /**
     * 删除属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    void delete(List<BoardPropertyGroupParam> boardPropertyGroupParamList);

    /**
     * 编辑属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    void edit(BoardPropertyGroupParam boardPropertyGroupParam);

    /**
     * 查看属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
     BoardPropertyGroup detail(BoardPropertyGroupParam boardPropertyGroupParam);

    /**
     * 导出属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
     void export(BoardPropertyGroupParam boardPropertyGroupParam);

    Object tree(BoardEventGroupParam boardEventGroupParam);
}
