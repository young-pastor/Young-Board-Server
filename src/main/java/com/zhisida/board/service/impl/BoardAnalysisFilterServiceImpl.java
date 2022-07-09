
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.entity.BoardAnalysisFilter;
import com.zhisida.board.enums.BoardAnalysisFilterExceptionEnum;
import com.zhisida.board.mapper.BoardAnalysisFilterMapper;
import com.zhisida.board.param.BoardAnalysisFilterParam;
import com.zhisida.board.service.BoardAnalysisFilterService;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实时分析条件service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:20:35
 */
@Service
public class BoardAnalysisFilterServiceImpl extends ServiceImpl<BoardAnalysisFilterMapper, BoardAnalysisFilter> implements BoardAnalysisFilterService {

    @Override
    public PageResult<BoardAnalysisFilter> page(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        QueryWrapper<BoardAnalysisFilter> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardAnalysisFilterParam)) {

            // 根据父级条件ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisFilterParam.getParentId())) {
                queryWrapper.lambda().eq(BoardAnalysisFilter::getParentId, boardAnalysisFilterParam.getParentId());
            }
            // 根据分析事件ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisFilterParam.getAnalysisEventId())) {
                queryWrapper.lambda().eq(BoardAnalysisFilter::getAnalysisEventId, boardAnalysisFilterParam.getAnalysisEventId());
            }
            // 根据分析属性ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisFilterParam.getAnalysisPropertyId())) {
                queryWrapper.lambda().eq(BoardAnalysisFilter::getAnalysisPropertyId, boardAnalysisFilterParam.getAnalysisPropertyId());
            }
            // 根据实时分析事件ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisFilterParam.getAnalysisId())) {
                queryWrapper.lambda().eq(BoardAnalysisFilter::getAnalysisId, boardAnalysisFilterParam.getAnalysisId());
            }
            // 根据属性ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisFilterParam.getPropertyId())) {
                queryWrapper.lambda().eq(BoardAnalysisFilter::getPropertyId, boardAnalysisFilterParam.getPropertyId());
            }
            // 根据子级条件逻辑 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisFilterParam.getSubLogic())) {
                queryWrapper.lambda().eq(BoardAnalysisFilter::getSubLogic, boardAnalysisFilterParam.getSubLogic());
            }
            // 根据计算方式 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisFilterParam.getMeasure())) {
                queryWrapper.lambda().eq(BoardAnalysisFilter::getMeasure, boardAnalysisFilterParam.getMeasure());
            }
            // 根据条件值 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisFilterParam.getValue())) {
                queryWrapper.lambda().eq(BoardAnalysisFilter::getValue, boardAnalysisFilterParam.getValue());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardAnalysisFilter> list(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        return this.list();
    }

    @Override
    public void add(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        BoardAnalysisFilter boardAnalysisFilter = new BoardAnalysisFilter();
        BeanUtil.copyProperties(boardAnalysisFilterParam, boardAnalysisFilter);
        this.save(boardAnalysisFilter);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardAnalysisFilterParam> boardAnalysisFilterParamList) {
        boardAnalysisFilterParamList.forEach(boardAnalysisFilterParam -> {
            this.removeById(boardAnalysisFilterParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        BoardAnalysisFilter boardAnalysisFilter = this.queryBoardAnalysisFilter(boardAnalysisFilterParam);
        BeanUtil.copyProperties(boardAnalysisFilterParam, boardAnalysisFilter);
        this.updateById(boardAnalysisFilter);
    }

    @Override
    public BoardAnalysisFilter detail(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        return this.queryBoardAnalysisFilter(boardAnalysisFilterParam);
    }

    /**
     * 获取实时分析条件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:20:35
     */
    private BoardAnalysisFilter queryBoardAnalysisFilter(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        BoardAnalysisFilter boardAnalysisFilter = this.getById(boardAnalysisFilterParam.getId());
        if (ObjectUtil.isNull(boardAnalysisFilter)) {
            throw new ServiceException(BoardAnalysisFilterExceptionEnum.NOT_EXIST);
        }
        return boardAnalysisFilter;
    }

    @Override
    public void export(BoardAnalysisFilterParam boardAnalysisFilterParam) {
        List<BoardAnalysisFilter> list = this.list(boardAnalysisFilterParam);
        PoiUtil.exportExcelWithStream("BoardAnalysisFilter.xls", BoardAnalysisFilter.class, list);
    }

}
