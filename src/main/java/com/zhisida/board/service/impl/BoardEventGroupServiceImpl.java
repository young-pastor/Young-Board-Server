
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.entity.BoardEventGroup;
import com.zhisida.board.enums.BoardEventGroupExceptionEnum;
import com.zhisida.board.mapper.BoardEventGroupMapper;
import com.zhisida.board.param.BoardEventGroupParam;
import com.zhisida.board.service.BoardEventGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 元事件分组service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:21
 */
@Service
public class BoardEventGroupServiceImpl extends ServiceImpl<BoardEventGroupMapper, BoardEventGroup> implements BoardEventGroupService {

    @Override
    public PageResult<BoardEventGroup> page(BoardEventGroupParam boardEventGroupParam) {
        QueryWrapper<BoardEventGroup> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardEventGroupParam)) {

            // 根据事件名称 查询
            if (ObjectUtil.isNotEmpty(boardEventGroupParam.getDisplayName())) {
                queryWrapper.lambda().eq(BoardEventGroup::getDisplayName, boardEventGroupParam.getDisplayName());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardEventGroup> list(BoardEventGroupParam boardEventGroupParam) {
        return this.list();
    }

    @Override
    public void add(BoardEventGroupParam boardEventGroupParam) {
        BoardEventGroup boardEventGroup = new BoardEventGroup();
        BeanUtil.copyProperties(boardEventGroupParam, boardEventGroup);
        this.save(boardEventGroup);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardEventGroupParam> boardEventGroupParamList) {
        boardEventGroupParamList.forEach(boardEventGroupParam -> {
            this.removeById(boardEventGroupParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardEventGroupParam boardEventGroupParam) {
        BoardEventGroup boardEventGroup = this.queryBoardEventGroup(boardEventGroupParam);
        BeanUtil.copyProperties(boardEventGroupParam, boardEventGroup);
        this.updateById(boardEventGroup);
    }

    @Override
    public BoardEventGroup detail(BoardEventGroupParam boardEventGroupParam) {
        return this.queryBoardEventGroup(boardEventGroupParam);
    }

    /**
     * 获取元事件分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:21
     */
    private BoardEventGroup queryBoardEventGroup(BoardEventGroupParam boardEventGroupParam) {
        BoardEventGroup boardEventGroup = this.getById(boardEventGroupParam.getId());
        if (ObjectUtil.isNull(boardEventGroup)) {
            throw new ServiceException(BoardEventGroupExceptionEnum.NOT_EXIST);
        }
        return boardEventGroup;
    }

    @Override
    public void export(BoardEventGroupParam boardEventGroupParam) {
        List<BoardEventGroup> list = this.list(boardEventGroupParam);
        PoiUtil.exportExcelWithStream("Young-BoardBoardEventGroup.xls", BoardEventGroup.class, list);
    }

}
