
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.zhisida.board.core.consts.GenConstant;
import com.zhisida.board.core.context.XnVelocityContext;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.param.XnCodeGenParam;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.StringDateUtil;
import com.zhisida.board.core.util.Util;
import com.zhisida.board.entity.SysCodeGenerate;
import com.zhisida.board.entity.SysCodeGenerateConfig;
import com.zhisida.board.enums.CodeGenerateExceptionEnum;
import com.zhisida.board.mapper.SysCodeGenerateMapper;
import com.zhisida.board.param.CodeGenerateParam;
import com.zhisida.board.param.SysCodeGenerateConfigParam;
import com.zhisida.board.result.FileContentResult;
import com.zhisida.board.result.InforMationColumnsResult;
import com.zhisida.board.result.InformationResult;
import com.zhisida.board.service.SysCodeGenerateConfigService;
import com.zhisida.board.service.SysCodeGenerateService;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成基础配置service接口实现类
 *
 * @author young-pastor
 */
@Service
public class SysCodeGenerateServiceImpl extends ServiceImpl<SysCodeGenerateMapper, SysCodeGenerate> implements SysCodeGenerateService {

    /**
     * 模板后缀
     */
    private static String TEMP_SUFFIX = ".vm";

    /**
     * 转换的编码
     */
    private static String ENCODED = "UTF-8";

    private static String SELECT_SYS_MENU_SQL = "select * from sys_menu where id = {0}";

    /**
     * 转换模板名称所需变量
     */
    private static String ADD_FORM_PAGE_NAME = "addForm.vue";
    private static String EDIT_FORM_PAGE_NAME = "editForm.vue";
    private static String INDEX_PAGE_NAME = "index.vue";
    private static String MANAGE_JS_NAME = "Manage.js";
    private static String SQL_NAME = ".sql";
    private static String JAVA_SUFFIX = ".java";
    private static String TEMP_ENTITY_NAME = "entity";

    @Resource
    private SysCodeGenerateConfigService sysCodeGenerateConfigService;

    @Override
    public PageResult<SysCodeGenerate> page(CodeGenerateParam codeGenerateParam) {
        QueryWrapper<SysCodeGenerate> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(codeGenerateParam)) {
            //根据表名模糊查询
            if (ObjectUtil.isNotEmpty(codeGenerateParam.getTableName())) {
                queryWrapper.lambda().like(SysCodeGenerate::getTableName, codeGenerateParam.getTableName());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public void add(CodeGenerateParam codeGenerateParam) {
        SysCodeGenerate sysCodeGenerate = new SysCodeGenerate();
        BeanUtil.copyProperties(codeGenerateParam, sysCodeGenerate);
        if (!vldTablePri(sysCodeGenerate.getTableName())) {
            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_TABLE_NOT_PRI);
        }
        this.save(sysCodeGenerate);

        // 加入配置表中
        codeGenerateParam.setId(sysCodeGenerate.getId());
        this.sysCodeGenerateConfigService.addList(this.getInforMationColumnsResultList(codeGenerateParam), sysCodeGenerate);
    }

    @Override
    public void delete(List<CodeGenerateParam> codeGenerateParamList) {
        codeGenerateParamList.forEach(codeGenerateParam -> {
            this.removeById(codeGenerateParam.getId());
            SysCodeGenerateConfigParam sysCodeGenerateConfigParam = new SysCodeGenerateConfigParam();
            sysCodeGenerateConfigParam.setCodeGenId(codeGenerateParam.getId());
            this.sysCodeGenerateConfigService.delete(sysCodeGenerateConfigParam);
        });
    }

    @Override
    public void edit(CodeGenerateParam codeGenerateParam) {
        SysCodeGenerate sysCodeGenerate = this.queryCodeGenerate(codeGenerateParam);
        BeanUtil.copyProperties(codeGenerateParam, sysCodeGenerate);
        if (!vldTablePri(sysCodeGenerate.getTableName())) {
            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_TABLE_NOT_PRI);
        }
        this.updateById(sysCodeGenerate);
    }

    @Override
    public SysCodeGenerate detail(CodeGenerateParam codeGenerateParam) {
        return this.queryCodeGenerate(codeGenerateParam);
    }

