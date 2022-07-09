package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.entity.BoardPropertyGroup;
import com.zhisida.board.enums.BoardPropertyGroupExceptionEnum;
import com.zhisida.board.mapper.BoardPropertyGroupMapper;
import com.zhisida.board.param.BoardEventGroupParam;
import com.zhisida.board.param.BoardPropertyGroupParam;
import com.zhisida.board.service.BoardPropertyGroupService;
import com.zhisida.core.consts.SymbolConstant;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.factory.TreeBuildFactory;
import com.zhisida.core.pojo.node.AntdBaseTreeNode;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 属性分组service接口实现类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:37
 */
@Service
public class BoardPropertyGroupServiceImpl extends ServiceImpl<BoardPropertyGroupMapper, BoardPropertyGroup> implements BoardPropertyGroupService {

    @Override
    public List<AntdBaseTreeNode> tree(BoardEventGroupParam boardEventGroupParam) {
        List<AntdBaseTreeNode> treeNodeList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<BoardPropertyGroup> queryWrapper = new LambdaQueryWrapper<>();
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
    public PageResult<BoardPropertyGroup> page(BoardPropertyGroupParam boardPropertyGroupParam) {
        QueryWrapper<BoardPropertyGroup> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(boardPropertyGroupParam)) {

            // 根据分组名称 查询
            if (ObjectUtil.isNotEmpty(boardPropertyGroupParam.getDisplayName())) {
                queryWrapper.lambda().eq(BoardPropertyGroup::getDisplayName, boardPropertyGroupParam.getDisplayName());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<BoardPropertyGroup> list(BoardPropertyGroupParam boardPropertyGroupParam) {
        return this.list();
    }

    @Override
    public void add(BoardPropertyGroupParam boardPropertyGroupParam) {
        BoardPropertyGroup boardPropertyGroup = new BoardPropertyGroup();
        BeanUtil.copyProperties(boardPropertyGroupParam, boardPropertyGroup);
        this.fillPids(boardPropertyGroup);
        this.save(boardPropertyGroup);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BoardPropertyGroupParam> boardPropertyGroupParamList) {
        boardPropertyGroupParamList.forEach(boardPropertyGroupParam -> {
            this.removeById(boardPropertyGroupParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BoardPropertyGroupParam boardPropertyGroupParam) {
        BoardPropertyGroup boardPropertyGroup = this.queryBoardPropertyGroup(boardPropertyGroupParam);
        BeanUtil.copyProperties(boardPropertyGroupParam, boardPropertyGroup);
        this.updateById(boardPropertyGroup);
    }

    @Override
    public BoardPropertyGroup detail(BoardPropertyGroupParam boardPropertyGroupParam) {
        return this.queryBoardPropertyGroup(boardPropertyGroupParam);
    }

    /**
     * 获取属性分组
     *
     * @author Young-Pastor
     * @date 2022-06-20 11:57:37
     */
    private BoardPropertyGroup queryBoardPropertyGroup(BoardPropertyGroupParam boardPropertyGroupParam) {
        BoardPropertyGroup boardPropertyGroup = this.getById(boardPropertyGroupParam.getId());
        if (ObjectUtil.isNull(boardPropertyGroup)) {
            throw new ServiceException(BoardPropertyGroupExceptionEnum.NOT_EXIST);
        }
        return boardPropertyGroup;
    }

    @Override
    public void export(BoardPropertyGroupParam boardPropertyGroupParam) {
        List<BoardPropertyGroup> list = this.list(boardPropertyGroupParam);
        PoiUtil.exportExcelWithStream("BoardPropertyGroup.xls", BoardPropertyGroup.class, list);
    }

    private void fillPids(BoardPropertyGroup boardPropertyGroup) {
        if (boardPropertyGroup.getPid().equals(0L)) {
            boardPropertyGroup.setPids(SymbolConstant.LEFT_SQUARE_BRACKETS +
                    0 +
                    SymbolConstant.RIGHT_SQUARE_BRACKETS +
                    SymbolConstant.COMMA);
        } else {
            //获取父组织机构
            BoardPropertyGroup pBoardPropertyGroup = this.getById(boardPropertyGroup.getPid());
            boardPropertyGroup.setPids(pBoardPropertyGroup.getPids() +
                    SymbolConstant.LEFT_SQUARE_BRACKETS + pBoardPropertyGroup.getId() +
                    SymbolConstant.RIGHT_SQUARE_BRACKETS +
                    SymbolConstant.COMMA);
        }
    }
}
