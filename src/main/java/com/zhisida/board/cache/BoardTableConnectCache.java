package com.zhisida.board.cache;

import cn.hutool.core.bean.BeanUtil;
import com.zhisida.board.analysis.AliasNameUtil;
import com.zhisida.board.analysis.enums.AliasNameEnum;
import com.zhisida.board.entity.BoardTableConnect;
import com.zhisida.board.param.BoardTableConnectParam;
import com.zhisida.board.param.BoardTableParam;
import com.zhisida.board.service.BoardTableConnectService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BoardTableConnectCache {

    @Resource
    BoardTableConnectService boardTableConnectService;

    @Resource
    private BoardTableCache boardTableCache;

    @Resource
    private BoardTableColumnCache boardTableColumnCache;


    //@Cacheable(cacheNames = "Young:Board:Table:Connect:ListByTableList", key = "#tableIdList", unless = " #result == null")
    public List<BoardTableConnectParam> getTableConnectList(Set<Long> tableIdList, Map<String, String> aliasNames) {
        Assert.notEmpty(tableIdList, "表信息不能为空");

        List<BoardTableConnectParam> tableConnectList = null;
        tableIdList = tableIdList.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        //如果只有一张表不许要关联
        if (tableIdList.size() == 1) {
            tableConnectList = new ArrayList<>();
            BoardTableConnectParam boardTableConnectParam = new BoardTableConnectParam();
            boardTableConnectParam.setTableId(tableIdList.iterator().next());
            tableConnectList.add(boardTableConnectParam);
        } else {
            //两张表以上才需要关联
            tableConnectList = this.getConnectList(tableIdList);
        }
        //根据表列表查询关联表信息
        Assert.notEmpty(tableConnectList, "关联表信息不能为空");

        tableConnectList.stream().forEach(e -> {
            if (!StringUtils.isEmpty(e.getTableId())) {
                BoardTableParam connectTable = boardTableCache.getTableParamById(e.getTableId());
                connectTable.setAliasName(AliasNameUtil.getAliasName(connectTable.getId(), AliasNameEnum.TABLE, aliasNames));
                e.setTable(connectTable);

            }
            if (!StringUtils.isEmpty(e.getColumnId())) {
                e.setColumn(boardTableColumnCache.getTableColumnParamById(e.getColumnId()));
            }
            if (!StringUtils.isEmpty(e.getConnectTableId())) {
                BoardTableParam connectTable = boardTableCache.getTableParamById(e.getConnectTableId());
                connectTable.setAliasName(AliasNameUtil.getAliasName(connectTable.getId(), AliasNameEnum.TABLE, aliasNames));
                e.setConnectTable(connectTable);
            }
            if (!StringUtils.isEmpty(e.getConnectColumnId())) {
                e.setConnectColumn(boardTableColumnCache.getTableColumnParamById(e.getConnectColumnId()));
            }
        });
        return tableConnectList;
    }


    public List<BoardTableConnectParam> getConnectList(Set<Long> tableIdList) {
        List<BoardTableConnectParam> connectAllList = getDistinctList();
        Map<Long, List<BoardTableConnectParam>> nodeMap = new HashMap<>();
        connectAllList.forEach(e -> {
            List<BoardTableConnectParam> tableList = nodeMap.get(e.getTableId());
            if (tableList.isEmpty()) {
                tableList = new ArrayList<>();
                nodeMap.put(e.getTableId(), tableList);
            }
            tableList.add(e);

            List<BoardTableConnectParam> connectList = nodeMap.get(e.getConnectTableId());
            if (connectList.isEmpty()) {
                connectList = new ArrayList<>();
                nodeMap.put(e.getTableId(), connectList);
            }
            connectList.add(e);
        });
        List<List<BoardTableConnectParam>> resultList = new ArrayList<>();
        //计算路径
        for (Long tableId : tableIdList) {
            this.calculatePath(nodeMap, resultList, new ArrayList<>(), tableId, Arrays.asList(tableId), tableIdList);
        }
        return resultList.stream().sorted(Comparator.comparingInt(List::size)).findFirst().get();
    }

    void calculatePath(
            Map<Long, List<BoardTableConnectParam>> nodeMap,
            List<List<BoardTableConnectParam>> resultList,
            List<BoardTableConnectParam> curNodeList,
            Long tableId,
            List<Long> curTableIdList,
            Set<Long> targetTableIdList) {
        for (BoardTableConnectParam tableConnectParam : nodeMap.get(tableId)) {
            List<BoardTableConnectParam> nList = new ArrayList<>(curNodeList);
            nList.add(tableConnectParam);
            List<Long> cIdList = new ArrayList<>(curTableIdList);
            Long curTableId = null;
            if (tableId.equals(tableConnectParam.getTableId())) {
                curTableId = tableConnectParam.getConnectTableId();
            } else if (tableId.equals(tableConnectParam.getConnectTableId())) {
                curTableId = tableConnectParam.getTableId();
            } else {
                continue;
            }
            cIdList.add(curTableId);
            if (cIdList.containsAll(targetTableIdList)) {
                resultList.add(nList);
            } else {
                this.calculatePath(nodeMap, resultList, nList, curTableId, cIdList, targetTableIdList);
            }
        }
    }

    private List<BoardTableConnectParam> getDistinctList() {
        List<BoardTableConnect> connectAllList = boardTableConnectService.list();
        return connectAllList.stream().filter(c1 -> {
            List<BoardTableConnect> t = connectAllList.stream().filter(c2 ->
                    c2.getId() != c1.getId()
                            && (
                            (c1.getTableId() == c2.getTableId()
                                    && c1.getConnectTableId() == c2.getConnectTableId()
                                    && c1.getConnectType().equals(c2.getConnectType()))
                                    || (
                                    (("ONE_TO_MANY".equals(c1.getConnectType()) && "MANY_TO_ONE".equals(c2.getConnectType())
                                            || ("ONE_TO_MANY".equals(c2.getConnectType()) && "MANY_TO_ONE".equals(c1.getConnectType())))
                                            && c1.getTableId() == c2.getConnectTableId()
                                            && c1.getConnectTableId() == c2.getTableId())
                            )
                    )
            ).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(t)) {
                return true;
            }
            return false;
        }).map(e -> {
            BoardTableConnectParam boardTableConnectParam = new BoardTableConnectParam();
            BeanUtil.copyProperties(e, boardTableConnectParam, true);
            return boardTableConnectParam;
        }).collect(Collectors.toList());

    }
    // a
}

