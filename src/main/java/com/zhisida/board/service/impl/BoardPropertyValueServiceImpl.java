
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.entity.BoardPropertyValue;
import com.zhisida.board.enums.BoardPropertyValueExceptionEnum;
import com.zhisida.board.mapper.BoardPropertyValueMapper;
import com.zhisida.board.param.BoardPropertyValueParam;
import com.zhisida.board.service.BoardPropertyValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 属性值service接口实现类
 *
 * @author young-pastor
 * @date 2022-06-20 11:57:45
 */
@Service
public class BoardPropertyValueServiceImpl extends ServiceImpl<BoardPropertyValueMapper, BoardPropertyValue> implements BoardPropertyValueService {

    @Override
    public PageResult<BoardPropertyValue> page(BoardPropertyValueParam boardPropertyValueParam) {
        QueryWrapper<BoardPropertyValue> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardPropertyValueParam)) {

        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardPropertyValue> list(BoardPropertyValueParam boardPropertyValueParam) {
        return this.list();
    }

    @Override
    public void add(BoardPropertyValueParam boardPropertyValueParam) {
        BoardPropertyValue boardPropertyValue = new BoardPropertyValue();
        BeanUtil.copyProperties(boardPropertyValueParam, boardPropertyValue);
        this.save(boardPropertyValue);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardPropertyValueParam> boardPropertyValueParamList) {
        boardPropertyValueParamList.forEach(boardPropertyValueParam -> {
            this.removeById(boardPropertyValueParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardPropertyValueParam boardPropertyValueParam) {
        BoardPropertyValue boardPropertyValue = this.queryBoardPropertyValue(boardPropertyValueParam);
        BeanUtil.copyProperties(boardPropertyValueParam, boardPropertyValue);
        this.updateById(boardPropertyValue);
    }

    @Override
    public BoardPropertyValue detail(BoardPropertyValueParam boardPropertyValueParam) {
        return this.queryBoardPropertyValue(boardPropertyValueParam);
    }

    /**
     * 获取属性值
     *
     * @author young-pastor
     * @date 2022-06-20 11:57:45
     */
    private BoardPropertyValue queryBoardPropertyValue(BoardPropertyValueParam boardPropertyValueParam) {
        BoardPropertyValue boardPropertyValue = this.getById(boardPropertyValueParam.getId());
        if (ObjectUtil.isNull(boardPropertyValue)) {
            throw new ServiceException(BoardPropertyValueExceptionEnum.NOT_EXIST);
        }
        return boardPropertyValue;
    }

    @Override
    public void export(BoardPropertyValueParam boardPropertyValueParam) {
        List<BoardPropertyValue> list = this.list(boardPropertyValueParam);
        PoiUtil.exportExcelWithStream("Young-BoardBoardPropertyValue.xls", BoardPropertyValue.class, list);
    }

}
