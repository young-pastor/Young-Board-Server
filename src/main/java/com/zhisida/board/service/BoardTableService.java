
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.param.BoardTableParam;

import java.util.List;

/**
 * 数据表配置service接口
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:17:36
 */
public interface BoardTableService extends IService<BoardTable> {

    /**
     * 查询数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    PageResult<BoardTable> page(BoardTableParam boardTableParam);

    /**
     * 数据表配置列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    List<BoardTable> list(BoardTableParam boardTableParam);

    /**
     * 添加数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    void add(BoardTableParam boardTableParam);

    /**
     * 删除数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    void delete(List<BoardTableParam> boardTableParamList);

    /**
     * 编辑数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
    void edit(BoardTableParam boardTableParam);

    /**
     * 查看数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
     BoardTable detail(BoardTableParam boardTableParam);

    /**
     * 导出数据表配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:17:36
     */
     void export(BoardTableParam boardTableParam);

    void sync(BoardTableParam boardTableParams);
}
