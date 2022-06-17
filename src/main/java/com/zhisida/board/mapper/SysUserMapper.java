
package com.zhisida.board.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhisida.board.entity.SysUser;
import com.zhisida.board.result.SysUserResult;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户mapper接口
 *
 * @author young-pastor
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取用户分页列表
     *
     * @param page         分页参数
     * @param queryWrapper 查询参数
     * @return 查询分页结果
     * @author young-pastor
     */
    Page<SysUserResult> page(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);
}
