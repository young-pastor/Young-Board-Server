
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.analysis.DataSourceProviderManager;
import com.zhisida.board.analysis.provider.DataSourceProvider;
import com.zhisida.board.entity.BoardDataSource;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.enums.BoardTableColumnExceptionEnum;
import com.zhisida.board.mapper.BoardTableColumnMapper;
import com.zhisida.board.param.BoardTableColumnParam;
import com.zhisida.board.service.BoardDataSourceService;
import com.zhisida.board.service.BoardTableColumnService;
import com.zhisida.board.service.BoardTableService;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.factory.TreeBuildFactory;
import com.zhisida.core.pojo.node.AntdBaseTreeNode;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字段配置service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:27:41
 */
@Service
public class BoardTableColumnServiceImpl extends ServiceImpl<BoardTableColumnMapper, BoardTableColumn> implements BoardTableColumnService {
    @Resource
    BoardDataSourceService boardDataSourceService;
    @Resource
    BoardTableService boardTableService;

    @Override
    public List<AntdBaseTreeNode> tree(BoardTableColumnParam boardTableColumnParam) {
        List<AntdBaseTreeNode> treeNodeList = CollectionUtil.newArrayList();
        QueryWrapper<BoardDataSource> dataSourceQueryWrapper = new QueryWrapper();
        List<BoardTable> tables = new ArrayList<>();
        if (!StringUtils.isEmpty(boardTableColumnParam.getTableId())) {
            QueryWrapper<BoardTable> tableQueryWrapper = new QueryWrapper();
            tableQueryWrapper.lambda().eq(BoardTable::getId, boardTableColumnParam.getTableId());
            BoardTable boardTable = boardTableService.getOne(tableQueryWrapper);
            if (!ObjectUtil.isEmpty(boardTable)) {
                tables.add(boardTable);
                dataSourceQueryWrapper.lambda().eq(BoardDataSource::getId, boardTable.getDataSourceId());
            }
       } else if (!StringUtils.isEmpty(boardTableColumnParam.getDataSourceId())) {
            dataSourceQueryWrapper.lambda().eq(BoardDataSource::getId, boardTableColumnParam.getDataSourceId());
        }
        List<BoardDataSource> dataSources = boardDataSourceService.list(dataSourceQueryWrapper);
        for (BoardDataSource dataSource : dataSources) {
            AntdBaseTreeNode dataSourceNode = new AntdBaseTreeNode();
            dataSourceNode.setId(dataSource.getId());
            dataSourceNode.setParentId(0l);
            dataSourceNode.setTitle(dataSource.getDisplayName());
            dataSourceNode.setValue(String.valueOf(dataSource.getId()));
            treeNodeList.add(dataSourceNode);
            if (ObjectUtil.isEmpty(tables)) {
                QueryWrapper<BoardTable> tableQueryWrapper = new QueryWrapper();
                tableQueryWrapper.lambda().eq(BoardTable::getDataSourceId, dataSource.getId());
                tables = boardTableService.list(tableQueryWrapper);
            }
            for (BoardTable table : tables){
                AntdBaseTreeNode tableNode = new AntdBaseTreeNode();
                tableNode.setId(table.getId());
                tableNode.setParentId(dataSource.getId());
                tableNode.setTitle(table.getDisplayName());
                tableNode.setValue(String.valueOf(table.getId()));
                treeNodeList.add(tableNode);
                QueryWrapper<BoardTableColumn> tableColumnQueryWrapper = new QueryWrapper();
                tableColumnQueryWrapper.lambda().eq(BoardTableColumn::getTableId, table.getId());
                List<BoardTableColumn> columns = this.list(tableColumnQueryWrapper);
                for (BoardTableColumn column : columns){
                    AntdBaseTreeNode columnNode = new AntdBaseTreeNode();
                    columnNode.setId(column.getId());
                    columnNode.setParentId(table.getId());
                    columnNode.setTitle(column.getDisplayName());
                    columnNode.setValue(String.valueOf(column.getId()));
                    treeNodeList.add(columnNode);
                }
            }
        }

        return new TreeBuildFactory<AntdBaseTreeNode>().doTreeBuild(treeNodeList);
    }

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

    @Override
    public void sync(BoardTableColumnParam boardTableColumnParam) {
        Long tableId = boardTableColumnParam.getTableId();
        BoardTable boardTable = boardTableService.getById(tableId);
        BoardDataSource boardDataSource = boardDataSourceService.getById(boardTable.getDataSourceId());
        DataSourceProvider dataProvider = DataSourceProviderManager.getDataProvider(boardDataSource);
        List<BoardTableColumn> boardTableColumns = dataProvider.queryColumns(boardTable.getTableName());
        if (!CollectionUtils.isEmpty(boardTableColumns)) {
            QueryWrapper<BoardTableColumn> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BoardTableColumn::getTableId, tableId);
            this.remove(queryWrapper);
        }
        boardTableColumns.forEach(e -> {
            e.setTableId(tableId);
        });
        this.saveBatch(boardTableColumns);
    }


}
