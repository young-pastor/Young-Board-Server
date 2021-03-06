
package com.zhisida.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.core.consts.CommonConstant;
import com.zhisida.core.consts.SymbolConstant;
import com.zhisida.core.context.login.LoginContextHolder;
import com.zhisida.core.context.requestno.RequestNoContext;
import com.zhisida.core.exception.LibreOfficeException;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.file.FileOperator;
import com.zhisida.core.file.modular.local.LocalFileOperator;
import com.zhisida.core.pojo.login.SysLoginUser;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.HttpServletUtil;
import com.zhisida.core.util.LibreOfficeUtil;
import com.zhisida.config.FileConfig;
import com.zhisida.system.entity.SysFileInfo;
import com.zhisida.system.enums.SysFileInfoExceptionEnum;
import com.zhisida.system.enums.SysFileLocationEnum;
import com.zhisida.system.mapper.SysFileInfoMapper;
import com.zhisida.system.param.SysFileInfoParam;
import com.zhisida.core.util.DownloadUtil;
import com.zhisida.core.util.OnlineDocumentUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.zhisida.system.result.SysFileInfoResult;
import com.zhisida.system.result.SysOnlineFileInfoResult;
import com.zhisida.system.service.SysFileInfoService;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * ??????????????? ???????????????
 *
 * @author Young-Pastor
 */
@Service
public class SysFileInfoServiceImpl extends ServiceImpl<SysFileInfoMapper, SysFileInfo> implements SysFileInfoService {

    private static final Log log = Log.get();

    @Resource
    private FileOperator fileOperator;

    @Override
    public SysOnlineFileInfoResult onlineAddOrUpdate(SysFileInfoParam sysFileInfoParam) {
        if(fileOperator instanceof LocalFileOperator) {
            //????????????
            String fileSuffix = sysFileInfoParam.getFileSuffix();
            //????????????
            String fileOriginName = sysFileInfoParam.getFileOriginName();
            //??????id
            Long id = sysFileInfoParam.getId();
            //????????????
            if(ObjectUtil.isAllEmpty(fileSuffix, fileOriginName, id)) {
                throw new ServiceException(SysFileInfoExceptionEnum.ONLINE_EDIT_PARAM_ERROR);
            }
            //??????????????????
            SysLoginUser sysLoginUser = LoginContextHolder.me().getSysLoginUser();
            SysFileInfo sysFileInfo;
            SysOnlineFileInfoResult sysOnlineFileInfoResult;
            //??????id????????????????????????
            if(ObjectUtil.isNotEmpty(id)) {
                sysFileInfo = this.getById(id);
                sysOnlineFileInfoResult= new SysOnlineFileInfoResult(Convert.toStr(sysFileInfo.getId()), sysFileInfo.getFileOriginName(), Convert.toStr(sysLoginUser.getId()), sysLoginUser.getName());
            } else {
                //??????????????????
                Boolean sample = sysFileInfoParam.getSample();
                sysFileInfo = createDemo(fileSuffix, fileOriginName, sample, Convert.toStr(sysLoginUser.getId()), sysLoginUser.getName());
                sysOnlineFileInfoResult= new SysOnlineFileInfoResult(Convert.toStr(sysFileInfo.getId()), fileOriginName + SymbolConstant.PERIOD + fileSuffix, Convert.toStr(sysLoginUser.getId()), sysLoginUser.getName());

            }
            //??????history
            sysOnlineFileInfoResult.history = OnlineDocumentUtil.getHistory(sysOnlineFileInfoResult);
            if(ObjectUtil.isAllNotEmpty(sysFileInfoParam.getMode(), sysFileInfoParam.getType())) {
                sysOnlineFileInfoResult.changeType(sysFileInfoParam.getMode(), sysFileInfoParam.getType());
            }
            return sysOnlineFileInfoResult;
        } else {
            //???????????????????????????
            throw new ServiceException(SysFileInfoExceptionEnum.ONLINE_EDIT_SUPPORT_LOCAL_ONLY);
        }
    }

