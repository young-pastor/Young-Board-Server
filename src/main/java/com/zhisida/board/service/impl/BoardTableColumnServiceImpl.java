
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.analysis.DataProviderManager;
import com.zhisida.board.analysis.provider.DataProvider;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.entity.BoardDataSource;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.enums.BoardTableColumnExceptionEnum;
import com.zhisida.board.mapper.BoardTableColumnMapper;
import com.zhisida.board.param.BoardTableColumnParam;
import com.zhisida.board.service.BoardDataSourceService;
import com.zhisida.board.service.BoardTableColumnService;
import com.zhisida.board.service.BoardTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字段配置service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:27:41
 */
@Service
public class BoardTableColumnServiceImpl extends ServiceImpl<BoardTableColumnMapper, BoardTableColumn> implements BoardTableColumnService {

    @Override
    public PageResult<BoardTableColumn> page(BoardTableColumnParam boardTableColumnParam) {
        QueryWrapper<BoardTableColumn> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardTableColumnParam)) {

            // 根据数据表ID 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getTableId())) {
                queryWrapper.lambda().eq(BoardTableColumn::getTableId, boardTableColumnParam.getTableId());
            }
            // 根据字段名称 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getColumnName())) {
                queryWrapper.lambda().eq(BoardTableColumn::getColumnName, boardTableColumnParam.getColumnName());
            }
            // 根据展示名称 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getDisplayName())) {
                queryWrapper.lambda().eq(BoardTableColumn::getDisplayName, boardTableColumnParam.getDisplayName());
            }
            // 根据刷新方式 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getRefreshType())) {
                queryWrapper.lambda().eq(BoardTableColumn::getRefreshType, boardTableColumnParam.getRefreshType());
            }
            // 根据字段类型 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getColumnType())) {
                queryWrapper.lambda().eq(BoardTableColumn::getColumnType, boardTableColumnParam.getColumnType());
            }
            // 根据数据类型 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getDataType())) {
                queryWrapper.lambda().eq(BoardTableColumn::getDataType, boardTableColumnParam.getDataType());
            }
            // 根据备注 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getRemark())) {
                queryWrapper.lambda().eq(BoardTableColumn::getRemark, boardTableColumnParam.getRemark());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardTableColumn> list(BoardTableColumnParam boardTableColumnParam) {
        QueryWrapper<BoardTableColumn> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardTableColumnParam)) {

            // 根据数据表ID 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getTableId())) {
                queryWrapper.lambda().eq(BoardTableColumn::getTableId, boardTableColumnParam.getTableId());
            }
            // 根据字段名称 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getColumnName())) {
                queryWrapper.lambda().eq(BoardTableColumn::getColumnName, boardTableColumnParam.getColumnName());
            }
            // 根据展示名称 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getDisplayName())) {
                queryWrapper.lambda().eq(BoardTableColumn::getDisplayName, boardTableColumnParam.getDisplayName());
            }
            // 根据刷新方式 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getRefreshType())) {
                queryWrapper.lambda().eq(BoardTableColumn::getRefreshType, boardTableColumnParam.getRefreshType());
            }
            // 根据字段类型 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getColumnType())) {
                queryWrapper.lambda().eq(BoardTableColumn::getColumnType, boardTableColumnParam.getColumnType());
            }
            // 根据数据类型 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getDataType())) {
                queryWrapper.lambda().eq(BoardTableColumn::getDataType, boardTableColumnParam.getDataType());
            }
            // 根据备注 查询
            if (ObjectUtil.isNotEmpty(boardTableColumnParam.getRemark())) {
                queryWrapper.lambda().eq(BoardTableColumn::getRemark, boardTableColumnParam.getRemark());
            }
        }
        return this.list(queryWrapper);
    }

    @Override
    public void add(BoardTableColumnParam boardTableColumnParam) {
        BoardTableColumn boardTableColumn = new BoardTableColumn();
        BeanUtil.copyProperties(boardTableColumnParam, boardTableColumn);
        this.save(boardTableColumn);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardTableColumnParam> boardTableColumnParamList) {
        boardTableColumnParamList.forEach(boardTableColumnParam -> {
            this.removeById(boardTableColumnParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardTableColumnParam boardTableColumnParam) {
        BoardTableColumn boardTableColumn = this.queryBoardTableColumn(boardTableColumnParam);
        BeanUtil.copyProperties(boardTableColumnParam, boardTableColumn);
        this.updateById(boardTableColumn);
    }

    @Override
    public BoardTableColumn detail(BoardTableColumnParam boardTableColumnParam) {
        return this.queryBoardTableColumn(boardTableColumnParam);
    }

    /**
     * 获取数据字段配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:27:41
     */
    private BoardTableColumn queryBoardTableColumn(BoardTableColumnParam boardTableColumnParam) {
        BoardTableColumn boardTableColumn = this.getById(boardTableColumnParam.getId());
        if (ObjectUtil.isNull(boardTableColumn)) {
            throw new ServiceException(BoardTableColumnExceptionEnum.NOT_EXIST);
        }
        return boardTableColumn;
    }

    @Override
    public void export(BoardTableColumnParam boardTableColumnParam) {
        List<BoardTableColumn> list = this.list(boardTableColumnParam);
        PoiUtil.exportExcelWithStream("BoardTableColumn.xls", BoardTableColumn.class, list);
    }

    @Override
    public void deleteByBoardTableIds(List<Long> oldTableIds) {
        QueryWrapper<BoardTableColumn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BoardTableColumn::getTableId, oldTableIds);
        this.remove(queryWrapper);
    }

    @Resource
    BoardDataSourceService boardDataSourceService;

    @Resource
    BoardTableService boardTableService;

    @Override
    public void sync(BoardTableColumnParam boardTableColumnParam) {
        Long tableId = boardTableColumnParam.getTableId();
        BoardTable boardTable = boardTableService.getById(tableId);
        BoardDataSource boardDataSource = boardDataSourceService.getById(boardTable.getDataSourceId());
        DataProvider dataProvider = DataProviderManager.getDataProviderByType(boardDataSource);
        List<BoardTableColumn> boardTableColumns = dataProvider.queryColumns(boardTable.getTableName());
        if(!CollectionUtils.isEmpty(boardTableColumns)){
            QueryWrapper<BoardTableColumn> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BoardTableColumn::getTableId,tableId);
            this.remove(queryWrapper);
        }
        boardTableColumns.forEach(e -> {
            e.setTableId(tableId);
        });
        this.saveBatch(boardTableColumns);
    }

}
