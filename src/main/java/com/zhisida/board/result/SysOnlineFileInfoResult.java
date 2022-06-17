package com.zhisida.board.result;

import cn.hutool.core.io.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhisida.board.core.consts.CommonConstant;
import com.zhisida.board.core.consts.SymbolConstant;
import com.zhisida.board.util.OnlineDocumentUtil;
import lombok.Data;

import java.io.File;
import java.util.HashMap;

/**
 * 在线文件信息结果集
 *
 * @author young-pastor
 */
@Data
public class SysOnlineFileInfoResult {

    public String fileId;
    public String[] history;
    public String type = "desktop";
    public String mode = "edit";
    public String documentType;
    public Document document;
    public EditorConfig editorConfig;
    public String token;

    public static class Document {
        public String title;
        public String url;
        public String fileType;
        public String key;
        public Permissions permissions;
    }

    public static class Permissions {
        public Boolean comment;
        public Boolean download;
        public Boolean edit;
        public Boolean fillForms;
        public Boolean modifyFilter;
        public Boolean modifyContentControl;
        public Boolean review;

        public Permissions(String mode, String type, Boolean canEdit) {
            comment = !mode.equals("view") && !mode.equals("fillForms") && !mode.equals("embedded") && !mode.equals("blockcontent");
            download = true;
            edit = canEdit && (mode.equals("edit") || mode.equals("filter") || mode.equals("blockcontent"));
            fillForms = !mode.equals("view") && !mode.equals("comment") && !mode.equals("embedded") && !mode.equals("blockcontent");
            modifyFilter = !mode.equals("filter");
            modifyContentControl = !mode.equals("blockcontent");
            review = mode.equals("edit") || mode.equals("review");
        }
    }

    public static class EditorConfig {
        public HashMap<String, Object> actionLink = null;
        public String mode = "edit";
        public String callbackUrl;
        public String lang = "en";
        public Integer forcesavetype = 1;
        public User user;
        public Customization customization;
        public Embedded embedded;

        public EditorConfig(String actionData) {
            if (actionData != null) {
                Gson gson = new Gson();
                actionLink = gson.fromJson(actionData, new TypeToken<HashMap<String, Object>>() { }.getType());
            }
            user = new User();
            customization = new Customization();
        }

        public void InitDesktop(String url) {
            embedded = new Embedded();
            embedded.saveUrl = url;
            embedded.embedUrl = url;
            embedded.shareUrl = url;
            embedded.toolbarDocked = "top";
        }

        public static class User {
            public String id = "-1";
            public String name = CommonConstant.UNKNOWN;
        }

        public static class Customization {
            public GoBack goback;
            public Boolean forcesave = true;
            public Customization()
            {
                goback = new GoBack();
            }

            public class GoBack {
                public String url;
            }
        }

        public static class Embedded {
            public String saveUrl;
            public String embedUrl;
            public String shareUrl;
            public String toolbarDocked;
        }
    }

    public SysOnlineFileInfoResult(String fileId, String fileOriginName, String userId, String userName) {
        if (fileOriginName == null) fileOriginName = "";
        fileOriginName = fileOriginName.trim();
        documentType = OnlineDocumentUtil.getFileType(fileOriginName).toLowerCase();
        this.fileId = fileId;
        document = new Document();
        document.title = fileOriginName;
        document.url = OnlineDocumentUtil.getFileUri(fileId, fileOriginName);
        document.fileType = FileUtil.getSuffix(fileOriginName);
        document.key = GenerateRevisionId(fileOriginName + SymbolConstant.PERIOD + new File(OnlineDocumentUtil.getStoragePath(fileId + SymbolConstant.PERIOD + FileUtil.getSuffix(fileOriginName))).lastModified());

        editorConfig = new EditorConfig(null);
        editorConfig.callbackUrl = OnlineDocumentUtil.getCallback(fileId, document.fileType);
        editorConfig.lang = "zh";

        if (userId != null) editorConfig.user.id = userId;
        if (userName != null) editorConfig.user.name = userName;

        editorConfig.customization.goback.url = "";

        changeType(mode, type);
    }

    public void changeType(String _mode, String _type) {
        if (_mode != null) mode = _mode;
        if (_type != null) type = _type;

        Boolean canEdit = OnlineDocumentUtil.getEditedSuffix().contains(SymbolConstant.PERIOD + FileUtil.getSuffix(document.title));

        editorConfig.mode = canEdit && !mode.equals("view") ? "edit" : "view";

        document.permissions = new Permissions(mode, type, canEdit);

        if (type.equals("embedded")) InitDesktop();
    }

    public static String GenerateRevisionId(String expectedKey) {
        if (expectedKey.length() > 20)
            expectedKey = Integer.toString(expectedKey.hashCode());

        String key = expectedKey.replace("[^0-9-.a-zA-Z_=]", "_");

        return key.substring(0, Math.min(key.length(), 20));
    }

    public void InitDesktop() {
        editorConfig.InitDesktop(document.url);
    }


}
