
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.mapper.BoardAnalysisEventMapper;
import com.zhisida.board.param.BoardAnalysisEventParam;
import com.zhisida.board.service.BoardAnalysisEventService;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import com.zhisida.board.entity.BoardAnalysisEvent;
import com.zhisida.board.enums.BoardAnalysisEventExceptionEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实时分析事件service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:27:10
 */
@Service
public class BoardAnalysisEventServiceImpl extends ServiceImpl<BoardAnalysisEventMapper, BoardAnalysisEvent> implements BoardAnalysisEventService {

    @Override
    public PageResult<BoardAnalysisEvent> page(BoardAnalysisEventParam boardAnalysisEventParam) {
        QueryWrapper<BoardAnalysisEvent> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardAnalysisEventParam)) {

            // 根据实时分析ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisEventParam.getAnalysisId())) {
                queryWrapper.lambda().eq(BoardAnalysisEvent::getAnalysisId, boardAnalysisEventParam.getAnalysisId());
            }
            // 根据事件ID 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisEventParam.getEventId())) {
                queryWrapper.lambda().eq(BoardAnalysisEvent::getEventId, boardAnalysisEventParam.getEventId());
            }
            // 根据排序 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisEventParam.getSort())) {
                queryWrapper.lambda().eq(BoardAnalysisEvent::getSort, boardAnalysisEventParam.getSort());
            }
            // 根据 查询
            if (ObjectUtil.isNotEmpty(boardAnalysisEventParam.getSubLogic())) {
                queryWrapper.lambda().eq(BoardAnalysisEvent::getSubLogic, boardAnalysisEventParam.getSubLogic());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardAnalysisEvent> list(BoardAnalysisEventParam boardAnalysisEventParam) {
        return this.list();
    }

    @Override
    public void add(BoardAnalysisEventParam boardAnalysisEventParam) {
        BoardAnalysisEvent boardAnalysisEvent = new BoardAnalysisEvent();
        BeanUtil.copyProperties(boardAnalysisEventParam, boardAnalysisEvent);
        this.save(boardAnalysisEvent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardAnalysisEventParam> boardAnalysisEventParamList) {
        boardAnalysisEventParamList.forEach(boardAnalysisEventParam -> {
            this.removeById(boardAnalysisEventParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardAnalysisEventParam boardAnalysisEventParam) {
        BoardAnalysisEvent boardAnalysisEvent = this.queryBoardAnalysisEvent(boardAnalysisEventParam);
        BeanUtil.copyProperties(boardAnalysisEventParam, boardAnalysisEvent);
        this.updateById(boardAnalysisEvent);
    }

    @Override
    public BoardAnalysisEvent detail(BoardAnalysisEventParam boardAnalysisEventParam) {
        return this.queryBoardAnalysisEvent(boardAnalysisEventParam);
    }

    /**
     * 获取实时分析事件
     *
     * @author Young-Pastor
     * @date 2022-07-08 14:27:10
     */
    private BoardAnalysisEvent queryBoardAnalysisEvent(BoardAnalysisEventParam boardAnalysisEventParam) {
        BoardAnalysisEvent boardAnalysisEvent = this.getById(boardAnalysisEventParam.getId());
        if (ObjectUtil.isNull(boardAnalysisEvent)) {
            throw new ServiceException(BoardAnalysisEventExceptionEnum.NOT_EXIST);
        }
        return boardAnalysisEvent;
    }

    @Override
    public void export(BoardAnalysisEventParam boardAnalysisEventParam) {
        List<BoardAnalysisEvent> list = this.list(boardAnalysisEventParam);
        PoiUtil.exportExcelWithStream("BoardAnalysisEvent.xls", BoardAnalysisEvent.class, list);
    }

}
