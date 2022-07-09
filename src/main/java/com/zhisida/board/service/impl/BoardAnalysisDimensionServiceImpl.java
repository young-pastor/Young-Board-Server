package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import com.zhisida.board.entity.BoardAnalysisDimension;
import com.zhisida.board.enums.BoardAnalysisDimensionExceptionEnum;
import com.zhisida.board.mapper.BoardAnalysisDimensionMapper;
import com.zhisida.board.param.BoardAnalysisDimensionParam;
import com.zhisida.board.service.BoardAnalysisDimensionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实时分析分组属性service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:03
 */
@Service
public class BoardAnalysisDimensionServiceImpl extends ServiceImpl<BoardAnalysisDimensionMapper, BoardAnalysisDimension> implements BoardAnalysisDimensionService {

    @Override
    public PageResult<BoardAnalysisDimension> page(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        QueryWrapper<BoardAnalysisDimension> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardAnalysisDimensionParam)) {

            // 根据实时分析ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisDimensionParam.getAnalysisId())) {
                queryWrapper.lambda().eq(BoardAnalysisDimension::getAnalysisId, boardAnalysisDimensionParam.getAnalysisId());
            }
            // 根据实时分析事件ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisDimensionParam.getAnalysisEventId())) {
                queryWrapper.lambda().eq(BoardAnalysisDimension::getAnalysisEventId, boardAnalysisDimensionParam.getAnalysisEventId());
            }
            // 根据实时分析属性ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisDimensionParam.getAnalysisPropertyId())) {
                queryWrapper.lambda().eq(BoardAnalysisDimension::getAnalysisPropertyId, boardAnalysisDimensionParam.getAnalysisPropertyId());
            }
            // 根据属性ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisDimensionParam.getPropertyId())) {
                queryWrapper.lambda().eq(BoardAnalysisDimension::getPropertyId, boardAnalysisDimensionParam.getPropertyId());
            }
            // 根据排序 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisDimensionParam.getSort())) {
                queryWrapper.lambda().eq(BoardAnalysisDimension::getSort, boardAnalysisDimensionParam.getSort());
            }
            // 根据分组单位 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisDimensionParam.getUnit())) {
                queryWrapper.lambda().eq(BoardAnalysisDimension::getUnit, boardAnalysisDimensionParam.getUnit());
            }
            // 根据分组单位类型 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisDimensionParam.getUnitType())) {
                queryWrapper.lambda().eq(BoardAnalysisDimension::getUnitType, boardAnalysisDimensionParam.getUnitType());
            }
            // 根据类型 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisDimensionParam.getType())) {
                queryWrapper.lambda().eq(BoardAnalysisDimension::getType, boardAnalysisDimensionParam.getType());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardAnalysisDimension> list(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        return this.list();
    }

    @Override
    public void add(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        BoardAnalysisDimension boardAnalysisDimension = new BoardAnalysisDimension();
        BeanUtil.copyProperties(boardAnalysisDimensionParam, boardAnalysisDimension);
        this.save(boardAnalysisDimension);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardAnalysisDimensionParam> boardAnalysisDimensionParamList) {
        boardAnalysisDimensionParamList.forEach(boardAnalysisDimensionParam -> {
            this.removeById(boardAnalysisDimensionParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        BoardAnalysisDimension boardAnalysisDimension = this.queryBoardAnalysisDimension(boardAnalysisDimensionParam);
        BeanUtil.copyProperties(boardAnalysisDimensionParam, boardAnalysisDimension);
        this.updateById(boardAnalysisDimension);
    }

    @Override
    public BoardAnalysisDimension detail(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        return this.queryBoardAnalysisDimension(boardAnalysisDimensionParam);
    }

    /**
     * 获取实时分析分组属性
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:03
     */
    private BoardAnalysisDimension queryBoardAnalysisDimension(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        BoardAnalysisDimension boardAnalysisDimension = this.getById(boardAnalysisDimensionParam.getId());
        if (ObjectUtil.isNull(boardAnalysisDimension)) {
            throw new ServiceException(BoardAnalysisDimensionExceptionEnum.NOT_EXIST);
        }
        return boardAnalysisDimension;
    }

    @Override
    public void export(BoardAnalysisDimensionParam boardAnalysisDimensionParam) {
        List<BoardAnalysisDimension> list = this.list(boardAnalysisDimensionParam);
        PoiUtil.exportExcelWithStream("BoardAnalysisDimension.xls", BoardAnalysisDimension.class, list);
    }

}
