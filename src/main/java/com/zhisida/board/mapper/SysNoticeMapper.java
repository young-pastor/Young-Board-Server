
package com.zhisida.board.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.zhisida.board.entity.SysNotice;
import com.zhisida.board.result.SysNoticeReceiveResult;

/**
 * 系统通知公告mapper接口
 *
 * @author young-pastor
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 查询当前登陆用户已收公告
     *
     * @param page         分页参数
     * @param queryWrapper 查询参数
     * @return 查询分页结果
     * @author young-pastor
     */
    Page<SysNoticeReceiveResult> page(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);
}
