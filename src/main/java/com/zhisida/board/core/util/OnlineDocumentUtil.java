package com.zhisida.board.core.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zhisida.board.core.consts.SymbolConstant;
import com.zhisida.board.core.file.FileOperator;
import com.zhisida.board.core.file.modular.local.LocalFileOperator;
import com.zhisida.board.config.FileConfig;
import com.zhisida.board.result.SysOnlineFileInfoResult;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 在线文档相关工具类
 *
 * @author young-pastor
 */
public class OnlineDocumentUtil {

    private static final String VIEWED_SUFFIX = ".pdf|.djvu|.xps";
    private static final String EDITED_SUFFIX = ".docx|.xlsx|.csv|.pptx|.txt";
    private static final String CONVERT_SUFFIX = ".docm|.dotx|.dotm|.dot|.doc|.odt|.fodt|.ott|.xlsm|.xltx|.xltm|.xlt|.xls|.ods|.fods|.ots|.pptm|.ppt|.ppsx|.ppsm|.pps|.potx|.potm|.pot|.odp|.fodp|.otp|.rtf|.mht|.html|.htm|.epub";

    public static final List<String> DOCUMENT_SUFFIX = Arrays.asList(".doc", ".docx", ".docm", ".dot", ".dotx", ".dotm", ".odt", ".fodt", ".ott", ".rtf", ".txt", ".html", ".htm",
            ".mht", ".pdf", ".djvu", ".fb2", ".epub", ".xps");

    public static final List<String> SPREADSHEET_SUFFIX = Arrays.asList(".xls", ".xlsx", ".xlsm", ".xlt", ".xltx", ".xltm", ".ods", ".fods", ".ots", ".csv");

    public static final List<String> Presentation_SUFFIX = Arrays.asList(".pps", ".ppsx", ".ppsm",".ppt", ".pptx", ".pptm", ".pot", ".potx", ".potm", ".odp", ".fodp", ".otp");


    public static List<String> getFileSuffix() {
        List<String> res = new ArrayList<>();
        res.addAll(getViewedSuffix());
        res.addAll(getEditedSuffix());
        res.addAll(getConvertSuffix());
        return res;
    }

    public static List<String> getViewedSuffix() {
        return Arrays.asList(VIEWED_SUFFIX.split("\\|"));
    }

    public static List<String> getEditedSuffix() {
        return Arrays.asList(EDITED_SUFFIX.split("\\|"));
    }

    public static List<String> getConvertSuffix() {
        return Arrays.asList(CONVERT_SUFFIX.split("\\|"));
    }

    public static String getFileType(String fileOriginName) {
        String suffix = SymbolConstant.PERIOD + FileUtil.getSuffix(fileOriginName);
        if (DOCUMENT_SUFFIX.contains(suffix)) return "Text";
        if (SPREADSHEET_SUFFIX.contains(suffix)) return "Spreadsheet";
        if (Presentation_SUFFIX.contains(suffix)) return "Presentation";
        return "Text";
    }

