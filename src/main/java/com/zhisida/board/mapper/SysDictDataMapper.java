
package com.zhisida.board.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhisida.board.entity.SysDictData;

import java.util.List;

/**
 * 系统字典值mapper接口
 *
 * @author Young-Pastor
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 通过字典类型code获取字典编码值列表
     *
     * @param dictTypeCodes 字典类型编码集合
     * @return 字典编码值列表
     * @author Young-Pastor
     */
    List<String> getDictCodesByDictTypeCode(String[] dictTypeCodes);

}