    @Override
    public PageResult<SysFileInfo> page(SysFileInfoParam sysFileInfoParam) {

        // ????????????
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();

        // ??????????????????-?????????????????????1:????????????2:????????????3:minio???4:?????????
        if (ObjectUtil.isNotNull(sysFileInfoParam)) {
            if (ObjectUtil.isNotEmpty(sysFileInfoParam.getFileLocation())) {
                queryWrapper.like(SysFileInfo::getFileLocation, sysFileInfoParam.getFileLocation());
            }

            // ??????????????????-????????????
            if (ObjectUtil.isNotEmpty(sysFileInfoParam.getFileBucket())) {
                queryWrapper.like(SysFileInfo::getFileBucket, sysFileInfoParam.getFileBucket());
            }

            // ??????????????????-??????????????????????????????????????????
            if (ObjectUtil.isNotEmpty(sysFileInfoParam.getFileOriginName())) {
                queryWrapper.like(SysFileInfo::getFileOriginName, sysFileInfoParam.getFileOriginName());
            }

            // ??????????????????
            if(ObjectUtil.isNotEmpty(sysFileInfoParam.getFileSuffix())) {
                if(sysFileInfoParam.getFileSuffix().contains(SymbolConstant.COMMA)) {
                    queryWrapper.in(SysFileInfo::getFileSuffix, Arrays.asList(sysFileInfoParam.getFileSuffix().split(SymbolConstant.COMMA)));
                } else {
                    queryWrapper.eq(SysFileInfo::getFileSuffix, sysFileInfoParam.getFileSuffix());
                }
            }
        }

        // ??????????????????
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<SysFileInfo> list(SysFileInfoParam sysFileInfoParam) {

        // ????????????
        LambdaQueryWrapper<SysFileInfo> queryWrapper = new LambdaQueryWrapper<>();

        return this.list(queryWrapper);
    }

    @Override
    public void add(SysFileInfoParam sysFileInfoParam) {

        // ???dto????????????
        SysFileInfo sysFileInfo = new SysFileInfo();
        BeanUtil.copyProperties(sysFileInfoParam, sysFileInfo);

        this.save(sysFileInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(SysFileInfoParam sysFileInfoParam) {

        // ?????????????????????
        SysFileInfo sysFileInfo = this.getById(sysFileInfoParam.getId());

        // ??????????????????
        this.removeById(sysFileInfoParam.getId());

        // ??????????????????
        this.fileOperator.deleteFile(sysFileInfo.getFileBucket(), sysFileInfo.getFileObjectName());
    }

    @Override
    public void edit(SysFileInfoParam sysFileInfoParam) {

        // ??????id????????????
        SysFileInfo sysFileInfo = this.querySysFileInfo(sysFileInfoParam);

        // ???????????????????????????
        BeanUtil.copyProperties(sysFileInfoParam, sysFileInfo);

        this.updateById(sysFileInfo);
    }

    @Override
    public SysFileInfo detail(SysFileInfoParam sysFileInfoParam) {
        return this.querySysFileInfo(sysFileInfoParam);
    }

    @Override
    public Long uploadFile(MultipartFile file) {

        // ?????????????????????id
        Long fileId = IdWorker.getId();

        // ????????????????????????
        String originalFilename = file.getOriginalFilename();

        // ??????????????????
        String fileSuffix = null;

        if (ObjectUtil.isNotEmpty(originalFilename)) {
            fileSuffix = StrUtil.subAfter(originalFilename, SymbolConstant.PERIOD, true);
        }
        // ???????????????????????????
        String finalName = fileId + SymbolConstant.PERIOD + fileSuffix;

        // ????????????
        byte[] bytes;
        try {
            bytes = file.getBytes();
            fileOperator.storageFile(FileConfig.DEFAULT_BUCKET, finalName, bytes);
        } catch (IOException e) {
            throw new ServiceException(SysFileInfoExceptionEnum.ERROR_FILE);
        }

        // ??????????????????kb
        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024))
                .setScale(0, BigDecimal.ROUND_HALF_UP));

        //????????????????????????
        String fileSizeInfo = FileUtil.readableFileSize(file.getSize());

        // ??????????????????
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setId(fileId);
        sysFileInfo.setFileLocation(SysFileLocationEnum.LOCAL.getCode());
        sysFileInfo.setFileBucket(FileConfig.DEFAULT_BUCKET);
        sysFileInfo.setFileObjectName(finalName);
        sysFileInfo.setFileOriginName(originalFilename);
        sysFileInfo.setFileSuffix(fileSuffix);
        sysFileInfo.setFileSizeKb(fileSizeKb);
        sysFileInfo.setFileSizeInfo(fileSizeInfo);
        this.save(sysFileInfo);

        // ????????????id
        return fileId;
    }

    @Override
    public SysFileInfoResult getFileInfoResult(Long fileId) {
        byte[] fileBytes;
        // ???????????????
        SysFileInfo sysFileInfo = this.getById(fileId);
        if (sysFileInfo == null) {
            throw new ServiceException(SysFileInfoExceptionEnum.NOT_EXISTED_FILE);
        }
        try {
            // ?????????????????????
            fileBytes = fileOperator.getFileBytes(FileConfig.DEFAULT_BUCKET, sysFileInfo.getFileObjectName());
        } catch (Exception e) {
            log.error(">>> ???????????????????????????????????????{}?????????????????????{}", RequestNoContext.get(), e.getMessage());
            throw new ServiceException(SysFileInfoExceptionEnum.FILE_STREAM_ERROR);
        }

        SysFileInfoResult sysFileInfoResult = new SysFileInfoResult();
        BeanUtil.copyProperties(sysFileInfo, sysFileInfoResult);
        sysFileInfoResult.setFileBytes(fileBytes);

        return sysFileInfoResult;
    }

