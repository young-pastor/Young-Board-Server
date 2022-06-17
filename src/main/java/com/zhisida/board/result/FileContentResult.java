
package com.zhisida.board.result;

import lombok.Data;

/**
 * 文件内容返回对象
 *
 * @author young-pastor
 */
@Data
public class FileContentResult {

    /**
     * 文件名称（字母形式的）
     */
    public String fileName;

    /**
     * 文件内容
     */
    public String fileContent;

    public FileContentResult() {

    }

    public FileContentResult(String name, String content) {
        fileName = name;
        fileContent = content;
    }

}
