
package com.zhisida.board.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhisida.board.entity.SysCodeGenerate;
import com.zhisida.board.result.InforMationColumnsResult;
import com.zhisida.board.result.InformationResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码生成基础配置
 *
 * @author Young-Pastor
 */
public interface SysCodeGenerateMapper extends BaseMapper<SysCodeGenerate> {

    /**
     * 查询指定库中所有表
     *
     * @author Young-Pastor
     */
    List<InformationResult> selectInformationTable(@Param("dbName") String dbName);

    /**
     * 查询指定表中所有字段
     *
     * @author Young-Pastor
     */
    List<InforMationColumnsResult> selectInformationColumns(@Param("dbName") String dbName, @Param("tableName") String tableName);
}
