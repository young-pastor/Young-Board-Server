package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.analysis.Analysis;
import com.zhisida.board.analysis.AnalysisManager;
import com.zhisida.board.analysis.BoardAnalysisData;
import com.zhisida.board.cache.*;
import com.zhisida.board.entity.BoardAnalysis;
import com.zhisida.board.enums.BoardAnalysisExceptionEnum;
import com.zhisida.board.mapper.BoardAnalysisMapper;
import com.zhisida.board.param.*;
import com.zhisida.board.service.BoardAnalysisService;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 实时分析service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-07-07 21:06:23
 */
@Service
public class BoardAnalysisServiceImpl extends ServiceImpl<BoardAnalysisMapper, BoardAnalysis> implements BoardAnalysisService {

    @Resource
    private BoardPropertyCache boardPropertyCache;

    @Resource
    private BoardEventCache boardEventCache;

    @Resource
    private BoardTableCache boardTableCache;

    @Resource
    private BoardTableColumnCache boardTableColumnCache;

    @Resource
    BoardAnalysisFilterCache boardAnalysisFilterCache;
    @Resource
    BoardAnalysisPropertyCache boardAnalysisPropertyCache;
    @Resource
    BoardAnalysisEventCache boardAnalysisEventCache;
    @Resource
    BoardAnalysisDimensionCache boardAnalysisDimensionCache;


