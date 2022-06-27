
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.node.AntdBaseTreeNode;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.BoardProperty;
import com.zhisida.board.param.BoardPropertyParam;

import java.util.List;

/**
 * 属性配置service接口
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:25
 */
public interface BoardPropertyService extends IService<BoardProperty> {

    /**
     * 查询属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    PageResult<BoardProperty> page(BoardPropertyParam boardPropertyParam);

    /**
     * 属性配置列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    List<BoardProperty> list(BoardPropertyParam boardPropertyParam);

    /**
     * 添加属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    void add(BoardPropertyParam boardPropertyParam);

    /**
     * 删除属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    void delete(List<BoardPropertyParam> boardPropertyParamList);

    /**
     * 编辑属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
    void edit(BoardPropertyParam boardPropertyParam);

    /**
     * 查看属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
     BoardProperty detail(BoardPropertyParam boardPropertyParam);

    /**
     * 导出属性配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:25
     */
     void export(BoardPropertyParam boardPropertyParam);

    List<AntdBaseTreeNode> tree(BoardPropertyParam boardPropertyParam);
}
