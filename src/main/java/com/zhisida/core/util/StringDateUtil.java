
package com.zhisida.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间与String转换工具类
 *
 * @author Young-Pastor
 */
public class StringDateUtil {


    /**
     * 获取现在时间
     *
     * @author Young-Pastor
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