    /**
     * 获取代码生成基础配置
     *
     * @author young-pastor
     */
    private SysCodeGenerate queryCodeGenerate(CodeGenerateParam codeGenerateParam) {
        SysCodeGenerate sysCodeGenerate = this.getById(codeGenerateParam.getId());
        if (ObjectUtil.isNull(sysCodeGenerate)) {
            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_NOT_EXIST);
        }
        return sysCodeGenerate;
    }


    @Override
    public List<InformationResult> InformationTableList() {
        return this.baseMapper.selectInformationTable(Util.getDataBasename());
    }

    @Override
    public void runLocal(CodeGenerateParam codeGenerateParam) {
        XnCodeGenParam xnCodeGenParam = copyParams(codeGenerateParam);
        codeGenLocal(xnCodeGenParam);
    }

    @Override
    public void runDown(CodeGenerateParam codeGenerateParam, HttpServletResponse response) {
        XnCodeGenParam xnCodeGenParam = copyParams(codeGenerateParam);
        downloadCode(xnCodeGenParam, response);
    }

    @Override
    public List<FileContentResult> runFileContent(CodeGenerateParam codeGenerateParam) {
        XnCodeGenParam xnCodeGenParam = copyParams(codeGenerateParam);
        return fileContentCode(xnCodeGenParam);
    }

    /**
     * 校验表中是否包含主键
     *
     * @author young-pastor
     */
    private boolean vldTablePri(String tableName) {
        List<InforMationColumnsResult> inforMationColumnsResultList = this.baseMapper.selectInformationColumns(Util.getDataBasename(), tableName);
        for (int a = 0; a < inforMationColumnsResultList.size(); a++) {
            if (ObjectUtil.isNotNull(inforMationColumnsResultList.get(a).columnKey)
                    && inforMationColumnsResultList.get(a).columnKey.equals(GenConstant.DB_TABLE_COM_KRY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 下载方式组装代码基础
     *
     * @author young-pastor
     */
    private void downloadCode(XnCodeGenParam xnCodeGenParam, HttpServletResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        codeGenDown(xnCodeGenParam, zipOutputStream);
        IOUtils.closeQuietly(zipOutputStream);
        outputStream.toByteArray();
        try {
            Util.DownloadGen(response, outputStream.toByteArray());
        } catch (Exception e) {
            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_NOT_PATH);
        }
    }

    /**
     * 文件内容化代码基础
     *
     * @author young-pastor
     */
    private List<FileContentResult> fileContentCode(XnCodeGenParam xnCodeGenParam) {
        Util.initVelocity();
        XnVelocityContext context = new XnVelocityContext();

        List<FileContentResult> reList = new ArrayList<>();
        String[] filePath = GenConstant.xnCodeGenFilePath(xnCodeGenParam.getBusName(), xnCodeGenParam.getPackageName());
        for (int a = 0; a < filePath.length; a++) {
            String templateName = GenConstant.xnCodeGenTempFile[a];
            String fileBaseName = ResetFileBaseName(xnCodeGenParam.getClassName(),
                    templateName.substring(templateName.indexOf(GenConstant.FILE_SEP) + 1, templateName.lastIndexOf(TEMP_SUFFIX)));

            VelocityContext velContext = context.createVelContext(xnCodeGenParam);
            String tempName = GenConstant.templatePath + templateName;
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(tempName, ENCODED);
            tpl.merge(velContext, sw);

            FileContentResult fcResult = new FileContentResult(fileBaseName, sw.toString());
            reList.add(fcResult);
        }

        return reList;
    }

    /**
     * 获取表中所有字段集合
     *
     * @author young-pastor
     */
    private List<InforMationColumnsResult> getInforMationColumnsResultList(CodeGenerateParam codeGenerateParam) {
        SysCodeGenerate sysCodeGenerate = this.queryCodeGenerate(codeGenerateParam);
        return this.baseMapper.selectInformationColumns(Util.getDataBasename(), sysCodeGenerate.getTableName());
    }

    private XnCodeGenParam copyParams(CodeGenerateParam codeGenerateParam) {
        SysCodeGenerate sysCodeGenerate = this.queryCodeGenerate(codeGenerateParam);
        SysCodeGenerateConfigParam sysCodeGenerateConfigParam = new SysCodeGenerateConfigParam();
        sysCodeGenerateConfigParam.setCodeGenId(codeGenerateParam.getId());
        List<SysCodeGenerateConfig> configList = this.sysCodeGenerateConfigService.list(sysCodeGenerateConfigParam);
        XnCodeGenParam param = new XnCodeGenParam();
        BeanUtil.copyProperties(sysCodeGenerate, param);
        // 功能名
        param.setFunctionName(sysCodeGenerate.getTableComment());
        param.setConfigList(configList);
        param.setCreateTimeString(StringDateUtil.getStringDate());
        if (!sysCodeGenerate.getMenuPid().equals("0")) {
            Map<String, Object> map = SqlRunner.db().selectOne(SELECT_SYS_MENU_SQL, sysCodeGenerate.getMenuPid());
            param.setMenuPids(map.get("pids").toString());
        }
        return param;
    }

    /**
     * 本地项目生成
     */
    private void codeGenLocal(XnCodeGenParam xnCodeGenParam) {
        XnVelocityContext context = new XnVelocityContext();
        //初始化参数
        Properties properties = new Properties();
        //设置velocity资源加载方式为class
        properties.setProperty("resource.loader", "class");
        //设置velocity资源加载方式为file时的处理类
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        //实例化一个VelocityEngine对象
        VelocityEngine velocityEngine = new VelocityEngine(properties);

        String[] filePath = GenConstant.xnCodeGenFilePath(xnCodeGenParam.getBusName(), xnCodeGenParam.getPackageName());
        for (int i = 0; i < filePath.length; i++) {
            String templateName = GenConstant.xnCodeGenTempFile[i];

            String fileBaseName = ResetFileBaseName(xnCodeGenParam.getClassName(),
                    templateName.substring(templateName.indexOf(GenConstant.FILE_SEP) + 1, templateName.lastIndexOf(TEMP_SUFFIX)));
            String path = GenConstant.getLocalPath();
            // 前端VUE位置有所变化, sql同样根目录
            if (fileBaseName.contains(INDEX_PAGE_NAME) || fileBaseName.contains(ADD_FORM_PAGE_NAME) ||
                    fileBaseName.contains(EDIT_FORM_PAGE_NAME) || fileBaseName.contains(MANAGE_JS_NAME) ||
                    fileBaseName.contains(SQL_NAME)) {
                path = GenConstant.getLocalFrontPath();
            }

            File file = new File(path + filePath[i] + fileBaseName);

            //判断是否覆盖存在的文件
            if (file.exists() && !GenConstant.FLAG) {
                continue;
            }

            //获取父目录
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                Writer writer = new FileWriter(file);
                velocityEngine.mergeTemplate(GenConstant.templatePath + templateName, ENCODED, context.createVelContext(xnCodeGenParam), writer);
                writer.close();
            } catch (Exception e) {
                throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_NOT_PATH);
            }
        }
    }

    /**
     * 下载ZIP方式
     */
    private void codeGenDown(XnCodeGenParam xnCodeGenParam, ZipOutputStream zipOutputStream) {
        Util.initVelocity();
        XnVelocityContext context = new XnVelocityContext();

        String[] filePath = GenConstant.xnCodeGenFilePath(xnCodeGenParam.getBusName(), xnCodeGenParam.getPackageName());
        for (int a = 0; a < filePath.length; a++) {
            String templateName = GenConstant.xnCodeGenTempFile[a];

            String fileBaseName = ResetFileBaseName(xnCodeGenParam.getClassName(),
                    templateName.substring(templateName.indexOf(GenConstant.FILE_SEP) + 1, templateName.lastIndexOf(TEMP_SUFFIX)));
            XnZipOutputStream(context.createVelContext(xnCodeGenParam),
                    GenConstant.templatePath + templateName,
                    filePath[a] + fileBaseName,
                    zipOutputStream);
        }
    }

    /**
     * 重置文件名称
     */
    private static String ResetFileBaseName(String className, String fileName) {
        String fileBaseName = className + fileName;
        // 实体类名称单独处理
        if (fileBaseName.contains(TEMP_ENTITY_NAME)) {
            return className + JAVA_SUFFIX;
        }
        // 首页index.vue界面
        if (fileBaseName.contains(INDEX_PAGE_NAME)) {
            return INDEX_PAGE_NAME;
        }
        // 表单界面名称
        if (fileBaseName.contains(ADD_FORM_PAGE_NAME)) {
            return ADD_FORM_PAGE_NAME;
        }
        if (fileBaseName.contains(EDIT_FORM_PAGE_NAME)) {
            return EDIT_FORM_PAGE_NAME;
        }
        // js名称
        if (fileBaseName.contains(MANAGE_JS_NAME)) {
            return className.substring(0, 1).toLowerCase() + className.substring(1) + MANAGE_JS_NAME;
        }
        return fileBaseName;
    }

    /**
     * 生成ZIP
     */
    private void XnZipOutputStream(VelocityContext velContext, String tempName, String fileBaseName, ZipOutputStream zipOutputStream) {
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(tempName, ENCODED);
        tpl.merge(velContext, sw);
        try {
            // 添加到zip
            zipOutputStream.putNextEntry(new ZipEntry(fileBaseName));
            IOUtils.write(sw.toString(), zipOutputStream, ENCODED);
            IOUtils.closeQuietly(sw);
            zipOutputStream.flush();
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new ServiceException(CodeGenerateExceptionEnum.CODE_GEN_NOT_PATH);
        }
    }

}
