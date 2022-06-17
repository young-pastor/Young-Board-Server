
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 * @author young-pastor
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_file_info")
public class SysFileInfo extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * 文件大小信息，计算后的
     */
    private String fileSizeInfo;

    /**
     * 存储到bucket的名称（文件唯一标识id）
     */
    private String fileObjectName;

    /**
     * 存储路径
     */
    private String filePath;

}
