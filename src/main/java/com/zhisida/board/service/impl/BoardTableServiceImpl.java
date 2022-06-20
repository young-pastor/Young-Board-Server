
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.enums.BoardTableExceptionEnum;
import com.zhisida.board.mapper.BoardTableMapper;
import com.zhisida.board.param.BoardTableParam;
import com.zhisida.board.service.BoardTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据表配置service接口实现类
 *
 * @author young-pastor
 * @date 2022-06-20 11:17:36
 */
@Service
public class BoardTableServiceImpl extends ServiceImpl<BoardTableMapper, BoardTable> implements BoardTableService {

    @Override
    public PageResult<BoardTable> page(BoardTableParam boardTableParam) {
        QueryWrapper<BoardTable> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardTableParam)) {

            // 根据数据源ID 查询
            if (ObjectUtil.isNotEmpty(boardTableParam.getDataSourceId())) {
                queryWrapper.lambda().eq(BoardTable::getDataSourceId, boardTableParam.getDataSourceId());
            }
            // 根据表名称 查询
            if (ObjectUtil.isNotEmpty(boardTableParam.getTableName())) {
                queryWrapper.lambda().eq(BoardTable::getTableName, boardTableParam.getTableName());
            }
            // 根据展示名称 查询
            if (ObjectUtil.isNotEmpty(boardTableParam.getDisplayName())) {
                queryWrapper.lambda().eq(BoardTable::getDisplayName, boardTableParam.getDisplayName());
            }
            // 根据刷新方式 查询
            if (ObjectUtil.isNotEmpty(boardTableParam.getRefreshType())) {
                queryWrapper.lambda().eq(BoardTable::getRefreshType, boardTableParam.getRefreshType());
            }
            // 根据备注 查询
            if (ObjectUtil.isNotEmpty(boardTableParam.getRemark())) {
                queryWrapper.lambda().eq(BoardTable::getRemark, boardTableParam.getRemark());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardTable> list(BoardTableParam boardTableParam) {
        return this.list();
    }

    @Override
    public void add(BoardTableParam boardTableParam) {
        BoardTable boardTable = new BoardTable();
        BeanUtil.copyProperties(boardTableParam, boardTable);
        this.save(boardTable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardTableParam> boardTableParamList) {
        boardTableParamList.forEach(boardTableParam -> {
            this.removeById(boardTableParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardTableParam boardTableParam) {
        BoardTable boardTable = this.queryBoardTable(boardTableParam);
        BeanUtil.copyProperties(boardTableParam, boardTable);
        this.updateById(boardTable);
    }

    @Override
    public BoardTable detail(BoardTableParam boardTableParam) {
        return this.queryBoardTable(boardTableParam);
    }

    /**
     * 获取数据表配置
     *
     * @author young-pastor
     * @date 2022-06-20 11:17:36
     */
    private BoardTable queryBoardTable(BoardTableParam boardTableParam) {
        BoardTable boardTable = this.getById(boardTableParam.getId());
        if (ObjectUtil.isNull(boardTable)) {
            throw new ServiceException(BoardTableExceptionEnum.NOT_EXIST);
        }
        return boardTable;
    }

    @Override
    public void export(BoardTableParam boardTableParam) {
        List<BoardTable> list = this.list(boardTableParam);
        PoiUtil.exportExcelWithStream("Young-BoardBoardTable.xls", BoardTable.class, list);
    }

}
