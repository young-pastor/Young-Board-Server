
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.analysis.DataSourceProviderManager;
import com.zhisida.board.analysis.provider.DataSourceProvider;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import com.zhisida.board.entity.BoardDataSource;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.enums.BoardTableExceptionEnum;
import com.zhisida.board.mapper.BoardTableMapper;
import com.zhisida.board.param.BoardTableParam;
import com.zhisida.board.service.BoardDataSourceService;
import com.zhisida.board.service.BoardTableColumnService;
import com.zhisida.board.service.BoardTableConnectService;
import com.zhisida.board.service.BoardTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据表配置service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:17:36
 */
@Service
public class BoardTableServiceImpl extends ServiceImpl<BoardTableMapper, BoardTable> implements BoardTableService {

    @Resource
    private BoardDataSourceService boardDataSourceService;

    @Resource
    private BoardTableColumnService boardTableColumnService;

    @Resource
    private BoardTableConnectService boardTableConnectService;

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
     * @author Young-Pastor
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
        PoiUtil.exportExcelWithStream("BoardTable.xls", BoardTable.class, list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sync(BoardTableParam boardTableParam) {
        if (!Boolean.TRUE.equals(boardTableParam.getSyncTable())
                && !Boolean.TRUE.equals(boardTableParam.getSyncColumn())
                && !Boolean.TRUE.equals(boardTableParam.getSyncConnect())) {
            return;
        }

        List<BoardDataSource> boardDataSources = null;
        List<Long> boardDataSourceIds = new ArrayList<>();
        QueryWrapper<BoardTable> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(boardTableParam.getDataSourceId())) {
            boardDataSourceIds.add(boardTableParam.getDataSourceId());
            if (!CollectionUtils.isEmpty(boardDataSourceIds)) {
                boardDataSources = boardDataSourceService.listByIds(boardDataSourceIds);
                queryWrapper.lambda().in(BoardTable::getDataSourceId, boardDataSourceIds);
            }
        }
        if (CollectionUtils.isEmpty(boardDataSources)) {
            boardDataSources = boardDataSourceService.list();
        }
        if (!CollectionUtils.isEmpty(boardDataSources)) {
            List<BoardTable> oldTables = this.list(queryWrapper);
            if (!CollectionUtils.isEmpty(oldTables)) {
                List<Long> oldTableIds = new ArrayList<>();
                for (BoardTable boardTable : oldTables) {
                    oldTableIds.add(boardTable.getId());
                }
            }

            for (BoardDataSource boardDataSource : boardDataSources) {
                DataSourceProvider dataProvider = DataSourceProviderManager.getDataProvider(boardDataSource);
                List<BoardTable> boardTables = dataProvider.queryTables();
                if (!CollectionUtils.isEmpty(boardTables)) {
                    if (Boolean.TRUE.equals(boardTableParam.getSyncTable())
                            || Boolean.TRUE.equals(boardTableParam.getSyncColumn())) {
                        boardTables.forEach((e) -> {
                            e.setDataSourceId(boardDataSource.getId());
                            QueryWrapper<BoardTable> existTableWrapper = new QueryWrapper<>();
                            existTableWrapper.lambda().eq(BoardTable::getDataSourceId, e.getDataSourceId());
                            existTableWrapper.lambda().eq(BoardTable::getTableName, e.getTableName());
                            BoardTable historyTable = this.getOne(existTableWrapper);
                            if (!Objects.isNull(historyTable)) {
                                e.setId(historyTable.getId());
                            }
                            if (Boolean.TRUE.equals(boardTableParam.getSyncTable())) {
                                this.saveOrUpdate(e);
                            }
                            if (Boolean.TRUE.equals(boardTableParam.getSyncColumn())) {
                                List<BoardTableColumn> boardTableColumns = dataProvider.queryColumns(e.getTableName());
                                if (!CollectionUtils.isEmpty(boardTableColumns)) {
                                    for (BoardTableColumn boardTableColumn : boardTableColumns) {
                                        boardTableColumn.setTableId(e.getId());
                                        QueryWrapper<BoardTableColumn> existColumnWrapper = new QueryWrapper<>();
                                        existColumnWrapper.lambda().eq(BoardTableColumn::getTableId, e.getId());
                                        existColumnWrapper.lambda().eq(BoardTableColumn::getColumnName, boardTableColumn.getColumnName());
                                        BoardTableColumn historyColumn = boardTableColumnService.getOne(existColumnWrapper);
                                        if (!Objects.isNull(historyColumn)) {
                                            boardTableColumn.setId(historyColumn.getId());
                                        }
                                        boardTableColumnService.saveOrUpdate(boardTableColumn);
                                    }
                                }
                            }
                        });
                    }
                    if (Boolean.TRUE.equals(boardTableParam.getSyncConnect())
                            && boardTables.size() > 1) {
                        boardTableConnectService.sync(boardDataSource, boardTables);
                    }
                }
            }
        }
    }
}
