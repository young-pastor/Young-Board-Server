
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.BoardPropertyValue;
import com.zhisida.board.param.BoardPropertyValueParam;
import com.zhisida.core.pojo.page.PageResult;

import java.util.List;

/**
 * 属性值service接口
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:45
 */
public interface BoardPropertyValueService extends IService<BoardPropertyValue> {

    /**
     * 查询属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    PageResult<BoardPropertyValue> page(BoardPropertyValueParam boardPropertyValueParam);

    /**
     * 属性值列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    List<BoardPropertyValue> list(BoardPropertyValueParam boardPropertyValueParam);

    /**
     * 添加属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    void add(BoardPropertyValueParam boardPropertyValueParam);

    /**
     * 删除属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    void delete(List<BoardPropertyValueParam> boardPropertyValueParamList);

    /**
     * 编辑属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
    void edit(BoardPropertyValueParam boardPropertyValueParam);

    /**
     * 查看属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
     BoardPropertyValue detail(BoardPropertyValueParam boardPropertyValueParam);

    /**
     * 导出属性值
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:45
     */
     void export(BoardPropertyValueParam boardPropertyValueParam);

}
