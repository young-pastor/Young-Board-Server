
package com.zhisida.board.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.board.param.CodeGenerateParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.exception.DemoException;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.service.SysCodeGenerateService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 代码生成器
 *
 * @author Young-Pastor
 */
@RestController
public class SysCodeGenerateController {

    @Resource
    private SysCodeGenerateService sysCodeGenerateService;

    /**
     * 代码生成基础数据
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/codeGenerate/page")
    @BusinessLog(title = "代码生成配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(CodeGenerateParam codeGenerateParam) {
        return new SuccessResponseData(sysCodeGenerateService.page(codeGenerateParam));
    }

    /**
     * 代码生成基础配置保存
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/codeGenerate/add")
    @BusinessLog(title = "代码生成配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(CodeGenerateParam.add.class) CodeGenerateParam codeGenerateParam) {
        this.sysCodeGenerateService.add(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 代码生成基础配置编辑
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/codeGenerate/edit")
    @BusinessLog(title = "代码生成配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(CodeGenerateParam.add.class) CodeGenerateParam codeGenerateParam) {
        sysCodeGenerateService.edit(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 删除代码生成基础配置
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/codeGenerate/delete")
    @BusinessLog(title = "代码生成配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(CodeGenerateParam.delete.class) List<CodeGenerateParam> codeGenerateParamList) {
        sysCodeGenerateService.delete(codeGenerateParamList);
        return new SuccessResponseData();
    }

    /**
     * 查询当前数据库用户下的所有表
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/codeGenerate/InformationList")
    @BusinessLog(title = "数据库表列表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData InformationList() {
        return ResponseData.success(sysCodeGenerateService.InformationTableList());
    }

    /**
     * 代码生成基础配置生成
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/codeGenerate/runLocal")
    @BusinessLog(title = "代码生成_本地项目", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData runLocal(@RequestBody @Validated(CodeGenerateParam.detail.class) CodeGenerateParam codeGenerateParam) {
        // 演示环境开启，则不允许操作
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getDemoEnvFlag()) {
            throw new DemoException();
        }
        this.sysCodeGenerateService.runLocal(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 代码生成基础配置生成
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/codeGenerate/runDown")
    @BusinessLog(title = "代码生成_下载方式", opType = LogAnnotionOpTypeEnum.OTHER)
    public void runDown(@Validated(CodeGenerateParam.detail.class) CodeGenerateParam codeGenerateParam, HttpServletResponse response) {
        // 演示环境开启，则不允许操作
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getDemoEnvFlag()) {
            throw new DemoException();
        }
        this.sysCodeGenerateService.runDown(codeGenerateParam, response);
    }

    /**
     * 代码生成基础配置生成返回预览
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/codeGenerate/runFileContent")
    @BusinessLog(title = "代码生成_返回预览", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData runFileContent(@Validated(CodeGenerateParam.detail.class) CodeGenerateParam codeGenerateParam) {

        // 演示环境开启，则不允许操作
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getDemoEnvFlag()) {
            throw new DemoException();
        }

        return ResponseData.success(this.sysCodeGenerateService.runFileContent(codeGenerateParam));
    }

}