    @Override
    public void assertFile(Long field) {
        SysFileInfo sysFileInfo = this.getById(field);
        if (ObjectUtil.isEmpty(sysFileInfo)) {
            throw new ServiceException(SysFileInfoExceptionEnum.NOT_EXISTED);
        }
    }

    @Override
    public void preview(SysFileInfoParam sysFileInfoParam, HttpServletResponse response) {

        byte[] fileBytes;
        //????????????id???????????????????????????
        SysFileInfoResult sysFileInfoResult = this.getFileInfoResult(sysFileInfoParam.getId());
        //??????????????????
        String fileSuffix = sysFileInfoResult.getFileSuffix().toLowerCase();
        //?????????????????????
        fileBytes = sysFileInfoResult.getFileBytes();
        //???????????????????????????????????????
        if (LibreOfficeUtil.isPic(fileSuffix)) {
            try {
                //??????contentType
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                //??????outputStream
                ServletOutputStream outputStream = response.getOutputStream();
                //??????
                IoUtil.write(outputStream, true, fileBytes);
            } catch (IOException e) {
                throw new ServiceException(SysFileInfoExceptionEnum.PREVIEW_ERROR_NOT_SUPPORT);
            }

        } else if (LibreOfficeUtil.isDoc(fileSuffix)) {
            try {
                //?????????????????????????????????libreoffice?????????pdf???html
                InputStream inputStream = IoUtil.toStream(fileBytes);

                //????????????contentType???word???ppt???text??????pdf???excel??????html)
                String targetContentType = LibreOfficeUtil.getTargetContentTypeBySuffix(fileSuffix);

                //??????contentType
                response.setContentType(targetContentType);

                //??????outputStream
                ServletOutputStream outputStream = response.getOutputStream();

                //??????
                LibreOfficeUtil.convertToPdf(inputStream, outputStream, fileSuffix);

                //??????
                IoUtil.write(outputStream, true, fileBytes);
            } catch (IOException e) {
                log.error(">>> ??????????????????", e.getMessage());
                throw new ServiceException(SysFileInfoExceptionEnum.PREVIEW_ERROR_NOT_SUPPORT);

            } catch (LibreOfficeException e) {
                log.error(">>> ?????????LibreOffice??????", e.getMessage());
                throw new ServiceException(SysFileInfoExceptionEnum.PREVIEW_ERROR_LIBREOFFICE);
            }

        } else {
            //?????????????????????????????????
            throw new ServiceException(SysFileInfoExceptionEnum.PREVIEW_ERROR_NOT_SUPPORT);
        }
    }

    @Override
    public void download(SysFileInfoParam sysFileInfoParam, HttpServletResponse response) {
        // ???????????????????????????
        SysFileInfoResult sysFileInfoResult = this.getFileInfoResult(sysFileInfoParam.getId());
        String fileName = sysFileInfoResult.getFileOriginName();
        byte[] fileBytes = sysFileInfoResult.getFileBytes();
        DownloadUtil.download(fileName, fileBytes, response);
    }

    /**
     * ?????????????????????
     *
     * @author Young-Pastor
     */
    private SysFileInfo querySysFileInfo(SysFileInfoParam sysFileInfoParam) {
        SysFileInfo sysFileInfo = this.getById(sysFileInfoParam.getId());
        if (ObjectUtil.isEmpty(sysFileInfo)) {
            throw new ServiceException(SysFileInfoExceptionEnum.NOT_EXISTED);
        }
        return sysFileInfo;
    }

