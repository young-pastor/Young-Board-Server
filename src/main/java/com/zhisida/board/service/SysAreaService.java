
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.SysArea;
import com.zhisida.board.param.SysAreaParam;

import java.util.List;

/**
 * 系统字典值service接口
 *
 * @author Young-Pastor
 */
public interface SysAreaService extends IService<SysArea> {

    /**
     * 系统区域列表（树表）
     *
     * @param sysAreaParam 查询参数
     * @return 区域树表列表
     * @author Young-Pastor
     */
    List<SysArea> list(SysAreaParam sysAreaParam);
}
