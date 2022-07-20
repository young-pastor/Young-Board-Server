package com.zhisida.board.analysis.surpport;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.RandomUtil;
import com.zhisida.board.analysis.AliasNameUtil;
import com.zhisida.board.analysis.Analysis;
import com.zhisida.board.analysis.BoardAnalysisData;
import com.zhisida.board.analysis.DataSourceProviderManager;
import com.zhisida.board.analysis.enums.AliasNameEnum;
import com.zhisida.board.analysis.enums.DimensionTypeEnum;
import com.zhisida.board.analysis.enums.IndicatorMeasureEnum;
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
        boardAnalysisParam.getEventList().stream().filter(e->Objects.nonNull(e.getEventId())).forEach((ae) -> {
            //获取数据源
            Long analysisEventId = ae.getId();
            String analysisEventName = this.getEventAnalysisDisplayName(ae);
            BoardEventParam event = ae.getEvent();

            Long dataSourceId = event.getDataSourceId();

            HashSet<Long> tableIdList = new HashSet<>();
            Map<String, String> aliasNames = new HashMap<>();
            tableIdList.add(event.getTableId());

            List<BoardAnalysisPropertyParam> propertyList = new ArrayList<>();
            if (Objects.nonNull(ae.getProperty())) {
                ae.getProperty().setAliasName(AliasNameUtil.getAliasName(RandomUtil.randomLong(), AliasNameEnum.PROPERTY, aliasNames));
                tableIdList.add(ae.getProperty().getProperty().getTableId());
                propertyList.add(ae.getProperty());
            }

            List<BoardAnalysisFilterParam> filterList = null;
            if (!CollectionUtils.isEmpty(ae.getFilterList())) {
                filterList = ae.getFilterList().stream().filter(e -> Objects.nonNull(e.getPropertyId())).map(e -> {
                    tableIdList.add(e.getProperty().getTableId());
                    return e;
                }).collect(Collectors.toList());
            }

            //筛选相关事件相关数据
            if (!CollectionUtils.isEmpty(propertyTotalList)) {
                propertyList = propertyTotalList.stream().filter(
                        ap -> (analysisEventId.equals(ap.getAnalysisEventId())
                                || dataSourceId.equals(ap.getProperty().getDataSourceId()))

                ).map(e -> {
                    e.setAliasName(AliasNameUtil.getAliasName(RandomUtil.randomLong(), AliasNameEnum.PROPERTY, aliasNames));
                    tableIdList.add(e.getProperty().getTableId());
                    return e;
                }).collect(Collectors.toList());
            }

            if (!CollectionUtils.isEmpty(filterTotalList)) {
                List<BoardAnalysisFilterParam> analysisFilterList = filterTotalList.stream()
                        .filter(e -> Objects.nonNull(e.getPropertyId())).filter(
                        af -> StringUtils.isEmpty(af.getAnalysisEventId()) ?
                                dataSourceId.equals(af.getProperty().getDataSourceId()) :
                                analysisEventId.equals(af.getAnalysisEventId()) && dataSourceId.equals(af.getProperty().getDataSourceId())
                ).map(e -> {
                    tableIdList.add(e.getProperty().getTableId());
                    return e;
                }).collect(Collectors.toList());
                if (Objects.isNull(filterList)) {
                    filterList = new ArrayList<>();
                }
                filterList.addAll(analysisFilterList);
            }

            List<BoardAnalysisDimensionParam> dimensionList = dimensionTotalList.stream().filter(e -> Objects.nonNull(e.getPropertyId())).filter(
                    ad -> dataSourceId.equals(ad.getProperty().getDataSourceId())
            ).map(e -> {
                e.setAliasName(AliasNameUtil.getAliasName(RandomUtil.randomLong(), AliasNameEnum.DIMENSION, aliasNames));
                tableIdList.add(e.getProperty().getTableId());
                return e;
            }).collect(Collectors.toList());

            //组装查询
            BoardAnalysisParam currentEventAnalysisParam = new BoardAnalysisParam();
            currentEventAnalysisParam.setEventList(ListUtil.toList(ae));
            currentEventAnalysisParam.setPropertyList(propertyList);
            currentEventAnalysisParam.setFilterList(filterList);
            currentEventAnalysisParam.setDimensionList(dimensionList);

            //查询数据
            BoardDataSource boardDataSource = boardDataSourceService.getById(dataSourceId);
            DataSourceProvider dataSourceProvider = DataSourceProviderManager.getDataProvider(boardDataSource);
            currentEventAnalysisParam.setTableConnectList(boardTableConnectCache.getTableConnectList(tableIdList, aliasNames));
            List<Map> originDataList = dataSourceProvider.queryByAnalysisParam(currentEventAnalysisParam);

            Map eventData = boardAnalysisData.getResultData();
            if (Objects.isNull(eventData)) {
                eventData = new HashMap();
            }
            List displayRow = (List) eventData.get("displayRow");
            if (Objects.isNull(displayRow)) {
                displayRow = new ArrayList();
                eventData.put("displayRow", displayRow);
            }
            List groupRow = (List) eventData.get("groupRow");
            if (Objects.isNull(groupRow)) {
                groupRow = new ArrayList();
                eventData.put("groupRow", groupRow);
            }
            List valueCol = (List) eventData.get("valueCol");
            if (Objects.isNull(valueCol)) {
                valueCol = new ArrayList();
                eventData.put("valueCol", valueCol);
            }

            //处理原始统计数据
            BoardAnalysisPropertyParam valProperty = ae.getProperty();
            for (Map originData : originDataList) {
                Object seriesDimValue = null;
                List groupRows = new ArrayList();
                groupRows.add(analysisEventName);
                for (BoardAnalysisDimensionParam dimension : dimensionList) {
                    String dimAliasName = dimension.getAliasName();
                    Object dimValue = originData.get(dimAliasName);
                    if (DimensionTypeEnum.ROW.name().equals(dimension.getType())) {
                        seriesDimValue = dimValue;
                    } else {
                        groupRows.add(dimValue);
                    }
                }
                if (!displayRow.contains(seriesDimValue)) {
                    displayRow.add(seriesDimValue);
                }
                if (!groupRow.contains(groupRows)) {
                    groupRow.add(groupRows);
                }
                int posX = displayRow.indexOf(seriesDimValue);
                int posY = groupRow.indexOf(groupRows);
                List valY = null;
                if (posY >= valueCol.size()) {
                    valY = new ArrayList();
                    valueCol.add(valY);
                } else {
                    valY = (List) valueCol.get(posY);
                }
                for (int i = valY.size(); i < posX; i++) {
                    valY.add(i, null);
                }
                valY.add(posX, originData.get(valProperty.getAliasName()));
            }
        });
        return boardAnalysisData;
    }

    private String getEventAnalysisDisplayName(BoardAnalysisEventParam eventParam) {
        if (StringUtils.hasLength(eventParam.getDisplayName())) {
            return eventParam.getDisplayName();
        }
        String displayName = eventParam.getEvent().getDisplayName();
        displayName += "的";
        BoardAnalysisPropertyParam analysisProperty = eventParam.getProperty();
        if (StringUtils.hasLength(analysisProperty.getDisplayName())) {
            displayName += analysisProperty.getDisplayName();
            if (StringUtils.hasLength(analysisProperty.getMeasure())) {
                displayName += IndicatorMeasureEnum.valueOf(analysisProperty.getMeasure()).getDescription();
            }
        } else if (Objects.nonNull(analysisProperty.getProperty()) && Objects.nonNull(analysisProperty.getProperty().getId())) {
            BoardPropertyParam property = analysisProperty.getProperty();
            if (StringUtils.hasLength(property.getDisplayName())) {
                displayName += property.getDisplayName();
            }
            if (StringUtils.hasLength(analysisProperty.getMeasure())) {
                displayName += IndicatorMeasureEnum.valueOf(analysisProperty.getMeasure()).getDescription();
            }
        } else {
            displayName += "总次数";
        }
        return displayName;
    }

}
