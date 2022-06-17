
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysFileInfo;
import com.zhisida.board.param.SysFileInfoParam;
import org.springframework.web.multipart.MultipartFile;
import com.zhisida.board.result.SysFileInfoResult;
import com.zhisida.board.result.SysOnlineFileInfoResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息表 服务类
 *
 * @author young-pastor
 */
public interface SysFileInfoService extends IService<SysFileInfo> {

    /**
     * 分页查询文件信息表
     *
     * @param sysFileInfoParam 查询参数
     * @return 查询分页结果
     * @author young-pastor
     */
    PageResult<SysFileInfo> page(SysFileInfoParam sysFileInfoParam);

    /**
     * 查询所有文件信息表
     *
     * @param sysFileInfoParam 查询参数
     * @return 文件信息列表
     * @author young-pastor
     */
    List<SysFileInfo> list(SysFileInfoParam sysFileInfoParam);

    /**
     * 添加文件信息表
     *
     * @param sysFileInfoParam 添加参数
     * @author young-pastor
     */
    void add(SysFileInfoParam sysFileInfoParam);

    /**
     * 删除文件信息表
     *
     * @param sysFileInfoParam 删除参数
     * @author young-pastor
     */
    void delete(SysFileInfoParam sysFileInfoParam);

    /**
     * 编辑文件信息表
     *
     * @param sysFileInfoParam 编辑参数
     * @author young-pastor
     */
    void edit(SysFileInfoParam sysFileInfoParam);

    /**
     * 查看详情文件信息表
     *
     * @param sysFileInfoParam 查看参数
     * @return 文件信息
     * @author young-pastor
     */
    SysFileInfo detail(SysFileInfoParam sysFileInfoParam);

    /**
     * 上传文件，返回文件的唯一标识
     *
     * @param file 要上传的文件
     * @return 文件id
     * @author young-pastor
     */
    Long uploadFile(MultipartFile file);

    /**
     * 获取文件信息结果集
     *
     * @param fileId 文件id
     * @return 文件信息结果集
     * @author young-pastor
     */
    SysFileInfoResult getFileInfoResult(Long fileId);

    /**
     * 判断文件是否存在
     *
     * @param field 文件id
     * @author young-pastor
     */
    void assertFile(Long field);

    /**
     * 文件预览
     *
     * @param sysFileInfoParam 文件预览参数
     * @param response         响应结果
     * @author young-pastor
     */
    void preview(SysFileInfoParam sysFileInfoParam, HttpServletResponse response);

    /**
     * 文件下载
     *
     * @param sysFileInfoParam 文件下载参数
     * @param response         响应结果
     * @author young-pastor
     */
    void download(SysFileInfoParam sysFileInfoParam, HttpServletResponse response);

    /**
     * 新增或编辑在线文档
     *
     * @param sysFileInfoParam 新增或编辑参数
     * @author young-pastor
     */
    SysOnlineFileInfoResult onlineAddOrUpdate(SysFileInfoParam sysFileInfoParam);

    /**
     * 在线文档编辑回调
     *
     * @author young-pastor
     */
    void track();
}
