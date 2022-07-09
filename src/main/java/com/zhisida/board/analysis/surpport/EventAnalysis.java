package com.zhisida.board.analysis.surpport;

import cn.hutool.core.collection.ListUtil;
import com.zhisida.board.analysis.AliasNameUtil;
import com.zhisida.board.analysis.Analysis;
import com.zhisida.board.analysis.BoardAnalysisData;
import com.zhisida.board.analysis.DataSourceProviderManager;
import com.zhisida.board.analysis.enums.AliasNameEnum;
import com.zhisida.board.analysis.enums.DimensionTypeEnum;
import com.zhisida.board.analysis.provider.DataSourceProvider;
import com.zhisida.board.cache.BoardTableConnectCache;
import com.zhisida.board.entity.BoardDataSource;
import com.zhisida.board.param.*;
import com.zhisida.board.service.BoardDataSourceService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class EventAnalysis implements Analysis {

    @Resource
    BoardDataSourceService boardDataSourceService;

    @Resource
    BoardTableConnectCache boardTableConnectCache;

    @Override
    public void check(BoardAnalysisParam boardAnalysisParam) {
    }

    public BoardAnalysisData analysis(BoardAnalysisParam boardAnalysisParam) {
        BoardAnalysisData boardAnalysisData = new BoardAnalysisData();
        List<BoardAnalysisPropertyParam> propertyTotalList = boardAnalysisParam.getPropertyList();
        List<BoardAnalysisFilterParam> filterTotalList = boardAnalysisParam.getFilterList();
        List<BoardAnalysisDimensionParam> dimensionTotalList = boardAnalysisParam.getDimensionList();
        Assert.notEmpty(dimensionTotalList, "展示属性不能为空");
        boardAnalysisParam.getEventList().forEach(ae -> {
            //获取数据源
            Long analysisEventId = ae.getId();
            BoardEventParam event = ae.getEvent();
            Long eventId = event.getId();
            Long dataSourceId = event.getDataSourceId();
            BoardDataSource boardDataSource = boardDataSourceService.getById(dataSourceId);

            TreeSet<Long> tableIdList = new TreeSet<>();
            Map<String, String> aliasNames = new HashMap<>();
            tableIdList.add(event.getTableId());
            //筛选相关事件相关数据
            List<BoardAnalysisPropertyParam> propertyList = null;
            if (!CollectionUtils.isEmpty(propertyTotalList)) {
                propertyList = propertyTotalList.stream().filter(
                        ap -> (StringUtils.isEmpty(ap.getPropertyId())
                                || dataSourceId.equals(ap.getProperty().getDataSourceId()))
                                && analysisEventId.equals(ap.getAnalysisEventId())
                ).map(e -> {
                    e.setAliasName(AliasNameUtil.getAliasName(e.getId(), AliasNameEnum.PROPERTY, aliasNames));
                    tableIdList.add(e.getProperty().getTableId());
                    return e;
                }).collect(Collectors.toList());
            }

            List<BoardAnalysisFilterParam> filterList = null;
            if (!CollectionUtils.isEmpty(filterTotalList)) {
                filterList = filterTotalList.stream().filter(
                        af -> StringUtils.isEmpty(af.getAnalysisEventId()) ?
                                dataSourceId.equals(af.getProperty().getDataSourceId()) :
                                analysisEventId.equals(af.getAnalysisEventId()) && dataSourceId.equals(af.getProperty().getDataSourceId())
                ).map(e -> {
                    tableIdList.add(e.getProperty().getTableId());
                    return e;
                }).collect(Collectors.toList());
            }

            List<BoardAnalysisDimensionParam> dimensionList = dimensionTotalList.stream().filter(
                    ad -> StringUtils.isEmpty(ad.getAnalysisEventId()) ?
                            dataSourceId.equals(ad.getProperty().getDataSourceId()) :
                            analysisEventId.equals(ad.getAnalysisEventId()) && dataSourceId.equals(ad.getProperty().getDataSourceId())
            ).map(e -> {
                e.setAliasName(AliasNameUtil.getAliasName(e.getId(), AliasNameEnum.DIMENSION, aliasNames));
                tableIdList.add(e.getProperty().getTableId());
                return e;
            }).collect(Collectors.toList());

            //组装查询
            DataSourceProvider dataSourceProvider = DataSourceProviderManager.getDataProvider(boardDataSource);
            BoardAnalysisParam currentEventAnalysisParam = new BoardAnalysisParam();
            currentEventAnalysisParam.setEventList(ListUtil.toList(ae));
            currentEventAnalysisParam.setPropertyList(propertyList);
            currentEventAnalysisParam.setFilterList(filterList);
            currentEventAnalysisParam.setDimensionList(dimensionList);

            currentEventAnalysisParam.setTableConnectList(boardTableConnectCache.getTableConnectList(tableIdList, aliasNames));


            //查询数据
            List<Map> originDataList = dataSourceProvider.queryByAnalysisParam(currentEventAnalysisParam);

            Map resultData = boardAnalysisData.getResultData();
            Map eventData = (Map) resultData.get(analysisEventId);
            if (Objects.isNull(eventData)) {
                eventData = new HashMap();

                List displayRow = new ArrayList();
                List groupRow = new ArrayList();
                List valueCol = new ArrayList();

                eventData.put("displayRow", displayRow);
                eventData.put("groupRow", groupRow);
                eventData.put("valueCol", valueCol);

                resultData.put(analysisEventId, eventData);
            }
            List displayRow = (List) eventData.get("displayRow");
            List groupRow = (List) eventData.get("groupRow");
            List valueCol = (List) eventData.get("valueCol");
            //处理原始统计数据
            BoardAnalysisPropertyParam valProperty = propertyList.get(0);
            for (Map originData : originDataList) {
                Object seriesDimValue = null;
                List valueRow = new ArrayList();
                for (BoardAnalysisDimensionParam dimension : dimensionList) {
                    String dimAliasName = dimension.getAliasName();
                    Object dimValue = originData.get(dimAliasName);
                    if (DimensionTypeEnum.ROW.name().equals(dimension.getType())) {
                        seriesDimValue = dimValue;
                    } else {
                        valueRow.add(dimValue);
                    }
                }
                if (!displayRow.contains(seriesDimValue)) {
                    displayRow.add(seriesDimValue);
                }
                if (!groupRow.contains(valueRow)) {
                    groupRow.add(valueRow);
                }
                int posX = displayRow.indexOf(seriesDimValue);
                int posY = groupRow.indexOf(valueRow);
                List valY = null;
                if (posY >= valueCol.size()) {
                    valY = new ArrayList();
                    valueCol.add(valY);
                } else {
                    valY = (List) valueCol.get(posY);
                }

                List valX = null;
                if (posX >= valY.size()) {
                    valX = new ArrayList();
                    valY.add(valX);
                } else {
                    valX = (List) valY.get(posX);
                }
                valX.add(originData.get(valProperty.getAliasName()));
            }
        });
        return boardAnalysisData;
    }


}
