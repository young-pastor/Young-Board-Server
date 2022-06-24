
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.entity.BoardTableConnect;
import com.zhisida.board.enums.BoardTableConnectExceptionEnum;
import com.zhisida.board.mapper.BoardTableConnectMapper;
import com.zhisida.board.param.BoardTableConnectParam;
import com.zhisida.board.service.BoardTableConnectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字段关联配置service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:45:42
 */
@Service
public class BoardTableConnectServiceImpl extends ServiceImpl<BoardTableConnectMapper, BoardTableConnect> implements BoardTableConnectService {

    @Override
    public PageResult<BoardTableConnect> page(BoardTableConnectParam boardTableConnectParam) {
        QueryWrapper<BoardTableConnect> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardTableConnectParam)) {

            // 根据字段ID 查询
            if (ObjectUtil.isNotEmpty(boardTableConnectParam.getColumnId())) {
                queryWrapper.lambda().eq(BoardTableConnect::getColumnId, boardTableConnectParam.getColumnId());
            }
            // 根据关联字段ID 查询
            if (ObjectUtil.isNotEmpty(boardTableConnectParam.getConnectColumnId())) {
                queryWrapper.lambda().eq(BoardTableConnect::getConnectColumnId, boardTableConnectParam.getConnectColumnId());
            }
            // 根据关联类型 查询
            if (ObjectUtil.isNotEmpty(boardTableConnectParam.getConnectType())) {
                queryWrapper.lambda().eq(BoardTableConnect::getConnectType, boardTableConnectParam.getConnectType());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardTableConnect> list(BoardTableConnectParam boardTableConnectParam) {
        return this.list();
    }

    @Override
    public void add(BoardTableConnectParam boardTableConnectParam) {
        BoardTableConnect boardTableConnect = new BoardTableConnect();
        BeanUtil.copyProperties(boardTableConnectParam, boardTableConnect);
        this.save(boardTableConnect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardTableConnectParam> boardTableConnectParamList) {
        boardTableConnectParamList.forEach(boardTableConnectParam -> {
            this.removeById(boardTableConnectParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardTableConnectParam boardTableConnectParam) {
        BoardTableConnect boardTableConnect = this.queryBoardTableConnect(boardTableConnectParam);
        BeanUtil.copyProperties(boardTableConnectParam, boardTableConnect);
        this.updateById(boardTableConnect);
    }

    @Override
    public BoardTableConnect detail(BoardTableConnectParam boardTableConnectParam) {
        return this.queryBoardTableConnect(boardTableConnectParam);
    }

    /**
     * 获取字段关联配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:45:42
     */
    private BoardTableConnect queryBoardTableConnect(BoardTableConnectParam boardTableConnectParam) {
        BoardTableConnect boardTableConnect = this.getById(boardTableConnectParam.getId());
        if (ObjectUtil.isNull(boardTableConnect)) {
            throw new ServiceException(BoardTableConnectExceptionEnum.NOT_EXIST);
        }
        return boardTableConnect;
    }

    @Override
    public void export(BoardTableConnectParam boardTableConnectParam) {
        List<BoardTableConnect> list = this.list(boardTableConnectParam);
        PoiUtil.exportExcelWithStream("BoardTableConnect.xls", BoardTableConnect.class, list);
    }

}