    @Override
    public void track() {
        HttpServletRequest request = HttpServletUtil.getRequest();
        HttpServletResponse response = HttpServletUtil.getResponse();
        String fileObjectName = request.getParameter("fileObjectName");
        String id = request.getParameter("id");
        String storagePath = OnlineDocumentUtil.getStoragePath(id + SymbolConstant.PERIOD + FileUtil.getSuffix(fileObjectName));
        String body = "";
        Scanner scanner;

        try {
            scanner = new Scanner(request.getInputStream());
            scanner.useDelimiter("\\A");
            body = scanner.hasNext() ? scanner.next() : "";
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObj;

        if (body.isEmpty()) {
            log.error(">>> ????????????request???????????????");
            return;
        }

        try {
            jsonObj = JSONObject.parseObject(body);
        } catch (Exception ex) {
            log.error(">>> ????????????body???????????????");
            return;
        }

        int status = (int) jsonObj.get("status");
        String downloadUri =  (String) jsonObj.get("url");
        String changesUri = (String) jsonObj.get("changesurl");
        String key = (String) jsonObj.get("key");

        int saved = 0;
        if (status == 2 || status == 3) {
            //MustSave, Corrupted
            try {
                String histDir = OnlineDocumentUtil.getHistoryDir(OnlineDocumentUtil.getStoragePath(id));
                String versionDir =  OnlineDocumentUtil.getVersionDir(histDir, OnlineDocumentUtil.getFileVersion(histDir) + 1);
                File ver = new File(versionDir);
                File toSave = new File(storagePath);
                if (!ver.exists()) ver.mkdirs();
                toSave.renameTo(new File(versionDir + File.separator + "prev" + SymbolConstant.PERIOD + FileUtil.getSuffix(fileObjectName)));
                DownloadUtil.downloadToFile(downloadUri, toSave);
                DownloadUtil.downloadToFile(changesUri, new File(versionDir + File.separator + "diff.zip"));

                String history = (String) jsonObj.get("changeshistory");
                if (history == null && jsonObj.containsKey("history")) {
                    history = ((JSONObject) jsonObj.get("history")).toJSONString();
                }
                if (history != null && !history.isEmpty()) {
                    FileWriter fw = new FileWriter(new File(versionDir + File.separator + "changes.json"));
                    fw.write(history);
                    fw.close();
                }

                FileWriter fw = new FileWriter(new File(versionDir + File.separator + "key.txt"));
                fw.write(key);
                fw.close();
            } catch (Exception ex) {
                saved = 1;
            }
        }
        try {
            response.getWriter().write("{\"error\":" + saved + "}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     *
     * @param fileSuffix ????????????
     * @param originalFilename ??????????????????
     * @param sample ?????????????????????????????????????????????
     * @param userId ??????id
     * @param userName ????????????
     * @author Young-Pastor
     */
    public SysFileInfo createDemo(String fileSuffix, String originalFilename, Boolean sample, String userId, String userName) {
        // ??????????????????
        originalFilename = originalFilename + SymbolConstant.PERIOD + fileSuffix;
        // ????????????
        String demoName = (sample ? "sample." : "new.") + fileSuffix;
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/" + demoName);
        // ?????????????????????id
        Long fileId = IdWorker.getId();
        // ???????????????????????????
        String finalName = fileId + SymbolConstant.PERIOD + fileSuffix;
        // ?????????
        byte[] bytes = IoUtil.readBytes(stream);
        // ?????????????????????????????????
        fileOperator.storageFile(FileConfig.DEFAULT_BUCKET, finalName, bytes);
        // ?????????????????????
        createMeta(Convert.toStr(fileId), userId, userName);
        // ??????????????????kb
        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(bytes.length), BigDecimal.valueOf(1024))
                .setScale(0, BigDecimal.ROUND_HALF_UP));
        // ????????????????????????
        String fileSizeInfo = FileUtil.readableFileSize(bytes.length);
        // ??????????????????
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setId(fileId);
        sysFileInfo.setFileLocation(SysFileLocationEnum.LOCAL.getCode());
        sysFileInfo.setFileBucket(FileConfig.DEFAULT_BUCKET);
        sysFileInfo.setFileObjectName(finalName);
        sysFileInfo.setFileOriginName(originalFilename);
        sysFileInfo.setFileSuffix(fileSuffix);
        sysFileInfo.setFileSizeKb(fileSizeKb);
        sysFileInfo.setFileSizeInfo(fileSizeInfo);
        // ???????????????????????????????????????
        this.save(sysFileInfo);
        return sysFileInfo;
    }

    /**
     * ?????????????????????
     *
     * @param fileId ??????id
     * @param userId ??????id
     * @param userName ????????????
     * @author Young-Pastor
     */
    public void createMeta(String fileId, String userId, String userName) {
        // ??????????????????
        Object localClient = fileOperator.getClient();
        if(ObjectUtil.isNull(localClient)) {
            throw new ServiceException(SysFileInfoExceptionEnum.CLIENT_INIT_ERROR);
        }
        Dict localClientDict = (Dict) localClient;
        // ??????????????????????????????
        String histDir = localClientDict.getStr("currentSavePath") + File.separator + FileConfig.DEFAULT_BUCKET + File.separator + fileId + "-hist";
        if(!FileUtil.exist(histDir)) {
            // ??????????????????????????????
            File dir = new File(histDir);
            dir.mkdir();
        }
        Dict dict = new Dict();
        dict.put("created", DateUtil.now());
        dict.put("id", (userId == null || userId.isEmpty()) ? -1 : userId);
        dict.put("name", (userName == null || userName.isEmpty()) ? CommonConstant.UNKNOWN : userName);
        File metaFile = new File(histDir + File.separator + "createdInfo.json");
        FileUtil.writeString(JSONUtil.toJsonStr(dict), metaFile, Charset.defaultCharset());
    }

}
