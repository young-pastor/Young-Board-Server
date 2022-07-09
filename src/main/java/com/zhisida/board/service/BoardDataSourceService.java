
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.BoardDataSource;
import com.zhisida.board.param.BoardDataSourceParam;
import com.zhisida.core.pojo.page.PageResult;

import java.util.List;

/**
 * 数据源配置表service接口
 *
 * @author Young-Pastor
 * @date 2022-06-17 15:08:24
 */
public interface BoardDataSourceService extends IService<BoardDataSource> {

    /**
     * 查询数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    PageResult<BoardDataSource> page(BoardDataSourceParam boardDataSourceParam);

    /**
     * 数据源配置表列表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    List<BoardDataSource> list(BoardDataSourceParam boardDataSourceParam);

    /**
     * 添加数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    void add(BoardDataSourceParam boardDataSourceParam);

    /**
     * 删除数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    void delete(List<BoardDataSourceParam> boardDataSourceParamList);

    /**
     * 编辑数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    void edit(BoardDataSourceParam boardDataSourceParam);

    /**
     * 查看数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
     BoardDataSource detail(BoardDataSourceParam boardDataSourceParam);

    /**
     * 导出数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
     void export(BoardDataSourceParam boardDataSourceParam);

}
