
package com.zhisida.board.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.entity.SysArea;
import com.zhisida.board.param.SysAreaParam;
import com.zhisida.board.service.SysAreaService;
import org.springframework.stereotype.Service;
import com.zhisida.board.mapper.SysAreaMapper;

import java.util.List;

/**
 * 系统区域service接口实现类
 *
 * @author young-pastor
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements SysAreaService {

    @Override
    public List<SysArea> list(SysAreaParam sysAreaParam) {
        LambdaQueryWrapper<SysArea> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotNull(sysAreaParam)) {
            if(ObjectUtil.isNotEmpty(sysAreaParam.getParentCode())) {
                queryWrapper.eq(SysArea::getParentCode, sysAreaParam.getParentCode());
            } else {
                queryWrapper.eq(SysArea::getParentCode, "0");
            }
        }
        return this.list(queryWrapper);
    }
}
