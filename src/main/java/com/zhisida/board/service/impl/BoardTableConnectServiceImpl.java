
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.entity.BoardDataSource;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.entity.BoardTableConnect;
import com.zhisida.board.enums.BoardTableConnectExceptionEnum;
import com.zhisida.board.mapper.BoardTableConnectMapper;
import com.zhisida.board.param.BoardTableConnectParam;
import com.zhisida.board.service.BoardTableColumnService;
import com.zhisida.board.service.BoardTableConnectService;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 字段关联配置service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:45:42
 */
@Service
public class BoardTableConnectServiceImpl extends ServiceImpl<BoardTableConnectMapper, BoardTableConnect> implements BoardTableConnectService {
    @Resource
    BoardTableColumnService boardTableColumnService;

    @Override
    public PageResult<BoardTableConnect> page(BoardTableConnectParam boardTableConnectParam) {
        QueryWrapper<BoardTableConnect> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardTableConnectParam)) {

            // 根据关联字段ID 查询
            if (ObjectUtil.isNotEmpty(boardTableConnectParam.getTableId())) {
                QueryWrapper<BoardTableColumn> tableColumnQueryWrapper = new QueryWrapper<>();
                tableColumnQueryWrapper.lambda().eq(BoardTableColumn::getTableId, boardTableConnectParam.getTableId());
                List<BoardTableColumn> columns = boardTableColumnService.list(tableColumnQueryWrapper);
                List<Long> columnIds = columns.stream().map(e -> e.getId()).collect(Collectors.toList());
                queryWrapper.lambda().in(BoardTableConnect::getColumnId, columnIds)
                        .or().in(BoardTableConnect::getConnectColumnId, columnIds);
            }
            // 根据字段ID 查询
            if (ObjectUtil.isNotEmpty(boardTableConnectParam.getColumnId())) {
                queryWrapper.lambda().eq(BoardTableConnect::getColumnId, boardTableConnectParam.getColumnId());
            }

            // 根据关联字段ID 查询
            if (ObjectUtil.isNotEmpty(boardTableConnectParam.getConnectTableId())) {
                QueryWrapper<BoardTableColumn> tableColumnQueryWrapper = new QueryWrapper<>();
                tableColumnQueryWrapper.lambda().eq(BoardTableColumn::getTableId, boardTableConnectParam.getConnectTableId());
                List<BoardTableColumn> columns = boardTableColumnService.list(tableColumnQueryWrapper);
                List<Long> columnIds = columns.stream().map(e -> e.getId()).collect(Collectors.toList());
                queryWrapper.lambda().in(BoardTableConnect::getColumnId, columnIds)
                        .or().in(BoardTableConnect::getConnectColumnId, columnIds);
            }
            // 根据关联字段ID 查询
            if (ObjectUtil.isNotEmpty(boardTableConnectParam.getConnectColumnId())) {
                queryWrapper.lambda().eq(BoardTableConnect::getConnectColumnId, boardTableConnectParam.getConnectColumnId());
            }
            // 根据关联类型 查询
            if (ObjectUtil.isNotEmpty(boardTableConnectParam.getConnectType())) {
                queryWrapper.lambda().eq(BoardTableConnect::getConnectType, boardTableConnectParam.getConnectType());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardTableConnect> list(BoardTableConnectParam boardTableConnectParam) {
        return this.list();
    }

    @Override
    public void add(BoardTableConnectParam boardTableConnectParam) {
        BoardTableConnect boardTableConnect = new BoardTableConnect();
        BeanUtil.copyProperties(boardTableConnectParam, boardTableConnect);
        this.save(boardTableConnect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardTableConnectParam> boardTableConnectParamList) {
        boardTableConnectParamList.forEach(boardTableConnectParam -> {
            this.removeById(boardTableConnectParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardTableConnectParam boardTableConnectParam) {
        BoardTableConnect boardTableConnect = this.queryBoardTableConnect(boardTableConnectParam);
        BeanUtil.copyProperties(boardTableConnectParam, boardTableConnect);
        this.updateById(boardTableConnect);
    }

    @Override
    public BoardTableConnect detail(BoardTableConnectParam boardTableConnectParam) {
        return this.queryBoardTableConnect(boardTableConnectParam);
    }

    /**
     * 获取字段关联配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:45:42
     */
    private BoardTableConnect queryBoardTableConnect(BoardTableConnectParam boardTableConnectParam) {
        BoardTableConnect boardTableConnect = this.getById(boardTableConnectParam.getId());
        if (ObjectUtil.isNull(boardTableConnect)) {
            throw new ServiceException(BoardTableConnectExceptionEnum.NOT_EXIST);
        }
        return boardTableConnect;
    }

    @Override
    public void export(BoardTableConnectParam boardTableConnectParam) {
        List<BoardTableConnect> list = this.list(boardTableConnectParam);
        PoiUtil.exportExcelWithStream("BoardTableConnect.xls", BoardTableConnect.class, list);
    }

    @Override
    public void sync(BoardDataSource boardDataSource, List<BoardTable> boardTables) {
        String[] primaryKeyStrs = new String[]{"id", "oid"};
        if (!StringUtils.isEmpty(boardDataSource.getPrimaryKeys())) {
            primaryKeyStrs = boardDataSource.getPrimaryKeys().split(",");
        }

        String[] tablePrefixs = new String[]{};
        if (!StringUtils.isEmpty(boardDataSource.getTablePrefix())) {
            tablePrefixs = boardDataSource.getTablePrefix().split(",");
        }

        String[] tableSubfixs = new String[]{};
        if (!StringUtils.isEmpty(boardDataSource.getTableSubfix())) {
            tableSubfixs = boardDataSource.getTableSubfix().split(",");
        }

        for (BoardTable boardTable : boardTables) {
            QueryWrapper<BoardTableColumn> columnQueryWrapper = new QueryWrapper<>();
            columnQueryWrapper.lambda().in(BoardTableColumn::getColumnName, primaryKeyStrs);
            columnQueryWrapper.lambda().eq(BoardTableColumn::getTableId, boardTable.getId());
            BoardTableColumn column = boardTableColumnService.getOne(columnQueryWrapper);
            if (Objects.isNull(column) || Objects.isNull(column.getColumnName())) {
                continue;
            }
            List<String> connectColumnNames = new ArrayList<>();
            String connectTableName = boardTable.getTableName();
            String finalConnectTableName1 = connectTableName;
            connectColumnNames.addAll(Arrays.stream(primaryKeyStrs).map(e -> {
                return finalConnectTableName1 + "_" + e;
            }).collect(Collectors.toList()));
            for (String tablePrefix : tablePrefixs) {
                if (!StringUtils.isEmpty(tablePrefix) && boardTable.getTableName().startsWith(tablePrefix)) {
                    connectTableName = connectTableName.substring(tablePrefix.length());
                    if (connectTableName.startsWith("_")) {
                        connectTableName = connectTableName.substring(1);
                    }
                    if (connectTableName.endsWith("_")) {
                        connectTableName = connectTableName.substring(0, connectTableName.length() - 1);
                    }
                    String finalConnectTableName2 = connectTableName;
                    connectColumnNames.addAll(Arrays.stream(primaryKeyStrs).map(e -> {
                        return finalConnectTableName2 + "_" + e;
                    }).collect(Collectors.toList()));
                }
                for (String subPrefix : tableSubfixs) {
                    if (!StringUtils.isEmpty(subPrefix) && boardTable.getTableName().endsWith(subPrefix)) {
                        connectTableName = connectTableName.substring(0, connectTableName.length() - subPrefix.length());
                        if (connectTableName.endsWith("_")) {
                            connectTableName = connectTableName.substring(0, connectTableName.length() - 1);
                        }
                        String finalConnectTableName3 = connectTableName;
                        connectColumnNames.addAll(Arrays.stream(primaryKeyStrs).map(e -> {
                            return finalConnectTableName3 + "_" + e;
                        }).collect(Collectors.toList()));
                    }
                }

            }

            for (BoardTable connectTable : boardTables) {
                if (connectTable.getId().equals(boardTable.getId())) {
                    continue;
                }
                QueryWrapper<BoardTableColumn> connectColumnQueryWrapper = new QueryWrapper<>();
                connectColumnQueryWrapper.lambda().in(BoardTableColumn::getColumnName, connectColumnNames);
                connectColumnQueryWrapper.lambda().eq(BoardTableColumn::getTableId, connectTable.getId());
                BoardTableColumn connectColumn = boardTableColumnService.getOne(connectColumnQueryWrapper);
                if (Objects.isNull(connectColumn)) {
                    continue;
                }
                BoardTableConnect boardTableConnect = new BoardTableConnect();
                boardTableConnect.setColumnId(connectColumn.getId());
                boardTableConnect.setTableId(connectColumn.getTableId());
                boardTableConnect.setConnectColumnId(column.getId());
                boardTableConnect.setConnectTableId(column.getTableId());
                boardTableConnect.setConnectType("MANY_TO_ONE");

                QueryWrapper<BoardTableConnect> deleteWrapper = new QueryWrapper<>();
                deleteWrapper.lambda().and(wrapper -> wrapper.eq(BoardTableConnect::getTableId, boardTableConnect.getTableId()).eq(BoardTableConnect::getConnectTableId, boardTableConnect.getConnectColumnId()));
                deleteWrapper.lambda().or(wrapper -> wrapper.eq(BoardTableConnect::getConnectTableId, boardTableConnect.getTableId()).eq(BoardTableConnect::getTableId, boardTableConnect.getConnectColumnId()));
                this.remove(deleteWrapper);
                this.save(boardTableConnect);
            }
        }
    }

}
