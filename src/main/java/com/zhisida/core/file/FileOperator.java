
package com.zhisida.core.file;


import com.zhisida.core.file.common.enums.BucketAuthEnum;

import java.io.InputStream;

/**
 * 文件操纵者（内网操作）
 * <p>
 * 如果存在未包含的操作，可以调用getClient()自行获取client进行操作
 *
 * @author Young-Pastor
 */
public interface FileOperator {

    /**
     * 初始化操作的客户端
     *
     * @author Young-Pastor
     */
    void initClient();

    /**
     * 销毁操作的客户端
     *
     * @author Young-Pastor
     */
    void destroyClient();

    /**
     * 获取操作的客户端
     *
     * @author Young-Pastor
     */
    Object getClient();

    /**
     * 查询存储桶是否存在
     * <p>
     * 例如：传入参数examplebucket-1250000000，返回true代表存在此桶
     *
     * @author Young-Pastor
     */
    boolean doesBucketExist(String bucketName);

    /**
     * 设置预定义策略
     * <p>
     * 预定义策略如公有读、公有读写、私有读
     *
     * @author Young-Pastor
     */
    void setBucketAcl(String bucketName, BucketAuthEnum bucketAuthEnum);

    /**
     * 判断是否存在文件
     *
     * @param bucketName 桶名称
     * @param key        唯一标示id，例如a.txt, doc/a.txt
     * @author Young-Pastor
     */
    boolean isExistingFile(String bucketName, String key);

    /**
     * 存储文件
     *
     * @param bucketName 桶名称
     * @param key        唯一标示id，例如a.txt, doc/a.txt
     * @param bytes      文件字节数组
     * @author Young-Pastor
     */
    void storageFile(String bucketName, String key, byte[] bytes);

    /**
     * 存储文件（存放到指定的bucket里边）
     *
     * @param bucketName  桶名称
     * @param key         唯一标示id，例如a.txt, doc/a.txt
     * @param inputStream 文件流
     * @author Young-Pastor
     */
    void storageFile(String bucketName, String key, InputStream inputStream);

    /**
     * 获取某个bucket下的文件字节
     *
     * @param bucketName 桶名称
     * @param key        唯一标示id，例如a.txt, doc/a.txt
     * @author Young-Pastor
     */
    byte[] getFileBytes(String bucketName, String key);

    /**
     * 文件访问权限管理
     *
     * @param bucketName     桶名称
     * @param key            唯一标示id，例如a.txt, doc/a.txt
     * @param bucketAuthEnum 文件权限
     * @author Young-Pastor
     */
    void setFileAcl(String bucketName, String key, BucketAuthEnum bucketAuthEnum);

    /**
     * 拷贝文件
     *
     * @param originBucketName 源文件桶
     * @param originFileKey    源文件名称
     * @param newBucketName    新文件桶
     * @param newFileKey       新文件名称
     * @author Young-Pastor
     */
    void copyFile(String originBucketName, String originFileKey, String newBucketName, String newFileKey);

    /**
     * 获取文件的下载地址（带鉴权的），生成外网地址
     *
     * @param bucketName 文件桶
     * @param key        文件唯一标识
     * @author Young-Pastor
     */
    String getFileAuthUrl(String bucketName, String key, Long timeoutMillis);

    /**
     * 删除文件
     *
     * @param bucketName 文件桶
     * @param key        文件唯一标识
     * @author Young-Pastor
     */
    void deleteFile(String bucketName, String key);

}