    public static String getFileUri(String fileId, String fileName) {
        try {
            String filePath = "sysFileInfo/download?id="+ fileId +"&name=" + URLEncoder.encode(fileName, java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20");

            return filePath;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String getCallback(String fileId, String fileSuffix) {
        return "sysFileInfo/track?fileObjectName=" + fileId + SymbolConstant.PERIOD + fileSuffix + "&id=" + fileId;
    }

    public static String getStoragePath(String fileIdOrObjectName) {
        String directory = FilesRootPath();
        return directory + File.separator + FileConfig.DEFAULT_BUCKET + File.separator + fileIdOrObjectName;
    }

    public static String FilesRootPath() {
        LocalFileOperator localFileOperator = (LocalFileOperator) SpringUtil.getBean(FileOperator.class);
        Dict localClientDict = (Dict) localFileOperator.getClient();
        String currentSavePath = localClientDict.getStr("currentSavePath");
        File file = new File(currentSavePath);

        if (!file.exists()) {
            file.mkdirs();
        }
        return currentSavePath;
    }

    public static String[] getHistory(SysOnlineFileInfoResult sysOnlineFileInfoResult) {
        String histDir = OnlineDocumentUtil.getHistoryDir(OnlineDocumentUtil.getStoragePath(sysOnlineFileInfoResult.getFileId()));
        if (getFileVersion(histDir) > 0) {
            Integer curVer = getFileVersion(histDir);

            Set<Object> hist = new HashSet<Object>();
            Map<String, Object> histData = new HashMap<String, Object>();

            for (Integer i = 0; i <= curVer; i++) {
                Map<String, Object> obj = new HashMap<String, Object>();
                Map<String, Object> dataObj = new HashMap<String, Object>();
                String verDir = getVersionDir(histDir, i + 1);

                try {
                    String key = null;

                    key = i == curVer ? sysOnlineFileInfoResult.document.key : readFileToEnd(new File(verDir + File.separator + "key.txt"));

                    obj.put("key", key);
                    obj.put("version", i);

                    if (i == 0) {
                        String createdInfo = readFileToEnd(new File(histDir + File.separator + "createdInfo.json"));
                        JSONObject json = (JSONObject) JSONUtil.parse(createdInfo);

                        obj.put("created", json.get("created"));
                        Map<String, Object> user = new HashMap<String, Object>();
                        user.put("id", json.get("id"));
                        user.put("name", json.get("name"));
                        obj.put("user", user);
                    }

                    dataObj.put("key", key);
                    dataObj.put("url", i == curVer ? sysOnlineFileInfoResult.document.url : getStoragePath(verDir + File.separator + "prev" + FileUtil.getSuffix(sysOnlineFileInfoResult.getDocument().title)));
                    dataObj.put("version", i);

                    if (i > 0) {
                        JSONObject changes = (JSONObject) JSONUtil.parse(readFileToEnd(new File(getVersionDir(histDir, i) + File.separator + "changes.json")));
                        JSONObject change = (JSONObject) ((JSONArray) changes.get("changes")).get(0);

                        obj.put("changes", changes.get("changes"));
                        obj.put("serverVersion", changes.get("serverVersion"));
                        obj.put("created", change.get("created"));
                        obj.put("user", change.get("user"));

                        Map<String, Object> prev = (Map<String, Object>) histData.get(Integer.toString(i - 1));
                        Map<String, Object> prevInfo = new HashMap<String, Object>();
                        prevInfo.put("key", prev.get("key"));
                        prevInfo.put("url", prev.get("url"));
                        dataObj.put("previous", prevInfo);
                        dataObj.put("changesUrl", getStoragePath(getVersionDir(histDir, i) + File.separator + "diff.zip"));
                    }

                    hist.add(obj);
                    histData.put(Integer.toString(i), dataObj);

                } catch (Exception ex) {
                }
            }

            Map<String, Object> histObj = new HashMap<String, Object>();
            histObj.put("currentVersion", curVer);
            histObj.put("history", hist);

            Gson gson = new Gson();
            return new String[]{gson.toJson(histObj), gson.toJson(histData)};
        }
        return new String[]{"", ""};
    }

    public static String getHistoryDir(String storagePath) {
        return storagePath += "-hist";
    }

    public static String getVersionDir(String histPath, Integer version) {
        return histPath + File.separator + Integer.toString(version);
    }

    public static Integer getFileVersion(String historyPath) {
        File dir = new File(historyPath);

        if (!dir.exists()) return 0;

        File[] dirs = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        return dirs.length;
    }

    private static String readFileToEnd(File file) {
        String output = "";
        try {
            try(FileInputStream is = new FileInputStream(file))
            {
                Scanner scanner = new Scanner(is);
                scanner.useDelimiter("\\A");
                while (scanner.hasNext()) {
                    output += scanner.next();
                }
                scanner.close();
            }
        } catch (Exception e) { }
        return output;
    }

    public static void deleteFileHistory(String fileId) {
        // 判断文件存在不存在
        String histDir = OnlineDocumentUtil.getHistoryDir(OnlineDocumentUtil.getStoragePath(fileId));
        File hisDirFile = FileUtil.file(histDir);
        if (!FileUtil.exist(hisDirFile)) {
            return;
        }

        // 删除文件
        FileUtil.del(hisDirFile);
    }
}
