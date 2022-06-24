
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.entity.BoardDataSource;
import com.zhisida.board.enums.BoardDataSourceExceptionEnum;
import com.zhisida.board.mapper.BoardDataSourceMapper;
import com.zhisida.board.param.BoardDataSourceParam;
import com.zhisida.board.service.BoardDataSourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据源配置表service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-17 15:08:24
 */
@Service
public class BoardDataSourceServiceImpl extends ServiceImpl<BoardDataSourceMapper, BoardDataSource> implements BoardDataSourceService {

    @Override
    public PageResult<BoardDataSource> page(BoardDataSourceParam boardDataSourceParam) {
        QueryWrapper<BoardDataSource> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardDataSourceParam)) {

            // 根据数据源名称 查询
            if (ObjectUtil.isNotEmpty(boardDataSourceParam.getDisplayName())) {
                queryWrapper.lambda().eq(BoardDataSource::getDisplayName, boardDataSourceParam.getDisplayName());
            }
            // 根据分组 查询
            if (ObjectUtil.isNotEmpty(boardDataSourceParam.getGroup())) {
                queryWrapper.lambda().eq(BoardDataSource::getGroup, boardDataSourceParam.getGroup());
            }
            // 根据数据库配置 查询
            if (ObjectUtil.isNotEmpty(boardDataSourceParam.getConfig())) {
                queryWrapper.lambda().eq(BoardDataSource::getConfig, boardDataSourceParam.getConfig());
            }
            // 根据数据库类型 查询
            if (ObjectUtil.isNotEmpty(boardDataSourceParam.getType())) {
                queryWrapper.lambda().eq(BoardDataSource::getType, boardDataSourceParam.getType());
            }
            // 根据备注 查询
            if (ObjectUtil.isNotEmpty(boardDataSourceParam.getRemark())) {
                queryWrapper.lambda().eq(BoardDataSource::getRemark, boardDataSourceParam.getRemark());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardDataSource> list(BoardDataSourceParam boardDataSourceParam) {
        return this.list();
    }

    @Override
    public void add(BoardDataSourceParam boardDataSourceParam) {
        BoardDataSource boardDataSource = new BoardDataSource();
        BeanUtil.copyProperties(boardDataSourceParam, boardDataSource);
        this.save(boardDataSource);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardDataSourceParam> boardDataSourceParamList) {
        boardDataSourceParamList.forEach(boardDataSourceParam -> {
            this.removeById(boardDataSourceParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardDataSourceParam boardDataSourceParam) {
        BoardDataSource boardDataSource = this.queryBoardDataSource(boardDataSourceParam);
        BeanUtil.copyProperties(boardDataSourceParam, boardDataSource);
        this.updateById(boardDataSource);
    }

    @Override
    public BoardDataSource detail(BoardDataSourceParam boardDataSourceParam) {
        return this.queryBoardDataSource(boardDataSourceParam);
    }

    /**
     * 获取数据源配置表
     *
     * @author Young-Pastor
     * @date 2022-06-17 15:08:24
     */
    private BoardDataSource queryBoardDataSource(BoardDataSourceParam boardDataSourceParam) {
        BoardDataSource boardDataSource = this.getById(boardDataSourceParam.getId());
        if (ObjectUtil.isNull(boardDataSource)) {
            throw new ServiceException(BoardDataSourceExceptionEnum.NOT_EXIST);
        }
        return boardDataSource;
    }

    @Override
    public void export(BoardDataSourceParam boardDataSourceParam) {
        List<BoardDataSource> list = this.list(boardDataSourceParam);
        PoiUtil.exportExcelWithStream("BoardDataSource.xls", BoardDataSource.class, list);
    }

}
