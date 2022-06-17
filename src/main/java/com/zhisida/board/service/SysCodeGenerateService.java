
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysCodeGenerate;
import com.zhisida.board.param.CodeGenerateParam;
import com.zhisida.board.result.FileContentResult;
import com.zhisida.board.result.InformationResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 代码生成基础配置service接口
 *
 * @author young-pastor
 */
public interface SysCodeGenerateService extends IService<SysCodeGenerate> {

    /**
     * 查询代码生成基础配置
     *
     * @author young-pastor
     */
    PageResult<SysCodeGenerate> page(CodeGenerateParam codeGenerateParam);

    /**
     * 添加查询代码生成基础配置
     *
     * @author young-pastor
     */
    void add(CodeGenerateParam codeGenerateParam);

    /**
     * 删除查询代码生成基础配置
     *
     * @author young-pastor
     */
    void delete(List<CodeGenerateParam> codeGenerateParamList);

    /**
     * 编辑查询代码生成基础配置
     *
     * @author young-pastor
     */
    void edit(CodeGenerateParam codeGenerateParam);

    /**
     * 查看查询代码生成基础配置
     *
     * @author young-pastor
     */
    SysCodeGenerate detail(CodeGenerateParam codeGenerateParam);

    /**
     * 查询当前数据库用户下的所有表
     *
     * @author young-pastor
     */
    List<InformationResult> InformationTableList ();

    /**
     * 本地生成代码
     *
     * @author young-pastor
     */
    void runLocal(CodeGenerateParam codeGenerateParam);

    /**
     * 下载zip方式
     *
     * @author young-pastor
     */
    void runDown(CodeGenerateParam codeGenerateParam, HttpServletResponse response);

    /**
     * 获取文件内容
     *
     * @author young-pastor
     */
    List<FileContentResult> runFileContent(CodeGenerateParam codeGenerateParam);
}
