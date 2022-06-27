
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.factory.TreeBuildFactory;
import com.zhisida.board.core.pojo.node.AntdBaseTreeNode;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.entity.BoardEvent;
import com.zhisida.board.enums.BoardEventExceptionEnum;
import com.zhisida.board.mapper.BoardEventMapper;
import com.zhisida.board.param.BoardEventParam;
import com.zhisida.board.service.BoardEventGroupService;
import com.zhisida.board.service.BoardEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 元事件配置service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:17
 */
@Service
public class BoardEventServiceImpl extends ServiceImpl<BoardEventMapper, BoardEvent> implements BoardEventService {
    @Resource
    BoardEventGroupService eventGroupService;

    @Override
    public List<AntdBaseTreeNode> tree(BoardEventParam boardEventParam) {
        List<AntdBaseTreeNode> treeNodeList = CollectionUtil.newArrayList();
        eventGroupService.list().forEach(sysOrg -> {
            AntdBaseTreeNode orgTreeNode = new AntdBaseTreeNode();
            orgTreeNode.setId(sysOrg.getId());
            orgTreeNode.setParentId(sysOrg.getPid());
            orgTreeNode.setTitle(sysOrg.getDisplayName());
            orgTreeNode.setValue(String.valueOf(sysOrg.getId()));
            treeNodeList.add(orgTreeNode);
        });
        this.list().forEach(sysOrg -> {
            AntdBaseTreeNode orgTreeNode = new AntdBaseTreeNode();
            orgTreeNode.setId(sysOrg.getId());
            orgTreeNode.setParentId(sysOrg.getEventGroupId());
            orgTreeNode.setTitle(sysOrg.getDisplayName());
            orgTreeNode.setValue(String.valueOf(sysOrg.getId()));
            treeNodeList.add(orgTreeNode);
        });
        return new TreeBuildFactory<AntdBaseTreeNode>().doTreeBuild(treeNodeList);
    }

    @Override
    public PageResult<BoardEvent> page(BoardEventParam boardEventParam) {
        QueryWrapper<BoardEvent> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardEventParam)) {

            // 根据事件分组 查询
            if (ObjectUtil.isNotEmpty(boardEventParam.getEventGroupId())) {
                queryWrapper.lambda().eq(BoardEvent::getEventGroupId, boardEventParam.getEventGroupId());
            }
            // 根据事件名称 查询
            if (ObjectUtil.isNotEmpty(boardEventParam.getDisplayName())) {
                queryWrapper.lambda().eq(BoardEvent::getDisplayName, boardEventParam.getDisplayName());
            }
            // 根据表字段ID 查询
            if (ObjectUtil.isNotEmpty(boardEventParam.getTableColumnId())) {
                queryWrapper.lambda().eq(BoardEvent::getTableColumnId, boardEventParam.getTableColumnId());
            }
            // 根据计算方式 查询
            if (ObjectUtil.isNotEmpty(boardEventParam.getMeasure())) {
                queryWrapper.lambda().eq(BoardEvent::getMeasure, boardEventParam.getMeasure());
            }
            // 根据事件值 查询
            if (ObjectUtil.isNotEmpty(boardEventParam.getValue())) {
                queryWrapper.lambda().eq(BoardEvent::getValue, boardEventParam.getValue());
            }
            // 根据事件值类型 查询
            if (ObjectUtil.isNotEmpty(boardEventParam.getValueType())) {
                queryWrapper.lambda().eq(BoardEvent::getValueType, boardEventParam.getValueType());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardEvent> list(BoardEventParam boardEventParam) {
        return this.list();
    }

    @Override
    public void add(BoardEventParam boardEventParam) {
        BoardEvent boardEvent = new BoardEvent();
        BeanUtil.copyProperties(boardEventParam, boardEvent);
        this.save(boardEvent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardEventParam> boardEventParamList) {
        boardEventParamList.forEach(boardEventParam -> {
            this.removeById(boardEventParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardEventParam boardEventParam) {
        BoardEvent boardEvent = this.queryBoardEvent(boardEventParam);
        BeanUtil.copyProperties(boardEventParam, boardEvent);
        this.updateById(boardEvent);
    }

    @Override
    public BoardEvent detail(BoardEventParam boardEventParam) {
        return this.queryBoardEvent(boardEventParam);
    }

    /**
     * 获取元事件配置
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:52:17
     */
    private BoardEvent queryBoardEvent(BoardEventParam boardEventParam) {
        BoardEvent boardEvent = this.getById(boardEventParam.getId());
        if (ObjectUtil.isNull(boardEvent)) {
            throw new ServiceException(BoardEventExceptionEnum.NOT_EXIST);
        }
        return boardEvent;
    }

    @Override
    public void export(BoardEventParam boardEventParam) {
        List<BoardEvent> list = this.list(boardEventParam);
        PoiUtil.exportExcelWithStream("BoardEvent.xls", BoardEvent.class, list);
    }


}
