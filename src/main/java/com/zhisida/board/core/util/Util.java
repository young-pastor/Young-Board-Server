
package com.zhisida.board.core.util;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.app.Velocity;
import com.zhisida.board.core.consts.CommonConstant;
import com.zhisida.board.core.context.constant.ConstantContext;
import com.zhisida.board.core.enums.DbIdEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码生成工具类
 *
 * @author young-pastor
 */
public class Util {

    /**
     * 初始化vm
     *
     * @author young-pastor
     */
    public static void initVelocity() {
        Properties properties = new Properties();
        try {
            properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
            properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
            Velocity.init(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成压缩包文件
     *
     * @author young-pastor
     */
    public static void DownloadGen(HttpServletResponse response, byte[] bytes) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"board-gen.zip\"");
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(bytes, response.getOutputStream());
    }

    /**
     * 查询某字符串第i次出现的游标
     *
     * @param string 字符串
     * @param i      第i次出现
     * @param str    子字符串
     * @author young-pastor
     */
    private static int getIndex(String string, int i, String str) {
        Matcher slashMatcher = Pattern.compile(str).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if (mIdx == i) {
                break;
            }
        }
        return slashMatcher.start();
    }

    /**
     * 获取数据库用户
     *
     * @author young-pastor
     */
    public static String getDataBasename () {
        String dataUrl = ConstantContext.me().getStr(CommonConstant.DATABASE_URL_NAME);
        String driverName = ConstantContext.me().getStr(CommonConstant.DATABASE_DRIVER_NAME);
        if (driverName.contains(DbIdEnum.MYSQL.getCode())) {
            return dataUrl.substring(getIndex(dataUrl, 3, "/") + 1, dataUrl.indexOf("?"));
        } else if (driverName.contains(DbIdEnum.ORACLE.getCode())) {
            return ConstantContext.me().getStr(CommonConstant.DATABASE_USER_NAME);
        } else {
            return "";
        }
    }

}
