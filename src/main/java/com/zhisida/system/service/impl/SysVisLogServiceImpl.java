
package com.zhisida.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import org.springframework.stereotype.Service;
import com.zhisida.system.entity.SysVisLog;
import com.zhisida.system.mapper.SysVisLogMapper;
import com.zhisida.system.param.SysVisLogParam;
import com.zhisida.system.service.SysVisLogService;

import java.util.List;

/**
 * 系统访问日志service接口实现类
 *
 * @author Young-Pastor
 */
@Service
public class SysVisLogServiceImpl extends ServiceImpl<SysVisLogMapper, SysVisLog> implements SysVisLogService {

    @Override
    public PageResult<SysVisLog> page(SysVisLogParam sysVisLogParam) {
        LambdaQueryWrapper<SysVisLog> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysVisLogParam)) {
            //根据名称模糊查询
            if (ObjectUtil.isNotEmpty(sysVisLogParam.getName())) {
                queryWrapper.like(SysVisLog::getName, sysVisLogParam.getName());
            }
            //跟据访问类型（字典 1登入 2登出）查询
            if (ObjectUtil.isNotEmpty(sysVisLogParam.getVisType())) {
                queryWrapper.eq(SysVisLog::getVisType, sysVisLogParam.getVisType());
            }
            //根据是否成功查询
            if (ObjectUtil.isNotEmpty(sysVisLogParam.getSuccess())) {
                queryWrapper.eq(SysVisLog::getSuccess, sysVisLogParam.getSuccess());
            }
            // 根据时间范围查询
            if (ObjectUtil.isAllNotEmpty(sysVisLogParam.getSearchBeginTime(), sysVisLogParam.getSearchEndTime())) {
                queryWrapper.apply("date_format (vis_time,'%Y-%m-%d') >= date_format('" + sysVisLogParam.getSearchBeginTime() + "','%Y-%m-%d')")
                        .apply("date_format (vis_time,'%Y-%m-%d') <= date_format('" + sysVisLogParam.getSearchEndTime() + "','%Y-%m-%d')");
            }
        }
        //根据访问时间倒序排列
        queryWrapper.orderByDesc(SysVisLog::getVisTime);
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public void delete() {
        this.remove(new QueryWrapper<>());
    }

    @Override
    public void export(SysVisLogParam sysVisLogParam) {
        List<SysVisLog> list = this.list();
        PoiUtil.exportExcelWithStream("SysVisLog.xls", SysVisLog.class, list);
    }
}
