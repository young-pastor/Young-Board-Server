
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.core.consts.SymbolConstant;
import com.zhisida.board.core.context.login.LoginContextHolder;
import com.zhisida.board.core.enums.CommonStatusEnum;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.factory.TreeBuildFactory;
import com.zhisida.board.core.pojo.node.AntdBaseTreeNode;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.entity.BoardEventGroup;
import com.zhisida.board.entity.SysOrg;
import com.zhisida.board.enums.BoardEventGroupExceptionEnum;
import com.zhisida.board.mapper.BoardEventGroupMapper;
import com.zhisida.board.param.BoardEventGroupParam;
import com.zhisida.board.param.SysOrgParam;
import com.zhisida.board.service.BoardEventGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 元事件分组service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:21
 */
@Service
public class BoardEventGroupServiceImpl extends ServiceImpl<BoardEventGroupMapper, BoardEventGroup> implements BoardEventGroupService {

    @Override
    public List<AntdBaseTreeNode> tree(BoardEventGroupParam boardEventGroupParam) {
        List<AntdBaseTreeNode> treeNodeList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<BoardEventGroup> queryWrapper = new LambdaQueryWrapper<>();
        this.list(queryWrapper).forEach(sysOrg -> {
            AntdBaseTreeNode orgTreeNode = new AntdBaseTreeNode();
            orgTreeNode.setId(sysOrg.getId());
            orgTreeNode.setParentId(sysOrg.getPid());
            orgTreeNode.setTitle(sysOrg.getDisplayName());
            orgTreeNode.setValue(String.valueOf(sysOrg.getId()));
            treeNodeList.add(orgTreeNode);
        });
        return new TreeBuildFactory<AntdBaseTreeNode>().doTreeBuild(treeNodeList);
    }
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
        this.fillPids(boardEventGroup);
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

    private void fillPids(BoardEventGroup boardEventGroup) {
        if (boardEventGroup.getPid().equals(0L)) {
            boardEventGroup.setPids(SymbolConstant.LEFT_SQUARE_BRACKETS +
                    0 +
                    SymbolConstant.RIGHT_SQUARE_BRACKETS +
                    SymbolConstant.COMMA);
        } else {
            //获取父组织机构
            BoardEventGroup pSysOrg = this.getById(boardEventGroup.getPid());
            boardEventGroup.setPids(pSysOrg.getPids() +
                    SymbolConstant.LEFT_SQUARE_BRACKETS + pSysOrg.getId() +
                    SymbolConstant.RIGHT_SQUARE_BRACKETS +
                    SymbolConstant.COMMA);
        }
    }
}