    @Override
    public PageResult<BoardAnalysis> page(BoardAnalysisParam boardAnalysisParam) {
        QueryWrapper<BoardAnalysis> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardAnalysisParam)) {

            // 根据展示名称 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisParam.getDisplayName())) {
                queryWrapper.lambda().eq(BoardAnalysis::getDisplayName, boardAnalysisParam.getDisplayName());
            }
            // 根据类型 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisParam.getType())) {
                queryWrapper.lambda().eq(BoardAnalysis::getType, boardAnalysisParam.getType());
            }
            // 根据图标配置 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisParam.getChartConfig())) {
                queryWrapper.lambda().eq(BoardAnalysis::getChartConfig, boardAnalysisParam.getChartConfig());
            }
            // 根据条件逻辑 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisParam.getSubLogic())) {
                queryWrapper.lambda().eq(BoardAnalysis::getSubLogic, boardAnalysisParam.getSubLogic());
            }
            // 根据顺序 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisParam.getSort())) {
                queryWrapper.lambda().eq(BoardAnalysis::getSort, boardAnalysisParam.getSort());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardAnalysis> list(BoardAnalysisParam boardAnalysisParam) {
        return this.list();
    }

    @Override
    public void add(BoardAnalysisParam boardAnalysisParam) {
        BoardAnalysis boardAnalysis = new BoardAnalysis();
        BeanUtil.copyProperties(boardAnalysisParam, boardAnalysis);
        this.save(boardAnalysis);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardAnalysisParam> boardAnalysisParamList) {
        boardAnalysisParamList.forEach(boardAnalysisParam -> {
            this.removeById(boardAnalysisParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardAnalysisParam boardAnalysisParam) {
        BoardAnalysis boardAnalysis = this.queryBoardAnalysis(boardAnalysisParam);
        BeanUtil.copyProperties(boardAnalysisParam, boardAnalysis);
        this.updateById(boardAnalysis);
    }

    @Override
    public BoardAnalysis detail(BoardAnalysisParam boardAnalysisParam) {
        return this.queryBoardAnalysis(boardAnalysisParam);
    }

    /**
     * 获取实时分析
     *
     * @author Young-Pastor
     * @date 2022-07-07 21:06:23
     */
    private BoardAnalysis queryBoardAnalysis(BoardAnalysisParam boardAnalysisParam) {
        BoardAnalysis boardAnalysis = this.getById(boardAnalysisParam.getId());
        if (ObjectUtil.isNull(boardAnalysis)) {
            throw new ServiceException(BoardAnalysisExceptionEnum.NOT_EXIST);
        }
        return boardAnalysis;
    }

    @Override
    public void export(BoardAnalysisParam boardAnalysisParam) {
        List<BoardAnalysis> list = this.list(boardAnalysisParam);
        PoiUtil.exportExcelWithStream("BoardAnalysis.xls", BoardAnalysis.class, list);
    }

    public BoardAnalysisData analysisById(Long id) {
        BoardAnalysis boardAnalysis = this.getById(id);
        BoardAnalysisParam boardAnalysisParam = new BoardAnalysisParam();
        BeanUtil.copyProperties(boardAnalysis, boardAnalysisParam, true);

        Long analysisId = boardAnalysis.getId();

        List<BoardAnalysisFilterParam> filterList = boardAnalysisFilterCache.getFilterListByAnalysisId(analysisId);
        List<BoardAnalysisPropertyParam> propertyList = boardAnalysisPropertyCache.getPropertyListByAnalysisId(analysisId);
        List<BoardAnalysisEventParam> eventList = boardAnalysisEventCache.getEventListByAnalysisId(analysisId);
        List<BoardAnalysisDimensionParam> dimensionList = boardAnalysisDimensionCache.getDimensionListByAnalysisId(analysisId);

        boardAnalysisParam.setFilterList(filterList);
        boardAnalysisParam.setPropertyList(propertyList);
        boardAnalysisParam.setEventList(eventList);
        boardAnalysisParam.setDimensionList(dimensionList);

        return this.analysis(boardAnalysisParam);
    }

    @Override
    public BoardAnalysisData analysis(BoardAnalysisParam boardAnalysisParam) {
        if (Objects.isNull(boardAnalysisParam)) {
            throw new RuntimeException("");
        }
        Analysis analysis = AnalysisManager.getAnalysis(boardAnalysisParam.getType());

        //检查当前分析是否满足
        analysis.check(boardAnalysisParam);

        //初始化数据非ID数据初始化
        List<BoardAnalysisFilterParam> filterList = boardAnalysisParam.getFilterList();
        List<BoardAnalysisPropertyParam> propertyList = boardAnalysisParam.getPropertyList();
        List<BoardAnalysisEventParam> eventList = boardAnalysisParam.getEventList();
        List<BoardAnalysisDimensionParam> dimensionList = boardAnalysisParam.getDimensionList();

        filterList.stream()
                .filter(f -> !StringUtils.isEmpty(f.getPropertyId()))
                .forEach(f -> {
                    f.setProperty(this.getPropertyParamById(f.getPropertyId()));
                });
        propertyList.stream()
                .filter(f -> !StringUtils.isEmpty(f.getPropertyId()))
                .forEach(f -> {
                    f.setProperty(this.getPropertyParamById(f.getPropertyId()));
                });
        dimensionList.stream()
                .filter(f -> !StringUtils.isEmpty(f.getPropertyId()))
                .forEach(f -> {
                    f.setProperty(this.getPropertyParamById(f.getPropertyId()));
                });
        eventList.stream()
                .filter(f -> !StringUtils.isEmpty(f.getEventId()))
                .forEach(f -> {
                    f.setEvent(this.getEventParamById(f.getEventId()));
                });
        //调用分析实例
        return analysis.analysis(boardAnalysisParam);
    }


    private BoardPropertyParam getPropertyParamById(Long id) {
        BoardPropertyParam boardPropertyParam = boardPropertyCache.getPropertyParamById(id);
        if (!StringUtils.isEmpty(boardPropertyParam.getTableId())) {
            boardPropertyParam.setTable(boardTableCache.getTableParamById(boardPropertyParam.getTableId()));
        }
        if (!StringUtils.isEmpty(boardPropertyParam.getTableColumnId())) {
            boardPropertyParam.setColumn(boardTableColumnCache.getTableColumnParamById(boardPropertyParam.getTableColumnId()));
        }
        return boardPropertyParam;
    }

    private BoardEventParam getEventParamById(Long id) {
        BoardEventParam boardEventParam = boardEventCache.getEventParamById(id);
        if (!StringUtils.isEmpty(boardEventParam.getTableId())) {
            boardEventParam.setTable(boardTableCache.getTableParamById(boardEventParam.getTableId()));
        }
        if (!StringUtils.isEmpty(boardEventParam.getTableColumnId())) {
            boardEventParam.setColumn(boardTableColumnCache.getTableColumnParamById(boardEventParam.getTableColumnId()));
        }
        return boardEventParam;
    }
}
