
package com.zhisida.board.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.annotion.Permission;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.base.param.BaseParam;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.param.SysFileInfoParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.zhisida.board.result.SysOnlineFileInfoResult;
import com.zhisida.board.service.SysFileInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件信息表 控制器
 *
 * @author young-pastor
 */
@RestController
public class SysFileInfoController {

    @Resource
    private SysFileInfoService sysFileInfoService;

    /**
     * onlyoffice资源文件路径
     */
    public static final String ONLY_OFFICE_APP_JS_SUFFIX = "/web-apps/apps/api/documents/api.js";

    /**
     * 在线文档配置
     *
     * @author young-pastor
     */
    @GetMapping("/sysFileInfo/getOnlineFileConfig")
    public ResponseData getOnlineFileConfig(SysFileInfoParam sysFileInfoParam) {
        //生成在线文档的model
        SysOnlineFileInfoResult sysOnlineFileInfoResult = sysFileInfoService.onlineAddOrUpdate(sysFileInfoParam);
        Dict dict = Dict.create();
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        dict.put("docServiceApiUrl",  sysConfigCache.getOnlyOfficeUrl() + ONLY_OFFICE_APP_JS_SUFFIX);
        dict.put("sysOnlineFileInfoResult", sysOnlineFileInfoResult);
        return new SuccessResponseData(dict);
    }

    /**
     * 上传文件
     *
     * @author young-pastor
     */
    @PostMapping("/sysFileInfo/upload")
    @BusinessLog(title = "文件信息表_上传文件", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData upload(@RequestPart("file") MultipartFile file) {
        Long fileId = sysFileInfoService.uploadFile(file);
        return new SuccessResponseData(fileId);
    }

    /**
     * 下载文件
     *
     * @author young-pastor
     */
    @GetMapping("/sysFileInfo/download")
    @BusinessLog(title = "文件信息表_下载文件", opType = LogAnnotionOpTypeEnum.OTHER)
    public void download(@Validated(BaseParam.detail.class) SysFileInfoParam sysFileInfoParam, HttpServletResponse response) {
        sysFileInfoService.download(sysFileInfoParam, response);
    }

    /**
     * 文件预览
     *
     * @author young-pastor
     */
    @GetMapping("/sysFileInfo/preview")
    public void preview(@Validated(BaseParam.detail.class) SysFileInfoParam sysFileInfoParam, HttpServletResponse response) {
        sysFileInfoService.preview(sysFileInfoParam, response);
    }

    /**
     * 分页查询文件信息表
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysFileInfo/page")
    @BusinessLog(title = "文件信息表_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysFileInfoParam sysFileInfoParam) {
        return new SuccessResponseData(sysFileInfoService.page(sysFileInfoParam));
    }

    /**
     * 获取全部文件信息表
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysFileInfo/list")
    @BusinessLog(title = "文件信息表_查询所有", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysFileInfoParam sysFileInfoParam) {
        return new SuccessResponseData(sysFileInfoService.list(sysFileInfoParam));
    }

    /**
     * 查看详情文件信息表
     *
     * @author young-pastor
     */
    @Permission
    @GetMapping("/sysFileInfo/detail")
    @BusinessLog(title = "文件信息表_查看详情", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BaseParam.detail.class) SysFileInfoParam sysFileInfoParam) {
        return new SuccessResponseData(sysFileInfoService.detail(sysFileInfoParam));
    }

    /**
     * 删除文件信息表
     *
     * @author young-pastor
     */
    @Permission
    @PostMapping("/sysFileInfo/delete")
    @BusinessLog(title = "文件信息表_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BaseParam.delete.class) SysFileInfoParam sysFileInfoParam) {
        sysFileInfoService.delete(sysFileInfoParam);
        return new SuccessResponseData();
    }

    /**
     * 在线文档编辑回调
     *
     * @author young-pastor
     */
    @ResponseBody
    @PostMapping("/sysFileInfo/track")
    public void track() {
        sysFileInfoService.track();
    }
}
