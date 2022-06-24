
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.BoardTableConnect;
import com.zhisida.board.param.BoardTableConnectParam;

import java.util.List;

/**
 * 字段关联配置service接口
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:45:42
 */
public interface BoardTableConnectService extends IService<BoardTableConnect> {

    /**
     * 查询字段关联配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:45:42
     */
    PageResult<BoardTableConnect> page(BoardTableConnectParam boardTableConnectParam);

    /**
     * 字段关联配置列表
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:45:42
     */
    List<BoardTableConnect> list(BoardTableConnectParam boardTableConnectParam);

    /**
     * 添加字段关联配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:45:42
     */
    void add(BoardTableConnectParam boardTableConnectParam);

    /**
     * 删除字段关联配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:45:42
     */
    void delete(List<BoardTableConnectParam> boardTableConnectParamList);

    /**
     * 编辑字段关联配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:45:42
     */
    void edit(BoardTableConnectParam boardTableConnectParam);

    /**
     * 查看字段关联配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:45:42
     */
     BoardTableConnect detail(BoardTableConnectParam boardTableConnectParam);

    /**
     * 导出字段关联配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:45:42
     */
     void export(BoardTableConnectParam boardTableConnectParam);

}
