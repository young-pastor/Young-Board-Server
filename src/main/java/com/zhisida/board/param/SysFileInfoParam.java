
package com.zhisida.board.param;

import com.zhisida.board.core.pojo.base.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 * @author young-pastor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFileInfoParam extends BaseParam {

    /**
     * 主键id
     */
    @NotNull(message = "id不能为空，请检查id参数", groups = {delete.class, detail.class})
    private Long id;

    /**
     * 文件存储位置（1:阿里云，2:腾讯云，3:minio，4:本地）
     */
    private Integer fileLocation;

    /**
     * 文件仓库
     */
    private String fileBucket;

    /**
     * 文件名称（上传时候的文件名）
     */
    private String fileOriginName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小kb
     */
    private Long fileSizeKb;

    /**
     * 存储到bucket的名称（文件唯一标识id）
     */
    @NotEmpty(message = "存储到bucket的名称不能为空，请检查fileObjectName参数", groups = {trace.class})
    private String fileObjectName;

    /**
     * 存储路径
     */
    private String filePath;

    /**
     * 文档创建时是否创建相同文件内容的模板文件
     */
    private Boolean sample = false;

    /**
     * 模式：编辑edit 查看view
     */
    private String mode;

    /**
     * 类型：桌面desktop 手机mobile
     */
    private String type;

}
