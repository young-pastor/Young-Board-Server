package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.entity.BoardAnalysisProperty;
import com.zhisida.board.enums.BoardAnalysisPropertyExceptionEnum;
import com.zhisida.board.mapper.BoardAnalysisPropertyMapper;
import com.zhisida.board.param.BoardAnalysisPropertyParam;
import com.zhisida.board.service.BoardAnalysisPropertyService;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实时分析属性表service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:05
 */
@Service
public class BoardAnalysisPropertyServiceImpl extends ServiceImpl<BoardAnalysisPropertyMapper, BoardAnalysisProperty> implements BoardAnalysisPropertyService {

    @Override
    public PageResult<BoardAnalysisProperty> page(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        QueryWrapper<BoardAnalysisProperty> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardAnalysisPropertyParam)) {

            // 根据属性ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisPropertyParam.getPropertyId())) {
                queryWrapper.lambda().eq(BoardAnalysisProperty::getPropertyId, boardAnalysisPropertyParam.getPropertyId());
            }
            // 根据 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisPropertyParam.getAnalysisEventId())) {
                queryWrapper.lambda().eq(BoardAnalysisProperty::getAnalysisEventId, boardAnalysisPropertyParam.getAnalysisEventId());
            }
            // 根据排序 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisPropertyParam.getSort())) {
                queryWrapper.lambda().eq(BoardAnalysisProperty::getSort, boardAnalysisPropertyParam.getSort());
            }
            // 根据 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisPropertyParam.getSubLogic())) {
                queryWrapper.lambda().eq(BoardAnalysisProperty::getSubLogic, boardAnalysisPropertyParam.getSubLogic());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardAnalysisProperty> list(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        return this.list();
    }

    @Override
    public void add(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        BoardAnalysisProperty boardAnalysisProperty = new BoardAnalysisProperty();
        BeanUtil.copyProperties(boardAnalysisPropertyParam, boardAnalysisProperty);
        this.save(boardAnalysisProperty);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardAnalysisPropertyParam> boardAnalysisPropertyParamList) {
        boardAnalysisPropertyParamList.forEach(boardAnalysisPropertyParam -> {
            this.removeById(boardAnalysisPropertyParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        BoardAnalysisProperty boardAnalysisProperty = this.queryBoardAnalysisProperty(boardAnalysisPropertyParam);
        BeanUtil.copyProperties(boardAnalysisPropertyParam, boardAnalysisProperty);
        this.updateById(boardAnalysisProperty);
    }

    @Override
    public BoardAnalysisProperty detail(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        return this.queryBoardAnalysisProperty(boardAnalysisPropertyParam);
    }

    /**
     * 获取实时分析属性表
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:40:05
     */
    private BoardAnalysisProperty queryBoardAnalysisProperty(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        BoardAnalysisProperty boardAnalysisProperty = this.getById(boardAnalysisPropertyParam.getId());
        if (ObjectUtil.isNull(boardAnalysisProperty)) {
            throw new ServiceException(BoardAnalysisPropertyExceptionEnum.NOT_EXIST);
        }
        return boardAnalysisProperty;
    }

    @Override
    public void export(BoardAnalysisPropertyParam boardAnalysisPropertyParam) {
        List<BoardAnalysisProperty> list = this.list(boardAnalysisPropertyParam);
        PoiUtil.exportExcelWithStream("BoardAnalysisProperty.xls", BoardAnalysisProperty.class, list);
    }

}
